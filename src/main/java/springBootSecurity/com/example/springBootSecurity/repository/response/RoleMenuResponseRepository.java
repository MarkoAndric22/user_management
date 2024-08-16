package springBootSecurity.com.example.springBootSecurity.repository.response;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.response.RoleMenuResponse;

import java.util.Optional;

@Repository
public interface RoleMenuResponseRepository extends CrudRepository<RoleMenuResponse, Integer> {

  Optional<RoleMenuResponse> findByRoleIdAndMenuId(Integer roleId, Integer menuId);
}
