package com.andersoncarvalho.pidtp.service.util;
import java.text.DecimalFormat;

public abstract class Kernel
{
	public DecimalFormat formatter;

	public abstract double GetFactor(double x, double y);

	public String toString()
	{
		String format = "0.00";
		formatter = new DecimalFormat(format);

		String result = "";

		for(double x = -1; x <= 1; x+=0.1)
		{
			for(double y = -1; y <= 1; y+=0.1)
				{
					result += formatter.format(GetFactor(x, y)) + " ";
				}
			result += "\n";
		}
		return result;
	}
}
