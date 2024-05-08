package tn.esprit.models;

public class Review extends EventElement {
    private int id_review,nbr_star;
    private String description;
    private int id_event;

    public Review(int id_review, int nbr_star, String description, int id_event) {
        this.id_review = id_review;
        this.nbr_star = nbr_star;
        this.description = description;
        this.id_event = id_event;
    }
    public Review(int nbr_star, String description, int id_event) {
        this.nbr_star = nbr_star;
        this.description = description;
        this.id_event = id_event;
    }

    public Review() {

    }

    public  int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public int getNbr_star() {
        return nbr_star;
    }

    public void setNbr_star(int nbr_star) {
        this.nbr_star = nbr_star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id_review=" + id_review +
                ", nbr_star=" + nbr_star +
                ", description='" + description + '\'' +
                ", id_event=" + id_event +
                '}';
    }
}

