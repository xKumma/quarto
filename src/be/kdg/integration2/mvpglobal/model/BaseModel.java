package be.kdg.integration2.mvpglobal.model;

public interface BaseModel {
    default void init(Object data){
        // Do nothing, override for in model that need to be initialized with some data
    }
}
