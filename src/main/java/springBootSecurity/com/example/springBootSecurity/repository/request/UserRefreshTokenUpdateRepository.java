package springBootSecurity.com.example.springBootSecurity.repository.request;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.request.UserRefreshTokenUpdate;

@Repository
public interface UserRefreshTokenUpdateRepository extends CrudRepository<UserRefreshTokenUpdate, Integer> {
}
