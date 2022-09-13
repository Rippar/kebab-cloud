package by.murzo.kebabcloud.data;

import by.murzo.kebabcloud.model.KebabOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<KebabOrder, Long> {

}
