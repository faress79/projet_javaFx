package tn.esprit.controllers;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import tn.esprit.helper.AlertHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import tn.esprit.models.user;
import tn.esprit.models.UserSession;

import tn.esprit.services.ServicePersonne;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField   ;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import javafx.fxml.Initializable;


import java.net.URL;
import java.util.ResourceBundle;





import java.io.IOException;

public class AjouterPersonne {

    ServicePersonne sp  = new ServicePersonne() {
        @Override
        public void update(user user) {

        }


    };


    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfemail;
    @FXML
    private  PasswordField fPassword_Login;

    @FXML
    private TextField tfEmail_Login;
    @FXML
    private PasswordField mdpTF;
    @FXML
    private WebView captchaWebView;


    @FXML
    void affichierPerssone(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichierPersonne.fxml"));

        try {
            Parent root = loader.load();
            AffichierPersonne ap = loader.getController();

            // Get the list of users
            ArrayList<user> userList = sp.getAll();

            // Construct a formatted string containing user information
            StringBuilder userInfo = new StringBuilder();
            for (user u : userList) {
                userInfo.append("ID: ").append(u.getId_user()).append("\n")
                        .append("Nom: ").append(u.getNom()).append("\n")
                        .append("Prenom: ").append(u.getPrenom()).append("\n")
                        .append("Email: ").append(u.getEmail()).append("\n\n")
                        .append("Password: ").append(u.getPassword()).append("\n\n");
            }

            // Set the formatted user information to the label
            ap.setLbPersones(userInfo.toString());

            // Set the scene root
            tfemail.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean patternMatches(String email) {
        // Define your email validation pattern here
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }
    private boolean passwordMatches(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()-_=+].*")) {
            return false;
        }

        // Password meets all criteria
        return true;
    }

    @FXML
    void ajouterPersonne(ActionEvent event) {

        // Assuming you're in a controller class
        Stage stage = (Stage) tfemail.getScene().getWindow();
        if (tfemail.getText().isEmpty() || mdpTF.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfNom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, stage, "Error", "Please fill all fields.");
            return;
        }
        if (!patternMatches(tfemail.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, tfemail.getScene().getWindow(), "Error", "Invalid email.");
            return;
        }
        if (!passwordMatches(mdpTF.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, mdpTF.getScene().getWindow(), "Error", "Invalid passowrd.");
            return;
        }

        if (sp.exsitemail(tfemail.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, tfemail.getScene().getWindow(), "Error", "User already exists.");
        }


        user u = new user();
        String hashedPassword = BCrypt.hashpw(mdpTF.getText(), BCrypt.gensalt());
        u.setNom(tfNom.getText());
        u.setPrenom(tfPrenom.getText());
        u.setEmail(tfemail.getText());
        u.setPassword(hashedPassword);

        sp.add(u);

    }

    void goToHome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));

        try {
            Parent   root = loader.load();
            AjouterPersonne controller = loader.getController();
            tfEmail_Login.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    @FXML
    void login(ActionEvent event) throws IOException {
        String email = tfEmail_Login.getText();
        String password = fPassword_Login.getText();

        if (email.isEmpty() || password.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, tfEmail_Login.getScene().getWindow(), "Error",
                    "Insert email and password.");
            return;
        }

        int authenticationResult = sp.authenticate(email, password);
        if (authenticationResult != 0) {
            // Authentication successful
            UserSession userSession = UserSession.getInstace(email, sp.roles(authenticationResult));
            System.out.println(userSession);
            goToHome();

        } else {
            // Authentication failed
            AlertHelper.showAlert(Alert.AlertType.ERROR, tfEmail_Login.getScene().getWindow(), "Error",
                    "Invalid email or password.");
        }
    }


//    public void initialize() {
//        WebEngine webEngine = captchaWebView.getEngine();
//
//        String htmlContent = "<html><head>" +
//                "<script src='https://www.google.com/recaptcha/api.js?render=6LfYuoYpAAAAACfIW5Y97UGkS5D0ar_OfWg3MRHU'></script>" +
//                "</head><body>" +
//                "<div class='g-recaptcha' data-sitekey='6LfYuoYpAAAAACfIW5Y97UGkS5D0ar_OfWg3MRHU' data-callback='onClick'></div>" +
//                "</body></html>";
//
//        webEngine.loadContent(htmlContent);
//
//        // Add listener to handle WebView loading failure
//        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == Worker.State.FAILED) {
//                System.out.println("WebView failed to load: " + webEngine.getLoadWorker().getException().getMessage());
//            }
//        });
//    }



    public void handleCreateAccountButtonAction(ActionEvent actionEvent) {
    }
}
