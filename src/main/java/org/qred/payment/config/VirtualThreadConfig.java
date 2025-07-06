package org.qred.payment.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.coyote.ProtocolHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VirtualThreadConfig {

    @Value("${use.virtual.threads:false}")
    private boolean useVirtualThreads;

    @Bean
    public Executor taskExecutor() {
        return useVirtualThreads
                ? Executors.newVirtualThreadPerTaskExecutor()
                : Executors.newCachedThreadPool(); // fallback or standard thread pool
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> tomcatVirtualThreadCustomizer() {
        return factory -> {
            if (useVirtualThreads && factory instanceof TomcatServletWebServerFactory tomcatFactory) {
                tomcatFactory.addConnectorCustomizers(connector -> {
                    ProtocolHandler handler = connector.getProtocolHandler();
                    handler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
                });
            }
        };
    }
}
