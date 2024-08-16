package springBootSecurity.com.example.springBootSecurity.repository.response;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.response.RoleEndpointsResponse;

import java.util.Optional;

@Repository
public interface RoleEndpointsResponseRepository extends CrudRepository<RoleEndpointsResponse, Integer> {

  Optional<RoleEndpointsResponse> findByRoleIdAndEndpointId(Integer roleId, String endpointId);
}
