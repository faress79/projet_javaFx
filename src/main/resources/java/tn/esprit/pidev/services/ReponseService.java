package tn.esprit.pidev.services;

import tn.esprit.pidev.interfaces.IReponseService;
import tn.esprit.pidev.models.Reponse;
import tn.esprit.pidev.utils.SQLConnector;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReponseService implements IReponseService {

    private final Connection cn;

    public ReponseService() {
        this.cn = SQLConnector.getInstance().getConnection();
    }

    @Override
    public int createReponse(Reponse reponse) {
        int reponseId = 0;
        try {
            PreparedStatement statement = cn.prepareStatement(
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
            Statement statement = cn.createStatement();
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
            PreparedStatement statement = cn.prepareStatement(
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
            PreparedStatement statement = cn.prepareStatement("SELECT * FROM reponse WHERE id = ?");
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
            PreparedStatement statement = cn.prepareStatement("DELETE FROM reponse WHERE id = ?");
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
            PreparedStatement statement = cn.prepareStatement("SELECT * FROM reponse WHERE reclamation_id = ?");
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
