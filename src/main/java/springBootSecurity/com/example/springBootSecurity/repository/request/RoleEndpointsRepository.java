package springBootSecurity.com.example.springBootSecurity.repository.request;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.request.RoleEndpoints;

import java.util.Optional;

@Repository
public interface RoleEndpointsRepository extends CrudRepository<RoleEndpoints, Integer> {

  Optional<RoleEndpoints> findByRoleIdAndEndpointId(Integer roleId, String endpointId);
}
