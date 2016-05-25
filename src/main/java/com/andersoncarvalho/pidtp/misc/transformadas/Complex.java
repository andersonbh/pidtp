package com.andersoncarvalho.pidtp.misc.transformadas;

public class Complex
{
	//components of complex number
	public double real, imaginary;

	//default constructor
	public Complex()
	{
		real = 0;
		imaginary = 0;
	}

	//parameterized constructor
	public Complex(double _real, double _imaginary)
	{
		real = _real;
		imaginary = _imaginary;
	}

	//gets the result of euler's number raised to the complex
	public Complex GetExponential()
	{
		return new Complex(Math.exp(real)*Math.cos(imaginary),Math.exp(real)*Math.sin(imaginary));
	}

	//adds two complex numbers together
	public Complex Add(Complex c)
	{
		return new Complex(real + c.real, imaginary + c.imaginary);
	}

	//subtracts two complex numbers
	public Complex Subtract(Complex c)
	{
		return new Complex(real - c.real, imaginary - c.imaginary);
	}

	//multiplies two complex numbers
	public Complex Multiply(Complex c)
	{
		return new Complex(real*c.real-imaginary*c.imaginary,real*c.imaginary + imaginary*c.real);
	}

	//divides two complex numbers
	public Complex Divide(Complex c)
	{
		Complex numerator = this.Multiply(c.GetConjugate());
		Complex denominator = c.Multiply(c.GetConjugate());
		return new Complex(numerator.real/denominator.real,numerator.imaginary/denominator.real);
	}

	//gets the conjugate of a complex number
	public Complex GetConjugate()
	{
		return new Complex(real,-imaginary);
	}

	//gets the magnitude of the complex number
	public double GetMagnitude()
	{
		return Math.sqrt(real*real + imaginary*imaginary);
	}

	//gets the phase of the complex number
	public double GetPhase()
	{
		return (real == 0 && imaginary ==0)?0:Math.atan(imaginary/real);
	}

	//for printing purposes
	public String toString()
	{
		return real + ((imaginary<0)?" ":" + ") + imaginary + "i";
	}
}
