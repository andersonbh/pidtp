package com.andersoncarvalho.pidtp.entity;

import javax.persistence.*;

/**
 * Created by anderson on 09/03/16.
 */
@Entity(name = "imagem")
@SequenceGenerator(name = "imagem_id_seq",sequenceName = "imagem_id_seq",initialValue = 1, allocationSize = 1)

public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imagem_id_seq")
    private long id;
    private String caminho;
    private int width;
    private int height;
    private String nome;
    private double[] taxadecor;
    private float[] histogramargb;
    private float[] histogramahsv;
    private float[] histogramayuv;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double[] getTaxadecor() {
        return taxadecor;
    }

    public void setTaxadecor(double[] taxadecor) {
        this.taxadecor = taxadecor;
    }

    public float[] getHistogramargb() {
        return histogramargb;
    }

    public void setHistogramargb(float[] histogramargb) {
        this.histogramargb = histogramargb;
    }

    public float[] getHistogramahsv() {
        return histogramahsv;
    }

    public void setHistogramahsv(float[] histogramahsv) {
        this.histogramahsv = histogramahsv;
    }

    public float[] getHistogramayuv() {
        return histogramayuv;
    }

    public void setHistogramayuv(float[] histogramayuv) {
        this.histogramayuv = histogramayuv;
    }
}
