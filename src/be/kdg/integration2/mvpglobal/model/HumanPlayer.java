package be.kdg.integration2.mvpglobal.model;

public class HumanPlayer extends Player{
    private static HumanPlayer Instance = new HumanPlayer();

    private String name;

    public HumanPlayer () {
        super();
        Instance = this;
        name = "Human";
    }

    public HumanPlayer (String name) {
        super();
        Instance = this;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static HumanPlayer getInstance() {
        return Instance;
    }

}
