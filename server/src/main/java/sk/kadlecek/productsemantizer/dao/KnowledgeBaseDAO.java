package sk.kadlecek.productsemantizer.dao;

import org.springframework.data.repository.CrudRepository;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;

public interface KnowledgeBaseDAO extends CrudRepository<KnowledgeBase, Long> {

    Iterable<KnowledgeBase> findByActive(Boolean active);

}
