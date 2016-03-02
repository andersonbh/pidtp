package com.andersoncarvalho.pidtp.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Created by anderson on 02/03/16.
 */
@Repository
@Scope("prototype")
@Transactional
public class CrudDAO implements DAO {
    @PersistenceContext
    private EntityManager em;
    @Override
    public void persist(Object entity){
        em.persist(entity);
    }
    @Override
    public Object merge(Object entity){
        return em.merge(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public <T> T findById(final Class<T> type, final Long id){
        return (T) em.find(type, id);
    }
    @Override
    public <T> void remove(final T entity){
        em.remove(entity);
    }
    @Override
    public <T> void removeById(Class<T> type, Long id){
        T entity = findById(type, id);
        em.remove(entity);
    }
    @Override
    public <T> void removeById(Class<T> type, Integer id){
        T entity = findById(type, id);
        em.remove(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public <T> Collection<T> findAll(Class<T> type){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        Root<T> from = criteriaQuery.from(type);
        CriteriaQuery<T> select = criteriaQuery.select(from);
        TypedQuery<T> query = em.createQuery(select);
        return (Collection<T>) query.getResultList();
    }

    @Override
    public <T> T findById(Class<T> type, Integer id) {
        return em.find(type, id);
    }
}
