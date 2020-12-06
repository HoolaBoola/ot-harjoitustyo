package dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {
    boolean create(T object);
    T read(K key);
    T update(T object);
    boolean delete(K key);
    List<T> list();
}
