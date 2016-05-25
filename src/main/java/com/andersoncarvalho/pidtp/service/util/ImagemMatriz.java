package com.andersoncarvalho.pidtp.service.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagemMatriz
{
	public Color[][] matriz;
	public int width, height;

	//constructs object from color array
	public ImagemMatriz(Color[][] _matriz)
	{
		width = _matriz[0].length;
		height = _matriz.length;

		matriz = new Color[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				matriz[x][y]= _matriz[x][y];
	}

	//converts array of doubles into array of colors
	public ImagemMatriz(double[][] _matriz)
	{
		width = _matriz[0].length;
		height = _matriz.length;

		matriz = new Color[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				try
				{
					matriz[x][y]= new Color((float)_matriz[x][y],(float)_matriz[x][y],(float)_matriz[x][y]);
				}
				catch(Exception e)
				{

					matriz[x][y] = Color.white;
				}
	}

	//converts an image into an array of colors
	public ImagemMatriz(BufferedImage image)
	{
		width = image.getWidth();
		height = image.getHeight();

		matriz = new Color[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				matriz[x][y]= new Color(image.getRGB(x, y));
	}

	//converts array of colors back into an image
	public BufferedImage toImage()
	{
		BufferedImage result = new BufferedImage(width, height, 0);
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				result.setRGB(x, y, matriz[x][y].getRGB());

		return result;
	}

	//creates a copy
	public ImagemMatriz copy()
	{
		return new ImagemMatriz(matriz);
	}
}
