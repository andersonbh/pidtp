package com.andersoncarvalho.pidtp.model;

import com.andersoncarvalho.pidtp.entity.Imagem;
import com.andersoncarvalho.pidtp.misc.ProcessadorImagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.andersoncarvalho.pidtp.dao.DAO;
import javax.inject.Scope;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anderson on 02/03/16.
 */
@Service
public class RICModel {
    @Autowired
    protected DAO DAO;
    @PersistenceContext
    protected EntityManager em;

    //Pega o caminho da pasta webapp
    private ServletContext servletContext;


    public String listarImagens() {

        String bla = "dsadsa";
        File folder = new File("../../../webbapp/assets/images/imageorig");
        File[] listOfFiles = folder.listFiles();

//        for (int i = 0; i < listOfFiles.length; i++) {
//            if (listOfFiles[i].isFile()) {
//                results.add(listOfFiles[i].getName());
//                System.out.println("File " + listOfFiles[i].getName());
//            } else if (listOfFiles[i].isDirectory()) {
//                System.out.println("Directory " + listOfFiles[i].getName());
//            }
//        }
        return bla;
    }

    public void processarImagem(String caminhoPadrao,String nomeImagem, boolean normalizar) throws IOException {
        ProcessadorImagem  pi = new ProcessadorImagem(caminhoPadrao ,nomeImagem, normalizar);

    }

    public void calcularDadosDoBanco(String caminhopadrao) throws IOException {
            ProcessadorImagem pi;

        for(long i = 0; i < 1000; i++){
            pi = new ProcessadorImagem(caminhopadrao,"" + i);
//            Imagem img = DAO.findById(Imagem.class, i);
//            img.setTaxadecor(pi.getTaxadeCor());
//            img.setHistogramahsv(pi.getHSVHistogramAsArray());
//            img.setHistogramayuv(pi.getYUVHistogramAsArray());
//            img.setHistogramargb(pi.getRGBHistogramAsArray());
//            DAO.merge(img);
        }
    }
}
