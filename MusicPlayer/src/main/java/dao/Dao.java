package dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {
    boolean create(T object) throws SQLException;
    T read(K key) throws SQLException;
    T update(T object) throws SQLException;
    boolean delete(K key) throws SQLException;
    List<T> list() throws SQLException;
}
