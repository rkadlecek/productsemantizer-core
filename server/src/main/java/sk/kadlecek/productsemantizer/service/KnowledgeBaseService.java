package sk.kadlecek.productsemantizer.service;

import sk.kadlecek.productsemantizer.bean.KnowledgeBase;
import sk.kadlecek.productsemantizer.exception.DatabaseException;

import java.util.List;
import java.util.Set;

public interface KnowledgeBaseService {

    List<KnowledgeBase> findAllActive() throws DatabaseException;
    void updateListOfSupportedKnowledgeBases(Set<KnowledgeBase> supportedBases);
}
