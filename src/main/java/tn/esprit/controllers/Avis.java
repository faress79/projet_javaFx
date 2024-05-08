package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import tn.esprit.models.Review;
import tn.esprit.services.ReviewService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Avis {
    @FXML
    private ListView<Review> list;

    private int selectedReviewId = -1;


    @FXML
    void initialize() {
        ReviewService reviewService = new ReviewService();

        try {
            // Retrieve the list of reviews
            List<Review> reviews = reviewService.recuperer();

            // Create an ObservableList to hold the reviews
            ObservableList<Review> reviewList = FXCollections.observableArrayList(reviews);

            // Set the items of the ListView
            list.setItems(reviewList);

            // Add event handler to ListView to store the selected review ID
            list.setOnMouseClicked(event -> {
                Review selectedReview = list.getSelectionModel().getSelectedItem();
                if (selectedReview != null) {
                    selectedReviewId = selectedReview.getId_review();
                }
            });

            // Add custom cell factory to the ListView to display reviews properly
            list.setCellFactory(param -> new ListCell<Review>() {
                @Override
                protected void updateItem(Review review, boolean empty) {
                    super.updateItem(review, empty);

                    if (empty || review == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Create a VBox to hold the review details
                        VBox container = new VBox(5);
                        container.setAlignment(Pos.CENTER_LEFT);

                        // Add review details to the container
                        Label descriptionLabel = new Label("Description: " + review.getDescription());
                        Label nbrStarLabel = new Label("Nombre de Stars: " + review.getNbr_star());

                        container.getChildren().addAll(descriptionLabel, nbrStarLabel);

                        // Set the container as the graphic of the cell
                        setGraphic(container);
                        setText(null); // Make sure no text is displayed
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    public void supprimerAvis(ActionEvent event) {
        if (selectedReviewId != -1) {
            try {
                ReviewService reviewService = new ReviewService();
                reviewService.supprimerReview(selectedReviewId);

                // Optionally, refresh the ListView after deletion
                list.getItems().removeIf(review -> review.getId_review() == selectedReviewId);
                selectedReviewId = -1; // Reset the selected review ID
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        } else {
            // Show an error message if no review is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Veuillez sélectionner un avis à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    public void returnTF(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminEvenment.fxml"));
        try {
            list.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

