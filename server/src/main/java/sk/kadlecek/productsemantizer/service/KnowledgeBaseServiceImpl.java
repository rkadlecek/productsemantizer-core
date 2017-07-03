package sk.kadlecek.productsemantizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;
import sk.kadlecek.productsemantizer.dao.KnowledgeBaseDAO;
import sk.kadlecek.productsemantizer.exception.DatabaseException;
import sk.kadlecek.productsemantizer.util.ConversionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("KnowledgeBaseService")
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final static Logger logger = LoggerFactory.getLogger(KnowledgeBaseServiceImpl.class);
    private final KnowledgeBaseDAO knowledgeBaseDAO;

    @Autowired
    public KnowledgeBaseServiceImpl(KnowledgeBaseDAO knowledgeBaseDAO) {
        Assert.notNull(knowledgeBaseDAO, "KnowledgeBaseDAO must not be null!");
        this.knowledgeBaseDAO = knowledgeBaseDAO;
    }

    @Override
    public List<KnowledgeBase> findAllActive() throws DatabaseException {
        return ConversionUtils.iterableToList(knowledgeBaseDAO.findByActive(true));
    }

    @Transactional(
            readOnly = false
    )
    @Override
    public void updateListOfSupportedKnowledgeBases(Set<KnowledgeBase> supportedBases) {
        Set<KnowledgeBase> allKnownBases = ConversionUtils.iterableToSet(knowledgeBaseDAO.findAll());

        Map<String, KnowledgeBase> supportedBasesMap = knowledgeBaseSetToMap(supportedBases);
        Map<String, KnowledgeBase> allKnownBasesMap = knowledgeBaseSetToMap(allKnownBases);

        Map<Boolean, List<KnowledgeBase>> partitioned = allKnownBases.stream()
                .collect(Collectors.partitioningBy(kb -> supportedBasesMap.containsKey(kb.getName())));

        // Set active to to false and save
        Set<KnowledgeBase> deprecatedExistingBases = setActivePropertyOnEachKnowledgeBase(partitioned.get(false).stream(), false);

        // Set active to true and save
        Set<KnowledgeBase> activeExistingBases = setActivePropertyOnEachKnowledgeBase(partitioned.get(true).stream(), true);

        // Add
        Set<KnowledgeBase> activeNonExistingBases = setActivePropertyOnEachKnowledgeBase(
                supportedBases.stream().filter(kb -> !allKnownBasesMap.containsKey(kb.getName())), true);


        knowledgeBaseDAO.save(deprecatedExistingBases);
        knowledgeBaseDAO.save(activeExistingBases);
        knowledgeBaseDAO.save(activeNonExistingBases);
        logger.info("Persisted {} deprecated, {} active and {} new active KnowledgeBases.",
                deprecatedExistingBases.size(), activeExistingBases.size(), activeNonExistingBases.size());
    }

    private Map<String, KnowledgeBase> knowledgeBaseSetToMap(Set<KnowledgeBase> kbSet) {
        return kbSet.stream().collect(Collectors.toMap(KnowledgeBase::getName, Function.identity()));
    }

    private Set<KnowledgeBase> setActivePropertyOnEachKnowledgeBase(Stream<KnowledgeBase> kbStream, boolean active) {
        return kbStream
                .map(kb -> { kb.setActive(active); return kb; } )
                .collect(Collectors.toSet());
    }
}
