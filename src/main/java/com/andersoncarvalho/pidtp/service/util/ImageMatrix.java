package com.andersoncarvalho.pidtp.service.util;

import java.awt.*;
import java.awt.image.BufferedImage;

//likely all of this could be done much more efficiently by reading into a
//one dimensional array instead of getting and setting individual pixels
public class ImageMatrix
{
	//fields
	public Color[][] matrix;
	public int width, height;

	//constructs object from color array
	public ImageMatrix(Color[][] _matrix)
	{
		width = _matrix[0].length;
		height = _matrix.length;

		matrix = new Color[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				matrix[x][y]= _matrix[x][y];
	}

	//converts array of doubles into array of colors
	public ImageMatrix(double[][] _matrix)
	{
		width = _matrix[0].length;
		height = _matrix.length;

		matrix = new Color[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				try
				{
					matrix[x][y]= new Color((float)_matrix[x][y],(float)_matrix[x][y],(float)_matrix[x][y]);
				}
				catch(Exception e)
				{

					matrix[x][y] = Color.white;
				}
	}

	//converts an image into an array of colors
	public ImageMatrix(BufferedImage image)
	{
		width = image.getWidth();
		height = image.getHeight();

		matrix = new Color[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				matrix[x][y]= new Color(image.getRGB(x, y));
	}

	//converts array of colors back into an image
	public BufferedImage toImage()
	{
		BufferedImage result = new BufferedImage(width, height, 0);
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				result.setRGB(x, y, matrix[x][y].getRGB());

		return result;
	}

	//creates a copy
	public ImageMatrix copy()
	{
		return new ImageMatrix(matrix);
	}
}
