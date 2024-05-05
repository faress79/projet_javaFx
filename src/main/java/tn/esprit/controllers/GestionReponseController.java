package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import tn.esprit.models.Reponse;
import tn.esprit.services.ReponseService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;





import javafx.scene.control.Alert;


import javafx.scene.control.TextField;



public class GestionReponseController {

    public TextField Recherche;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ListView<Reponse> reponsesListView;
    private ObservableList<Reponse> allReponses;

    private int reclamationId;

    private final ReponseService reponseService = new ReponseService();

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
        loadReponsesForReclamation();
    }

    private void loadReponsesForReclamation() {
        List<Reponse> reponses = reponseService.getReponsesForReclamation(reclamationId);
        allReponses = FXCollections.observableArrayList(reponses);
        reponsesListView.setItems(allReponses);
    }

    @FXML
    private void addResponseAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreateOrEditReponse.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            CreateOrEditReponseController controller = fxmlLoader.getController();
            controller.initializeData(null,reclamationId);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editResponseAction(ActionEvent actionEvent) {
        Reponse selectedResponse = reponsesListView.getSelectionModel().getSelectedItem();
        if (selectedResponse != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreateOrEditReponse.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());
                CreateOrEditReponseController controller = fxmlLoader.getController();
                controller.initializeData(selectedResponse,selectedResponse.getReclamationId());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a response to edit.");
        }
    }

    @FXML
    private void deleteResponseAction(ActionEvent actionEvent) {
        Reponse selectedResponse = reponsesListView.getSelectionModel().getSelectedItem();
        if (selectedResponse != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Response");
            alert.setContentText("Are you sure you want to delete this response?");

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    int deleteResult = reponseService.removeReponse(selectedResponse.getId());
                    if (deleteResult > 0) {
                        loadReponsesForReclamation();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("Delete Error");
                        errorAlert.setContentText("Failed to delete response.");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a response to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void refreshListAction(ActionEvent actionEvent) {
        loadReponsesForReclamation();
    }

    @FXML
    private void initialize() {
        comboBox.setValue("No Sorting");
        comboBox.setOnAction(this::onComboBoxChange);
        Recherche.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rechercheAction(newValue);
            }
        });
    }

    private void rechercheAction(String searchString) {
        ObservableList<Reponse> filteredReponses = FXCollections.observableArrayList();

        if (searchString == null || searchString.isEmpty()) {
            filteredReponses.addAll(allReponses);
        } else {
            // Filter based on the search string
            filteredReponses.addAll(allReponses.filtered(reponse ->
                    reponse.getDescription().toLowerCase().contains(searchString.toLowerCase())));
        }

        reponsesListView.setItems(filteredReponses);
    }
    private void loadReponses() {
        allReponses = FXCollections.observableArrayList(reponseService.getAllReponses());
        reponsesListView.setItems(allReponses);
    }
    public void onComboBoxChange(ActionEvent actionEvent) {
        String selectedOption = comboBox.getValue();

        Comparator<Reponse> comparator = null;

        switch (selectedOption) {
            case "No Sorting":
                loadReponses();  // Reset to default state
                return;  // No further action required
            case "Sort by Description":
                comparator = Comparator.comparing(Reponse::getDescription);
                break;
            case "Sort by Created At":
                comparator = Comparator.comparing(Reponse::getCreatedAt);
                break;
        }

        if (comparator != null) {
            FXCollections.sort(allReponses, comparator);  // Sort based on selected comparator
        }

        reponsesListView.setItems(allReponses);  // Update ListView with sorted data
    }

}
