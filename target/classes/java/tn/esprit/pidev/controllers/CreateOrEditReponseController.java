package tn.esprit.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tn.esprit.pidev.models.Reponse;
import tn.esprit.pidev.services.ReponseService;

import java.util.Arrays;
import java.util.List;

public class CreateOrEditReponseController {

    @FXML
    private TextArea descriptionTextArea;

    private Reponse reponse = null;
    private int reclamation;
    private final ReponseService reponseService = new ReponseService();

    public void initializeData(Reponse reponse, int reclamation) {
        this.reclamation = reclamation;
        if (reponse != null) {
            this.reponse = reponse;
            descriptionTextArea.setText(reponse.getDescription());
        }
    }

    @FXML
    public void saveReponseAction(ActionEvent actionEvent) {
        String description = descriptionTextArea.getText();

        List<String> badWords = Arrays.asList("baha", "badword2", "badword3","kilma okhra");

        if (description != null && !description.isEmpty()) {
            boolean containsBadWord = badWords.stream().anyMatch(description::contains);
            if (!containsBadWord) {
                if (reponse == null) {
                    Reponse newReponse = new Reponse();
                    newReponse.setDescription(description);
                    newReponse.setReclamationId(this.reclamation);
                    reponseService.createReponse(newReponse);
                } else {
                    reponse.setDescription(description);
                    reponseService.updateReponse(reponse, reponse.getId());
                }
                closeWindow(actionEvent);
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
            alert.setContentText("Description cannot be null or empty.");
            alert.showAndWait();
        }
    }

    @FXML
    public void cancelAction(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    private void closeWindow(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
