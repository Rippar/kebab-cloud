package by.murzo.kebabcloud.data;

import by.murzo.kebabcloud.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
