package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.EventElement;
import tn.esprit.services.EvenmentService;
import tn.esprit.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RechercheEvenment {

    @FXML
    private TextField rechercheCol;
    @FXML
    private ListView<EventElement> list;

    public RechercheEvenment() {
        EvenmentService evenmentService = new EvenmentService();
    }

    @FXML
    public void initialize() {
        // Add a listener to the text property of rechercheCol TextField
        rechercheCol.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Call rechercheEvenment method whenever the text changes
                rechercheEvenment(newValue);
            }
        });
    }

    public void rechercheEvenment(String searchText) {
        try {
            // Perform the search using the EvenmentService
            EvenmentService evenmentService = new EvenmentService();
            List<EventElement> evenments = evenmentService.rechercheEvenment(searchText);

            // Update the ListView with the search results
            ObservableList<EventElement> observableList = FXCollections.observableList(evenments);
            list.setItems(observableList);

        } catch (SQLException e) {
            // Handle SQLException
            // Clear the ListView if an exception occurs
            list.getItems().clear();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void returnR(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AdminEvenment.fxml"));
        try {
            list.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}

