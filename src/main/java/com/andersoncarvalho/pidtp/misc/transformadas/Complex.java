package com.andersoncarvalho.pidtp.misc.transformadas;

public class Complex
{
	//componentes do numero complexo
	public double real, imaginary;

	//construtor padrao
	public Complex()
	{
		real = 0;
		imaginary = 0;
	}

	//contrutor parametrizado
	public Complex(double _real, double _imaginary)
	{
		real = _real;
		imaginary = _imaginary;
	}

	//pegar o resultado do numero euleriano elevado pelo complexo
	public Complex GetExponential()
	{
		return new Complex(Math.exp(real)*Math.cos(imaginary),Math.exp(real)*Math.sin(imaginary));
	}

	//adiciona dois numeros complexos
	public Complex Add(Complex c)
	{
		return new Complex(real + c.real, imaginary + c.imaginary);
	}

	//subtrai dois numeros complexos
	public Complex Subtract(Complex c)
	{
		return new Complex(real - c.real, imaginary - c.imaginary);
	}

	//multiplica dois numeros complexos
	public Complex Multiply(Complex c)
	{
		return new Complex(real*c.real-imaginary*c.imaginary,real*c.imaginary + imaginary*c.real);
	}

	//divide dois numeros complexos
	public Complex Divide(Complex c)
	{
		Complex numerator = this.Multiply(c.GetConjugate());
		Complex denominator = c.Multiply(c.GetConjugate());
		return new Complex(numerator.real/denominator.real,numerator.imaginary/denominator.real);
	}

	//pega o conjugado do numero complexo
	public Complex GetConjugate()
	{
		return new Complex(real,-imaginary);
	}

	//pega a magnitude do numero complexo
	public double GetMagnitude()
	{
		return Math.sqrt(real*real + imaginary*imaginary);
	}

	//pega a fase do numero complexo
	public double GetPhase()
	{
		return (real == 0 && imaginary ==0)?0:Math.atan(imaginary/real);
	}

	//para propositos de impressao
	public String toString()
	{
		return real + ((imaginary<0)?" ":" + ") + imaginary + "i";
	}
}
