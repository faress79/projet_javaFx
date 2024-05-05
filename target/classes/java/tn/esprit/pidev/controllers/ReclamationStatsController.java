package tn.esprit.pidev.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import tn.esprit.pidev.services.ReclamationService;
import tn.esprit.pidev.services.ReponseService;

public class ReclamationStatsController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;

    private final ReclamationService reclamationService = new ReclamationService();
    private final ReponseService reponseService = new ReponseService();

    @FXML
    public void initialize() {
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Reclamations");

        int totalReclamations = reclamationService.getAllReclamations().size();
        barSeries.getData().add(new XYChart.Data<>("Total", totalReclamations));

        barChart.getData().add(barSeries);

        PieChart.Data respondedData = new PieChart.Data("Responded", reponseService.getAllReponses().size());
        PieChart.Data nonRespondedData = new PieChart.Data("Non-Responded", totalReclamations - reponseService.getAllReponses().size());

        pieChart.getData().add(respondedData);
        pieChart.getData().add(nonRespondedData);
    }

    @FXML
    public void closeStats() {
        Stage stage = (Stage) barChart.getScene().getWindow();
        stage.close();
    }
}
