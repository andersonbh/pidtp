package com.andersoncarvalho.pidtp.model;

import com.andersoncarvalho.pidtp.controller.data.DataResponse;
import com.andersoncarvalho.pidtp.entity.Imagem;
import com.andersoncarvalho.pidtp.misc.ProcessadorImagem;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anderson on 02/03/16.
 */
@Service
@Scope("prototype")
public class RICModel {
    @Autowired
    protected com.andersoncarvalho.pidtp.dao.DAO DAO;
    @PersistenceContext
    protected EntityManager em;

    //Pega o caminho da pasta webapp
    private ServletContext servletContext;


    public String listarImagens() {

        String bla = "dsadsa";
        File folder = new File("../../../webbapp/assets/images/imageorig");

        return bla;
    }

    public void processarImagem(String caminhoPadrao,String nomeImagem, boolean normalizar) throws IOException {
        ProcessadorImagem  pi = new ProcessadorImagem(caminhoPadrao ,nomeImagem, normalizar);

    }

    public float[] getHistogramaRGB(String caminhoPadrao,String nomeImagem, boolean normalizar) throws IOException {
        ProcessadorImagem  pi = new ProcessadorImagem(caminhoPadrao ,nomeImagem, normalizar);
        return pi.getRGBHistogramAsArray();
    }

    public float[] getHistogramaYUV(String caminhoPadrao,String nomeImagem, boolean normalizar) throws IOException {
        ProcessadorImagem  pi = new ProcessadorImagem(caminhoPadrao ,nomeImagem, normalizar);
        return pi.getYUVHistogramAsArray();
    }

    public float[] getHistogramaHSV(String caminhoPadrao,String nomeImagem, boolean normalizar) throws IOException {
        ProcessadorImagem  pi = new ProcessadorImagem(caminhoPadrao ,nomeImagem, normalizar);
        return pi.getHSVHistogramAsArray();
    }

    public void uploadImagem(String caminhopadrao, String dataURL){

        try {
        //  Ignora o inicio do dataURL e pega somente o base64 da iamgem
            dataURL = dataURL.substring(23);

            byte[] imageByteArray = Base64.decodeBase64((dataURL));

            FileOutputStream imageOutFile = new FileOutputStream(
                caminhopadrao + "principal.jpg");

            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Imagem não encontrada" + e);
        } catch (IOException ioe) {
            System.out.println("Erro ao ler a imagem " + ioe);
        }
    }

    public void calcEuclidiana(String caminhoPadrao) throws IOException {
        ProcessadorImagem pi;
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,"principal", false );
        double[] taxadeCorImgPrincipal = piPrincipal.getTaxadeCor();
        for(long i = 0; i < 1000; i++){
            pi = new ProcessadorImagem(caminhoPadrao,"" + i);
            Imagem temp = DAO.findById(Imagem.class, i);
            double bla = pi.calcularEuclidiana(taxadeCorImgPrincipal,pi.getTaxadeCor());
            temp.setDistanciaatual(bla);
            DAO.merge(temp);
        }
    }

    public Imagem  filtroMedia(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
         return piPrincipal.filtroMedia() ;
    }

    public Imagem  filtroMediana(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.filtroMediana() ;
    }

    public Imagem  filtroMaximo(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.filtroMaximo() ;
    }

    public Imagem  filtroMinimo(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.filtroMinimo() ;
    }

    public Imagem  transformadaBinarizacao(String caminhoPadrao, String nomeImagem, int variaveis) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.transformadaBinarizacao(variaveis) ;
    }

    public Imagem filtroLaplaciano(String caminhoPadrao, String nomeImagem, int variaveis) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.filtroLaplaciano(variaveis) ;
    }

    public Imagem filtroSobel(String caminhoPadrao, String nomeImagem, int variaveis) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.filtroSobel(variaveis) ;
    }

    public Imagem filtroCanny(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.filtroCanny();
    }

    public Imagem  transformadaMonocromatico(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.transformadaMonocromatico() ;
    }

    public Imagem  transformadaDeFourier(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.transformadaDeFourier() ;
    }

    public Imagem filtroNegativo(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.filtroNegativo();
    }

    public Imagem girarImagem(String caminhoPadrao, String nomeImagem, int variaveis) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.girar(variaveis);
    }

    public Imagem espelharImagem(String caminhoPadrao, String nomeImagem) throws IOException {
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,nomeImagem, false );
        return piPrincipal.espelhar();
    }

    public void calcXadrez(String caminhoPadrao) throws IOException {
        ProcessadorImagem pi;
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,"principal", false);
        double[] taxadeCorImgPrincipal = piPrincipal.getTaxadeCor();
        for(long i = 0; i < 1000; i++){
            pi = new ProcessadorImagem(caminhoPadrao,"" + i);
            Imagem temp = DAO.findById(Imagem.class, i);
            double bla = pi.calcularXadrez(taxadeCorImgPrincipal,pi.getTaxadeCor());
            temp.setDistanciaatual(bla);
            DAO.merge(temp);
        }
    }

    public void calcCoseno(String caminhoPadrao) throws IOException {
        ProcessadorImagem pi;
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,"principal", false);
        double[] taxadeCorImgPrincipal = piPrincipal.getTaxadeCor();
        for(long i = 0; i < 1000; i++){
            pi = new ProcessadorImagem(caminhoPadrao,"" + i);
            Imagem temp = DAO.findById(Imagem.class, i);
            double bla = pi.calcularCoseno(taxadeCorImgPrincipal,pi.getTaxadeCor());
            temp.setDistanciaatual(bla);
            DAO.merge(temp);
        }
    }

    public List<Imagem> listarImagensOrdenadas(){
        Query query = em.createQuery("SELECT im FROM imagem im ORDER BY -distanciaatual");
        return query.getResultList();
    }

    public void calcManhattan(String caminhoPadrao) throws IOException {
        ProcessadorImagem pi;
        ProcessadorImagem piPrincipal = new ProcessadorImagem(caminhoPadrao,"principal", false);
        double[] taxadeCorImgPrincipal = piPrincipal.getTaxadeCor();
        for(long i = 0; i < 1000; i++){
            pi = new ProcessadorImagem(caminhoPadrao,"" + i);
            Imagem temp = DAO.findById(Imagem.class, i);
            double bla = pi.calcularManhattan(taxadeCorImgPrincipal,pi.getTaxadeCor());
            temp.setDistanciaatual(bla);
            DAO.merge(temp);
        }
    }

    public void calcularDadosDoBanco(String caminhopadrao) throws IOException {
            ProcessadorImagem pi;

        for(long i = 0; i < 1000; i++){
            pi = new ProcessadorImagem(caminhopadrao,"" + i);
//            Imagem img = DAO.findById(Imagem.class, i);
//            Query query = em.createQuery("update imagem set taxadecor = ARRAY[:taxa] where id = :id");
//            query.setParameter("taxa", pi.getTaxadeCor());
//            query.setParameter("id", i);
//            int result = query.executeUpdate();
//            System.out.println(result);
//            img.setHistogramahsv(pi.getHSVHistogramAsArray());
//            img.setHistogramayuv(pi.getYUVHistogramAsArray());
//            img.setHistogramargb(pi.getRGBHistogramAsArray());
//            DAO.merge(img);
        }
    }

}
