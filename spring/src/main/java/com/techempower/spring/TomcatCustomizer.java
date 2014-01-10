package com.techempower.spring;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class TomcatCustomizer implements EmbeddedServletContainerCustomizer {

	@Value("${tomcat.connector.maxThreads}")
	private int maxThreads;

	@Value("${tomcat.connector.connectionTimeout}")
	private int connectionTimeout;

	@Value("${tomcat.connector.maxConnections}")
	private int maxConnections;

	@Override
	public void customize(ConfigurableEmbeddedServletContainerFactory factory) {

		customizeTomcatConnector((TomcatEmbeddedServletContainerFactory) factory);
	}

	private void customizeTomcatConnector(TomcatEmbeddedServletContainerFactory factory) {

		factory.addConnectorCustomizers(
				new TomcatConnectorCustomizer() {
					@Override
					public void customize(Connector connector) {
						ProtocolHandler handler = connector.getProtocolHandler();
						if (handler instanceof AbstractProtocol) {
							AbstractProtocol protocol = (AbstractProtocol) handler;
							protocol.setMaxThreads(maxThreads);
							protocol.setConnectionTimeout(connectionTimeout);
							protocol.setMaxConnections(maxConnections);
						}
					}
				}
		);
	}
}
