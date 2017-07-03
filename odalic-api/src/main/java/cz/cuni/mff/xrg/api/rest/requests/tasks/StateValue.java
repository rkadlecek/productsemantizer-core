package cz.cuni.mff.xrg.api.rest.requests.tasks;

public enum StateValue {

    /**
     * Task is specified, but not yet submitted for execution.
     */
    READY,

    /**
     * Task is submitted for execution, but not done or canceled yet.
     */
    RUNNING,

    /**
     * Task execution has ended with success.
     */
    SUCCESS,

    /**
     * Task execution has ended with warnings.
     */
    WARNING,

    /**
     * Task execution has ended with an error.
     */
    ERROR

}
