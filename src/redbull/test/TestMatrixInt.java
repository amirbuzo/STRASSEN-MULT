package redbull.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redbull.Int;
import redbull.Matrix;
import redbull.NotCompatibleException;
import redbull.QRMatrix;


public class TestMatrixInt {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHashCode() {
		Matrix<Int> matrix = new Matrix<Int>(2,2);
		matrix.setElementAt(new Int(1), 0, 0);
		matrix.setElementAt(new Int(2), 1, 0);
		matrix.setElementAt(new Int(3), 0, 1);
		matrix.setElementAt(new Int(4), 1, 1);
		
		Matrix<Int> matrix1 = new Matrix<Int>(2,2);
		matrix1.setElementAt(new Int(1), 0, 0);
		matrix1.setElementAt(new Int(2), 1, 0);
		matrix1.setElementAt(new Int(3), 0, 1);
		matrix1.setElementAt(new Int(4), 1, 1);
		//System.out.println(matrix.toString());
		//System.out.println(matrix.hashCode()+ " hash code" + matrix1.hashCode());
		//System.out.println(matrix.hashCode());
		assertEquals(matrix.hashCode(),matrix1.hashCode());
		
		
		Matrix<Int> b = new Matrix<Int>(3,2);
		b.setElementAt(new Int(3), 0, 0);
		b.setElementAt(new Int(-1), 0, 1);
		b.setElementAt(new Int(1), 1, 0);
		b.setElementAt(new Int(2), 1, 1);
		b.setElementAt(new Int(6), 2, 0);
		b.setElementAt(new Int(1), 2, 1);
		
		Matrix<Int> t = new Matrix<Int>(3,2);
		t.setElementAt(new Int(-1), 0, 0);
		t.setElementAt(new Int(3), 0, 1);
		t.setElementAt(new Int(2), 1, 0);
		t.setElementAt(new Int(1), 1, 1);
		t.setElementAt(new Int(1), 2, 0);
		t.setElementAt(new Int(6), 2, 1);
	
		Matrix<Int> g = new Matrix<Int>(3,2);
		g.setElementAt(new Int(-1), 0, 0);
		g.setElementAt(new Int(3), 0, 1);
		g.setElementAt(new Int(1), 1, 0);
		g.setElementAt(new Int(6), 1, 1);
		g.setElementAt(new Int(2), 2, 0);
		g.setElementAt(new Int(1), 2, 1);
		
		System.out.println("HashCode b\n" + b.toString()+ b.hashCode());
		System.out.println("HashCode t\n"+t.toString()+ t.hashCode());
		System.out.println("HashCode g\n"+g.toString()+ g.hashCode());
		
		QRMatrix<Int> a = getMatrix(1000,1000);
		QRMatrix<Int> e = getMatrix(1000,1000);
		

		long milis1 = System.currentTimeMillis();

		System.out.println(a.hashCode());
		System.out.println(e.hashCode());

		long milis2 = System.currentTimeMillis();	

		long diff = milis2 - milis1;
		long diffSeconds = diff / 1000;
		
		System.out.println("seconds:" + diff);
	}
	
	@Test
	public final void testRows() {
		Matrix<Int> matrix1 = new Matrix<Int>(2,4);
		matrix1.setElementAt(new Int(1), 0, 0);
		matrix1.setElementAt(new Int(2), 1, 0);
		matrix1.setElementAt(new Int(3), 0, 1);
		matrix1.setElementAt(new Int(4), 1, 1);
		
		assertEquals(2, matrix1.rows());
	}

	@Test
	public final void testColumns() {
		Matrix<Int> matrix1 = new Matrix<Int>(2,4);
		matrix1.setElementAt(new Int(1), 0, 0);
		matrix1.setElementAt(new Int(2), 1, 0);
		matrix1.setElementAt(new Int(3), 0, 1);
		matrix1.setElementAt(new Int(4), 1, 1);
		assertEquals(4, matrix1.columns());
	}

	@Test
	public final void testSparseness() {
		Matrix<Int> b = new Matrix<Int>(3,3);
		b.setElementAt(new Int(7), 0, 0);
		b.setElementAt(new Int(0), 0, 1);
		b.setElementAt(new Int(0), 0, 2);
		b.setElementAt(new Int(0), 1, 0);
		b.setElementAt(new Int(2), 1, 1);
		b.setElementAt(new Int(0), 1, 2);
		b.setElementAt(new Int(0), 2, 0);
		b.setElementAt(new Int(0), 2, 1);
		b.setElementAt(new Int(2), 2, 2);
		
		double sparsness = (double) ((double)6 /(double) 9);
		assertTrue(sparsness==b.sparseness());
	}

	@Test (expected=IndexOutOfBoundsException.class)
	public final void testGetElementAt() {
		Matrix<Int> matrix1 = new Matrix<Int>(2,4);
		matrix1.setElementAt(new Int(1), 0, 0);
		matrix1.setElementAt(new Int(2), 1, 0);
		matrix1.setElementAt(new Int(3), 0, 1);
		matrix1.setElementAt(new Int(4), 1, 1);
		
		assertEquals(matrix1.getElementAt(0,0),new Int(1));
		assertEquals(matrix1.getElementAt(1,0),new Int(2));
		assertEquals(matrix1.getElementAt(0,1),new Int(3));
		assertEquals(matrix1.getElementAt(1,1),new Int(4));
		matrix1.getElementAt(4,4);
	}

	@Test (expected=IndexOutOfBoundsException.class)
	public final void testSetElementAt() {
		Matrix<Int> matrix1 = new Matrix<Int>(2,2);
		matrix1.setElementAt(new Int(1), 0, 0);
		matrix1.setElementAt(new Int(2), 1, 0);
		matrix1.setElementAt(new Int(3), 0, 1);
		matrix1.setElementAt(new Int(4), 1, 1);
		

		Matrix<Int> matrix2 = new Matrix<Int>(2,2);
		matrix2.setElementAt(new Int(4), 0, 0);
		matrix2.setElementAt(new Int(3), 1, 0);
		matrix2.setElementAt(new Int(2), 0, 1);
		matrix2.setElementAt(new Int(1), 1, 1);
		
		assertFalse(matrix1.equals(matrix2));

		matrix1.setElementAt(new Int(4), 0, 0);
		matrix1.setElementAt(new Int(3), 1, 0);
		matrix1.setElementAt(new Int(2), 0, 1);
		matrix1.setElementAt(new Int(1), 1, 1);
		assertEquals(matrix1,matrix2);
		matrix2.setElementAt(new Int(2), 3, 3);
		
		

	}

	@Test(expected=IndexOutOfBoundsException.class)
	public final void testGetColumn() {
		
		Matrix<Int> b = new Matrix<Int>(3,2);
		b.setElementAt(new Int(3), 0, 0);
		b.setElementAt(new Int(-1), 0, 1);
		b.setElementAt(new Int(1), 1, 0);
		b.setElementAt(new Int(2), 1, 1);
		b.setElementAt(new Int(6), 2, 0);
		b.setElementAt(new Int(1), 2, 1);
		//System.out.println("B" + b.toString());
    	b.getColumn(3);
		Matrix<Int> c = new Matrix<Int>(3,1);
		c.setElementAt(new Int(3), 0, 0);
		c.setElementAt(new Int(1), 1, 0);
		c.setElementAt(new Int(6), 2, 0);
		
		//System.out.println(c.toString());
		assertTrue(c.equals(b.getColumn(0)));
	}

	@Test//(expected=IndexOutOfBoundsException.class)
	public final void testGetRow() {
		Matrix<Int> b = new Matrix<Int>(3,2);
		b.setElementAt(new Int(3), 0, 0);
		b.setElementAt(new Int(-1), 0, 1);
		b.setElementAt(new Int(1), 1, 0);
		b.setElementAt(new Int(2), 1, 1);
		b.setElementAt(new Int(6), 2, 0);
		b.setElementAt(new Int(1), 2, 1);
		//System.out.println("B" + b.toString());
				
		Matrix<Int> c = new Matrix<Int>(1,2);
		c.setElementAt(new Int(3), 0, 0);
		c.setElementAt(new Int(-1), 0, 1);
		
		
		Matrix<Int> d = new Matrix<Int>(1,2);
		d.setElementAt(new Int(1), 0, 0);
		d.setElementAt(new Int(2), 0, 1);
		
		
		Matrix<Int> e = new Matrix<Int>(1,2);
		e.setElementAt(new Int(6), 0, 0);
		e.setElementAt(new Int(1), 0, 1);
		
		//System.out.println(b.getRow(0));
		//System.out.println(b.getRow(1));
		//System.out.println(b.getRow(2));
		assertTrue(c.equals(b.getRow(0)));

		//System.out.println(b.toString());
		//System.out.println(b.getRow(1));
		assertTrue(d.equals(b.getRow(1)));
		assertTrue(e.equals(b.getRow(2)));
		
		//b.getRow(3);//throws error

	}

	@Test
	public final void testGetTranspose() {
		Matrix<Int> b = new Matrix<Int>(3,2);
		b.setElementAt(new Int(1), 0, 0);
		b.setElementAt(new Int(2), 0, 1);
		b.setElementAt(new Int(3), 1, 0);
		b.setElementAt(new Int(4), 1, 1);
		b.setElementAt(new Int(5), 2, 0);
		b.setElementAt(new Int(6), 2, 1);
		//System.out.println("Transpose" + b.toString());
		//Matrix taken from internet
		Matrix<Int> c = new Matrix<Int>(2,3);
		c.setElementAt(new Int(1), 0, 0);
		c.setElementAt(new Int(3), 0, 1);
		c.setElementAt(new Int(5), 0, 2);
		c.setElementAt(new Int(2), 1, 0);
		c.setElementAt(new Int(4), 1, 1);
		c.setElementAt(new Int(6), 1, 2);
		
		Matrix<Int> transpose=  b.getTranspose();

		assertEquals(transpose,c);
		//System.out.println(transpose.toString());
	}

	@Test
	public final void testIsSquare() {
		Matrix<Int> a = new Matrix<Int>(2,2);
		assertTrue(a.isSquare());
		Matrix<Int> b = new Matrix<Int>(3,2);
		assertFalse(b.isSquare());

		Matrix<Int> c = new Matrix<Int>(4,3);
		assertFalse(c.isSquare());

		Matrix<Int> d = new Matrix<Int>(3,3);
		assertTrue(d.isSquare());
		
	}

	@Test
	public final void testIsDiagonal() {
		Matrix<Int> b = new Matrix<Int>(3,3);
		b.setElementAt(new Int(7), 0, 0);
		b.setElementAt(new Int(0), 0, 1);
		b.setElementAt(new Int(0), 0, 2);
		b.setElementAt(new Int(0), 1, 0);
		b.setElementAt(new Int(2), 1, 1);
		b.setElementAt(new Int(0), 1, 2);
		b.setElementAt(new Int(0), 2, 0);
		b.setElementAt(new Int(0), 2, 1);
		b.setElementAt(new Int(2), 2, 2);
		assertTrue(b.isDiagonal());
		Matrix<Int> c = new Matrix<Int>(3,3);
		c.setElementAt(new Int(7), 0, 0);
		c.setElementAt(new Int(0), 0, 1);
		c.setElementAt(new Int(3), 0, 2);
		c.setElementAt(new Int(0), 1, 0);
		c.setElementAt(new Int(2), 1, 1);
		c.setElementAt(new Int(0), 1, 2);
		c.setElementAt(new Int(0), 2, 0);
		c.setElementAt(new Int(0), 2, 1);
		c.setElementAt(new Int(2), 2, 2);
		assertFalse(c.isDiagonal());
	}

	@Test 
	public final void testToString() {
		Matrix<Int> b = new Matrix<Int>(2,3);
		b.setElementAt(new Int(0), 0, 0);
		b.setElementAt(new Int(-1), 0, 1);
		b.setElementAt(new Int(2), 0, 2);
		b.setElementAt(new Int(-1), 1, 0);
		b.setElementAt(new Int(11), 1, 1);
		b.setElementAt(new Int(2), 1, 2);
		
		Matrix<Int> c = new Matrix<Int>(2,3);
		c.setElementAt(new Int(0), 0, 0);
		c.setElementAt(new Int(-1), 0, 1);
		c.setElementAt(new Int(2), 0, 2);
		c.setElementAt(new Int(-1), 1, 0);
		c.setElementAt(new Int(11), 1, 1);
		c.setElementAt(new Int(2), 1, 2);
		assertEquals(b,c);
		

	}

	@Test
	public final void testIsSymmetric() {
		Matrix<Int> a = new Matrix<Int>(3,3);
		a.setElementAt(new Int(0), 0, 0);
		a.setElementAt(new Int(-1), 0, 1);
		a.setElementAt(new Int(2), 0, 2);
		a.setElementAt(new Int(-1), 1, 0);
		a.setElementAt(new Int(14), 1, 1);
		a.setElementAt(new Int(11), 1, 2);
		a.setElementAt(new Int(2), 2, 0);
		a.setElementAt(new Int(11), 2, 1);
		a.setElementAt(new Int(12), 2, 2);
		//System.out.println(a.toString());
		assertTrue(a.isSymmetric());
		
		Matrix<Int> b = new Matrix<Int>(2,3);
		b.setElementAt(new Int(0), 0, 0);
		b.setElementAt(new Int(-1), 0, 1);
		b.setElementAt(new Int(2), 0, 2);
		b.setElementAt(new Int(-1), 1, 0);
		b.setElementAt(new Int(11), 1, 1);
		b.setElementAt(new Int(2), 1, 2);
		//System.out.println(b.toString());
		assertFalse(b.isSymmetric());
	}

	@Test//(expected=NotCompatibleException.class)
	public final void testMult() {
		Matrix<Int> a = new Matrix<Int>(2,3);
		a.setElementAt(new Int(0), 0, 0);
		a.setElementAt(new Int(-1), 0, 1);
		a.setElementAt(new Int(2), 0, 2);
		a.setElementAt(new Int(4), 1, 0);
		a.setElementAt(new Int(11), 1, 1);
		a.setElementAt(new Int(2), 1, 2);
		
		//System.out.println(a.toString());
		Matrix<Int> b = new Matrix<Int>(3,2);
		b.setElementAt(new Int(3), 0, 0);
		b.setElementAt(new Int(-1), 0, 1);
		b.setElementAt(new Int(1), 1, 0);
		b.setElementAt(new Int(2), 1, 1);
		b.setElementAt(new Int(6), 2, 0);
		b.setElementAt(new Int(1), 2, 1);
		
		//System.out.println(b.toString());

		Matrix<Int> test = new Matrix<Int>(2,2);
		test.setElementAt(new Int(11), 0, 0);
		test.setElementAt(new Int(0), 0, 1);
		test.setElementAt(new Int(35), 1, 0);
		test.setElementAt(new Int(20), 1, 1);
	
		Matrix<Int> r = (Matrix<Int>) a.mult(b);
		//System.out.println(r.toString());
		//System.out.println(test.toString());
		assertTrue(r.equals(test));
		
		//a.mult(r);
	}

	@Test (expected=NotCompatibleException.class)
	public final void testPlus() {
		Matrix<Int> a = new Matrix<Int>(2,3);
		a.setElementAt(new Int(0), 0, 0);
		a.setElementAt(new Int(-1), 0, 1);
		a.setElementAt(new Int(2), 0, 2);
		a.setElementAt(new Int(4), 1, 0);
		a.setElementAt(new Int(11), 1, 1);
		a.setElementAt(new Int(2), 1, 2);
		
		//System.out.println(a.toString());
		Matrix<Int> b = new Matrix<Int>(3,2);
		b.setElementAt(new Int(3), 0, 0);
		b.setElementAt(new Int(-1), 0, 1);
		b.setElementAt(new Int(1), 1, 0);
		b.setElementAt(new Int(2), 1, 1);
		b.setElementAt(new Int(6), 2, 0);
		b.setElementAt(new Int(1), 2, 1);
		
		a.plus(b).toString();
		
		Matrix<Int> d = new Matrix<Int>(2,2);
		d.setElementAt(new Int(0), 0, 0);
		d.setElementAt(new Int(-1), 0, 1);
		d.setElementAt(new Int(2), 1, 0);
		d.setElementAt(new Int(4), 1, 1);
		
		Matrix<Int> e = new Matrix<Int>(2,2);
		e.setElementAt(new Int(3), 0, 0);
		e.setElementAt(new Int(-1), 0, 1);
		e.setElementAt(new Int(1), 1, 0);
		e.setElementAt(new Int(2), 1, 1);
		
		Matrix<Int> f = new Matrix<Int>(2,2);
		f.setElementAt(new Int(3), 0, 0);
		f.setElementAt(new Int(-2), 0, 1);
		f.setElementAt(new Int(3), 1, 0);
		f.setElementAt(new Int(6), 1, 1);
		
		assertEquals(d.plus(e),e.plus(d));
		assertEquals(d.plus(e),f);
	}

	@Test
	public final void testGetCopy(){
		Matrix<Int> f = new Matrix<Int>(2,2);
		f.setElementAt(new Int(3), 0, 0);
		f.setElementAt(new Int(-2), 0, 1);
		f.setElementAt(new Int(3), 1, 0);
		f.setElementAt(new Int(6), 1, 1);
		Matrix<Int> z = (Matrix<Int>) f.getCopy();
		//System.out.println("C"+z.columns());
		//System.out.println("R"+z.rows());
		//System.out.println("Copy\n" + z.toString() +"\n"+ f.toString());
		assertTrue(f.equals(z));
		assertEquals(f,z);
		//System.out.println(z.toString()+ f.toString());

		z.setElementAt(new Int(33), 1, 1);
		
		//System.out.println(z.toString()+ f.toString());
		assertFalse(f.equals(z));
		
		
		
	}

	@Test
	public final void testEqualsObject() {
		Matrix<Int> b = new Matrix<Int>(3,2);
		b.setElementAt(new Int(3), 0, 0);
		b.setElementAt(new Int(-1), 0, 1);
		b.setElementAt(new Int(1), 1, 0);
		b.setElementAt(new Int(2), 1, 1);
		b.setElementAt(new Int(6), 2, 0);
		b.setElementAt(new Int(1), 2, 1);
	
		Matrix<Int> c = new Matrix<Int>(3,2);
		c.setElementAt(new Int(3), 0, 0);
		c.setElementAt(new Int(-1), 0, 1);
		c.setElementAt(new Int(1), 1, 0);
		c.setElementAt(new Int(2), 1, 1);
		c.setElementAt(new Int(6), 2, 0);
		c.setElementAt(new Int(1), 2, 1);
		assertTrue(b.equals(c));
	}
	public int randomNumber()
	{
	
		int g;
		g = 1 + (int) ( Math.random() * 50 );
		return g;
	}
	public QRMatrix<Int> getMatrix(int r, int c)
	{
		QRMatrix<Int> ma = new Matrix<Int>(r,c);
		for(int i = 0; i < r; i++ )
		{
			for(int j = 0; j < c; j++ )
			{
				ma.setElementAt(new Int(randomNumber()), i, j);  
			}
		}
		return ma;
	}
	public boolean randomBoolean()
	{
		int g;
		g = 1 + (int) ( Math.random() * 1 );
		
		return g/1==0.5;
	}

}
