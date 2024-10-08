package lk.ijse.chat.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.chat.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane footerBar;

    @FXML
    private Label footerText;

    @FXML
    private Label btnSign;

    @FXML
    private AnchorPane singInPage;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane singUpPage;

    @FXML
    private JFXTextField txtUsernameSignUp;

    @FXML
    private JFXTextField txtPasswordSignUp;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private JFXTextField txtPhoneNumberSignUp;

    @FXML
    private JFXRadioButton male;

    @FXML
    private JFXRadioButton female;

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<User> loggedInUser = new ArrayList<>();
    public static String username, password, gender;

    public LoginController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singInPage.setVisible(true);
        singUpPage.setVisible(false);

        btnSignUp.setDisable(true);
    }

    @FXML
    void login(ActionEvent event) {
        username = txtUsername.getText();
        password = txtPassword.getText();

        boolean login = false;

        for (User user : users) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                login = true;
                loggedInUser.add(user);
                break;
            }
        }

        if (login) {
            loadingChatScreen();
        } else {
            System.out.println("Invalid Credential");
        }
    }

    @FXML
    void validateSingInPage(KeyEvent event) {
        if (!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            btnLogin.setDisable(false);
        }
    }

    private void loadingChatScreen(){
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        URL resource = this.getClass().getResource("/lk/ijse/chat/view/ChatPage.fxml");
        Parent load = null;
        try {
            if (resource == null) {
                throw new IOException("Resource not found: /lk/ijse/chat/view/ChatPage.fxml");
            }
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(username + "");
        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        stage.setResizable(false);
    }

    @FXML
    void singUp(ActionEvent event) throws IOException {
        String username = txtUsernameSignUp.getText();
        String password = txtPasswordSignUp.getText();
        String phoneNumber = txtPhoneNumberSignUp.getText();

        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setPhoneNo(phoneNumber);
        if (male.isSelected()) {
            user.setGender("Male");
        } else {
            user.setGender("Female");
        }

        if (checkUser(username)) {
            users.add(user);
            singInPage.setVisible(true);
            singUpPage.setVisible(false);
            btnSign.setText("Sign Up");
            btnSign.setDisable(true);
            btnLogin.setDisable(true);
        } else {
            System.out.println("Username already exists");
        }
    }

    private boolean checkUser(String username) {
        for (User user : users) {
            if (user.getName().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void validateForm(KeyEvent event) {
        if (!txtUsernameSignUp.getText().isEmpty() && !txtPasswordSignUp.getText().isEmpty()
                && !txtPhoneNumberSignUp.getText().isEmpty()) {
            btnSignUp.setDisable(false);
        }
    }

    @FXML
    void changeScreen(MouseEvent event) {
        if (btnSign.getText().equals("Sign Up")) {
            singInPage.setVisible(false);
            singUpPage.setVisible(true);
            btnSign.setText("Sign In");
            footerText.setText("Already have a account.?");
        } else {
            singInPage.setVisible(true);
            singUpPage.setVisible(false);
            btnSign.setText("Sign Up");
            footerText.setText("Dont't have an account.?");
        }
    }

    @FXML
    void selectGender(MouseEvent event) {
        if (male.isFocused()) {
            male.setSelected(true);
            female.setSelected(false);
        } else if (female.isFocused()) {
            male.setSelected(false);
            female.setSelected(true);
        }
    }
}
