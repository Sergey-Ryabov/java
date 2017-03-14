package com.startup.registrationcrash.dao.jpa;

import com.startup.registrationcrash.dao.GenericDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Сергей
 * @param <T>
 * @param <ID>
 */
public abstract class GenericDAOJpa<T, ID extends Serializable> implements GenericDAO<T, ID> {

    private final Class<T> persistentClass;
    @PersistenceContext
    private EntityManager entityManager;

    public GenericDAOJpa() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    public T findById(ID id, boolean lock) {
        T entity = null;
        try {

//        if (lock) {
//            entity = (T) getEntityManager().find(getPersistentClass(), id, LockModeType.UPGRADE);
//        } else {
            entity = getEntityManager().find(getPersistentClass(), id);
//        }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return findByQuery("from " + persistentClass.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public List<T> findAllPaginated(int startIndex, int size) {
        return createQuery("from " + persistentClass.getSimpleName())
                .setFirstResult(startIndex).setMaxResults(size).getResultList();
    }

    @SuppressWarnings("unchecked")
    public T persist(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public T merge(T entity) {
        getEntityManager().merge(entity);
        return entity;
    }

    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

    public void removeById(ID id) {
        remove(getEntityManager().find(getPersistentClass(), id));
    }

    public void flush() {
        getEntityManager().flush();
    }

    public void clear() {
        getEntityManager().clear();
    }

    protected List<T> findByQuery(String query) {
        return getEntityManager().createQuery(query).getResultList();
    }

    protected Query createQuery(String query) {
        return getEntityManager().createQuery(query);
    }

}
