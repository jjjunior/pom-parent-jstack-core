package br.com.jstack.autoconfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@EnableConfigurationProperties({ JStackTomcatConnectorProperties.class })
@Slf4j
@AutoConfiguration
public class JStackTomcatConnectorAutoConfiguration {

    private final JStackTomcatConnectorProperties JStackTomcatConnectorProperties;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
                var handler = (AbstractHttp11Protocol<?>) connector.getProtocolHandler();

                log.info("::: Loading Configuration of Timeout of JStack Tomcat :::");

                if (JStackTomcatConnectorProperties.getConnectionUploadTimeout() >= 0) {
                    handler.setConnectionUploadTimeout(JStackTomcatConnectorProperties.getConnectionUploadTimeout());
                    log.info("jstack.tomcat.connector.connection-upload-timeout: {}", JStackTomcatConnectorProperties.getConnectionUploadTimeout());
                }

                if (JStackTomcatConnectorProperties.getConnectionTimeout() >= 0) {
                    handler.setConnectionTimeout(JStackTomcatConnectorProperties.getConnectionTimeout());
                    log.info("jstack.tomcat.connector.connection-timeout:        {}", JStackTomcatConnectorProperties.getConnectionTimeout());
                }

                if (JStackTomcatConnectorProperties.getKeepAliveTimeout() >= 0) {
                    log.info("jstack.tomcat.connector.keep-alive-timeout:        {}", JStackTomcatConnectorProperties.getKeepAliveTimeout());
                    handler.setKeepAliveTimeout(JStackTomcatConnectorProperties.getKeepAliveTimeout());
                }

                if (JStackTomcatConnectorProperties.getDisableUploadTimeout() != null) {
                    log.info("jstack.tomcat.connector.disable-upload-timeout:    {}", JStackTomcatConnectorProperties.getDisableUploadTimeout());
                    handler.setDisableUploadTimeout(JStackTomcatConnectorProperties.getDisableUploadTimeout());
                }

                log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            }
        });
    }

}
