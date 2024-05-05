package tn.esprit.services;

import tn.esprit.interfaces.IReponseService;
import tn.esprit.models.Reponse;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReponseService implements IReponseService {

    private final Connection cnx;

    public ReponseService(){
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public int createReponse(Reponse reponse) {
        int reponseId = 0;
        try {
            PreparedStatement statement = cnx.prepareStatement(
                    "INSERT INTO reponse (description, reclamation_id, created_at) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, reponse.getDescription());
            statement.setInt(2, reponse.getReclamationId());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    reponseId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return reponseId;
    }

    @Override
    public List<Reponse> getAllReponses() {
        List<Reponse> reponsesList = new ArrayList<>();
        try {
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reponse");

            while (resultSet.next()) {
                Reponse reponse = new Reponse();
                reponse.setId(resultSet.getInt("id"));
                reponse.setDescription(resultSet.getString("description"));
                reponse.setReclamationId(resultSet.getInt("reclamation_id"));
                reponse.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                reponsesList.add(reponse);
            }

        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return reponsesList;
    }

    @Override
    public int updateReponse(Reponse reponse, int id) {
        int result = 0;
        try {
            PreparedStatement statement = cnx.prepareStatement(
                    "UPDATE reponse SET description = ?, reclamation_id = ? WHERE id = ?"
            );
            statement.setString(1, reponse.getDescription());
            statement.setInt(2, reponse.getReclamationId());
            statement.setInt(3, id);

            result = statement.executeUpdate();
            if (result > 0) {
            }
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return result;
    }

    @Override
    public Reponse getReponseById(int id) {
        Reponse reponse = null;
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM reponse WHERE id = ?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reponse = new Reponse();
                reponse.setId(resultSet.getInt("id"));
                reponse.setDescription(resultSet.getString("description"));
                reponse.setReclamationId(resultSet.getInt("reclamation_id"));
                reponse.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }

        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return reponse;
    }

    @Override
    public int removeReponse(int id) {
        int result = 0;
        try {
            PreparedStatement statement = cnx.prepareStatement("DELETE FROM reponse WHERE id = ?");
            statement.setInt(1, id);

            result = statement.executeUpdate();
            if (result > 0) {
            }
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return result;
    }

    public List<Reponse> getReponsesForReclamation(int reclamationId) {
        List<Reponse> reponsesList = new ArrayList<>();
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM reponse WHERE reclamation_id = ?");
            statement.setInt(1, reclamationId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Reponse reponse = new Reponse();
                reponse.setId(resultSet.getInt("id"));
                reponse.setDescription(resultSet.getString("description"));
                reponse.setReclamationId(resultSet.getInt("reclamation_id"));
                reponse.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                reponsesList.add(reponse);
            }

        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return reponsesList;
    }
}
