package tn.esprit.controllers;


import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import tn.esprit.utils.MyDataBase;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import tn.esprit.helper.AlertHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import tn.esprit.models.user;
import tn.esprit.models.UserSession;

import tn.esprit.services.ServicePersonne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField   ;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontPage.fxml"));

        try {
            Parent   root = loader.load();
            FrontPage controller = loader.getController();
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
    void goToBack() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserBack.fxml"));

        try {
            Parent root = loader.load();
            UserBack controller = loader.getController();
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

        if (sp.authenticate(tfEmail_Login.getText(), fPassword_Login.getText()) != 0) {
            UserSession u =UserSession.getInstace( tfEmail_Login.getText(), sp.roles(sp.authenticate(tfEmail_Login.getText(), fPassword_Login.getText())));
            System.out.println(u);
            if (sp.roles(sp.authenticate(tfEmail_Login.getText(), fPassword_Login.getText())).equals("role_admin")) {
//                auc.setAfficherTF(" bienvnu etudiant");
//                btn.getScene().setRoot(root);
                goToBack();
            }
            else if (sp.roles(sp.authenticate(tfEmail_Login.getText(), fPassword_Login.getText())).equals("role_user")) {
                //auc.setAfficherTF(" bienvnu responsable societe");
                //btn.getScene().setRoot(root);
                goToHome();


            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Invalid email and password.");

        }
    }}


