package springBootSecurity.com.example.springBootSecurity.repository.response;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.response.EndpointResponse;

@Repository
public interface EndpointResponseRepository extends CrudRepository<EndpointResponse, String> {
}
