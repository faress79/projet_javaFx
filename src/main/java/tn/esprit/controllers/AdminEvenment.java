package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.Review;
import tn.esprit.services.ReviewService;
import tn.esprit.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminEvenment {

    @FXML
    private PieChart pieChart;

    @FXML
    private AnchorPane afficherEvenmentPane;

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;
    @FXML
    private Label label11;


    @FXML
    private Button ajouterAdmin;

    @FXML
    private LineChart<String, Number> lineChart;


    @FXML
    void initialize() {
        // Retrieve data from the database
        Map<Integer, Integer> data = getDataForPieChart(); // Example method to retrieve data

        // Populate PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            pieChartData.add(new PieChart.Data(String.valueOf(entry.getKey()), entry.getValue()));
        }
        pieChart.setData(pieChartData);



        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AfficherEvenment.fxml"));
        try {
            // Load the content of AfficherEvenment directly into afficherEvenmentPane
            AnchorPane afficherEvenmentContent = fxmlLoader.load();

            // Clear any existing content in afficherEvenmentPane
            afficherEvenmentPane.getChildren().clear();

            // Set the loaded content as the content of afficherEvenmentPane
            AnchorPane.setTopAnchor(afficherEvenmentContent, 0.0);
            AnchorPane.setBottomAnchor(afficherEvenmentContent, 0.0);
            AnchorPane.setLeftAnchor(afficherEvenmentContent, 0.0);
            AnchorPane.setRightAnchor(afficherEvenmentContent, 0.0);

            afficherEvenmentPane.getChildren().add(afficherEvenmentContent);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        initializeLineChart();

    }

    private void initializeLineChart() {
        // Retrieve overall average
        Double overallAverage = getOverallAverage();

        // Populate LineChart
        lineChart.getData().clear(); // Clear existing data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("MOYENNE DE NOMBRE DE STARS DES REVIEWS", overallAverage));
        lineChart.getData().add(series);
    }

    private Double getOverallAverage() {
        ReviewService reviewService = new ReviewService(); // Create an instance of ReviewService
        try {
            List<Review> reviews = reviewService.recuperer(); // Retrieve all reviews from the database
            int totalStars = 0;
            int totalReviews = reviews.size();

            // Calculate the sum of nb_star for all reviews
            for (Review review : reviews) {
                totalStars += review.getNbr_star();
            }

            // Calculate the overall average
            if (totalReviews > 0) {
                return (double) totalStars / totalReviews;
            } else {
                return 0.0; // Return 0 if there are no reviews to avoid division by zero
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
            return 0.0; // Return 0 in case of exception
        }
    }



    private Map<Integer, Integer> getDataForPieChart() {
        // Retrieve data from the database
        Map<Integer, Integer> data = new HashMap<>();
        ReviewService reviewService = new ReviewService(); // Create an instance of ReviewService
        try {
            // Call the recuperer() method to fetch reviews from the database
            List<Review> reviews = reviewService.recuperer(); // Call recuperer() on the instance

            // Count the occurrences of each nbr_star value
            for (Review review : reviews) {
                int nbrStar = review.getNbr_star();
                data.put(nbrStar, data.getOrDefault(nbrStar, 0) + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return data;
    }








    @FXML
    void modifierAdmin(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ModifierEvenment.fxml"));
        try {
            label.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void ajouterAdmin(MouseEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AjouterEvenment.fxml"));
        try {
            label.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    @FXML
    void donation(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Donation.fxml"));

        try {
            label11.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    @FXML
    void rechercheAdmin(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/RechercheEvenment.fxml"));
        try {
            label.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void avisAdmin(MouseEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Avis.fxml"));
        try {
            label.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void acceuil(MouseEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/PowerFit.fxml"));
        try {
            label.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }
}
