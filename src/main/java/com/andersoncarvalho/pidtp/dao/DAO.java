package com.andersoncarvalho.pidtp.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by anderson on 02/03/16.
 */
public interface DAO {
    void persist(Object entity);

    Object merge(Object entity);

    @Transactional(readOnly = true)
    <T> T findById(Class<T> type, Long id);

    <T> void remove(T entity);

    <T> void removeById(Class<T> type, Long id);

    /**
     * Remove uma entidade pelo seu ID
     * @param type A classe entidade a ser removida
     * @param id  O ID da entidade a ser removida
     * @param <T>
     */
    <T> void removeById(Class<T> type, Integer id);

    @Transactional(readOnly = true)
    <T> Collection<T> findAll(Class<T> type);

    <T> T findById(Class<T> type, Integer id);
}
