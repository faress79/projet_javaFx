package tn.esprit.models;

public class Client {
    private String nom,prenom;
    private Integer age,poids,id_c;

    public Client(String nom, String prenom, Integer age, Integer poids, Integer id_c) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.poids = poids;
        this.id_c = id_c;
    }
    public Client(String nom,String prenom, Integer age , Integer poids ) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.poids = poids;

    }
    public Client() {

    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getPoids() {
        return poids;
    }

    public void setPoids(Integer poids) {
        this.poids = poids;
    }

    public Integer getId_c() {
        return id_c;
    }

    public void setId_c(Integer id_c) {
        this.id_c = id_c;
    }

}
