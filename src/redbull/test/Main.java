package redbull.test;

import redbull.Int;
import redbull.Ring;
import redbull.SquareMatrix;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Construct an 2x3 integer matrix  */
		//

		SquareMatrix<Int> a = getMatrix(500);
		int size = 500;
		//RMatrix<Int> e = getMatrix(2000);
		//SquareMatrix<Int> b2 = (SquareMatrix<Int>) a.multNaive1(a);
		long milis1 = System.currentTimeMillis();
		//System.out.println(a.toString());
		//a.minus(a).minus(a).minus(a).minus(a).minus(a);
		//a.minusRecursion(a).minusRecursion(a).minusRecursion(a).minusRecursion(a).minusRecursion(a);
		//SquareMatrix<Int> b1 = new SquareMatrix(getRing(size));
		//			SquareMatrix<Int> b2 = new SquareMatrix(getRing(size));
		//			SquareMatrix<Int> b3 = new SquareMatrix(getRing(size));
		//			SquareMatrix<Int> b4 = new SquareMatrix(getRing(size));
		//System.out.println("\nThread"+b1.toString());
		//			SquareMatrix<Int> b3 = new SquareMatrix(getRing(size));
		//			SquareMatrix<Int> b4 = new SquareMatrix(getRing(size));
		//			SquareMatrix<Int> b5 = new SquareMatrix(getRing(size));
		//
		//			SquareMatrix<Int> b3 = (SquareMatrix<Int>)
		//			a.multDense(a);
		//			 a.multDense(a);
		//			  a.multDense(a);
		//			   a.multDense(a);

		//System.out.println(b2.equals(b3));
		//System.out.println(a.toString()+a.multNaive1int(a).toString());
		//System.out.println(a.mult(a).toString());

		//SquareMatrix<Int> b1 =	a.multNaive1(a);
		//SquareMatrix<Int> b2 =	(SquareMatrix<Int>) a.multNaive(a);
		//System.out.println( a.hashCode());
		//System.out.println( a.hashCode());
		//System.out.println( a.hashCode());
		//	System.out.println( a.hashCode());
		//			System.out.println(b1.equals(b2));
		//			System.out.println(b3.equals(b2));
		//			System.out.println(b1.toString());
		//			System.out.println(b2.toString());
		//			a.mult(a);
		//System.out.println(a.getOne().toString());
		//a.plus(a).plus(a).plus(a).plus(a);
		//System.out.println("Rezultati\n " + a.mult(a));
		//System.out.println(" Rezultati Sparse\n" +a.multSparse(a));
		//			a.hashCode();
		//			a.hashCode();
		//			a.hashCode();
		//			a.hashCode();
		//a.getCopy();
		//			a.getCopy();
		//			a.getCopy();
		//			a.getCopy();
		long milis2 = System.currentTimeMillis();
		long diff = milis2 - milis1;
		//long diffSeconds = diff / 1000;
		System.out.println("Koha"+ diff);
	}
	public static int randomNumber()
	{
		int g;
		g = 1 + (int) ( Math.random() * 10);//change the complexity of data by changing 100
		return g;
	}
	public static SquareMatrix<Int> getMatrix(int c)
	{
		SquareMatrix<Int> ma = new SquareMatrix<Int>(c);
		for(int i = 0; i < c; i++ )
		{
			for(int j = 0; j < c; j++ )
			{
				ma.setElementAt(new Int(randomNumber()), i, j);
			}
		}
		return ma;
	}
	public static Ring<Int>[][] getRing(int c)
	{
		Ring<Int>[][] ma = new Ring[c][c];
		for(int i = 0; i < c; i++ )
		{
			//System.out.println();
			for(int j = 0; j < c; j++ )
			{
				ma[i][j]=new Int(randomNumber());
				//System.out.print(" " + ma[i][j].toString());
			}
		}
		return ma;
	}
	public boolean randomBoolean()
	{
		int g;
		g = 1 + (int) ( Math.random() * 1000 );
		return g/1==0.5;
	}
	
}
