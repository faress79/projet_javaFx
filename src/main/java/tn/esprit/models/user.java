package tn.esprit.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author fares
 */
public class user {
    public int id_user;
   private String cin;
    private String nom, prenom;
    private String username;
    private String roles;
    private String email;
    private String password;


    //constructeur par defaut
    public user(String string, String resultSetString, String email, String mdp, int idUser, int tel, String nom, String prenom, String role) {
    }

    public user(int id_user, String nom, String prenom, String username) {
        this.id_user = id_user;
    }

    public user(String nom, String prenom, String username, String roles, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.password = password;
    }


    //constructur parametre

    public user(int id_user, String cin, String nom, String prenom, String username, String roles, String email, String password) {
        this.id_user = id_user;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.password = password;


    }

    public user(String cin, String nom, String prenom, String username, String roles, String email, String password) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.password = password;

    }

    public user() {

    }

    public user(String idUser, String nom, String prenom, String roles, int email, String password) {
    }

    public static void add(user p) {
    }


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String nom) {
        this.username = username;
    }

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String role) {
        this.roles = role;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {


        return "user{" +
                "id_user=" + id_user +
                ", cin=" + cin +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }

}

















