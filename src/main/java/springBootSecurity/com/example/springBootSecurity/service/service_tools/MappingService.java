package springBootSecurity.com.example.springBootSecurity.service.service_tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.modelTools.Mapping;
import springBootSecurity.com.example.springBootSecurity.model.modelTools.MappingServicesWrapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MappingService {

  private final AppProperties appProperties;
  private final RestTemplate restTemplate;

  @Value("${spring.profiles.active}")
  private String activeProfile;

  /**
   * Get all Routes.
   *
   * @param appName application name
   * @return MappingServicesWrapper
   */
  public MappingServicesWrapper getMapping(String appName) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBasicAuth(appProperties.getGlobalUser(), appProperties.getGlobalPassword());
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<String> res =
      restTemplate.exchange(currentUrl() + "/actuator/mappings", HttpMethod.GET, entity, String.class);
    if (res.getStatusCode() != HttpStatus.OK || !res.hasBody() || res.getBody() == null) {
      return null;
    }
    return mappingServices(res.getBody(), appName);
  }

  /**
   * Return endpoint from request.
   *
   * @param request - HttpServletRequest
   * @return String
   */
  public String endpoint(HttpServletRequest request) {
    return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
  }

  /**
   * Get current url.
   *
   * @return String
   */
  private String currentUrl() {
    return activeProfile.equals("local")
      ? "http://localhost:8080"
      : "http://localhost:8080/" + appProperties.getMsApplicationName();
  }

  /**
   * Actuator mapper.
   *
   * @param body    body
   * @param appName appName
   * @return MappingServicesWrapper
   */
  private MappingServicesWrapper mappingServices(String body, String appName) {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualObj;
    try {
      actualObj = mapper.readTree(body);
    } catch (JsonProcessingException e) {
      throw new CustomException(e.getMessage());
    }
    if (actualObj == null) {
      return null;
    }
    JsonNode contexts =
      actualObj
        .get("contexts")
        .get(appName)
        .get("mappings")
        .get("dispatcherServlets")
        .get("dispatcherServlet");
    int routesSize = contexts.size();

    MappingServicesWrapper mappingServicesWrapper = new MappingServicesWrapper();
    List<Mapping> resultList = new LinkedList<>();
    List<String> servicesList = new LinkedList<>();

    int i = 0;
    while (i < routesSize) {
      JsonNode contexts2 = contexts.get(i).get("details");
      if (!contexts2.isNull()) {
        JsonNode contexts3 = contexts2.get("requestMappingConditions");
        JsonNode contexts4 = contexts3.get("methods");
        JsonNode contexts5 = contexts3.get("patterns");
        JsonNode contexts33 = contexts2.get("handlerMethod");
        JsonNode contexts55 = contexts33.get("className");
        String[] splitClass = contexts55.asText().split("\\.");
        String service = splitClass[3];
        String controllerName =
          contexts55.asText().substring(contexts55.asText().lastIndexOf('.') + 1).trim();
        if (!contexts4.isEmpty() && !contexts5.isEmpty()) {
          int contexts5Size = contexts5.size();
          int a = 0;
          while (a < contexts5Size) {
            String method = contexts4.get(0).asText();
            String patterns = contexts5.get(a).asText();
            if (!patterns.contains("/actuator")
              && !Arrays.asList(appProperties.getExcludedPackets()).contains(service)) {
              String controllerHash =
                DigestUtils.md5Hex(appProperties.getMsApplicationName() + "_" + controllerName);
              String pathHash =
                DigestUtils.md5Hex(
                  appProperties.getMsApplicationName() + "_" + controllerName + "_" + patterns);
              Mapping mapping = new Mapping();
              mapping.setService(service);
              mapping.setController(controllerName);
              mapping.setControllerHash(controllerHash);
              mapping.setMethod(method);
              mapping.setPath(patterns);
              mapping.setPathHash(pathHash);
              if (!servicesList.contains(service)) {
                servicesList.add(service);
              }
              resultList.add(mapping);
            }
            a++;
          }
        }
      }
      i++;
    }

    mappingServicesWrapper.setServices(servicesList);
    mappingServicesWrapper.setMappings(resultList);
    return mappingServicesWrapper;
  }
}
