package tn.esprit.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tn.esprit.pidev.models.Reclamation;
import tn.esprit.pidev.services.ReclamationService;

import java.util.Arrays;
import java.util.List;

public class CreateOrEditReclamation {

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ChoiceBox<String> sujetChoiceBox; // Changed to ChoiceBox

    private Reclamation reclamation = null;
    private final ReclamationService reclamationService = new ReclamationService();

    public void initializeData(Reclamation reclamation) {
        if (reclamation != null) {
            this.reclamation = reclamation;
            descriptionTextArea.setText(reclamation.getDescription());
            sujetChoiceBox.setValue(reclamation.getSujet());
        }
    }

    @FXML
    public void initialize() {
        List<String> subjects = Arrays.asList("Subject 1", "Subject 2", "Subject 3", "Subject 4");
        sujetChoiceBox.getItems().addAll(subjects);
    }

    public void saveReclamationAction(ActionEvent actionEvent) {
        String description = descriptionTextArea.getText();
        String sujet = sujetChoiceBox.getValue();

        // List of bad words
        List<String> badWords = Arrays.asList("badword1", "badword2", "badword3");

        // Check if description is not null or empty and does not contain bad words
        if (description != null && !description.isEmpty() && sujet != null && !sujet.isEmpty()) {
            boolean containsBadWord = badWords.stream().anyMatch(description::contains);
            if (!containsBadWord) {
                if (reclamation == null) {
                    Reclamation newReclamation = new Reclamation(description, sujet, 1);
                    reclamationService.createReclamation(newReclamation);
                } else {
                    reclamation.setDescription(description);
                    reclamation.setSujet(sujet);
                    reclamationService.updateReclamation(reclamation, reclamation.getId());
                }
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } else {
                // Show alert for bad words
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Description contains inappropriate content.");
                alert.showAndWait();
            }
        } else {
            // Show alert for missing input
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Description and sujet cannot be null or empty.");
            alert.showAndWait();
        }
    }

    @FXML
    public void cancelAction(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
