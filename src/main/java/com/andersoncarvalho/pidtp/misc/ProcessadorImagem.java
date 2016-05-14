package com.andersoncarvalho.pidtp.misc;

import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacv.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    //Imagem principal convertida para HSV
    private IplImage imagemPrincipalHSV;

    //Imagem principal convertida para YUV
    private IplImage imagemPrincipalYUV;

    //Tamanho da Imagem principal
    private CvSize tamanhoDaImagem;

    private String caminhoPadrao;

    private boolean normalizar;

    private int numberOfBins = 256;
    private float _minRange = 0.0f;
    private float _maxRange = 255.0f;

    public ProcessadorImagem( String caminhoPadrao, String nomeImagem) throws IOException {
        String caminho = caminhoPadrao + nomeImagem + ".jpg";
        this.caminhoPadrao = caminhoPadrao;
        imagemPrincipal = cvLoadImage(caminho);
        tamanhoDaImagem = imagemPrincipal.cvSize();
        //Carrega a imagem principal como escalas de cinza
        imagemPrincipalEscalaCinza =
            cvLoadImage(caminho, CV_LOAD_IMAGE_GRAYSCALE);

        // Converte a imagem principal para HSV
        imagemPrincipalHSV = cvCreateImage(tamanhoDaImagem, 8, 3);
        cvCvtColor(imagemPrincipal, imagemPrincipalHSV, CV_BGR2HSV);

        // Converte a imagem principal para YUV
        imagemPrincipalYUV = cvCreateImage(tamanhoDaImagem, 8, 3);
        cvCvtColor(imagemPrincipal, imagemPrincipalYUV, CV_BGR2YCrCb);
        String caminhoSalvar = caminhoPadrao + "histogramas/";

        if(normalizar){
            caminhoSalvar += "normalizados/";
        }

        //Salva o histograma YUV
        salvarArquivo(caminhoSalvar+ nomeImagem +"_yuv.jpg",getHistogramImage(imagemPrincipalYUV,normalizar));

        //Salva o histograma HSV
        salvarArquivo(caminhoSalvar + nomeImagem +"_hsv.jpg",getHistogramImage(imagemPrincipalHSV,normalizar));

        //Salva o histograma de escalas de cinza
        salvarArquivo(caminhoSalvar + nomeImagem +"_cinza.jpg",getHistogramImage(imagemPrincipalEscalaCinza,normalizar ));

        //Salva o histograma RGB
        salvarArquivo(caminhoSalvar + nomeImagem +"_rgb.jpg",getHistogramImage(imagemPrincipal, normalizar));
    }

    public ProcessadorImagem( String caminhoPadrao, String nomeImagem, boolean normalizar) throws IOException {
        this.caminhoPadrao = caminhoPadrao;
        String caminho = caminhoPadrao + nomeImagem + ".jpg";
        imagemPrincipal = cvLoadImage(caminho);
        tamanhoDaImagem = imagemPrincipal.cvSize();
        this.normalizar = normalizar;
        //Carrega a imagem principal como escalas de cinza
        imagemPrincipalEscalaCinza =
            cvLoadImage(caminho, CV_LOAD_IMAGE_GRAYSCALE);

        // Converte a imagem principal para HSV
        imagemPrincipalHSV = cvCreateImage(tamanhoDaImagem, 8, 3);
        cvCvtColor(imagemPrincipal, imagemPrincipalHSV, CV_BGR2HSV);

        // Converte a imagem principal para YUV
        imagemPrincipalYUV = cvCreateImage(tamanhoDaImagem, 8, 3);
        cvCvtColor(imagemPrincipal, imagemPrincipalYUV, CV_BGR2YCrCb);
    }

    /**
     * Salva a imagem utilizando o javacv
     * @param caminho
     * @param image
     */
    public void salvarArquivo(String caminho, IplImage image) {
        cvSaveImage(caminho, image);
    }


    /**
     * Salva a imagem utilizando o bufferedimage padrao do java.
     * @param caminho
     * @param image
     */
    public void salvarArquivo(String caminho, BufferedImage image) {
        File outputfile = new File(caminho);
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtem os 3 canais da imagem (RGB, BGR, HSV) e retorna os mesmos em um array
     * @param image
     * @return
     */
    public static IplImage [] splitChannels(IplImage image) {
        CvSize size = image.cvSize();
        int depth = image.depth();

        IplImage [] imageArray;

        if(image.nChannels() >= 3){
            IplImage channel0 = IplImage.create(size, depth, 1);
            IplImage channel1 = IplImage.create(size, depth, 1);
            IplImage channel2 = IplImage.create(size, depth, 1);

            cvSplit(image, channel0, channel1, channel2, null);

            imageArray = new IplImage[]{channel0, channel1, channel2};

        } else{
            imageArray = new IplImage[]{image};
        }

        return imageArray;
    }

    /**
     * @param image imagem de entrada
     * @param mask mascara opcional
     * @return OpenCV histograma
     */
    public CvHistogram getHistogram(IplImage image, IplImage mask) {
        // Aloca o objeto do histograma
        int dims = 1;
        int[] sizes = new int[] { numberOfBins };
        int histType = CV_HIST_ARRAY;
        //        ranges = Array(Array(_minRange, _maxRange));
        float[] minMax = new float[] { _minRange, _maxRange };
        float[][] ranges = new float[][] { minMax };
        CvHistogram hist = cvCreateHist(dims, sizes, histType, ranges, 1);

        // Calcula o histograma
        int accumulate = 0;
        IplImage [] iplArr = splitChannels(image);

        if(iplArr != null){
            cvCalcHist(iplArr, hist, accumulate, mask);
        }

        return hist;
    }

    public float [] getHSVHistogramAsArray(){
        return getHistogramAsArray(imagemPrincipalHSV,normalizar);
    }

    public float [] getRGBHistogramAsArray(){
        return getHistogramAsArray(imagemPrincipal,normalizar);
    }

    public float [] getYUVHistogramAsArray(){
        return getHistogramAsArray(imagemPrincipalYUV,normalizar);
    }

    /**
     * Calcula o histograma de uma imagem
     * @param image imagem de entrada (principal)
     * @return retorna o histograma como um vetor
     */
    public float [] getHistogramAsArray(IplImage image, boolean normalizar){

        if(normalizar){
            cvNormalize(image, image, 0, 255, CV_MINMAX, null);
        }

        // Cria e calcula o histograma da imagem recebida como parametro
        CvHistogram histogram = getHistogram(image, null);

        // Extrai os valores para um array
        float[] dest = new float[numberOfBins];
        for (int bin = 0; bin < numberOfBins; bin++) {
            dest[bin] = cvQueryHistValue_1D(histogram, bin);
        }

        // Limpa a memoria alocada para o vetor
        cvReleaseHist(histogram);

        return dest;
    }


    /**
     * Gera um histograma em formato de imagem
     * @param image que sera gerada o histograma
     * @return Imagem do histograma
     */
    public BufferedImage getHistogramImage(IplImage image, boolean normalizar) {

        // Tamanho da imagem de saida
        int width = numberOfBins;
        int height = numberOfBins;

        float[] hist = getHistogramAsArray(image, normalizar);
        // Set highest point to 90% of the number of bins
        double scale = 0.9 / hist.length * height;

        // Cria uma imagem pra desenhar nela
        BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = canvas.createGraphics();

        // Pinta o fundo da imagem em branco
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Desenha uma linha vertical para cada bin
        g.setPaint(Color.BLUE);
        for (int bin = 0; bin < numberOfBins; bin++) {
            int h = (int) Math.round(hist[bin] * scale);
            g.drawLine(bin, height - 1, bin, height - h - 1);
        }

        // Limpa o graphics2D
        g.dispose();

        return canvas;
    }

    /**
     * Converte uma IplImage para Tons de Cinza
     *
     * @param image Imagem que sera convertida
     * @return imagem convertida em tons de cinza
     */
    public IplImage colorToGrayImage(IplImage image) {
        IplImage grayImage = IplImage.create(image.cvSize(), IPL_DEPTH_8U, 1);
        cvCvtColor(image, grayImage, CV_BGR2GRAY);

        return grayImage;
    }

    /**
     * Gera uma imagem equalizada, verificar exemplo em http://www.dca.fee.unicamp.br/~alexgs/disciplinas/pi/grupo6/lab3/exercicio_3/index.html
     * @param src Imagem que sera equalizada
     * @return retorna a imagem equalizada
     */
    public IplImage equalize(IplImage src) {
        IplImage newImg = src;

        if(src.nChannels() > 1){
            newImg = colorToGrayImage(src);
        }

        IplImage dest = IplImage.create(newImg.cvSize(),
            newImg.depth(), newImg.nChannels());

        // Equaliza o histograma da imagem
        cvEqualizeHist(newImg, dest);

        return dest;
    }

    /**
     * Calcula a distancia Coseno
     * @param vetorA imagem 1
     * @param vetorB imagem 2
     * @return distancia
     */
    public double calcularCoseno(double[] vetorA, double[] vetorB)
    {
        double produto = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vetorA.length; i++) {
            produto += vetorA[i] * vetorB[i];
            normA += Math.pow(vetorA[i], 2);
            normB += Math.pow(vetorB[i], 2);
        }
        return produto / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * Calcula a distancia Euclidiana
     * @param vetor1 imagem 1
     * @param vetor2 imagem 2
     * @return distancia
     */
    public double calcularEuclidiana(double[] vetor1, double[] vetor2)
    {

        double somarParcial = 0.0;

        for (int i = 0; i < vetor1.length; i++)
        {
            somarParcial += Math.pow(vetor1[i] - vetor2[i], 2.0);
        }

        return Math.sqrt(somarParcial);
    }

    /**
     * Calcula a distancia de Manhattan
     * @param vetor1 imagem 1
     * @param vetor2 imagem 2
     * @return distancia
     */
    public double calcularManhattan(double[] vetor1, double[] vetor2)
    {

        double somaParcial = 0.0;

        for (int i = 0; i < vetor1.length; i++)
        {
            somaParcial += Math.abs(vetor1[i] - vetor2[i]);
        }

        return somaParcial;
    }

    /**
     *  Entre dois vetores, calcula qual é a maior distância entre seus elementos
     *
     * @param vetor1 imagem 1
     * @param vetor2 imagem 2
     * @return - em caso de vetores com dimensões diferentes, retorna 0
     */
    public double calcularXadrez( double[] vetor1, double[] vetor2 )
    {
        double max = 0;
        if(vetor1.length == vetor2.length)
        {  max = 0.0;
            for(int i=0; i< vetor1.length ; i++)
                if(Math.abs(vetor1[i]- vetor2[i]) > max )
                    max = Math.abs((vetor1[i]- vetor2[i])*1.0);
        }
        return max;
    }

    public BufferedImage IplImageToBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame,1);
    }

    /**
     * Pega a taxa de cor Rgb da imagem
     * @return taxa de cor da imagem em um vetor RGB
     */
    public double[] getTaxadeCor()
    {
        BufferedImage img = IplImageToBufferedImage(imagemPrincipal);

        long vermelho = 0;
        long verde = 0;
        long azul = 0;
        long contatorDePixels = 0;

        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                int rgb = img.getRGB(x, y);
                Color c = new Color(rgb, true);

                contatorDePixels++;
                vermelho += c.getRed();
                verde += c.getGreen();
                azul += c.getBlue();
            }
        }

        long mediaVermelho = vermelho / contatorDePixels;
        long mediaVerde = verde / contatorDePixels;
        long mediaAzul = azul / contatorDePixels;

        return new double[]{mediaVermelho, mediaVerde, mediaAzul};

    }


    /**
     * Aplicar filtros
     * CV_BLUR: Filtro da média: calcula a média de todos os pixels de uma janela
     * CV_MEDIAN: calcula o valor da mediana de uma janela
     * CV_GAUSSIAN: suaviza a imagem utilizando uma ponderação de gaussiana utilizando uma janela (size1 x size1) utilizando dois parametros de suavização (sigma1 e sigma2) que definem o formato da gaussiana
     * Exemplo de uso: cvSmooth(fonte, dest, FILTO_AQUI, tamanho1, tamanho2, sigma1, sigma2);
     */
    public boolean filtroMedia (){
            IplImage imgTmp = cvCreateImage(tamanhoDaImagem, 8, 3);
            cvSmooth(imagemPrincipal, imgTmp, CV_BLUR, 3,3,0,0);
            cvSaveImage(caminhoPadrao + "principal.jpg", imgTmp);
            return true;
    }




}
