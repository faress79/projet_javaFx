package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import tn.esprit.helper.AlertHelper;
import tn.esprit.models.UserSession;
import tn.esprit.models.user;
import tn.esprit.services.ServicePersonne;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AccuielController implements Initializable {
    ServicePersonne sp  = new ServicePersonne() {
        @Override
        public void update(user user) {

        }


    };
    @FXML
    private Button rest;

    @FXML
    private Button btn;
    @FXML
    private Button btnaz;

    @FXML
    private TextField tfEmail_Login;



    @FXML
    private  PasswordField fPassword_Login;
    Window window;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    void AjouterUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));

        Parent root=loader.load();
        AjouterPersonne auc=loader.getController();
        btnaz.getScene().setRoot(root);



    }
    @FXML
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
    void gotoConfimer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Email.fxml"));
        Parent root=loader.load();
        confirmController auc=loader.getController();
        rest.getScene().setRoot(root);
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




}


