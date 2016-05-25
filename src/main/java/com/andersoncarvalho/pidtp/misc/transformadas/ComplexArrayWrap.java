package com.andersoncarvalho.pidtp.misc.transformadas;


import com.andersoncarvalho.pidtp.service.util.Kernel;

public class ComplexArrayWrap {
    //variaveis
    public int width, height, size;
    public Complex[][] values;

    //enumeracao da representacao de diferentes numeros de 1 dimensao e de 2 dimensoes
    public enum Representation {
        Real, Imaginary, Magnitude, Phase;
    }

    //construtor parametrizado
    public ComplexArrayWrap(int _width, int _height) {
        width = _width;
        height = _height;
        size = (int) Math.pow(2, Math.ceil(Math.log(Math.max(width, height)) / Math.log(2)));

        values = new Complex[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                values[row][col] = new Complex();
            }
        }
    }

    //construtor parametrizado
    public ComplexArrayWrap(double[][] pixels) {
        width = pixels[0].length;
        height = pixels.length;

        size = (int) Math.pow(2, Math.ceil(Math.log(Math.max(width, height)) / Math.log(2)));

        values = new Complex[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                values[row][col] = (row < height && col < width) ? new Complex(
                        Math.pow(-1, row + col) * pixels[row][col], 0) : new Complex(0, 0);
            }
        }
    }

    //retorna uma unica linha com dois arrays dimensionais
    public Complex[] GetRow(int row) {
        Complex[] result = new Complex[size];
        for (int col = 0; col < size; col++) {
            result[col] = values[row][col];
        }
        return result;
    }

    //retorna uma unica coluna com dois arrays dimensionais
    public Complex[] GetColumn(int col) {
        Complex[] result = new Complex[size];
        for (int row = 0; row < size; row++) {
            result[row] = values[row][col];
        }
        return result;
    }

    //seta uma unica linha para os dois arrays dimensionais
    public void SetRow(int row, Complex[] data) {
        for (int col = 0; col < size; col++) {
            values[row][col] = data[col];
        }
    }

    //seta uma unica coluna para os dois arrays dimensionais
    public void SetColumn(int col, Complex[] data) {
        for (int row = 0; row < size; row++) {
            values[row][col] = data[row];
        }
    }

    //retorna a matriz com cada valor reduzido para uma unica dimensao especificada pelo usuario
    public double[][] GetRepresentation(Representation type) {
        double[][] result = new double[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                switch (type) {
                    case Real:
                        result[row][col] = values[row][col].real;
                        break;
                    case Imaginary:
                        result[row][col] = values[row][col].imaginary;
                        break;
                    case Magnitude:
                        result[row][col] = values[row][col].GetMagnitude();
                        break;
                    case Phase:
                        result[row][col] = values[row][col].GetPhase();
                        break;
                    default:
                        break;
                }
            }
        }

        return result;
    }

    //multiplica seccionalmente cada elemento no array pelo seu respectivo elemento no kernel
    //note que o kernel vem como um objeto para que o usuario nao precise criar uma matriz
    //em vez disso o objeto vai gerar seus valores baseados em suas localizacoes no array
    public void Convolve(Kernel k) {
        double ratio = 1 / (double) size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                values[row][col] = values[row][col]
                        .Multiply(new Complex(k.GetFactor(2 * (col * ratio) - 1, 2 * (row * ratio) - 1), 0));
            }
        }
    }

    //retorna uma string para representacao do objeto
    public String toString() {
        return "Width: " + width + " Height: " + height + " Size: " + size;
    }
}
