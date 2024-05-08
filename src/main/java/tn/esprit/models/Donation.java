package tn.esprit.models;

public class Donation extends EventElement {
    int id, event_id, utilisateur_id, amount;
    String message;


    public Donation(int id, int event_id, int utilisateur_id, int amount, String message) {
        this.id = id;
        this.event_id = event_id;
        this.utilisateur_id = utilisateur_id;
        this.amount = amount;
        this.message = message;
    }

    public Donation( int utilisateur_id, int amount, String message) {

        this.utilisateur_id = utilisateur_id;
        this.amount = amount;
        this.message = message;
    }

    public Donation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", utilisateur_id=" + utilisateur_id +
                ", amount=" + amount +
                ", message='" + message + '\'' +
                '}';
    }
}
