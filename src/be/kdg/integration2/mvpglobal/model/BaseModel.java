package be.kdg.integration2.mvpglobal.model;

public interface BaseModel {
    /**
     * Initializes the model with some data.
     *
     * @param data The data to initialize the model with.
     */
    default void init(Object data){
        // Do nothing, override for in model that need to be initialized with some data
    }
}
