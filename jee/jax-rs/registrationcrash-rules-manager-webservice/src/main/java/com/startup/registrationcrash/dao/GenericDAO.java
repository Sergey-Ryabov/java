package com.startup.registrationcrash.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Сергей
 * @param <T>
 * @param <ID>
 */
public interface GenericDAO<T, ID extends Serializable> {

    T findById(ID id, boolean lock);

    List<T> findAll();
    
    List<T> findAllPaginated(int startIndex, int size);

    T persist(T entity);
    
    T merge(T entity);

    void remove(T entity);
    
    void removeById(ID id);
    
    void flush();

    void clear();

}
