package com.andersoncarvalho.pidtp.misc;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.opencv_core.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.bytedeco.javacpp.helper.opencv_imgproc.cvCalcHist;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_legacy.cvQueryHistValue_1D;

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

    int numberOfBins = 256;
    float _minRange = 0.0f;
    float _maxRange = 255.0f;

    public ProcessadorImagem( String caminhoPadrao, String nomeImagem) {
        String caminho = caminhoPadrao + nomeImagem + ".jpg";
        imagemPrincipal = cvLoadImage(caminho);
        tamanhoDaImagem = imagemPrincipal.cvSize();

        imagemPrincipalEscalaCinza =
            cvLoadImage(caminho, CV_LOAD_IMAGE_GRAYSCALE);

//        getHSVHistogram(imagemPrincipal, 32);

        BufferedImage imagemHistograma = getHistogramImage(getHueHistogram(imagemPrincipal));
        salvarArquivo(caminhoPadrao + "histogramas/" + nomeImagem +".jpg",imagemHistograma);
    }

    public CvHistogram getHistogram(IplImage image, IplImage mask) {

        int dims = 1;
        int[] sizes = new int[]{numberOfBins};
        float[][] ranges = new float[][]{new float[]{_minRange, _maxRange}};

        CvHistogram hist = cvCreateHist(dims, sizes, CV_HIST_ARRAY, ranges, 1);

        int accumulate = 0;
        cvCalcHist(new IplImage[]{image}, hist, accumulate, mask);

        return hist;
    }

    /**
     * Fonction pour la reconnaissance faciale
     * @param image IplImage
     * @return CvHistogram
     */
    private static CvHistogram getHueHistogram(IplImage image) {
        if (image == null || image.nChannels() < 1) {
            new Exception("Erreur!");
        }
        IplImage greyImage = cvCreateImage(image.cvSize(), image.depth(), 1);
        cvCvtColor(image, greyImage, CV_RGB2GRAY);

        //bins and value-range
        int numberOfBins = 256;
        float minRange = 0f;
        float maxRange = 255f;
        // Allocate histogram object
        int dims = 1;
        int[] sizes = new int[]{numberOfBins};
        int histType = CV_HIST_ARRAY;
        float[] minMax = new float[]{minRange, maxRange};
        float[][] ranges = new float[][]{minMax};
        int uniform = 1;
        CvHistogram hist = cvCreateHist(dims, sizes, histType, ranges, uniform);
        // Compute histogram
        int accumulate = 0;
        IplImage mask = null;
        IplImage[] aux = new IplImage[]{greyImage};

        cvCalcHist(aux, hist, accumulate, null);
        cvNormalizeHist(hist, 1);

        cvGetMinMaxHistValue(hist, minMax, minMax, sizes, sizes);
        return hist;
    }

    public CvHistogram getHSVHistogram(final IplImage image, final int minSaturation) {

        // Converte a imagem RGB para HSV
        IplImage hsvImage = AbstractIplImage.create(cvGetSize(image), image.depth(), 3);

        cvCvtColor(image, hsvImage, CV_BGR2HSV);

        // Divide os 3 canais em 3 imagens
        IplImage[] hsvChannels = splitChannels(hsvImage);
        IplImage saturationMask = null;

        if (minSaturation > 0) {
            saturationMask = AbstractIplImage.create(cvGetSize(hsvImage)
                , IPL_DEPTH_8U, 1);
            cvThreshold(hsvChannels[1], saturationMask, minSaturation, 255
                , CV_THRESH_BINARY);
        }

        // Compute histogram of the hue channel
        return getHistogram(hsvChannels[0], saturationMask);

    }

    public CvHistogram getRGBHistogram(final IplImage image, final int minSaturation) {


        // Split the 3 channels into 3 images
        IplImage[] hsvChannels = splitChannels(image);
        IplImage saturationMask = null;

        if (minSaturation > 0) {
            saturationMask = AbstractIplImage.create(cvGetSize(image)
                , IPL_DEPTH_8U, 1);
            cvThreshold(hsvChannels[1], saturationMask, minSaturation, 255
                , CV_THRESH_BINARY);
        }

        // Compute histogram of the hue channel
        return getHistogram(hsvChannels[0], saturationMask);

    }

    private static IplImage[] splitChannels(final IplImage src) {

        CvSize size = cvGetSize(src);

        IplImage channel0 = AbstractIplImage.create(size, src.depth(), 1);
        IplImage channel1 = AbstractIplImage.create(size, src.depth(), 1);
        IplImage channel2 = AbstractIplImage.create(size, src.depth(), 1);

        cvSplit(src, channel0, channel1, channel2, null);

        return new IplImage[]{channel0, channel1, channel2};
    }

    public void salvarArquivo(String caminho, IplImage image) {
        cvSaveImage(caminho, image);
    }

    public void salvarArquivo(String caminho, BufferedImage image) {
        File outputfile = new File(caminho);
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Computes histogram of an image.
     * @return histogram represented as an array
     */
    public float [] getHistogramAsArray(CvHistogram histograma){

        // Extract values to an array
        float[] dest = new float[numberOfBins];
        for (int bin = 0; bin < numberOfBins; bin++) {
            dest[bin] = cvQueryHistValue_1D(histograma, bin);
        }

        // Release the memory allocated for histogram
        cvReleaseHist(histograma);

        return dest;
    }

    public BufferedImage getHistogramImage(CvHistogram histograma) {

        // Output image size
        int width = numberOfBins;
        int height = numberOfBins;

        float[] hist = getHistogramAsArray(histograma);
        // Set highest point to 90% of the number of bins
        double scale = 0.9 / hist.length * height;

        // Create a color image to draw on
        BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = canvas.createGraphics();

        // Paint background
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Draw a vertical line for each bin
        g.setPaint(Color.BLUE);
        for (int bin = 0; bin < numberOfBins; bin++) {
            int h = (int) Math.round(hist[bin] * scale);
            g.drawLine(bin, height - 1, bin, height - h - 1);
        }

        // Cleanup
        g.dispose();

        return canvas;
    }


}
