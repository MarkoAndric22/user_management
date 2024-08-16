package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.dto.RoleEndpointsDto;
import springBootSecurity.com.example.springBootSecurity.model.request.RoleEndpoints;
import springBootSecurity.com.example.springBootSecurity.model.response.RoleEndpointsResponse;
import springBootSecurity.com.example.springBootSecurity.repository.request.EndpointRepository;
import springBootSecurity.com.example.springBootSecurity.repository.request.RoleEndpointsRepository;
import springBootSecurity.com.example.springBootSecurity.repository.request.RoleRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.EndpointResponseRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.RoleEndpointsResponseRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleEndpointsService {

  private final RoleEndpointsRepository roleEndpointsRepository;
  private final RoleEndpointsResponseRepository roleEndpointsResponseRepository;
  private final RoleRepository roleRepository;
  private final EndpointRepository endpointRepository;
  private final EndpointResponseRepository endpointResponseRepository;

  @Transactional(rollbackFor = {Exception.class, CustomException.class})
  public ResponseEntity<String> create(RoleEndpointsDto models) {
    if (!roleRepository.existsById(models.getRoleId())) {
      throw new CustomException("Role with ID `" + models.getRoleId() + "` not exists.");
    }

    List<String> ep = models.getEndpointId();
    ep.forEach(e -> {
      if (!endpointRepository.existsById(e)) {
        throw new CustomException("Endpoint with ID `" + e + "` not exists.");
      }
    });

    List<RoleEndpoints> list = new LinkedList<>();
    ep.forEach(e -> {
      Optional<RoleEndpointsResponse> check = roleEndpointsResponseRepository.findByRoleIdAndEndpointId(models.getRoleId(), e);
      if (check.isEmpty()) {
        RoleEndpoints model = new RoleEndpoints();
        model.setRoleId(models.getRoleId());
        model.setEndpointId(e);
        list.add(model);
      }
    });
    if (list.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    try {
      roleEndpointsRepository.saveAll(list);
      return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  public ResponseEntity<HttpStatus> delete(List<Integer> ids) {
    try {
      roleEndpointsRepository.deleteAllById(ids);
      return ResponseEntity.ok(HttpStatus.OK);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  public boolean checkPermission(Integer roleId, String endpointId) {
    Optional<RoleEndpoints> check = roleEndpointsRepository.findByRoleIdAndEndpointId(roleId, endpointId);
    return check.isPresent();
  }
}
