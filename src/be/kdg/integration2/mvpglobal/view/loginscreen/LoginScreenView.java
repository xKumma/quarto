package be.kdg.integration2.mvpglobal.view.loginscreen;

import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LoginScreenView extends BaseView {
    private Label quartoLogo;
    private TextField nameField;
    private PasswordField passwordField;
    private Label passwordLabel;
    private Label nameLabel;
    private Button loginButton;
    private Button registerButton;
    private Label headerLabel;
    private UISettings uiSettings;
    private Rectangle rectangle;

    public LoginScreenView() {
        super();
        initialiseNodes();
        layoutNodes();
    }

    protected void initialiseNodes(){
        this.quartoLogo = new Label("\uD83C\uDD40uarto");
        this.nameField = new TextField();
        this.passwordField = new PasswordField();
        this.passwordLabel = new Label("Password:");
        this.nameLabel = new Label("Username:");
        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");
        this.headerLabel = new Label("Login/ Register");
        this.rectangle = new Rectangle(200, 100);
    }

    protected void layoutNodes(){
        //Rectangle
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setRotate(45);
        //Logo
        quartoLogo.setStyle("-fx-font-size: 30px");
        //Alignment of modules
        HBox box2 = new HBox(10,loginButton, registerButton);
        VBox box1 = new VBox(10, quartoLogo,headerLabel,nameLabel, nameField, passwordLabel, passwordField, box2);
        box1.setMaxWidth(400);
        box1.setMaxHeight(400);
        box1.setAlignment(Pos.CENTER);
        box2.setAlignment(Pos.CENTER);

        rectangle.widthProperty().bind(box1.widthProperty().add(20));
        rectangle.heightProperty().bind(box1.heightProperty().add(20));

        StackPane root = new StackPane(rectangle, box1);
        StackPane.setAlignment(box1, Pos.CENTER);
        StackPane.setAlignment(rectangle, Pos.CENTER);
        setCenter(root);
    }

    Button getLoginButton(){return loginButton;}
    TextField getNameField(){return nameField;}
    TextField getPasswordField(){return passwordField;}
    Label getPasswordLabel(){return passwordLabel;}
    Button getRegisterButton(){return registerButton;}

}

