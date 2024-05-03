package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.user;
import tn.esprit.utils.MyDataBase;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import tn.esprit.models.PasswordResetToken;

import org.mindrot.jbcrypt.BCrypt;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

public abstract class ServicePersonne implements IService<user> {

    private Connection cnx ;

    public ServicePersonne(){
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void add(user user) {
        //1-req sql INSERT
        //2-executer req
        String qry ="INSERT INTO `user`(`nom`, `prenom`, `email`, `password`) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm =cnx.prepareStatement(qry);

            pstm.setString(1,user.getNom());
            pstm.setString(2,user.getPrenom());
            pstm.setString(3,user.getEmail());
            pstm.setString(4,user.getPassword());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public int authenticate(String email, String password) {
        try {
            PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM user WHERE email = ? ");
            stmt.setString(1, email);
//           stmt.setString(2, password);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword))

                    return rs.getInt("id_user");


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean exsitemail(String email) {
        try {
            PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM user WHERE email = ? ");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


   /* @Override
    public  ArrayList<user> getAll()  {
        //1-req SELECT
        //2-recuperation de la base de donné remplissage dans Array
        //3-retour du tableau done
        ArrayList<user> users = new ArrayList<>();
        String qry ="SELECT * FROM user";
        try {
            Statement stm = cnx.createStatement();

            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                user p = new user();
                p.setId_user(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));

                users.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return users
                ;
    }*/
    public  ArrayList<user> getAll() {
        String requete = "select * from user";
        ArrayList<user> list = new ArrayList<>();

        try {
            Statement stm = cnx.createStatement();

            ResultSet rs = stm.executeQuery(requete);
            while (rs.next()) {
                list.add(new user(rs.getInt("id_user"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public String roles(int id_user) {
        try {

            PreparedStatement stmt1 = cnx.prepareStatement("SELECT roles FROM user where id_user=?");
            stmt1.setInt(1, id_user);

            ResultSet rs = stmt1.executeQuery();

            while (rs.next()) {
                return rs.getString("roles");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "not found";
    }


   /* @Override
    public void update(Personne personne) {

    }

    @Override
    public boolean delete(Personne personne) {
        return false;
    }*/
   @Override
   public void delete(user user) {
       String requete = "DELETE FROM user WHERE id_user = " + user.getId_user() + "";

       try {
           Statement stm = cnx.createStatement();

           stm.executeUpdate(requete);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

   }

    public user getUserByEmail(String email) {
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM user WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                return new user (resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("id_user"),
//                        resultSet.getInt("tel"),
//                        resultSet.getString("niveau"),
//                        resultSet.getString("image"),
                        resultSet.getString("roles"));


            }
//            resultSet.close();
//            statement.close();
            return new user();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserEmailByToken(String token) {
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT email FROM reset_token WHERE token = ?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                return resultSet.getString("email");

            }
//            resultSet.close();
//            statement.close();
            return "not found";
        } catch (SQLException e) {
            e.printStackTrace();
            return "sstem error";
        }
    }
    public void insertPasswordResetToken(int userId, String token, long timestamp, String email) {
        try {
            PreparedStatement stmt = cnx.prepareStatement("INSERT INTO reset_token(token,user_id, timestamp,email) VALUES (?, ?, ?,?)");
            stmt.setInt(2, userId);
            stmt.setString(1, token);
            stmt.setTimestamp(3, new Timestamp(timestamp));
            stmt.setString(4, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // handle the exception as appropriate for your application
        }
    }
    public PasswordResetToken getPasswordResetToken(String token) {
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM reset_token WHERE token = ?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            PasswordResetToken passwordResetToken = null;

            if (resultSet.next()) {
                passwordResetToken = new PasswordResetToken();
                passwordResetToken.setToken(resultSet.getString("token"));
                passwordResetToken.setUserId(resultSet.getInt("user_id"));
                System.out.println(resultSet.getInt("user_id"));
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                passwordResetToken.setTimestamp(localDateTime);
                System.out.println(resultSet.getString("token"));
                return passwordResetToken;

            }
//            resultSet.close();
//            statement.close();
            return passwordResetToken;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public int session(int id) {
        try {
            if (id != 0)
                return id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void updateUserPassword(int userId, String newPassword) {

        PreparedStatement stmt = null;
        try {
            stmt = cnx.prepareStatement("UPDATE user SET password = ? WHERE id_user = ?");
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            stmt.setString(1, hashedPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
//                    stmt.close();
                }
                if (cnx != null) {
//                    conn.close();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
    public void deleteToken(int id) {

        PreparedStatement stmt = null;
        try {
            stmt = cnx.prepareStatement("DELETE FROM reset_token WHERE user_id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<user> getAllll() {
        List<user> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                user user = new user();
                user.setId_user(resultSet.getInt("id_user"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setRoles(resultSet.getString("roles"));
                user.setEmail(resultSet.getString("email"));



                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
