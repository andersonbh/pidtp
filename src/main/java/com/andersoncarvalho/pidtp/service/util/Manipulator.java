package com.andersoncarvalho.pidtp.service.util;

import java.awt.*;


public class Manipulator
{
	double[][] luminance, greyscale, red, green, blue;

	public enum ColorSpace
	{
		Luminance, Greyscale, Red, Green, Blue;
	}

	public double[][] GetColorSpace(ColorSpace space)
	{
		switch(space)
		{
		case Luminance:
			return luminance;
		case Greyscale:
			return greyscale;
		case Red:
			return red;
		case Green:
			return green;
		case Blue:
			return blue;
			default:
			return greyscale;
		}
	}

	public Manipulator(Color[][] pixels)
	{
		luminance = new double[pixels.length][pixels[0].length];
		greyscale = new double[pixels.length][pixels[0].length];
		red = new double[pixels.length][pixels[0].length];
		green = new double[pixels.length][pixels[0].length];
		blue = new double[pixels.length][pixels[0].length];

		for(int row = 0; row < pixels.length; row++)
			for(int col = 0; col < pixels[row].length; col++)
			{
				double r = pixels[row][col].getRed() / 255.0f;
				double g = pixels[row][col].getGreen() / 255.0f;
				double b = pixels[row][col].getBlue() / 255.0f;

				luminance[row][col] = 0.2126*r + 0.7152*g + 0.0722*b;
				greyscale[row][col] = (r+g+b)/3.0f;
				red[row][col] = r;
				green[row][col] = g;
				blue[row][col] = b;
			}
	}


}
