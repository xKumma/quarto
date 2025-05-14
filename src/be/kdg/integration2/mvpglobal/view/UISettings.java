package be.kdg.integration2.mvpglobal.view;

import javafx.stage.Screen;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UISettings {
    public static UISettings Instance = null;

    private int resX;
    private int resY;
    private int insetsMargin;
    public static final char FILE_SEPARATOR = System.getProperties().getProperty("file.separator").charAt(0);
    private String ApplicationName;
    private String homeDir;
    private String defaultCss = "style.css";
    private Path styleSheetPath = Paths.get("resources"+FILE_SEPARATOR+ "stylesheets" +FILE_SEPARATOR+defaultCss);
    private Path AboutImagePath = Paths.get("resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"AboutImage.png");
    private Path applicationIconPath = Paths.get("resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"icon.png");
    private Path startScreenImagePath = Paths.get("resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"icon.png");
    private Path infoTextPath = Paths.get("resources"+FILE_SEPARATOR+"other"+FILE_SEPARATOR+"info.txt");

    private UISettings() {
        this.resX = (int) Screen.getPrimary().getVisualBounds().getWidth();
        this.resY = (int) Screen.getPrimary().getVisualBounds().getHeight();
        this.insetsMargin = this.getLowestRes()/100;
        this.homeDir = System.getProperties().getProperty("user.dir");
        this.ApplicationName = "QUARTO";
    }

    public static UISettings getInstance() {
        if (Instance == null) {
            Instance = new UISettings();
        }
        return Instance;
    }

    public int getResX () {return this.resX;}

    public int getResY () {return this.resY;}

    public int getInsetsMargin () {return this.insetsMargin;}

    public int getLowestRes () {return (resX>resY?resX:resY);}

    public boolean styleSheetAvailable (){return Files.exists(styleSheetPath);}

    public Path getStyleSheetPath () {return this.styleSheetPath;}

    public void setStyleSheetPath (Path styleSheetPath) {this.styleSheetPath = styleSheetPath;}

    public String getHomeDir () {return this.homeDir;}

    public Path getApplicationIconPath () {return this.applicationIconPath;}

    public Path getStartScreenImagePath () {return this.startScreenImagePath;}

    public Path getAboutImagePath () {return this.AboutImagePath;}

    public Path getInfoTextPath () {return this.infoTextPath;}

    public String getApplicationName () {return this.ApplicationName;}

}
