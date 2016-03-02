package com.andersoncarvalho.pidtp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.andersoncarvalho.pidtp.dao.DAO;
import javax.inject.Scope;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by anderson on 02/03/16.
 */
@Service
@Scope("prototype")
public class RTCModel {
    @Autowired
    protected DAO DAO;
    @PersistenceContext
    protected EntityManager em;


    public void manipularImagem(Imagem img) {
        DAO.persist(img);
    }
}
