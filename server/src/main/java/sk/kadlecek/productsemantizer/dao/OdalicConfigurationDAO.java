package sk.kadlecek.productsemantizer.dao;

import org.springframework.data.repository.CrudRepository;
import sk.kadlecek.productsemantizer.bean.OdalicConfiguration;

public interface OdalicConfigurationDAO extends CrudRepository<OdalicConfiguration, Long> {

}
