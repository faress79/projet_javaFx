package tn.esprit.models;

import java.util.Date;


public class Evenment extends EventElement {
    private int id_event;
    private String nom_event;
    private Date date_event;
    private String lieu_event,nom_star;

    private String image;

    public Evenment(int id_event, String nom_event, Date date_event, String lieu_event, String nom_star,String image) {
        this.id_event = id_event;
        this.nom_event = nom_event;
        this.date_event = date_event;
        this.lieu_event = lieu_event;
        this.nom_star = nom_star;
        this.image = image;
       // this.reviews = new ArrayList<>(); // Initialisation de la liste des avis
    }

    public Evenment(String nom_event, Date date_event, String lieu_event, String nom_star,String image) {
        this.nom_event = nom_event;
        this.date_event = date_event;
        this.lieu_event = lieu_event;
        this.nom_star = nom_star;
        this.image = image;
    }
    public Evenment() {

    }

   /* public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>(); // Initialisation de la liste des avis si elle est null
        }
        this.reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }*/

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getNom_event() {
        return nom_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public Date getDate_event() {
        return date_event;
    }

    public void setDate_event(Date date_event) {
        this.date_event = date_event;
    }

    public String getLieu_event() {
        return lieu_event;
    }

    public void setLieu_event(String lieu_event) {
        this.lieu_event = lieu_event;
    }

    public String getNom_star() {
        return nom_star;
    }

    public void setNom_star(String nom_star) {
        this.nom_star = nom_star;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
            this.image = image;
    }




    @Override
    public String toString(){
        return "Evenment{" +
                "id_event=" + id_event +
                ", nom_event='" + nom_event + '\'' +
                ", date_event='" + date_event + '\'' +
                ", lieu_event='" + lieu_event + '\'' +
                ", nom_star='" + nom_star + '\'' +
                ", image='" + image + '\'' +
                '}';
    }


}
