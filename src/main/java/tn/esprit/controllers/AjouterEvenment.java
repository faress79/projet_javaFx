package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tn.esprit.models.Evenment;
import tn.esprit.services.ClientService;
import tn.esprit.services.EvenmentService;
import tn.esprit.test.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;


public class AjouterEvenment {
    @FXML
    private DatePicker date_eventTF;

    @FXML
    private TextField lieu_eventTF;


    @FXML
    private Label nom_eventEror;

    @FXML
    private TextField nom_eventTF;

    @FXML
    private TextField nom_starTF;

    @FXML
    private Label nom_starEror;

    @FXML
    private Label lieu_eventEror;

    @FXML
    private ImageView imageTF;

    @FXML
    private Label image_error;

    private String filePath;



    void clearFields() {
        nom_eventTF.clear();
        date_eventTF.setValue(null);
        lieu_eventTF.clear();
        nom_starTF.clear();
    }


    @FXML
    void afficherEvenment(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AfficherEvenment.fxml"));
        try {
            nom_eventTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }



    @FXML
    public void handleUploadButtonAction(ActionEvent actionEvent) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        // Set file extension filter to only allow image files
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file is selected and it's an image
        if (selectedFile != null && isImageFile(selectedFile)) {
            // Store the file path with forward slashes
            filePath = selectedFile.getAbsolutePath().replace("\\", "/");
            System.out.println("File path stored: " + filePath);

            // Set the image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageTF.setImage(image);
        } else {
            System.out.println("Please select a valid image file.");
        }
    }


    // Method to check if the selected file is an image file
    private boolean isImageFile(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            return image.isError() ? false : true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to retrieve the stored file path
    public String getFilePath() {
        return filePath;
    }

    @FXML
    void ajouterEvenment(ActionEvent event) {
        EvenmentService evenmentService = new EvenmentService();
        Evenment evenment = new Evenment();

        // Get the text from nom_eventTF
        String nomEventText = nom_eventTF.getText();

        // Get the text from lieu_eventTF
        String lieuEventText = lieu_eventTF.getText();

        // Get the text from nom_starTF
        String nomStarText = nom_starTF.getText();

        // Get the file path from the ImageView
        String path = getFilePath();

        // Clear previous error messages
        clearErrorLabels();

        boolean valid = true;

        // Check if the text is empty for nom_event
        if (nomEventText.isEmpty()) {
            nom_eventEror.setText("Le champ 'nom event' ne doit pas être vide.");
            nom_eventEror.setStyle("-fx-text-fill: red;");
            valid = false;
        }
        // Check if the text contains only letters and numbers for nom_event
        else if (!nomEventText.matches("[a-zA-Z0-9 ]+")) {
            nom_eventEror.setText("Le champ 'nom event' ne doit contenir que des lettres et des chiffres.");
            nom_eventEror.setStyle("-fx-text-fill: red;");
            valid = false;
        }

        // Check if the text is empty for lieu_event
        if (lieuEventText.isEmpty()) {
            lieu_eventEror.setText("Le champ 'lieu event' ne doit pas être vide.");
            lieu_eventEror.setStyle("-fx-text-fill: red;");
            valid = false;
        }
        // Check if the text contains only letters and numbers for lieu_event
        else if (!lieuEventText.matches("[a-zA-Z0-9 ]+")) {
            lieu_eventEror.setText("Le champ 'lieu event' ne doit contenir que des lettres et des chiffres.");
            lieu_eventEror.setStyle("-fx-text-fill: red;");
            valid = false;
        }

        // Check if the text is empty for nom_star
        if (nomStarText.isEmpty()) {
            nom_starEror.setText("Le champ 'nom star' ne doit pas être vide.");
            nom_starEror.setStyle("-fx-text-fill: red;");
            valid = false;
        }
        // Check if the text contains only letters and numbers for nom_star
        else if (!nomStarText.matches("[a-zA-Z0-9 ]+")) {
            nom_starEror.setText("Le champ 'nom star' ne doit contenir que des lettres et des chiffres.");
            nom_starEror.setStyle("-fx-text-fill: red;");
            valid = false;
        }

        // Check if the image path is empty
        if (path == null || path.isEmpty()) {
            // Show error message for missing image
            image_error.setText("Veuillez sélectionner une image.");
            image_error.setStyle("-fx-text-fill: red;");
            valid = false;
        }

        if (valid) {
            // If all fields are valid, set them to the evenment object
            evenment.setNom_event(nomEventText);
            evenment.setLieu_event(lieuEventText);
            evenment.setNom_star(nomStarText);

            // Proceed with other fields
            evenment.setDate_event(java.sql.Date.valueOf(date_eventTF.getValue()));
            evenment.setImage(path);
            try {
                // Get the month and day values from the event date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(evenment.getDate_event());
                int month = calendar.get(Calendar.MONTH) + 1; // Adding 1 because Calendar months are zero-based
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Check if there's already an event on the same day
                if (evenmentService.eventExistsOnDay(month, day)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Il existe déjà un événement pour ce jour.");
                    alert.showAndWait();
                    return; // Exit the method if event exists on the same day
                }

                // Count the events in the month
                int eventCount = evenmentService.countEventsInMonth(month);

                // Check if the event count exceeds 5
                if (eventCount >= 5) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Vous ne pouvez pas ajouter plus de 5 événements pour ce mois.");
                    alert.showAndWait();
                    return; // Exit the method if event count exceeds 5
                }

                // Add the event if event count is within limit
                evenmentService.ajouter(evenment);
                // Generate PDF for the event
                File pdfFile = generatePDF(evenment);
                ClientService clientService = ClientService.getInstance();
                clientService.sendmail(pdfFile);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Evenment ajouté avec succès");
                alert.showAndWait();
                clearFields();
                imageTF.setImage(null);


            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }







    void clearErrorLabels() {
        nom_eventEror.setText("");
        lieu_eventEror.setText("");
        nom_starEror.setText("");
    }





    @FXML
    void returnA(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminEvenment.fxml"));
        try {
            nom_eventTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private File generatePDF(Evenment evenment) throws IOException {
        // Create a new PDF document
        PDDocument document = new PDDocument();

        try {
            // Create a new page
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a new content stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Define font and font size for title
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

            // Write title to the PDF (centered)
            String title = "Event Details";
            float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000f * 16;
            float titleX = (page.getMediaBox().getWidth() - titleWidth) / 2;
            float titleY = page.getMediaBox().getHeight() - 20; // Adjust vertical position
            contentStream.beginText();
            contentStream.newLineAtOffset(titleX, titleY);
            contentStream.showText(title);
            contentStream.endText();

            // Define font and font size for event details
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Write event details to the PDF
            contentStream.beginText();
            contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 50);
            contentStream.showText("Nom de l'événement: " + evenment.getNom_event());
            contentStream.newLineAtOffset(0, -20); // Move to the next line
            contentStream.showText("Lieu de l'événement: " + evenment.getLieu_event());
            contentStream.newLineAtOffset(0, -20); // Move to the next line
            contentStream.showText("Nom de la star: " + evenment.getNom_star());
            contentStream.newLineAtOffset(0, -20); // Move to the next line
            contentStream.showText("Date de l'événement: " + evenment.getDate_event());
            contentStream.endText();

            // Add image to the PDF
            String imagePath = evenment.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
                    float imageWidth = pdImage.getWidth();
                    float imageHeight = pdImage.getHeight();

                    // Calculate scale to fit image within page width
                    float maxWidth = page.getMediaBox().getWidth() - 100; // Adjust padding
                    float maxHeight = page.getMediaBox().getHeight() - 150; // Adjust padding and space for text
                    float scale = Math.min(maxWidth / imageWidth, maxHeight / imageHeight);

                    // Calculate coordinates for positioning image
                    float imageX = 50; // Adjust as needed
                    float imageY = 50; // Adjust as needed

                    // Draw image
                    contentStream.drawImage(pdImage, imageX, imageY, imageWidth * scale, imageHeight * scale);
                }
            }

            // Close the content stream
            contentStream.close();

            // Save the document to a file
            File pdfFile = new File("event_details.pdf");
            document.save(pdfFile);

            return pdfFile;
        } finally {
            // Close the PDF document
            document.close();
        }
    }






}
