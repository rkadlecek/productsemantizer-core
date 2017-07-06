package sk.kadlecek.productsemantizer.util;

import cz.cuni.mff.xrg.api.rest.requests.tasks.StateValue;
import cz.cuni.mff.xrg.api.rest.responses.bases.KnowledgeBaseResponse;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;
import sk.kadlecek.productsemantizer.enumeration.JobState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConversionUtils {

    public static String jobTitleToOdalicId(String jobTitle) {
        return jobTitle.replaceAll(" ", "_");
    }

    public static cz.cuni.mff.xrg.api.rest.requests.tasks.KnowledgeBase knowledgeBaseToOdalicKnowledgeBase(KnowledgeBase knowledgeBase) {
        return new cz.cuni.mff.xrg.api.rest.requests.tasks.KnowledgeBase() {{
            setName(knowledgeBase.getName());
        }};
    }

    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        List<T> lst = new ArrayList<>();
        iterable.forEach(lst::add);
        return lst;
    }

    public static <T> Set<T> iterableToSet(Iterable<T> iterable) {
        Set<T> set = new HashSet<>();
        iterable.forEach(set::add);
        return set;
    }

    public static KnowledgeBase knowledgeBaseResponseToKnowledgeBase(KnowledgeBaseResponse response) {
        KnowledgeBase kb = new KnowledgeBase();
        kb.setName(response.getName());
        return kb;
    }

    public static JobState odalicStatusValueToJobState(StateValue value) {
        switch(value) {
            case READY:
                return JobState.NOT_SUMITTED;
            case RUNNING:
                return JobState.ODALIC_RUNNING;
            case SUCCESS:
                return JobState.ODALIC_FINISHED;
            case ERROR:
                return JobState.ODALIC_ERROR;
            case WARNING:
                return JobState.ODALIC_ERROR;
            default:
                return JobState.UNKNOWN;
        }
    }
}
