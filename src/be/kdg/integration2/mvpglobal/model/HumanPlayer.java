package be.kdg.integration2.mvpglobal.model;

public class HumanPlayer extends Player{
    private static HumanPlayer Instance;

    private String name;

    /**
     * Instantiates a new Human player.
     *
     * @param name the name
     */
    public HumanPlayer (String name) {
        super();
        Instance = this;
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HumanPlayer getInstance() {
        return Instance;
    }
}
