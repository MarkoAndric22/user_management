package springBootSecurity.com.example.springBootSecurity.repository.request;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.request.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  List<User> findByRoleIdGreaterThan(Integer roleId);
}
