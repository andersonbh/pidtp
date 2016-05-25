package com.andersoncarvalho.pidtp.service.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagemMatriz
{
	public Color[][] matriz;
	public int width, height;

	//constroi o objeto a partir do array de cor
	public ImagemMatriz(Color[][] _matriz)
	{
		width = _matriz[0].length;
		height = _matriz.length;

		matriz = new Color[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				matriz[x][y]= _matriz[x][y];
	}

	//converte array de doubles em array de cores
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

	//converte uma imagem em um array de cores
	public ImagemMatriz(BufferedImage image)
	{
		width = image.getWidth();
		height = image.getHeight();

		matriz = new Color[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				matriz[x][y]= new Color(image.getRGB(x, y));
	}

	//converte um array de cor em uma imagem
	public BufferedImage toImage()
	{
		BufferedImage result = new BufferedImage(width, height, 0);
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				result.setRGB(x, y, matriz[x][y].getRGB());

		return result;
	}

	//cria uma copia
	public ImagemMatriz copy()
	{
		return new ImagemMatriz(matriz);
	}
}
