package be.kdg.integration2.mvpglobal.view.loginscreen;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
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
    private TextField nameField;
    private PasswordField passwordField;
    private Label passwordLabel;
    private Label nameLabel;
    private Button loginButton;
    private Button registerButton;
    private Rectangle rectangle;

    public LoginScreenView() {
        super();
        initialiseNodes();
        layoutNodes();
    }

    protected void initialiseNodes(){
        this.nameField = new TextField();
        this.passwordField = new PasswordField();
        this.passwordLabel = new Label("Password:");
        this.nameLabel = new Label("Username:");
        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");
        this.rectangle = new Rectangle(200, 100);
    }

    protected void layoutNodes(){
        //Rectangle
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setRotate(45);
        //Logo
        //Alignment of modules
        HBox box2 = new HBox(10,loginButton, registerButton);
        box2.getChildren().forEach(node -> {
            ((Button)node).setMaxSize(135,20);
            ((Button)node).getStyleClass().addAll("menu-button", "small");
        });
        VBox box1 = new VBox(10, new Header(),nameLabel, nameField, passwordLabel, passwordField, box2);
        box1.setMaxWidth(400);
        box1.setMaxHeight(400);
        box1.setAlignment(Pos.CENTER);
        box2.setAlignment(Pos.CENTER);

        passwordField.setMaxSize(275, 20);
        nameField.setMaxSize(275, 20);

        rectangle.widthProperty().bind(box1.widthProperty().add(60));
        rectangle.heightProperty().bind(box1.widthProperty().add(60));

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

