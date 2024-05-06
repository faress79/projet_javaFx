package tn.esprit.projet3a.controllers;

import tn.esprit.projet3a.models.Donation;
import tn.esprit.projet3a.services.DonationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class DonationController {

    DonationService ds = new DonationService();

    @FXML
    public TableColumn<?, ?> ColAmount;

    @FXML
    public TableColumn<?, ?> ColEvent;

    @FXML
    public TableColumn<?, ?> ColID;

    @FXML
    public TableColumn<?, ?> ColMessage;

    @FXML
    public TableColumn<?, ?> ColUtilisateur;

    @FXML
    public Button btnAjout;

    @FXML
    public Button btnModifier;

    @FXML
    public Button btnSupprimer;

    @FXML
    public TextField tAmount;

    @FXML
    public TextField tEvent_id;

    @FXML
    public TextField tID;

    @FXML
    public TextField tMessage;

    @FXML
    public TextField tUtilisateur_id;

    @FXML
    public TableView<Donation> tableviewDonation;

    @FXML
    void AjouterDonation(ActionEvent event) {
        if (validateInput()) {
            Donation d = new Donation(

                    Integer.parseInt(tUtilisateur_id.getText()),
                    Integer.parseInt(tAmount.getText()),
                    tMessage.getText()
            );
            try {
                ds.create(d);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setContentText("Donation insérée avec succès!");
                alert.show();
                clear();
                initialize();
            } catch (SQLException e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Erreur lors de l'insertion de la donation: " + e1.getMessage());
                alert.show();
                throw new RuntimeException(e1);
            }
        }
    }

    private boolean validateInput() {
        if (tUtilisateur_id.getText().isEmpty() || tAmount.getText().isEmpty() || tMessage.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return false;
        }

        try {

            int utilisateurId = Integer.parseInt(tUtilisateur_id.getText());
            int amount = Integer.parseInt(tAmount.getText());
            // Add more specific validation rules as needed
            return true;
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setContentText("Les champs ID de l'événement, ID de l'utilisateur et Montant doivent être des nombres entiers.");
            alert.show();
            return false;
        }
    }
    @FXML
    void ModifierDonation(ActionEvent event) {
        Donation d = new Donation(Integer.parseInt(tID.getText()),Integer.parseInt(tEvent_id.getText()),Integer.parseInt(tUtilisateur_id.getText()),Integer.parseInt(tAmount.getText()),tMessage.getText());
        try {
            ds.update(d);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("User modifier avec succés!");
            alert.show();
            clear();

            initialize();
        } catch (SQLException e1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(e1.getMessage());
            alert.show();
            throw new RuntimeException(e1);
        }
    }

    @FXML
    void SupprimerDonation(ActionEvent event) {
        int id1 = Integer.parseInt(tID.getText());
        try {
            ds.delete(id1);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Donation supprimée avec succés!");
            alert.show();

            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(e.getMessage());
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() throws SQLException {
        try {
            ObservableList<Donation> observableliste = FXCollections.observableList(ds.read());
            tableviewDonation.setItems(observableliste);
            // Remplir les colonnes avec les propriétés des objets User
            ColID.setCellValueFactory(new PropertyValueFactory<>("id"));
            ColEvent.setCellValueFactory(new PropertyValueFactory<>("event_id"));
            ColUtilisateur.setCellValueFactory(new PropertyValueFactory<>("utilisateur_id"));
            ColAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            ColMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initData(int eventId) {
        tEvent_id.setText(String.valueOf(eventId));  // Set the event ID text field with the passed ID
    }
    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        Donation selectedEvent = tableviewDonation.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            tID.setText(String.valueOf(selectedEvent.getId()));
            tEvent_id.setText(String.valueOf(selectedEvent.getEvent_id()));
            tUtilisateur_id.setText(String.valueOf(selectedEvent.getUtilisateur_id()));
            tAmount.setText(String.valueOf(selectedEvent.getAmount()));
            tMessage.setText(selectedEvent.getMessage());
            tUtilisateur_id.setDisable(true);
            btnAjout.setDisable(true);
        }
    }
    void clear(){

        tEvent_id.setText(null);
        tUtilisateur_id.setText(null);
        tAmount.setText(null);
        tMessage.setText(null);
        btnAjout.setDisable(false);

    }
    @FXML
    void clearField(){
        clear();
    }


}



