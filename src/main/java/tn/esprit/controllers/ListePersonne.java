package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import tn.esprit.models.user;

import java.awt.*;
import java.text.NumberFormat;

public class ListePersonne {

    @FXML
    private Label email;

    @FXML
    private Label nom;

    @FXML
    private Label prenom;
    private user user;


    public void getData(user user) {
        this.user = user;
        nom.setText(user.getNom());
        email.setText(user.getEmail());
        prenom.setText(user.getPrenom());
    }


    private ObservableList<user> produitsList = FXCollections.observableArrayList();

    private void initializeProduits() {
        // Add users to the list
        produitsList.add(new user(1, "John Doe", "John Doe", "john@example.com"));
        produitsList.add(new user(2, "Jane Smith","Jane Smith", "jane@example.com"));
    }


}
