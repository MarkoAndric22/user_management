package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.modelTools.Mapping;
import springBootSecurity.com.example.springBootSecurity.model.modelTools.MappingServicesWrapper;
import springBootSecurity.com.example.springBootSecurity.model.request.Endpoint;
import springBootSecurity.com.example.springBootSecurity.model.response.EndpointResponse;
import springBootSecurity.com.example.springBootSecurity.repository.request.EndpointRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.EndpointResponseRepository;
import springBootSecurity.com.example.springBootSecurity.service.service_tools.MappingService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EndpointService {

  private final EndpointRepository repo;
  private final EndpointResponseRepository repoResp;
  private final MappingService mappingService;

  private final AppProperties appProperties;

  /**
   * Populate endpoints.
   */
  @Transactional(rollbackFor = {Exception.class, CustomException.class})
  public void populate() {

    // List for mapping apps
    List<String> services = new LinkedList<>();
    List<Mapping> mappings = new LinkedList<>();

    // troter user management APIs
    MappingServicesWrapper troterUmEp = mappingService.getMapping(appProperties.getMsApplicationName());
    if (troterUmEp != null) {
      services.addAll(troterUmEp.getServices());
      mappings.addAll(troterUmEp.getMappings());
    }
    // Collect all
    MappingServicesWrapper endpoints = new MappingServicesWrapper();
    endpoints.setServices(services);
    endpoints.setMappings(mappings);

    if (endpoints.getServices().isEmpty()) {
      return;
    }

    deleteDiffEndpoints(endpoints);

    List<Endpoint> list = populateEndpoints(endpoints);
    if (list.isEmpty()) {
      return;
    }

    try {
      repo.saveAll(list);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Save new endpoint.
   *
   * @param model Endpoint
   * @return EndpointResponse
   */
  public ResponseEntity<EndpointResponse> create(Endpoint model) {
    Optional<EndpointResponse> check = repoResp.findById(model.getId());
    if (check.isPresent()) {
      throw new CustomException("Endpoint with id `" + model.getId() + "` already exists.");
    }
    try {
      Endpoint save = repo.save(model);
      Optional<EndpointResponse> result = repoResp.findById(save.getId());
      return result
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
        .orElseGet(() -> ResponseEntity.noContent().build());
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Populate endpoints.
   *
   * @param endpoints MappingServicesWrapper
   * @return List Endpoint
   */
  private List<Endpoint> populateEndpoints(MappingServicesWrapper endpoints) {
    List<Endpoint> list = new LinkedList<>();
    List<Mapping> mappings = endpoints.getMappings();
    mappings.forEach(arg -> {
      Optional<EndpointResponse> check = repoResp.findById(arg.getPathHash());
      if (check.isEmpty()) {
        Endpoint model = new Endpoint();
        model.setId(arg.getPathHash());
        model.setMethod(arg.getMethod());
        model.setService(arg.getService());
        model.setController(arg.getController());
        model.setControllerAlias(arg.getController().replace("Controller", ""));
        model.setAction(mapMethod(arg.getMethod()));
        model.setEndpoint(arg.getPath());
        list.add(model);
      }
    });
    return list;
  }

  /**
   * Delete different endpoints.
   *
   * @param endpoints MappingServicesWrapper
   */
  private void deleteDiffEndpoints(MappingServicesWrapper endpoints) {
    List<String> list = new LinkedList<>();
    List<Mapping> mappings = endpoints.getMappings();
    mappings.forEach(arg -> list.add(arg.getPathHash()));

    Iterable<Endpoint> iterAll = repo.findAll();

    List<String> existing = new LinkedList<>();
    iterAll.forEach(arg -> existing.add(arg.getId()));

    List<String> diff = new ArrayList<>(existing);
    diff.removeAll(list);
    System.out.println(diff);
    if (!diff.isEmpty()) {
      try {
        repo.deleteAllById(diff);
      } catch (Exception e) {
        throw new CustomException(e.getMessage());
      }
    }
  }

  /**
   * Map method to action.
   *
   * @param method String
   * @return String
   */
  private String mapMethod(String method) {
    Map<String, String> map = new HashMap<>();
    map.put("GET", "view");
    map.put("POST", "create");
    map.put("PUT", "update");
    map.put("PATCH", "update");
    map.put("DELETE", "delete");
    return map.getOrDefault(method, "n/a");
  }
}
