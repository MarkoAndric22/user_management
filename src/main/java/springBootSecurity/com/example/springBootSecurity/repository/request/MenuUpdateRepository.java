package springBootSecurity.com.example.springBootSecurity.repository.request;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBootSecurity.com.example.springBootSecurity.model.request.MenuUpdate;

@Repository
public interface MenuUpdateRepository extends CrudRepository<MenuUpdate, Integer> {
}
