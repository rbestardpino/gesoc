package com.testigos.gesoc.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.testigos.gesoc.persistence.EntityManagerFactory.MyEntitiyManagerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DAO<T> {

    protected Class<T> type;

    // Para métodos que no haya pasado a esta clase hacer
    // instancia.em.<metodo>, hay una banda pero pase los mas importantes
    // @PersistenceContext(unitName = "persistenceUnit")
    public EntityManager em;

    /**
     * Persiste un objeto a la base de datos
     */
    public void persist(Object entity) {
        createEntityManager();
        beginTransaction();
        em.persist(entity);
        commit();
        close();
    }

    /**
     * Elimina un objeto de la base de datos
     */
    public void remove(Object entity) {
        createEntityManager();
        beginTransaction();
        em.remove(entity);
        commit();
        close();
    }

    /**
     * Crea el entityManager
     */
    public void createEntityManager() {
        em = MyEntitiyManagerFactory.emf.createEntityManager();
    }

    /**
     * Antes de empezar a hacer cualquier cosa hay que ejecutar este metodo, es para
     * empezar la transaccion
     */
    public void beginTransaction() {
        em.getTransaction().begin();
    }

    /**
     * Commitea la transacción actual
     */
    public void commit() {
        em.getTransaction().commit();
    }

    /**
     * Rollbackea la transacción actual, casi no se usa
     */
    public void rollback() {
        em.getTransaction().rollback();
    }

    /**
     * Busca un objeto en la base de datos, hay otros finds pero son medio inutiles.
     * Devuelve null si no lo encuentra, CLAVE
     */
    public T find(Object primaryKey) {
        createEntityManager();
        beginTransaction();
        T t = em.find(type, primaryKey);
        if (t != null)
            t.getClass();
        commit();
        close();
        return t;
    }

    /**
     * Busca todos los objetos en la base de datos del tipo que le pases por
     * parametro
     */
    public List<T> findAll() {
        createEntityManager();
        beginTransaction();
        List<T> tList = createQuery("From " + type.getSimpleName()).getResultList();
        if (tList != null)
            tList.size();
        commit();
        close();
        return tList;
    }

    /**
     * Updatea el objecto que le estas pasando
     * 
     * @param object
     */
    public void update(Object object) {
        createEntityManager();
        beginTransaction();
        em.merge(object);
        em.flush();
        commit();
        close();
    }

    /**
     * Crea una query tipada con el tipo que le pases
     */
    public TypedQuery<T> createQuery(String qlString) {
        return em.createQuery(qlString, type);
    }

    /**
     * Apaga la entityManager, se debe usar siempre despues de terminar la ejecución
     * de cualquier bloque de conexión a la base de datos
     */
    public void close() {
        em.close();
    }

    public DAO(Class<T> type) {
        this.type = type;
    }

    public DAO() {
    }
}