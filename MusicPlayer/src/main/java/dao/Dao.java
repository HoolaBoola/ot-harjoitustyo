package dao;

import java.util.List;

public interface Dao<T, K> {
    boolean create(T object);
    T read(K key);
    T update(T object);
    boolean delete(T key);
    List<T> list();
}
