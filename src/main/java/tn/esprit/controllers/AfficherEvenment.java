package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.models.Evenment;
import tn.esprit.services.EvenmentService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherEvenment {

    @FXML
    private ListView<String> table;


    @FXML
    void returnTF(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminEvenment.fxml"));
        try {
            table.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void initialize() {
        displayEventData();
    }




    public static class ColumnListViewCell extends ListCell<String> {

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
                System.out.println(imagePath);
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
    void trier(ActionEvent event) {
        EvenmentService evenmentService = new EvenmentService();
        try {
            List<Evenment> evenments = evenmentService.tri();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (Evenment evenment : evenments) {
                String eventData = String.format("%-20s %-20s %-20s %-20s %-20s",
                        evenment.getDate_event(),
                        evenment.getLieu_event(),
                        evenment.getNom_event(),
                        evenment.getNom_star(),
                        evenment.getImage());
                observableList.add(eventData);
            }
            table.setItems(observableList);
            // Set custom ListCell to format the data in columns
            table.setCellFactory(listView -> new ColumnListViewCell());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void displayEventData() {
        EvenmentService evenmentService = new EvenmentService();
        try {
            List<Evenment> evenments = evenmentService.recuperer();

            // Create an ObservableList to store the event data
            ObservableList<String> eventDataList = FXCollections.observableArrayList();

            // Add column titles
            String columnTitles = String.format("%-20s %-20s %-20s %-20s %-20s", "Date", "Lieu", "Nom", "Star", "Image Path");
            eventDataList.add(columnTitles);

            // Iterate through the list of events and add their details to the eventDataList
            for (Evenment evenment : evenments) {
                String eventData = String.format("%-20s %-20s %-20s %-20s %-20s",
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
            table.setCellFactory(listView -> new ColumnListViewCell());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }





}

