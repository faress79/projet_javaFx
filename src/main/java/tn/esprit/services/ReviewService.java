package tn.esprit.services;

import tn.esprit.models.Evenment;
import tn.esprit.models.Review;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewService implements IService<Review> {



    public List<Review> selectidr() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String req = "SELECT id_review FROM review";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Review review = new Review();
            review.setId_review(rs.getInt("id_review"));
            reviews.add(review);
        }
        return reviews;
    }

    private Connection connection;

    public ReviewService(){
        connection = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void ajouter(Review review) throws SQLException {
        String req="INSERT INTO review (nbr_star,description,id_event) VALUES ('"+review.getNbr_star()+"','"+review.getDescription()+"','"+review.getId_event()+"')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);

    }

    @Override
    public void modifier(Integer id_event, Evenment evenment) throws SQLException {

    }


    @Override
    public void modifierReview(Integer id_review, Review review) throws SQLException {
        String req = "UPDATE review SET nbr_star = ? , description = ? , id_event = ? WHERE id_review = ?";
        PreparedStatement rss = connection.prepareStatement(req);
        rss.setInt(1, review.getNbr_star());
        rss.setString(2, review.getDescription());
        rss.setInt(3, review.getId_event());
        rss.setInt(4, review.getId_review());
        rss.executeUpdate();

    }

    @Override
    public void supprimerReview(int id_review) throws SQLException {
        String req = "DELETE FROM review WHERE id_review = ?";
        PreparedStatement rss = connection.prepareStatement(req);
        rss.setInt(1, id_review);
        rss.executeUpdate();

    }

    @Override
    public void supprimer(int id_event) throws SQLException {

    }

    @Override
    public List<Review> recuperer() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String req = "SELECT * FROM review";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Review review = new Review();
            review.setId_review(rs.getInt("id_review"));
            review.setNbr_star(rs.getInt("nbr_star"));
            review.setDescription(rs.getString("description"));
            review.setId_event(rs.getInt("id_event"));

            reviews.add(review);
        }
        return reviews;
    }





}
