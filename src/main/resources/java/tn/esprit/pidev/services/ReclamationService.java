package tn.esprit.pidev.services;

import tn.esprit.pidev.interfaces.IReclamationService;
import tn.esprit.pidev.models.Reclamation;
import tn.esprit.pidev.utils.SQLConnector;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService implements IReclamationService {

    private final Connection cn;

    public ReclamationService() {
        this.cn = SQLConnector.getInstance().getConnection();
    }

    @Override
    public int createReclamation(Reclamation reclamation) {
        int reclamationId = 0;
        try {
            PreparedStatement statement = cn.prepareStatement(
                    "INSERT INTO reclamation (description, sujet, client_id, created_at) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, reclamation.getDescription());
            statement.setString(2, reclamation.getSujet());
            statement.setInt(3, reclamation.getClientId());
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    reclamationId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getCause());
        }
        return reclamationId;
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        List<Reclamation> reclamationsList = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reclamation");

            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setSujet(resultSet.getString("sujet"));
                reclamation.setClientId(resultSet.getInt("client_id"));
                reclamation.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                reclamationsList.add(reclamation);
            }

        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return reclamationsList;
    }

    @Override
    public int updateReclamation(Reclamation reclamation, int id) {
        int result = 0;
        try {
            PreparedStatement statement = cn.prepareStatement(
                    "UPDATE reclamation SET description = ?, sujet = ?, client_id = ? WHERE id = ?"
            );
            statement.setString(1, reclamation.getDescription());
            statement.setString(2, reclamation.getSujet());
            statement.setInt(3, reclamation.getClientId());
            statement.setInt(4, id);

            result = statement.executeUpdate();
            if (result > 0) {
            }
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return result;
    }

    @Override
    public Reclamation getReclamationById(int id) {
        Reclamation reclamation = null;
        try {
            PreparedStatement statement = cn.prepareStatement("SELECT * FROM reclamation WHERE id = ?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setSujet(resultSet.getString("sujet"));
                reclamation.setClientId(resultSet.getInt("client_id"));
                reclamation.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }

        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return reclamation;
    }

    @Override
    public int removeReclamation(int id) {
        int result = 0;
        try {
            PreparedStatement statement = cn.prepareStatement("DELETE FROM reclamation WHERE id = ?");
            statement.setInt(1, id);

            result = statement.executeUpdate();
            if (result > 0) {
            }
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return result;
    }
}
