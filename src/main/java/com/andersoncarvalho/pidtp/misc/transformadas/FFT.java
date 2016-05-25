package com.andersoncarvalho.pidtp.misc.transformadas;


public class FFT
{
	//enumeration allows user to pick whether the FFT transformation is run forward or backwards
	public enum Direction
	{
		Forward,Reverse;
	}

	//for transforming two dimensional array of complex numbers
	public static ComplexArrayWrap Transform(ComplexArrayWrap input, Direction direction)
	{
		ComplexArrayWrap intermediate = new ComplexArrayWrap(input.width,input.height);
		ComplexArrayWrap result = new ComplexArrayWrap(input.width,input.height);

		for(int index=0;index<input.size;index++)
			intermediate.SetColumn(index, RecursiveFFT(input.GetColumn(index),(direction==Direction.Forward)?-1:1));

		for(int index=0;index<intermediate.size;index++)
			result.SetRow(index, RecursiveFFT(intermediate.GetRow(index),(direction==Direction.Forward)?-1:1));

		return result;
	}

	//performs the FFT on a single dimension in the desired direction through recursion
	private static Complex[] RecursiveFFT(Complex[] input, double direction)
	{
		int length = input.length;
		int half_length = input.length / 2;
		Complex[] result = new Complex[length];

		if(length==1)
		{
			result[0] = input[0];
		}
		else
		{
			Complex[] sum = new Complex[half_length];
			Complex[] diff = new Complex[half_length];

			Complex temp = new Complex(0.0, direction*(2*Math.PI)/length).GetExponential();
			Complex c1 = new Complex(1,0);
			Complex c2 = new Complex(2,0);

			for(int index=0;index<half_length;index++)
			{
				sum[index] = input[index].Add(input[index+half_length]).Divide(c2);
				diff[index] = input[index].Subtract(input[index+half_length]).Multiply(c1).Divide(c2);
				c1 = c1.Multiply(temp);
			}

			Complex[] even = RecursiveFFT(sum,direction);
			Complex[] odd = RecursiveFFT(diff,direction);

			for(int index=0;index<half_length;index++)
			{
				result[index*2] = even[index];
				result[index*2 + 1] = odd[index];
			}
		}

		return result;
	}
}
