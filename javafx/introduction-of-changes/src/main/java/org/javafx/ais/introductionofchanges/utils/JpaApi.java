package org.javafx.ais.introductionofchanges.utils;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author Сергей
 */
public class JpaApi {

    static EntityManagerFactory emf;
    static EntityManager em;
    static EntityTransaction tx;

    public static void beginTransaction() {
        emf = Persistence.createEntityManagerFactory("introduction-of-changes");
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    public static void persist(Object object) {
        em.persist(object);
    }

    public static void merge(Object object) {
        em.merge(object);
    }

    public static void remove(Object object) {
        em.remove(object);
    }

    public static Object find(Class entityClass, Long id) {
        return em.find(entityClass, id);
    }

    public static TypedQuery createQuery(String query, Class aClass) {
        return em.createQuery(query, aClass);
    }

    public static List getResultListFromQuery(String query) {
        return em.createQuery(query).getResultList();
    }

    public static void closeTransaction() {
        tx.commit();
        em.close();
        emf.close();
    }

    public static void closeEntityManager() {
        em.close();
        emf.close();
    }

}
