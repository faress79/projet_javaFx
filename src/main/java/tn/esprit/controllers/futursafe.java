package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import tn.esprit.test.HelloApplication;

import java.io.IOException;

public class futursafe {

    @FXML
    private Label exit1;


    @FXML
    void dashboard(MouseEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/EditProfile.fxml"));
        try {
            exit1.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void user(MouseEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/FrontPage.fxml"));
        try {
            exit1.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

}
