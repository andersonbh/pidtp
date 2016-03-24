package com.andersoncarvalho.pidtp.misc;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.opencv_core.*;

import static org.bytedeco.javacpp.helper.opencv_imgproc.cvCalcHist;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * Created by anderson on 24/03/16.
 */

public class ProcessadorImagem {
    //Imagem principal carregada pelo caminho recebido
    private IplImage imagemPrincipal;

    //Imagem principal convertida em escala de cinza
    private IplImage imagemPrincipalEscalaCinza;

    //Histograma gerado da Imagem Principal
    private CvHistogram histograma;

    //Tamanho da Imagem principal
    private CvSize tamanhoDaImagem;

    public ProcessadorImagem( String caminhoPadrao, String nomeImagem) {
        String caminho = caminhoPadrao + nomeImagem + ".jpg";
        imagemPrincipal = cvLoadImage(caminho);
        tamanhoDaImagem = imagemPrincipal.cvSize();

        imagemPrincipalEscalaCinza =
            cvLoadImage(caminho, CV_LOAD_IMAGE_GRAYSCALE);

        gerarHistograma(imagemPrincipal);

//        salvarArquivo(caminhoPadrao + "histogramas/" + nomeImagem +".jpg",desenharHistograma());
    }

    private CvHistogram gerarHistograma(IplImage imagem) {

        IplImage mascara = new IplImage(null);
        int dims = 1;
        int[] numBins = {256};
        float minRange = 0.0f;
        float maxRange = 255.0f;

        int tipoHistograma = CV_HIST_ARRAY;
        float[][] ranges = {{minRange, maxRange}};
        CvHistogram hist = cvCreateHist(dims, numBins, tipoHistograma, ranges, 1);

        //Caso queira a normalizacao do histograma
        cvNormalizeHist(hist, 1.0);

        int accumulate = 0;
        IplImage[] novaImagen = {imagem.clone()};
        cvCalcHist(novaImagen, hist, accumulate, mascara);

        //Caso queira a normalizacao do histograma
        cvNormalizeHist(hist, 1.0);

        histograma = hist;

        return hist;

    }

    public CvHistogram getHistograma() {
        return histograma;
    }

//    public IplImage getImagemDoHistograma() {
//        return desenharHistograma(histograma,imagemPrincipal);
//    }

    public void salvarArquivo(String caminho, IplImage image) {
        cvSaveImage(caminho, image);
    }

//    private IplImage desenharHistograma(CvHistogram hist, IplImage image) {
//        int scaleX = 1;
//        int scaleY = 1;
//        int i;
//        float[] max_value = {0};
//        int[] int_value = {0};
//        cvGetMinMaxHistValue(hist, max_value, max_value, int_value, int_value);
//
//        IplImage imgHist = cvCreateImage(cvSize(256, image.height()), IPL_DEPTH_8U, 1);//cria a imagem pra armazenar o histograma
//        cvZero(imgHist);
//        CvPoint pts = new CvPoint(5);
//
//        for (i = 0; i < 256; i++) {//desenha o histograma
//            float value = opencv_legacy.cvQueryHistValue_1D(hist, i);
//            float nextValue = opencv_legacy.cvQueryHistValue_1D(hist, i + 1);
//
//            pts.position(0).x(i * scaleX).y(image.height() * scaleY);
//            pts.position(1).x(i * scaleX + scaleX).y(image.height() * scaleY);
//            pts.position(2).x(i * scaleX + scaleX).y((int) ((image.height() - nextValue * image.height() / max_value[0]) * scaleY));
//            pts.position(3).x(i * scaleX).y((int) ((image.height() - value * image.height() / max_value[0]) * scaleY));
//            pts.position(4).x(i * scaleX).y(image.height() * scaleY);
//            cvFillConvexPoly(imgHist, pts.position(0), 5, CvScalar.RED, CV_AA, 0);
//        }
//        return imgHist;
//    }
}
