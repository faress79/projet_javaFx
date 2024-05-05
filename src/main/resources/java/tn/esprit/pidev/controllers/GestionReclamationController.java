package tn.esprit.pidev.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.pidev.HelloApplication;
import tn.esprit.pidev.models.Reclamation;
import tn.esprit.pidev.services.ReclamationService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class GestionReclamationController {

    public TextField Recherche;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ListView<Reclamation> reclamationsListView;
    private ObservableList<Reclamation> allReclamations;
    private final ReclamationService reclamationService;

    public GestionReclamationController() {
        this.reclamationService = new ReclamationService();
    }

    @FXML
    public void initialize() {
        // Load reclamations into the ListView
        loadReclamations();
        comboBox.setValue("Sort by Description");
        comboBox.setOnAction(this::onComboBoxChange);
        // Set custom cell factory
        reclamationsListView.setCellFactory(new Callback<ListView<Reclamation>, ListCell<Reclamation>>() {
            @Override
            public ListCell<Reclamation> call(ListView<Reclamation> listView) {
                return new ReclamationCell();
            }
        });

        // Set double-click action on list items
        reclamationsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Reclamation selectedReclamation = reclamationsListView.getSelectionModel().getSelectedItem();
                if (selectedReclamation != null) {
                    // Show reclamation content
                    showReclamationContent(selectedReclamation);
                }
            }
        });

        Recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercheAction(newValue);
        });
    }

    public void onComboBoxChange(ActionEvent actionEvent) {
        String selectedSort = comboBox.getValue();
        Comparator<Reclamation> comparator;

        switch (selectedSort) {
            case "Sort by Subject":
                comparator = Comparator.comparing(Reclamation::getSujet);
                break;
            case "Sort by Created At":
                comparator = Comparator.comparing(Reclamation::getCreatedAt);
                break;
            default:
                loadReclamations();
                comparator = Comparator.comparing(Reclamation::getDescription);
                break;
        }

        allReclamations.sort(comparator);
        reclamationsListView.getItems().setAll(allReclamations);
    }
    private void rechercheAction(String searchString) {
        ObservableList<Reclamation> filteredReclamations = FXCollections.observableArrayList();

        if (searchString == null || searchString.isEmpty()) {
            filteredReclamations.addAll(allReclamations);
        } else {
            // Filter based on the search string
            filteredReclamations.addAll(allReclamations.filtered(reclamation ->
                    reclamation.getDescription().toLowerCase().contains(searchString.toLowerCase()) ||
                            reclamation.getSujet().toLowerCase().contains(searchString.toLowerCase())));
        }

        reclamationsListView.setItems(filteredReclamations);
    }

    private void loadReclamations() {
        allReclamations = FXCollections.observableArrayList(reclamationService.getAllReclamations());
        reclamationsListView.getItems().clear();
        reclamationsListView.getItems().addAll(allReclamations);
    }

    public void addReclamationAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreateOrEditReclamation.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteReclamationAction(ActionEvent actionEvent) {
        Reclamation selectedReclamation = reclamationsListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Reclamation");
            alert.setContentText("Are you sure you want to delete this reclamation?");

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    int deleteResult = reclamationService.removeReclamation(selectedReclamation.getId());
                    if (deleteResult > 0) {
                        loadReclamations();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("Delete Error");
                        errorAlert.setContentText("Failed to delete reclamation.");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a reclamation to delete.");
            alert.showAndWait();
        }
    }

    private void showReclamationContent(Reclamation reclamation) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GestionReponse.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            GestionReponseController controller = fxmlLoader.getController();
            controller.setReclamationId(reclamation.getId());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editReclamationAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreateOrEditReclamation.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            CreateOrEditReclamation controller = fxmlLoader.getController();
            controller.initializeData(reclamationsListView.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshListAction(ActionEvent actionEvent) {
        refreshList();
    }

    private void refreshList() {
        List<Reclamation> reclamations = reclamationService.getAllReclamations();
        reclamationsListView.getItems().setAll(reclamations);
    }

    public void onComboBox(ActionEvent actionEvent) {
    }

    public void exportToPDF(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {

                // Add a title
                document.add(new Paragraph("Reclamations Report").setFontSize(20));

                // Create a table with headers
                float[] columnWidths = {1, 3, 3, 3}; // Adjust column widths
                Table table = new Table(columnWidths);
                table.addCell("ID");
                table.addCell("Description");
                table.addCell("Subject");
                table.addCell("Created At");

                // Fetch all reclamations and add them to the table
                List<Reclamation> reclamations = reclamationService.getAllReclamations();
                for (Reclamation reclamation : reclamations) {
                    table.addCell(String.valueOf(reclamation.getId()));
                    table.addCell(reclamation.getDescription());
                    table.addCell(reclamation.getSujet());
                    table.addCell(reclamation.getCreatedAt().toString());
                }

                // Add the table to the document
                document.add(table);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exportReclamationsToExcel(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            // Create a new workbook and sheet
            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOut = new FileOutputStream(file)) {

                Sheet sheet = workbook.createSheet("Reclamations");

                // Create a header row with bold font
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFont(headerFont);

                // Add header row
                Row headerRow = sheet.createRow(0);
                String[] columnHeaders = {"ID", "Description", "Subject", "Created At"};
                for (int i = 0; i < columnHeaders.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnHeaders[i]);
                    cell.setCellStyle(headerCellStyle);
                }

                // Fetch all reclamations and add them to the sheet
                List<Reclamation> reclamations = reclamationService.getAllReclamations();
                int rowNum = 1; // Start from the second row
                for (Reclamation reclamation : reclamations) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(reclamation.getId());
                    row.createCell(1).setCellValue(reclamation.getDescription());
                    row.createCell(2).setCellValue(reclamation.getSujet());
                    row.createCell(3).setCellValue(reclamation.getCreatedAt().toString());
                }

                // Auto-size all columns for better readability
                for (int i = 0; i < columnHeaders.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                // Write to the file
                workbook.write(fileOut);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openStats(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ReclamationStats.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class ReclamationCell extends ListCell<Reclamation> {
        private final Label descriptionLabel;
        private final Label sujetLabel;

        public ReclamationCell() {
            descriptionLabel = new Label();
            sujetLabel = new Label();

            setGraphic(createCell());
        }

        private HBox createCell() {
            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(descriptionLabel, sujetLabel);
            return hbox;
        }

        @Override
        protected void updateItem(Reclamation item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                descriptionLabel.setText(item.getDescription());
                sujetLabel.setText(item.getSujet());
                setGraphic(createCell());
            }
        }
    }
}
