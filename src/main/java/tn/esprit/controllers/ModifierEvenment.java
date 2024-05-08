package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.models.Evenment;
import tn.esprit.services.EvenmentService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ModifierEvenment {

    @FXML
    private Label date_error;

    @FXML
    private Label lieuerror;

    @FXML
    private Label nom_eventerror;


    @FXML
    private Label nom_starerror;
    @FXML
    private DatePicker date_eventTFM;

    @FXML
    private TextField lieu_eventTFM;

    @FXML
    private TextField nom_eventTFM;

    @FXML
    private TextField nom_starTFM;


    @FXML
    private ListView<String> table;

    private int selectedEventId = -1;

    @FXML
    void initialize() {
        EvenmentService evenmentService = new EvenmentService();
        try {
            List<Evenment> evenments = evenmentService.recuperer();
            List<Evenment> ids = evenmentService.selectid();

            // Create an ObservableList to store the event data
            ObservableList<String> eventDataList = FXCollections.observableArrayList();

            // Add column titles
            String columnTitles = String.format("%-10s %-20s %-20s %-20s %-20s %-20s", "ID", "Date", "Lieu", "Nom", "Star", "Image Path");
            eventDataList.add(columnTitles);

            // Iterate through the list of events and add their details to the eventDataList
            for (Evenment evenment : evenments) {
                String eventData = String.format("%-10s %-20s %-20s %-20s %-20s %-20s",
                       evenment.getId_event(),
                        evenment.getDate_event(),
                        evenment.getLieu_event(),
                        evenment.getNom_event(),
                        evenment.getNom_star(),
                        evenment.getImage());
                eventDataList.add(eventData);
            }
            // Set items for the table ListView
            table.setItems(eventDataList);

            // Set custom ListCell to format the data in columns
            table.setCellFactory(listView -> new AfficherEvenment.ColumnListViewCell());

            // Add listener to the ListView to store the selected event ID and fill text fields
            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    // Split the selected item's data into columns
                    String[] columns = newValue.split("\\s+");

                    // Store the ID of the selected event
                    selectedEventId = Integer.parseInt(columns[0]);

                    // Fill the text fields with the selected event's information
                    nom_eventTFM.setText(columns[3]);
                    date_eventTFM.setValue(LocalDate.parse(columns[1]));
                    lieu_eventTFM.setText(columns[2]);
                    nom_starTFM.setText(columns[4]);
                }
            });

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }




    public class ColumnListViewCell extends ListCell<String> {

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                // Split the data into columns
                String[] columns = item.split("\\s+");

                // Create a HBox to hold the columns
                HBox rowBox = new HBox();

                // Add labels for each column (except the image path)
                for (int i = 0; i < columns.length - 1; i++) {
                    Label label = new Label(columns[i]);
                    label.setPrefWidth(150); // Set the width as needed
                    rowBox.getChildren().add(label);
                }

                // Create an ImageView for the image
                ImageView imageView = new ImageView();
                String imagePath = columns[columns.length - 1];
                Image image = new Image("file:///" + imagePath);
                imageView.setImage(image);
                imageView.setFitWidth(100); // Adjust width of the image view
                imageView.setPreserveRatio(true);
                rowBox.getChildren().add(imageView);

                // Set the HBox as the cell's graphic
                setGraphic(rowBox);
            }
        }
    }



    @FXML
    void modifierEvenment(ActionEvent event) {
        // Check if an event is selected from the ListView
        if (selectedEventId == -1) {
            // If no event is selected, show an error message and return
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Veuillez sélectionner un événement à modifier.");
            alert.showAndWait();
            return;
        }

        // Clear previous error messages
        clearErrorLabels();

        // Proceed with modifying the selected event
        try {
            EvenmentService evenmentService = new EvenmentService();

            // Retrieve the selected Evenment object using the ID
            List<Evenment> evenments = evenmentService.selectid();
            Evenment selectedEvenment = null;
            for (Evenment evenment : evenments) {
                if (evenment.getId_event() == selectedEventId) {
                    selectedEvenment = evenment;
                    break;
                }
            }

            if (selectedEvenment == null) {
                // If the selected event is not found, show an error message and return
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Événement sélectionné introuvable.");
                alert.showAndWait();
                return;
            }

            // Get the text from input fields
            String nomEventText = nom_eventTFM.getText();
            LocalDate dateEventValue = date_eventTFM.getValue();
            String lieuEventText = lieu_eventTFM.getText();
            String nomStarText = nom_starTFM.getText();

            boolean valid = true;

            // Check if the text is empty for nom_event
            if (nomEventText.isEmpty()) {
                nom_eventerror.setText("Le champ 'nom event' ne doit pas être vide.");
                nom_eventerror.setStyle("-fx-text-fill: red;");
                valid = false;
            }

            // Check if the date field is selected
            if (dateEventValue == null) {
                date_error.setText("Veuillez sélectionner une date.");
                date_error.setStyle("-fx-text-fill: red;");
                valid = false;
            }

            // Check if the text is empty for lieu_event
            if (lieuEventText.isEmpty()) {
                lieuerror.setText("Le champ 'lieu event' ne doit pas être vide.");
                lieuerror.setStyle("-fx-text-fill: red;");
                valid = false;
            } else if (!lieuEventText.matches("[a-zA-Z0-9]+")) {
                lieuerror.setText("Le champ 'lieu event' ne doit contenir que des lettres et des chiffres.");
                lieuerror.setStyle("-fx-text-fill: red;");
                valid = false;
            }

            // Check if the text is empty for nom_star
            if (nomStarText.isEmpty()) {
                nom_starerror.setText("Le champ 'nom star' ne doit pas être vide.");
                nom_starerror.setStyle("-fx-text-fill: red;");
                valid = false;
            } else if (!nomStarText.matches("[a-zA-Z0-9]+")) {
                nom_starerror.setText("Le champ 'nom star' ne doit contenir que des lettres et des chiffres.");
                nom_starerror.setStyle("-fx-text-fill: red;");
                valid = false;
            }

            if (valid) {
                // If all fields are valid, update the selected event with the new values
                selectedEvenment.setNom_event(nomEventText);
                selectedEvenment.setDate_event(java.sql.Date.valueOf(dateEventValue));
                selectedEvenment.setLieu_event(lieuEventText);
                selectedEvenment.setNom_star(nomStarText);

                // Call the modifier method with the selected event ID and the modified Evenment object
                evenmentService.modifier(selectedEventId, selectedEvenment);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Evenement modifié avec succès");
                alert.showAndWait();

                initialize();

                // Clear input fields
                nom_eventTFM.clear();
                date_eventTFM.setValue(null);
                lieu_eventTFM.clear();
                nom_starTFM.clear();
            }

        } catch (SQLException e) {
            // Show error message if an exception occurs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    // Helper method to clear error labels



    // Helper method to clear error labels
    private void clearErrorLabels() {
        nom_eventerror.setText("");
        lieuerror.setText("");
        nom_starerror.setText("");
        date_error.setText("");
    }






    @FXML
    void returnM(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminEvenment.fxml"));
        try {
            nom_eventTFM.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


@FXML
void supprimerEvenment(ActionEvent event) {
    // Check if an event is selected from the ListView
    if (selectedEventId == -1) {
        // If no event is selected, show an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Veuillez sélectionner un événement à supprimer.");
        alert.showAndWait();
        return;
    }

    // Proceed with deleting the selected event
    try {
        EvenmentService evenmentService = new EvenmentService();

        // Call the supprimer method with the selected event ID
        evenmentService.supprimer(selectedEventId);

        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Événement supprimé avec succès");
        alert.showAndWait();

        // Clear input fields - Not applicable for deletion

        // Optionally, clear the ListView selection after deletion
        table.getSelectionModel().clearSelection();
        selectedEventId = -1; // Reset selected event ID

        // Optionally, refresh the ListView after deletion
        initialize(); // Reinitialize to reload the ListView with updated data

    } catch (SQLException e) {
        // Show error message if an exception occurs
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}


}

