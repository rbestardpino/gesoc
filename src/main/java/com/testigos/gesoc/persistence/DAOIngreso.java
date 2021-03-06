package com.testigos.gesoc.persistence;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;

import com.testigos.gesoc.model.domain.ingresos.Ingreso;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

@Repository
public class DAOIngreso extends DAO<Ingreso> {

    public DAOIngreso() {
        super(Ingreso.class);
    }

    public List<Ingreso> findAllConEgresos() {
        createEntityManager();
        beginTransaction();
        List<Ingreso> tList = createQuery("From " + type.getSimpleName()).getResultList();
        if (tList != null)
            tList.size();
        for (Ingreso i : tList)
            Hibernate.initialize(i.getEgresosAsociados());
        commit();
        close();
        return tList;
    }

    public List<Ingreso> findAllConProyecto() {
        createEntityManager();
        beginTransaction();
        List<Ingreso> tList = createQuery("From " + type.getSimpleName()).getResultList();
        if (tList != null)
            tList.size();
        for (Ingreso i : tList)
            Hibernate.initialize(i.getProyectoAsociado());
        commit();
        close();
        return tList;
    }

    public List<Ingreso> findAllSinProyecto() {
        createEntityManager();
        beginTransaction();
        List<Ingreso> tList = createQuery("From " + type.getSimpleName()).getResultList();
        if (tList != null)
            tList.size();
        for (Ingreso i : tList)
            Hibernate.initialize(i.getProyectoAsociado());
        commit();
        close();
        return tList.stream().filter(i -> i.getProyectoAsociado() == null).collect(Collectors.toList());
    }

    public void updateDoc(Ingreso i) {
        createEntityManager();
        beginTransaction();
        Query q = em.createQuery("UPDATE Ingreso i set i.proyectoAsociado = :proy where i.id = :id");
        q.setParameter("proy", i.getProyectoAsociado());
        q.setParameter("id", i.getId());
        q.executeUpdate();
        em.getTransaction().commit();
        close();
    }

}
