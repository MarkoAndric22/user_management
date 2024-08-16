package springBootSecurity.com.example.springBootSecurity.configuration;

import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {

  private final TLS[] protocols = new TLS[]{
    TLS.V_1_0,
    TLS.V_1_1,
    TLS.V_1_2,
    TLS.V_1_3
  };

  /**
   * Rest template SSL configuration.
   *
   * @return RestTemplate
   */
  @Bean
  public RestTemplate restTemplate() {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    SSLContext sslContext;
    try {
      sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
    } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
      throw new CustomException(e.getMessage());
    }

    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
      .setSSLSocketFactory(csf)
      .setDefaultTlsConfig(TlsConfig.custom()
        .setHandshakeTimeout(Timeout.ofSeconds(30))
        .setSupportedProtocols(protocols)
        .build())
      .build();

    CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpclient);

    return new RestTemplate(requestFactory);
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
