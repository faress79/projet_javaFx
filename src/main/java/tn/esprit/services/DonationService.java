package tn.esprit.services;

import tn.esprit.models.Donation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonationService implements IService1 <Donation>{

    private static Connection connection;
    public DonationService(){
        connection = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void create(Donation donation) throws SQLException {
        String sql = "insert into donation ( event_id, utilisateur_id, amount, message) values ( ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {  //requête dynamiques précompilees
            ps.setInt(1, donation.getEvent_id());
            ps.setInt(2, donation.getUtilisateur_id());
            ps.setInt(3, donation.getAmount());
            ps.setString(4, donation.getMessage());
            ps.executeUpdate();
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Donation donation) throws SQLException {
        String sql = "update donation set event_id = ?, utilisateur_id = ?, amount = ?, message = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, donation.getEvent_id());
        ps.setInt(2, donation.getUtilisateur_id());
        ps.setInt(3, donation.getAmount());
        ps.setString(4, donation.getMessage());
        ps.setInt(5,donation.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from donation where id = ?";
        PreparedStatement ps =connection.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
    }



    @Override
    public List<Donation> read() throws SQLException {
        String sql = "select * from donation";  //hadhi requête SQL
        Statement statement = connection.createStatement();  //3malna connextion bel base de donne
        ResultSet rs = statement.executeQuery(sql);  //exécution taa requête sql w nhotouha fi rs (kan bech naamlou ajout wala modif wala sup nhotou executeUpdate fi blaset executeQuery)
        List <Donation> D = new ArrayList<>();
        while (rs.next()){
            Donation donation = new Donation();
            donation.setId(rs.getInt("id"));
            donation.setEvent_id(rs.getInt("event_id"));
            donation.setUtilisateur_id(rs.getInt("utilisateur_id"));
            donation.setAmount(rs.getInt("amount"));
            donation.setMessage(rs.getString("message"));
            D.add(donation);
        }
        return D;
    }


}
