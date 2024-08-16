package springBootSecurity.com.example.springBootSecurity.repository.response;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.response.RoleResponse;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleResponseRepository extends CrudRepository<RoleResponse, Integer> {

  Optional<RoleResponse> findByName(String name);

  Set<RoleResponse> findByIdGreaterThan(Integer id);
}
