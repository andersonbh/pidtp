package com.andersoncarvalho.pidtp.misc.transformadas;


import com.andersoncarvalho.pidtp.service.util.Kernel;

public class ComplexArrayWrap {
    //fields
    public int width, height, size;
    public Complex[][] values;

    //enumeration of different 1 dimensional ways to represent the otherwise 2dimensional complex number
    public enum Representation {
        Real, Imaginary, Magnitude, Phase;
    }

    //parameterized constructor
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

    //parameterized constructor
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

    //returns a single row of the two dimensional array
    public Complex[] GetRow(int row) {
        Complex[] result = new Complex[size];
        for (int col = 0; col < size; col++) {
            result[col] = values[row][col];
        }
        return result;
    }

    //gets a single column of the two dimensional array
    public Complex[] GetColumn(int col) {
        Complex[] result = new Complex[size];
        for (int row = 0; row < size; row++) {
            result[row] = values[row][col];
        }
        return result;
    }

    //sets a single row of the two dimensional array
    public void SetRow(int row, Complex[] data) {
        for (int col = 0; col < size; col++) {
            values[row][col] = data[col];
        }
    }

    //sets a single column of the two dimensional array
    public void SetColumn(int col, Complex[] data) {
        for (int row = 0; row < size; row++) {
            values[row][col] = data[row];
        }
    }

    //returns the matriz with each value reduced to a single dimension as specified by the user
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

    //piecewise multiplies each element in the array by the corresponding element in the kernel
    //note that kernel is provided as an object so that user need not create an actual matriz
    //instead the object will generate its values based on its location in the array
    public void Convolve(Kernel k) {
        double ratio = 1 / (double) size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                values[row][col] = values[row][col]
                        .Multiply(new Complex(k.GetFactor(2 * (col * ratio) - 1, 2 * (row * ratio) - 1), 0));
            }
        }
    }

    //returns a string representation of the object
    public String toString() {
        return "Width: " + width + " Height: " + height + " Size: " + size;
    }
}
