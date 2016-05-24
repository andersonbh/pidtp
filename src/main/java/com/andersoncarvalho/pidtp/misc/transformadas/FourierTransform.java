package com.andersoncarvalho.pidtp.misc.transformadas;

/**
 * Created by anderson on 24/05/16.
 */
import java.awt.image.BufferedImage;

import com.andersoncarvalho.pidtp.service.util.ComplexNumber;


public interface FourierTransform {
    public ComplexNumber[][][] apply  (BufferedImage img);
    public void revert (BufferedImage img, ComplexNumber[][][] matrix);
}
