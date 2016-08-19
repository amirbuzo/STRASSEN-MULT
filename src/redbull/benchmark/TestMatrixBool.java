package redbull.benchmark;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import redbull.Bool;
import redbull.Matrix;
import redbull.NotCompatibleException;
import redbull.QRMatrix;


public class TestMatrixBool {
	public static Matrix<Bool> boolMatrix = new Matrix<Bool>(new Bool[2][3]);
	public static Matrix<Bool> boolMatrixx = new Matrix<Bool>(new Bool[2][3]);
	public static Matrix<Bool> boolMatrix1 = new Matrix<Bool>(new Bool[2][3]);
	public static Matrix<Bool> boolMatrix2 = new Matrix<Bool>(new Bool[3][2]);

	@Before
	public void setUp() throws Exception {
		boolMatrix.setElementAt(new Bool(false), 0, 0);
		boolMatrix.setElementAt(new Bool(true), 0, 1);
		boolMatrix.setElementAt(new Bool(false), 0, 2);
		boolMatrix.setElementAt(new Bool(true), 1, 0);
		boolMatrix.setElementAt(new Bool(false), 1, 1);
		boolMatrix.setElementAt(new Bool(true), 1, 2);
		
		boolMatrixx.setElementAt(new Bool(false), 0, 0);
		boolMatrixx.setElementAt(new Bool(true), 0, 1);
		boolMatrixx.setElementAt(new Bool(true), 0, 2);
		boolMatrixx.setElementAt(new Bool(true), 1, 0);
		boolMatrixx.setElementAt(new Bool(false), 1, 1);
		boolMatrixx.setElementAt(new Bool(false), 1, 2);
		
		
		boolMatrix1.setElementAt(new Bool(false), 0, 0);
		boolMatrix1.setElementAt(new Bool(true), 0, 1);
		boolMatrix1.setElementAt(new Bool(false), 0, 2);
		boolMatrix1.setElementAt(new Bool(true), 1, 0);
		boolMatrix1.setElementAt(new Bool(false), 1, 1);
		boolMatrix1.setElementAt(new Bool(true), 1, 2);
		
		boolMatrix2.setElementAt(new Bool(false), 0, 0);
		boolMatrix2.setElementAt(new Bool(true), 0, 1);
		boolMatrix2.setElementAt(new Bool(false), 1, 0);
		boolMatrix2.setElementAt(new Bool(true), 1, 1);
		boolMatrix2.setElementAt(new Bool(false), 2, 0);
		boolMatrix2.setElementAt(new Bool(true), 2, 1);
	}

	@Test
	public final void testHashCode() {
		System.out.println(boolMatrix.toString());
		System.out.println(boolMatrix.hashCode());
//		System.out.println(boolMatrix1.hashCode());
//		System.out.println(boolMatrix2.hashCode());
//		System.out.println(boolMatrixx.hashCode());
	}
	@Test
	public final void testRows() {
		assertEquals(2, boolMatrix.rows());
		assertEquals(3, boolMatrix2.rows());
	}

	@Test
	public final void testColumns() {
		assertEquals(3, boolMatrix.columns());
		assertEquals(2, boolMatrix2.columns());
	}

	@Test
	public final void testSparseness() {
		
		System.out.println(boolMatrix.sparseness());
		assertTrue(boolMatrix.sparseness()== 0.5);
		
		Matrix<Bool> bool = (Matrix<Bool>) getMatrix(7, 7);
		System.out.println(bool.sparseness());
//		System.out.println("H"+boolMatrixx.toString());
//		System.out.println("H"+boolMatrix.sparseness());
//		System.out.println("H"+boolMatrix.mult(boolMatrixx.getTranspose()).sparseness());
//		System.out.println("H"+boolMatrix.plus(boolMatrixx).sparseness());
		
	}

	@Test
	public final void testGetElementAt() {
		assertEquals(new Bool(true), boolMatrix.getElementAt(1, 2));
		assertEquals(new Bool(false), boolMatrix.getElementAt(1, 1));
		assertEquals(new Bool(true), boolMatrix2.getElementAt(0, 1));
		assertEquals(new Bool(false), boolMatrix2.getElementAt(2, 0));
	}

	@Test
	public final void testSetElementAt() {
		assertEquals(new Bool(false), boolMatrix.getElementAt(1, 1));
		boolMatrix.setElementAt(new Bool(true), 1, 1);
		assertEquals(new Bool(true), boolMatrix.getElementAt(1, 1));
	}

	@Test
	public final void testGetColumn() {
		Matrix<Bool> test = new Matrix<Bool>(new Bool[2][1]);
		test.setElementAt(new Bool(false), 0, 0);
		test.setElementAt(new Bool(true), 1, 0);
		assertFalse(test.equals(boolMatrix.getColumn(2)));
		
		Matrix<Bool> test1 = new Matrix<Bool>(new Bool[3][1]);
		test1.setElementAt(new Bool(true), 0, 0);
		test1.setElementAt(new Bool(true), 1, 0);
		test1.setElementAt(new Bool(true), 2, 0);
		assertTrue(test1.equals(boolMatrix2.getColumn(1)));
		
		}

	@Test
	public final void testGetRow() {
		
		Matrix<Bool> test = new Matrix<Bool>(new Bool[1][3]);
		test.setElementAt(new Bool(true), 0, 0);
		test.setElementAt(new Bool(false), 0, 1);
		test.setElementAt(new Bool(true), 0, 2);
		assertTrue(test.equals(boolMatrix.getRow(1)));
		
		Matrix<Bool> test1 = new Matrix<Bool>(new Bool[1][2]);
		test1.setElementAt(new Bool(false), 0, 0);
		test1.setElementAt(new Bool(true), 0, 1);
		assertTrue(test1.equals(boolMatrix2.getRow(2)));
		
		Matrix<Bool> test2 = new Matrix<Bool>(new Bool[1][2]);
		test2.setElementAt(new Bool(true), 0, 0);
		test2.setElementAt(new Bool(false), 0, 1);
		assertFalse(test2.equals(boolMatrix2.getRow(2)));
		
	}

	@Test
	public final void testGetTranspose() {
		Matrix<Bool> test = new Matrix<Bool>(new Bool[3][2]);
		test.setElementAt(new Bool(false), 0, 0);
		test.setElementAt(new Bool(true), 0, 1);
		test.setElementAt(new Bool(true), 1, 0);
		test.setElementAt(new Bool(false), 1, 1);
		test.setElementAt(new Bool(false), 2, 0);
		test.setElementAt(new Bool(true), 2, 1);
		assertTrue(test.equals(boolMatrix.getTranspose()));
		assertFalse(test.equals(test.getTranspose()));
		
		Matrix<Bool> test1 = new Matrix<Bool>(new Bool[2][3]);
		test1.setElementAt(new Bool(false), 0, 0);
		test1.setElementAt(new Bool(false), 0, 1);
		test1.setElementAt(new Bool(false), 0, 2);
		test1.setElementAt(new Bool(true), 1, 0);
		test1.setElementAt(new Bool(true), 1, 1);
		test1.setElementAt(new Bool(true), 1, 2);
		assertTrue(test1.equals(boolMatrix2.getTranspose()));
		assertFalse(test.equals(boolMatrix2.getTranspose()));
		
	}

	@Test
	public final void testIsSquare() {
		Matrix<Bool> test1 = new Matrix<Bool>(new Bool[2][2]);
		test1.setElementAt(new Bool(false), 0, 0);
		test1.setElementAt(new Bool(false), 0, 1);
		test1.setElementAt(new Bool(false), 1, 0);
		test1.setElementAt(new Bool(true), 1, 1);
		assertTrue(test1.isSquare());
		
		Matrix<Bool> test = new Matrix<Bool>(new Bool[2][3]);
		test.setElementAt(new Bool(false), 0, 0);
		test.setElementAt(new Bool(false), 0, 1);
		test.setElementAt(new Bool(false), 0, 2);
		test.setElementAt(new Bool(true), 1, 0);
		test.setElementAt(new Bool(true), 1, 1);
		test.setElementAt(new Bool(true), 1, 2);
		assertFalse(test.isSquare());
		
		Matrix<Bool> test11 = new Matrix<Bool>(new Bool[3][3]);
		test11.setElementAt(new Bool(false), 0, 0);
		test11.setElementAt(new Bool(false), 0, 1);
		test11.setElementAt(new Bool(false), 0, 2);
		test11.setElementAt(new Bool(true), 1, 0);
		test11.setElementAt(new Bool(true), 1, 1);
		test11.setElementAt(new Bool(true), 1, 2);
		test11.setElementAt(new Bool(false), 2, 0);
		test11.setElementAt(new Bool(false), 2, 1);
		test11.setElementAt(new Bool(true), 2, 2);
		assertTrue(test11.isSquare());
	}

	@Test
	public final void testIsDiagonal() {
		Matrix<Bool> test11 = new Matrix<Bool>(new Bool[3][3]);
		test11.setElementAt(new Bool(true), 0, 0);
		test11.setElementAt(new Bool(false), 0, 1);
		test11.setElementAt(new Bool(false), 0, 2);
		test11.setElementAt(new Bool(false), 1, 0);
		test11.setElementAt(new Bool(true), 1, 1);
		test11.setElementAt(new Bool(false), 1, 2);
		test11.setElementAt(new Bool(false), 2, 0);
		test11.setElementAt(new Bool(false), 2, 1);
		test11.setElementAt(new Bool(true), 2, 2);
		//System.out.println(test11.toString());
		assertTrue(test11.isDiagonal());
		
		test11.setElementAt(new Bool(false), 0, 0);
		assertFalse(test11.isDiagonal());
		
		test11.setElementAt(new Bool(true), 0, 0);
		test11.setElementAt(new Bool(true), 0, 1);
		assertFalse(test11.isDiagonal());
	}

	@Test
	public final void testToString() {
		//System.out.println(boolMatrix.toString());
		assertEquals(boolMatrix.toString(),"| false true false |\n| true false true |\n");
		assertEquals(boolMatrix2.toString(),"| false true |\n| false true |\n| false true |\n");
	}

	@Test
	public final void testIsSymmetric() {
		Matrix<Bool> test11 = new Matrix<Bool>(new Bool[3][3]);
		test11.setElementAt(new Bool(true), 0, 0);
		test11.setElementAt(new Bool(false), 0, 1);
		test11.setElementAt(new Bool(false), 0, 2);
		test11.setElementAt(new Bool(false), 1, 0);
		test11.setElementAt(new Bool(true), 1, 1);
		test11.setElementAt(new Bool(true), 1, 2);
		test11.setElementAt(new Bool(false), 2, 0);
		test11.setElementAt(new Bool(true), 2, 1);
		test11.setElementAt(new Bool(false), 2, 2);
		//System.out.println(test11.toString());
		assertTrue(test11.isSymmetric());
		
		test11.setElementAt(new Bool(true), 1, 0);
		assertFalse(test11.isSymmetric());
		
		Matrix<Bool> test1 = new Matrix<Bool>(new Bool[2][3]);
		test1.setElementAt(new Bool(true), 0, 0);
		test1.setElementAt(new Bool(false), 0, 1);
		test1.setElementAt(new Bool(false), 0, 2);
		test1.setElementAt(new Bool(false), 1, 0);
		test1.setElementAt(new Bool(true), 1, 1);
		test1.setElementAt(new Bool(true), 1, 2);
		assertFalse(test1.isSymmetric());
	}

	@Test (expected=NotCompatibleException.class)
	public final void testMult() {
		Matrix<Bool> test = new Matrix<Bool>(new Bool[2][2]);
		test.setElementAt(new Bool(true), 0, 0);
		test.setElementAt(new Bool(false), 0, 1);
		test.setElementAt(new Bool(true), 1, 0);
		test.setElementAt(new Bool(true), 1, 1);
		
		Matrix<Bool> test1 = new Matrix<Bool>(new Bool[3][2]);
		test1.setElementAt(new Bool(true), 0, 0);
		test1.setElementAt(new Bool(false), 0, 1);
		test1.setElementAt(new Bool(true), 1, 0);
		test1.setElementAt(new Bool(true), 1, 1);
		test1.setElementAt(new Bool(true), 2, 0);
		test1.setElementAt(new Bool(true), 2, 1);
		
		assertEquals(test, boolMatrix.mult(boolMatrixx.getTranspose()));
		test1.mult(test1);
		assertEquals(test1.mult(test1),test1);
		
	}

	@Test
	public final void testPlus() {
		Matrix<Bool> test1 = new Matrix<Bool>(new Bool[2][3]);
		test1.setElementAt(new Bool(false), 0, 0);
		test1.setElementAt(new Bool(true), 0, 1);
		test1.setElementAt(new Bool(true), 0, 2);
		test1.setElementAt(new Bool(true), 1, 0);
		test1.setElementAt(new Bool(false), 1, 1);
		test1.setElementAt(new Bool(true), 1, 2);
		
		assertTrue(test1.equals(boolMatrix.plus(boolMatrixx)));
	}

	@Test
	public final void testGetCopy() {
		Matrix<Bool> test11 = (Matrix<Bool>) boolMatrix.getCopy();
		Matrix<Bool> test1 = (Matrix<Bool>) boolMatrix1.getCopy();
		Matrix<Bool> test12 = (Matrix<Bool>) boolMatrixx.getCopy();
		Matrix<Bool> test = (Matrix<Bool>) boolMatrix2.getCopy();
		
		assertEquals(boolMatrix, test11);
		assertEquals(boolMatrix1, test1);
		assertEquals(boolMatrix2, test);
		assertEquals(boolMatrixx, test12);
	}

	@Test
	public final void testEqualsObject() {
		Matrix<Bool> test11 = (Matrix<Bool>) boolMatrix.getCopy();
		assertTrue(test11.equals(boolMatrix));
		assertFalse(test11.equals(boolMatrixx));
		assertFalse(test11.equals(boolMatrix2));
	}
	
	public QRMatrix<Bool> getMatrix(int r, int c)
	{
		QRMatrix<Bool> ma = new Matrix<Bool>(r,c);
		for(int i = 0; i < r; i++ )
		{
			for(int j = 0; j < c; j++ )
			{
				ma.setElementAt(new Bool(randomBoolean()), i, j);  
			}
		}
		return ma;
	}
	public boolean randomBoolean()
	{
		int g;
		g = (int) ( Math.random() * 2 );
		if(g==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}


}

