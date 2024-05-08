package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AffichierPersonne implements Initializable {

    @FXML
    private Label lbPersones;

    public void setLbPersones(String lbPersones) {
        this.lbPersones.setText(lbPersones);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
