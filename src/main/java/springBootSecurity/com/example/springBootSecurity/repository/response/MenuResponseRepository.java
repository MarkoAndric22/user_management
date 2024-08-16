package springBootSecurity.com.example.springBootSecurity.repository.response;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.response.MenuResponse;

import java.util.Optional;

@Repository
public interface MenuResponseRepository extends CrudRepository<MenuResponse, Integer> {

  Optional<MenuResponse> findByMenuNumber(Integer menuNumber);
}
