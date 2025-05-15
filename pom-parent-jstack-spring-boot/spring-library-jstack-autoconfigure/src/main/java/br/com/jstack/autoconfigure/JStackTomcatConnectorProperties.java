package br.com.jstack.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "atlante.tomcat.connector")
public class JStackTomcatConnectorProperties {
    private Integer connectionTimeout = -1;

    private Integer connectionUploadTimeout = -1;

    private Boolean disableUploadTimeout = null;

    private Integer keepAliveTimeout = -1;
}
