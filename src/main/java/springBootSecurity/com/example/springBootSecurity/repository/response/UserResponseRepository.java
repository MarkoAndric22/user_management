package springBootSecurity.com.example.springBootSecurity.repository.response;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.response.UserResponse;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserResponseRepository extends CrudRepository<UserResponse, Integer> {

  Optional<UserResponse> findByUserNameAndStatusIsTrueOrEmailAndStatusIsTrue(String user, String email);

  Optional<UserResponse> findByRefreshTokenAndStatusIsTrue(String refreshToken);

  Optional<UserResponse> findByUserName(String user);

  Optional<UserResponse> findByEmail(String email);

  List<UserResponse> findByRoleIdGreaterThan(Integer roleId);
}
