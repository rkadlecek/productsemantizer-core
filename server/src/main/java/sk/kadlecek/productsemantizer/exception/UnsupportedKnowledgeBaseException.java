package sk.kadlecek.productsemantizer.exception;

import sk.kadlecek.productsemantizer.bean.KnowledgeBase;

import java.util.ArrayList;
import java.util.Collection;

public class UnsupportedKnowledgeBaseException extends Exception {

    public UnsupportedKnowledgeBaseException(KnowledgeBase unsupportedKnowledgebase) {
        this(new ArrayList<KnowledgeBase>() {{
            add(unsupportedKnowledgebase);
        }});
    }
    public UnsupportedKnowledgeBaseException(Collection<KnowledgeBase> unsupportedKnowledgebases) {
        super("Unsupported Knowledge Bases: " + String.join("," + unsupportedKnowledgebases));
    }

}
