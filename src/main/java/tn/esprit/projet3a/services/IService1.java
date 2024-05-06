package tn.esprit.projet3a.services;
import tn.esprit.projet3a.models.Evenment;
import tn.esprit.projet3a.models.Review;

import java.sql.SQLException;
import java.util.List;

public interface IService1<T> {

    void create(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> read() throws SQLException;


}
