/*
 * File: SquareMatrix.java Author: Amir Buzo Author: Suejb Memeti Date: 22 march 2011
 */
package redbull;

import java.util.LinkedList;

/**
 * A two dimensional <em>square</em> matrix where each element is a ring member. Notice, each matrix
 * (of size NxN) is in itself an element of a ring over all NxN-matrices. This class implements
 * RMatrix Interface.
 * <p/>
 * 
 * @author Amir Buzo
 */
public class SquareMatrix<E extends Ring<E>> implements RMatrix<E> {
	
	private final int side;
	
	final static String space = " ";
	
	private Ring<E>[][] m;
	
	private boolean isNull, isOne, isSymetric, isSparse = false;
	
	private LinkedList<Element<E>>[] sparesematrix1;
	
	/**
	 * Constructor create an empty square matrix with side <tt>this</tt>.
	 */
	@SuppressWarnings("unchecked")
	public SquareMatrix(int i) {
	
		if (i > 0) {
			side = i;
			m = new Ring[side][side];
		} else
			throw new IllegalArgumentException("The index is out of range");
	}
	
	/**
	 * Constructor create an square matrix square Ring array <tt>this</tt>.
	 */
	public SquareMatrix(Ring<E>[][] a4) {
	
		if (a4.length < 1 || a4[0].length < 1)
			throw new ArrayIndexOutOfBoundsException("The index is out of range");
		else {
			side = a4.length;
			m = a4.clone();
			isSparse = isSparse();
			isSymetric = isSymmetric();
			if (isSparse) {
				isNull = getisNull();
				if (isNull)
					isSparse = false;
			} else
				isOne = getisOne();
			// m = new Ring[side][side];
			// for (int i = 0; i < side; i++)
			// System.arraycopy(a4[i], 0, m[i], 0, side);
		}
	}
	
	/**
	 * Returns a Null Square Matrix.
	 * 
	 * @return Matrix which is filled with Null elements.
	 */
	@Override
	public RMatrix<E> getNull() {
	
		// if(!isNull)
		// {
		SquareMatrix<E> g = new SquareMatrix<E>(side);
		final Ring<E> Null = m[0][0].getNull();
		for (int i = 0; i < side; i++)
			g.m[0][i] = Null;
		for (int i = 1; i < side; i++)
			System.arraycopy(g.m[0], 0, g.m[i], 0, side);
		g.isNull = true;
		return g;
		// }
		// else
		// return this.getCopy();
		// for(Ring<E>[] i:g.m)
		// Arrays.fill(i, Null);//i= Null;
		// for(int i=0;i<side; i++)
		// for(int j=0;j<side;j++)
		// g.m[i][j]=Null;
	}
	
	/**
	 * The matrix minus operation. It creates a new matrix without changing the two involved matrices.
	 * A NotCompatibleException is raised if the two matrices are not compatible.
	 */
	@Override
	public RMatrix<E> minus(RMatrix<E> b) {
	
		// TODO Auto-generated method stub
		SquareMatrix<E> e = (SquareMatrix<E>) b;
		if (side != e.side)
			throw new NotCompatibleException("These matrices can not be added");
		else {
			if (e.isOne) {
				SquareMatrix<E> sq = (SquareMatrix<E>) this.getCopy();
				for (int i = 0; i < side; i++)
					sq.m[i][i] = m[i][i].minus(m[0][0].getOne());
				if (isSymetric)
					sq.isSymetric = true;
				return sq;
			} else if (e.isNull) {
				return this.getCopy();
			} else if (isNull) {
				return e.negative();
			} else if (isOne) {
				SquareMatrix<E> sq = (SquareMatrix<E>) e.getCopy();
				for (int i = 0; i < side; i++)
					sq.m[i][i] = sq.m[i][i].minus(m[i][i].getOne());
				return sq;
			} else if (this.isSparse)
				return minusSparse((SquareMatrix<E>) e);
			else {
				SquareMatrix<E> sq = new SquareMatrix<E>(side);
				for (int i = 0; i < side; i++)
					for (int j = 0; j < side; j++)
						sq.m[i][j] = m[i][j].minus(e.getElementAt(i, j));
				return sq;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private SquareMatrix<E> minus1(SquareMatrix<E> e) {
	
		// TODO Auto-generated method stub
		SquareMatrix<E> sq = new SquareMatrix<E>(side);
		for (int i = 0; i < side; i++)
			for (int j = 0; j < side; j++)
				sq.m[i][j] = m[i][j].minus((E) e.m[i][j]);// sq.m[i][j] = m[i][j].minus(e.getElementAt(i,
																									// j));
		return sq;
	}
	
	/**
	 * It creates and return a new square matrix where all the values are negative of this matrix.
	 */
	@Override
	public RMatrix<E> negative() {
	
		if (isSparse) {
			fillSparse();
			SquareMatrix<E> result = (SquareMatrix<E>) this.getCopy();
			for (int i = 0; i < sparesematrix1.length; i++) {
				for (int j = 0; j < sparesematrix1[i].size(); j++) {
					int c = sparesematrix1[i].get(j).getColumn();
					result.m[i][c] = m[i][c].negative();// sparesematrix1[i].get(j).getOb().negative();//
				}
			}
			result.isSparse = true;
			return result;
		} else if (isSymetric) {
			SquareMatrix<E> negat1 = (SquareMatrix<E>) this.getCopy();// new SquareMatrix<E>(side);////new
																																// SquareMatrix<E>(side);//
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++)
					negat1.m[i][j] = negat1.m[i][j].negative();
			negat1.isSymetric = true;
			return negat1;
			// for(int i = 0; i <side ;i++)
			// for(int j = 0; j <= i;j++)
			// negat1.m[i][j] = negat1.m[j][i] = negat1.m[i][j].negative();
			// negat1.isSymetric = true;
			// return negat1;
		} else if (this.isNull)
			return this.getCopy();
		else {
			SquareMatrix<E> negative = new SquareMatrix<E>(side);// (SquareMatrix<E>) this.getCopy();////
																													 // //
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++)
					negative.m[i][j] = m[i][j].negative(); // /negative.m[i][j]= (Ring<E>) new
																								 // Int(-m[i][j].hashCode());//
			return negative;
			// for(Ring<E>[] b:negative.m)
			// for(Ring<E> e:b)
			// e = e.negative();//Arrays.fill(b,e.negative());
			// return negative;
		}
	}
	
	/**
	 * The matrix multiplication operation. It creates a new Square matrix without changing the two
	 * involved matrices. A NotCompatibleException is raised if the two matrices are not compatible.
	 */
	@Override
	public RMatrix<E> mult(RMatrix<E> B) {
	
		// TODO Auto-generated method stub
		if (side == B.size()) {
			if (isNull)
				return getNull();
			else if (((SquareMatrix<E>) B).isOne)
				return this.getCopy();
			else if (isOne)
				return B.getCopy();
			else if (isSparse)
				return multSparse((SquareMatrix<E>) B);
			else
				return multDense((SquareMatrix<E>) B);
		} else
			throw new NotCompatibleException("These two matrices are not compatible for multiplication.");
	}
	
	// Jama Matrix Multiplication
	@SuppressWarnings("unchecked")
	private SquareMatrix<E> multDense(SquareMatrix<E> B) {
	
		if (m[0][0] instanceof Int) {
			SquareMatrix<E> res = new SquareMatrix<E>(side);
			int[][] array = new int[side][side];
			int[][] array1 = new int[side][side];
			int[][] result;// = new int[side][side];
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++) {
					array[i][j] = ((Int) m[i][j]).value();// this.m[i][j].hashCode();
					array1[i][j] = ((Int) B.m[i][j]).value();// B.m[i][j].hashCode();
				}
			result = strassenMatrixMultiplication(array, array1);
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++)
					res.m[i][j] = (E) new Int(result[i][j]);
			return res;
		} else
			return multStrassen(B);
	}
	
	private boolean getisOne() {
	
		// TODO Auto-generated method stub
		// Ring<Int> One = new Int(1);//new Int(0);
		// Ring<Int> Null = new Int(0);
		// if(getOne().equals(this))
		// return true;
		// else
		// return false;
		for (int i = 0; i < side; i++)
			for (int j = 0; j < side; j++)
				if ((i == j && !m[i][j].equals(m[0][0].getOne()))
				    || (i != j && !m[i][j].equals(m[0][0].getNull())))
					return false;
		return true;
	}
	
	private boolean getisNull() {
	
		if (m[0][0] instanceof Int) {
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++)
					if (((Int) m[i][j]).value() != 0)
						return false;
			return true;
		} else {
			final Ring<E> Null = m[0][0].getNull();
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++)
					if (!m[i][j].equals(Null))
						return false;
			return true;
		}
	}
	
	// private RMatrix<E> multNaiveJama(RMatrix<E> e) {
	// // TODO Auto-generated method stub
	// SquareMatrix<E> result = new SquareMatrix<E>(side);
	//
	// Ring<E> z = this.m[0][0].getNull();
	// Ring<E>[] b = new Ring[side];
	// for (int j = 0; j < side; j++)
	// {
	// for (int k = 0; k < side; k++)
	// b[k] = e.getElementAt(k, j);
	// for (int i = 0; i < side; i++)
	// {
	// Ring<E>[] ar = m[i];
	// Ring<E> sum =z;
	// for (int k = 0; k < side; k++)
	// sum = sum.plus((E) ar[k].mult((E) b[k]));
	//
	// result.m[i][j] =(Ring<E>) sum;
	// }
	// }
	// return result;
	//
	// }
	// private SquareMatrix<E> multNaive1(SquareMatrix<E> b)
	// {
	// Ring<E> sum=this.m[0][0].getNull();
	// SquareMatrix<E> ma1 = new SquareMatrix<E>(side);
	// for (int i = 0; i < side; i++)
	// {
	// for (int j = 0; j < side; j++)
	// {
	// for (int k = 0; k <side ; k++)
	// {
	// sum = sum.plus(m[i][k].mult(b.getElementAt(k, j)));
	// }
	// ma1.m[i][j]=sum;
	// sum = this.m[0][0].getNull();
	// }
	// }
	// return ma1;
	// }
	// @SuppressWarnings("unchecked")
	// private SquareMatrix<E> multNaive1int(SquareMatrix<E> b)
	// {
	// int sum=0;
	// SquareMatrix<E> ma1 = new SquareMatrix<E>(side);
	// int [][] array = new int[side][side];
	// int [][] array1 = new int[side][side];
	// int [][] result = new int[side][side];
	//
	// for (int i = 0; i < side; i++)
	// for (int j = 0; j < side; j++)
	// { array[i][j] = ((Int)this.m[i][j]).value();
	// array1[i][j] = ((Int) b.m[i][j]).value();
	// }
	//
	// for (int i = 0; i < side; i++)
	// for (int j = 0; j < side; j++)
	// { for (int k = 0; k <side ; k++)
	// { sum = sum + (array[i][k]*array1[k][j]); //.plus(m[i][k].mult(b.getElementAt(k, j)));
	// }
	// result[i][j]=sum;
	// sum = 0;
	// }
	//
	// for (int i = 0; i < side; i++)
	// for (int j = 0; j < side; j++)
	// ma1.m[i][j] = (Ring<E>) new Int(result[i][j]);
	//
	// return ma1;
	// }
	// private int [][] multNaive1int(int [][] array, int [][] array1)
	// {//87sek me Int()
	// int sum=0;
	//
	// int [][] result = new int[array.length][array.length];
	// for (int i = 0; i < array.length; i++)
	// for (int j = 0; j < array.length; j++)
	// {
	// for (int k = 0; k <array.length ; k++)
	// {
	// sum = sum + (array[i][k]*array1[k][j]); //.plus(m[i][k].mult(b.getElementAt(k, j)));
	// }
	//
	// result[i][j]=sum;
	// sum = 0;
	// }
	// return result;
	// }
	private int[][] multNaive1Jama(int[][] a, int[][] b) {
	
		int[][] c = new int[a.length][a.length];
		final int m = a.length;
		final int n = b.length;
		final int p = b[0].length;
		final int[] Bcolj = new int[n];
		for (int j = 0; j < p; j++) {
			for (int k = 0; k < n; k++) {
				Bcolj[k] = b[k][j];
			}
			for (int i = 0; i < m; i++) {
				final int[] Arowi = a[i];
				int s = 0;
				for (int k = 0; k < n; k++)
					s += Arowi[k] * Bcolj[k];
				c[i][j] = s;
			}
		}
		return c;
	}
	
	@SuppressWarnings("unchecked")
	private SquareMatrix<E> multStrassen(SquareMatrix<E> B) {
	
		// TODO Auto-generated method stub
		if (side <= 128) {
			SquareMatrix<E> result = new SquareMatrix<E>(side);
			Ring<E> z = this.m[0][0].getNull();
			Ring<E>[] b = new Ring[side];
			for (int j = 0; j < side; j++) {
				for (int k = 0; k < side; k++)
					b[k] = B.getElementAt(k, j);
				for (int i = 0; i < side; i++) {
					Ring<E>[] ar = m[i];
					Ring<E> sum = z;
					for (int k = 0; k < side; k++)
						sum = sum.plus((E) ar[k].mult((E) b[k]));
					result.m[i][j] = (Ring<E>) sum;
				}
			}
			return result;
		} else {
			if (side % 2 != 0) {
				SquareMatrix<E> result = new SquareMatrix<E>(side);
				int side1 = side + 1;
				E not = this.m[0][0].getNull();// (0);
				SquareMatrix<E> A1 = new SquareMatrix<E>(side1);
				SquareMatrix<E> B1 = new SquareMatrix<E>(side1);
				for (int i = 0; i < side1; i++)
					for (int j = 0; j < side1; j++) {
						if ((i == side) || (j == side)) {
							A1.m[i][j] = not;
							B1.m[i][j] = not;
						} else {
							A1.m[i][j] = (Ring<E>) this.getElementAt(i, j);// [i][j];
							B1.m[i][j] = (Ring<E>) B.getElementAt(i, j);
						}
					}
				SquareMatrix<E> C1 = new SquareMatrix<E>(side1);
				C1 = (SquareMatrix<E>) A1.multStrassen(B1);
				for (int i = 0; i < side; i++) {
					for (int j = 0; j < side; j++) {
						result.m[i][j] = (Ring<E>) C1.m[i][j];
					}
				}
				return result;
			} else {
				SquareMatrix<E> result = new SquareMatrix<E>(side);
				SquareMatrix<E> A11 = new SquareMatrix<E>(side / 2);
				SquareMatrix<E> A12 = new SquareMatrix<E>(side / 2);
				SquareMatrix<E> A21 = new SquareMatrix<E>(side / 2);
				SquareMatrix<E> A22 = new SquareMatrix<E>(side / 2);
				SquareMatrix<E> B11 = new SquareMatrix<E>(side / 2);
				SquareMatrix<E> B12 = new SquareMatrix<E>(side / 2);
				SquareMatrix<E> B21 = new SquareMatrix<E>(side / 2);
				SquareMatrix<E> B22 = new SquareMatrix<E>(side / 2);
				divideArray((SquareMatrix<E>) this, A11, 0, 0);
				divideArray((SquareMatrix<E>) this, A12, 0, side / 2);
				divideArray((SquareMatrix<E>) this, A21, side / 2, 0);
				divideArray((SquareMatrix<E>) this, A22, side / 2, side / 2);
				divideArray((SquareMatrix<E>) B, B11, 0, 0);
				divideArray((SquareMatrix<E>) B, B12, 0, side / 2);
				divideArray((SquareMatrix<E>) B, B21, side / 2, 0);
				divideArray((SquareMatrix<E>) B, B22, side / 2, side / 2);
				SquareMatrix<E> P1 = (SquareMatrix<E>) (A11.plus1(A22)).multStrassen(B11.plus1(B22));
				SquareMatrix<E> P2 = (SquareMatrix<E>) (A21.plus1(A22)).multStrassen(B11);
				SquareMatrix<E> P3 = (SquareMatrix<E>) A11.multStrassen(B12.minus1(B22));
				SquareMatrix<E> P4 = (SquareMatrix<E>) A22.multStrassen(B21.minus1(B11));
				SquareMatrix<E> P5 = (SquareMatrix<E>) (A11.plus1(A12)).multStrassen(B22);
				SquareMatrix<E> P6 = (SquareMatrix<E>) (A21.minus1(A11)).multStrassen(B11.plus1(B12));
				SquareMatrix<E> P7 = (SquareMatrix<E>) (A12.minus1(A22)).multStrassen(B21.plus1(B22));
				SquareMatrix<E> C11 = P1.plus1(P4).minus1(P5).plus1(P7);
				SquareMatrix<E> C12 = P3.plus1(P5);
				SquareMatrix<E> C21 = P2.plus1(P4);
				SquareMatrix<E> C22 = P1.plus1(P3).minus1(P2).plus1(P6);
				copySubArray(C11, result, 0, 0);
				copySubArray(C12, result, 0, side / 2);
				copySubArray(C21, result, side / 2, 0);
				copySubArray(C22, result, side / 2, side / 2);
				return result;
			}
		}
	}
	
	private int[][] strassenMatrixMultiplication(int[][] A, int[][] B) {
	
		int n = A.length;
		int[][] result = new int[n][n];
		if (n < 130) {
			return multNaive1Jama(A, B);
		} else {
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];
			divideArray(A, A11, 0, 0);
			divideArray(A, A12, 0, n / 2);
			divideArray(A, A21, n / 2, 0);
			divideArray(A, A22, n / 2, n / 2);
			divideArray(B, B11, 0, 0);
			divideArray(B, B12, 0, n / 2);
			divideArray(B, B21, n / 2, 0);
			divideArray(B, B22, n / 2, n / 2);
			int[][] P1 = strassenMatrixMultiplication(addMatrices(A11, A22), addMatrices(B11, B22));
			int[][] P2 = strassenMatrixMultiplication(addMatrices(A21, A22), B11);
			int[][] P3 = strassenMatrixMultiplication(A11, subtractMatrices(B12, B22));
			int[][] P4 = strassenMatrixMultiplication(A22, subtractMatrices(B21, B11));
			int[][] P5 = strassenMatrixMultiplication(addMatrices(A11, A12), B22);
			int[][] P6 = strassenMatrixMultiplication(subtractMatrices(A21, A11), addMatrices(B11, B12));
			int[][] P7 = strassenMatrixMultiplication(subtractMatrices(A12, A22), addMatrices(B21, B22));
			int[][] C11 = addMatrices(subtractMatrices(addMatrices(P1, P4), P5), P7);
			int[][] C12 = addMatrices(P3, P5);
			int[][] C21 = addMatrices(P2, P4);
			int[][] C22 = addMatrices(subtractMatrices(addMatrices(P1, P3), P2), P6);
			copySubArray(C11, result, 0, 0);
			copySubArray(C12, result, 0, n / 2);
			copySubArray(C21, result, n / 2, 0);
			copySubArray(C22, result, n / 2, n / 2);
			return result;
		}
	}
	
	private int[][] addMatrices(int[][] A, int[][] B) {
	
		final int n = A.length;
		int[][] result = new int[n][n];
		// PlusArrayThread t1= new PlusArrayThread(A, B,result, A.length/2, A.length);
		// t1.run();
		for (int i = 0; i < A.length / 2; i++)
			for (int j = 0; j < n; j++)
				result[i][j] = A[i][j] + B[i][j];
		// while(t1.isAlive())
		// continue;
		return result;
	}
	
	private int[][] subtractMatrices(int[][] A, int[][] B) {
	
		int n = A.length;
		int[][] result = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				result[i][j] = A[i][j] - B[i][j];
		return result;
	}
	
	private void divideArray(int[][] parent, int[][] child, int iB, int jB) {
	
		for (int i1 = 0, i2 = iB; i1 < child.length; i1++, i2++)
			for (int j1 = 0, j2 = jB; j1 < child.length; j1++, j2++)
				child[i1][j1] = parent[i2][j2];
	}
	
	private void copySubArray(int[][] child, int[][] parent, int iB, int jB) {
	
		for (int i1 = 0, i2 = iB; i1 < child.length; i1++, i2++)
			for (int j1 = 0, j2 = jB; j1 < child.length; j1++, j2++)
				parent[i2][j2] = child[i1][j1];
	}
	
	@SuppressWarnings("unchecked")
	private SquareMatrix<E> plus1(SquareMatrix<E> e) {
	
		SquareMatrix<E> matrix = new SquareMatrix<E>(side);
		for (int i = 0; i < side; i++)
			for (int j = 0; j < side; j++)
				matrix.m[i][j] = m[i][j].plus((E) e.m[i][j]);// setElementAt(m[i][j].plus( e.getElementAt(i,
																										 // j)), i,j);
		return matrix;
	}
	
	/**
	 * The matrix addition operation. It creates a new Square matrix without changing the two involved
	 * matrices. A NotCompatibleException is raised if the two matrices are not compatible.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RMatrix<E> plus(RMatrix<E> b) {
	
		// TODO Auto-generated method stub
		SquareMatrix<E> e = (SquareMatrix<E>) b;
		if (side != e.side)
			throw new NotCompatibleException("These matrices can not be added");
		else {
			if (e.isNull || isNull) {
				if (e.isNull)
					return this.getCopy();
				else
					return e.getCopy();
			} else if (e.isSparse || isSparse) {
				if (isSparse)
					return plusSparse(e);
				else
					return e.plusSparse(this);
			}
			// else if(e.isSymetric || isSymetric)//me vone
			// {
			// if(isSymetric)
			// {
			// SquareMatrix<E> matrix = new SquareMatrix<E>(side);
			// for(int i = 0; i<side ;i++)
			// for(int j = 0; j <= i;j++)
			// { matrix.m[i][j] = m[i][j].plus((E) e.m[i][j]);// e.getElementAt(i, j))
			// matrix.m[j][i] = m[i][j].plus((E) e.m[j][i]);// e.getElementAt(i, j))
			// }
			// return matrix;
			// }
			// else
			// {
			// SquareMatrix<E> res = new SquareMatrix<E>(side);
			// for(int i = 0; i <side ;i++)
			// for(int j = 0; j <= i;j++)
			// {
			// res.m[i][j] = m[i][j].plus((E) e.m[i][j]);
			// res.m[j][i] = m[j][i].plus((E) e.m[i][j]);
			// }
			// return res;
			// }
			// else if(e.isSymetric && isSymetric)//me vone
			// {
			// //System.out.println("Hyri hyri");
			// SquareMatrix<E> res = new SquareMatrix<E>(side);
			// for(int i = 0; i <side ;i++)
			// for(int j = 0; j <= i;j++)
			// res.m[j][i] = res.m[i][j]= m[i][j].plus((E) e.m[i][j]);
			//
			// res.isSymetric=true;
			// return res;
			// }
			// }
			else if (e.isOne || isOne) {
				if (e.isOne) {
					SquareMatrix<E> sq = (SquareMatrix<E>) this.getCopy();
					for (int i = 0; i < side; i++)
						sq.m[i][i] = m[i][i].plus((E) m[i][i]);
					return sq;
				} else {
					SquareMatrix<E> sq = (SquareMatrix<E>) e.getCopy();
					for (int i = 0; i < side; i++)
						sq.m[i][i] = e.m[i][i].plus(m[0][0].getOne());
					return sq;
				}
			} else
				return plusThread((SquareMatrix<E>) e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private SquareMatrix<E> plusThread(SquareMatrix<E> e) {
	
		// if(m[0][0] instanceof Int)
		// {
		// if(side < 2000 )
		// {
		// SquareMatrix<E> matrix = new SquareMatrix<E>(side);
		// for (int i=0; i < side; i++)
		// for(int j = 0; j < side; j++)
		// matrix.m[i][j] =(E) new Int( ((Int)m[i][j]).value() + ((Int)e.m[i][j]).value());// )
		// return matrix;
		// }
		// else
		// {
		// SquareMatrix<E> res = new SquareMatrix<E>(side);
		// PlusThreadInt t1 = new PlusThreadInt(res, e, 0, side/2);
		// t1.setPriority(Thread.MAX_PRIORITY);
		// t1.start();
		// for (int i = side/2; i < side; i++)
		// for(int j = 0; j < side; j++)
		// res.m[i][j] =(E) new Int( ((Int)m[i][j]).value() + ((Int)e.m[i][j]).value());// )
		//
		// //res.m[i][j] = m[i][j].plus((E) e.m[i][j]);
		//
		// while(t1.isAlive())
		// System.out.println(t1.isAlive());
		// System.out.println("A"+t1.isAlive());
		// return res;
		// }
		// }
		// else{
		if (side < 1000) {
			SquareMatrix<E> matrix = new SquareMatrix<E>(side);
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++)
					matrix.m[i][j] = m[i][j].plus((E) e.m[i][j]);// )
			return matrix;
		} else {
			SquareMatrix<E> res = new SquareMatrix<E>(side);
			PlusThread t1 = new PlusThread(res, e, 0, side / 2);
			t1.setPriority(Thread.MAX_PRIORITY);
			t1.start();
			for (int i = side / 2; i < side; i++)
				for (int j = 0; j < side; j++)
					res.m[i][j] = m[i][j].plus((E) e.m[i][j]);
			while (t1.isAlive())
				continue;
			return res;
		}
		// }
	}
	
	@SuppressWarnings("unchecked")
	private SquareMatrix<E> plusSparse(SquareMatrix<E> e) {
	
		// TODO Auto-generated method stub
		fillSparse();
		SquareMatrix<E> result = (SquareMatrix<E>) this.getCopy();
		result.isSparse = false;
		int c = -1;
		for (int i = 0; i < e.sparesematrix1.length; i++)
			for (int j = 0; j < e.sparesematrix1[i].size(); j++) {
				c = e.sparesematrix1[i].get(j).getColumn();
				result.m[i][c] = e.m[i][c].plus((E) m[i][c]);
			}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void fillSparse() {
	
		if (sparesematrix1 == null) {
			sparesematrix1 = new LinkedList[side];
			if (m[0][0] instanceof Int) {
				for (int i = 0; i < side; i++) {
					LinkedList<Element<E>> lrow = new LinkedList<Element<E>>();
					for (int j = 0; j < side; j++)
						if (((Int) this.m[i][j]).value() != 0)
							lrow.addLast(new Element<E>(this.m[i][j], j));
					sparesematrix1[i] = lrow;
				}
			} else {
				final E Null = m[0][0].getNull();
				for (int i = 0; i < side; i++) {
					LinkedList<Element<E>> lrow = new LinkedList<Element<E>>();
					for (int j = 0; j < side; j++)
						if (!this.m[i][j].equals(Null))
							lrow.addLast(new Element<E>(this.m[i][j], j));
					sparesematrix1[i] = lrow;
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private SquareMatrix<E> minusSparse(SquareMatrix<E> e) {
	
		// TODO Auto-generated method stub
		SquareMatrix<E> result = (SquareMatrix<E>) e.getCopy();
		fillSparse();
		int c;
		for (int i = 0; i < this.sparesematrix1.length; i++) {
			for (int j = 0; j < sparesematrix1[i].size(); j++) {
				c = sparesematrix1[i].get(j).getColumn();
				result.m[i][c] = m[i][c].minus((E) e.m[i][c]);
			}
		}
		return result;
	}
	
	/**
	 * Returns a new matrix where the QRing Elements in the diagonal are One.
	 */
	@Override
	public RMatrix<E> getOne() {
	
		SquareMatrix<E> g = (SquareMatrix<E>) this.getNull();
		// final Ring<E> Null = m[0][0].getNull() ;
		// for(int i=0; i<side; i++)
		// g.m[0][i]= Null;
		// for (int i = 1; i < side; i++)
		// System.arraycopy(g.m[0], 0, g.m[i], 0, side);
		final Ring<E> One = m[0][0].getOne();
		for (int i = 0; i < side; i++)
			g.m[i][i] = One;
		g.isNull = false;
		g.isOne = true;
		return g;
	}
	
	/**
	 * Returns the number of rows (or columns) in <tt>this</tt> square matrix.
	 */
	@Override
	public int size() {
	
		return side;
	}
	
	/**
	 * Returns the sparseness (No. of zero elements/ No. of elements) of <tt>this</tt> matrix.
	 * 
	 * @return a double in the range 0.0 to 1.0 indicating the sparseness of <tt>this</tt> matrix.
	 */
	@Override
	public double sparseness() {
	
		int sparse = 0;
		for (int i = 0; i < side; i++)
			for (int j = 0; j < side; j++)
				if (m[i][j].equals(m[0][0].getNull()))
					sparse++;
		return (double) ((double) sparse / (double) (side * side));
	}
	
	/**
	 * Returns the element in position (row,column). Exception (java.lang.IndexOutOfBoundsException)
	 * is thrown if any of the indices are out of range.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E getElementAt(int r, int c) {
	
		if ((E) m[r][c] != null)
			return (E) m[r][c];
		else
			throw new ArrayIndexOutOfBoundsException("Index is out of bounds");
		// TODO Auto-generated method stub
		// if((r > side || r < 0) || (c > side || c < 0))
		// throw new ArrayIndexOutOfBoundsException("Index is out of bounds");
		// else
		// return (E) m[r][c];
	}
	
	/**
	 * Insert a new element in position (row,column). Exception (java.lang.IndexOutOfBoundsException)
	 * is thrown if any of the indices are out of range.
	 */
	@Override
	public void setElementAt(E elem, int row, int column) {
	
		// TODO Auto-generated method stub
		if (!(row < 0 || row > side || column < 0 || column > side))
			m[row][column] = elem;
		else
			throw new IndexOutOfBoundsException("Indexes are out of bounds");
		// if(m[row][column] != null)
		// m[row][column] = elem;
		// else
		// throw new IndexOutOfBoundsException("Indexes are out of bounds");
	}
	
	/**
	 * Given an NxN square matrix, this method returns a column (an Nx1 matrix). Exception
	 * (java.lang.IndexOutOfBoundsException) is thrown if the given column number is out of range.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public QRMatrix<E> getColumn(int column) {
	
		// TODO Auto-generated method stub
		if (m[0][column] != null) {
			Matrix<E> matrix1 = new Matrix<E>(side, 1);
			if (isSymetric) {
				for (int i = 0; i < side; i++)
					matrix1.setElementAt((E) m[column][i], i, 0);
				return matrix1;
			} else {
				for (int i = 0; i < side; i++)
					matrix1.setElementAt((E) m[i][column], i, 0); // .m[i][0] = m[i][column];
				return matrix1;
			}
		} else
			throw new IndexOutOfBoundsException("Column is out of bounds");
		// if(m[0][column] != null)
		// {
		// QRing<E>[][] g= new QRing[side][1];
		// for (int i = 0; i < side; i++)
		// g[i][0]=m[i][column];// matrix1.setElementAt((E) m[i][column], i, 0); //.m[i][0] =
		// m[i][column];
		//
		// return new Matrix<E>(g);
		// }
		// else
		// throw new IndexOutOfBoundsException("Column is out of bounds");
	}
	
	/**
	 * Given an NxN square matrix, this method returns a row (an 1xN matrix). Exception
	 * (java.lang.IndexOutOfBoundsException) is thrown if the given column number is out of range.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public QRMatrix<E> getRow(int row) {
	
		if (m[row] != null) {
			QRing<E>[][] g = new QRing[1][side];
			System.arraycopy(m[row], 0, g[0], 0, side);
			return new Matrix<E>(g);
			// return matrix1;
		} else
			throw new IndexOutOfBoundsException("Row is out of bounds");
		// TODO Auto-generated method stub
		// if(row < side && row >= 0)
		// {
		// QRMatrix<E> matrix1 = new Matrix<E>(1, side);
		// for (int j = 0; j < side; j++)
		// {
		// matrix1.setElementAt((E)m[row][j], 0, j);//.setElementAt((E)this.m[row][j], 0, j);
		// }
		//
		// return matrix1;
		// }
		// else
		// {
		// throw new IndexOutOfBoundsException("Row is out of bounds");
		// }
		// Matrix<E> matrix1 = new Matrix<E>(1,side);
		// for (int i = 0; i < side; i++)
		// matrix1.setElementAt((E) m[row][i], 0, i);
	}
	
	/**
	 * Given an NxN square matrix Aij, this method returns the so-called transpose (another NxN matrix
	 * B where Bij = Aji).
	 */
	@Override
	public RMatrix<E> getTranspose() {
	
		if (isSymetric)
			return this.getCopy();
		else if (isOne)
			return this.getCopy();
		else if (isSparse) {
			if (sparesematrix1 == null)
				fillSparse();
			SquareMatrix<E> spars = (SquareMatrix<E>) getNull();
			for (int i = 0; i < sparesematrix1.length; i++)
				for (int j = 0; j < sparesematrix1[i].size(); j++) {
					int c = sparesematrix1[i].get(j).getColumn();
					spars.m[c][i] = m[i][c];// sparesematrix1[i].get(j).getOb();
				}
			spars.isSparse = true;
			spars.isNull = false;
			return spars;
		} else {
			SquareMatrix<E> matrix1 = new SquareMatrix<E>(side);
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++)
					matrix1.m[j][i] = m[i][j];
			return matrix1;
		}
		// else if(getisOne())
		// {
		// SquareMatrix<E> matrix1 = new SquareMatrix<E>(side);
		// int j = side;
		// for(int i=0; i<j; i++){
		// matrix1.m[i][j] = (Ring<E>) new Int(1);
		// j--;
		// }
		// return matrix1;
		//
		// }
		// if(this.isNull)
		// return this.getNull();
		// if(this.isOne)
		// return this.getCopy();
		// else
		// for(int i=0; i < side ; i++)
		// for(int j=0; j < side; j++)
		// if(!m[i][j].equals(m[0][0].getNull()))
		// spars.m[i][j] = m[i][j];
	}
	
	/**
	 * Returns <tt>true</tt> if this square matrix is a <em>diagonal</em> matrix. Otherwise
	 * <tt>false</tt>.
	 */
	@Override
	public boolean isDiagonal() {
	
		// if(isNull)
		// return true;
		// else
		// works very fast; Do not touch anymore
		if (isSparse)
			return false;
		if (isOne)
			return true;
		else {
			for (int i = 0; i < side; i++)
				for (int j = 0; j < side; j++)
					if (i != j)
						if (!m[i][j].equals(m[0][0].getNull()))
							return false;
			return true;
		}
	}
	
	/**
	 * Returns <tt>true</tt> if this matrix is a <em>symmetric</em> matrix. Otherwise <tt>false</tt>.
	 */
	@Override
	public boolean isSymmetric() {
	
		if (this.isSymetric)
			return true;
		else if (this.isOne)
			return true;
		// else if(this.isSparse)
		// return false;
		else {
			if (m[0][0] instanceof Int) {
				for (int i = side; --i >= 0;)
					for (int j = side; --j >= i;)
						if (((Int) m[i][j]).value() != ((Int) m[j][i]).value())
							return false;
				return true;
			} else {
				for (int i = side; --i >= 0;)
					for (int j = side; --j >= i;)
						if (!m[i][j].equals(m[j][i]))
							return false;
				return true;
			}
		}
		// for(int i = 0; i<side;i++)
		// for(int j = 0; j < i;j++)
		// if(!m[i][j].equals(m[j][i]))
		// return false;
		// return true;
		//
		// return true;
	}
	
	// http://www.tincancamera.com/examples/java/strassen/Strassen1MatrixMultiplication.java.txt
	// the code is taken from this location and modified and adapted with our solution.
	private void divideArray(SquareMatrix<E> parent, SquareMatrix<E> child, int iB, int jB) {
	
		for (int i1 = 0, i2 = iB; i1 < child.size(); i1++, i2++)
			for (int j1 = 0, j2 = jB; j1 < child.size(); j1++, j2++)
				child.m[i1][j1] = parent.m[i2][j2];// setElementAt(parent.getElementAt(i2, j2), i1, j1);
	}
	
	// http://www.tincancamera.com/examples/java/strassen/Strassen1MatrixMultiplication.java.txt
	private void copySubArray(SquareMatrix<E> child, SquareMatrix<E> parent, int iB, int jB) {
	
		for (int i1 = 0, i2 = iB; i1 < child.size(); i1++, i2++)
			for (int j1 = 0, j2 = jB; j1 < child.size(); j1++, j2++)
				parent.m[i2][j2] = child.m[i1][j1];
		// parent.setElementAt(child.getElementAt(i1, j1), i2, j2);
	}
	
	/**
	 * Returns a copy (same size and entries) of this matrix.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RMatrix<E> getCopy() {
	
		// TODO Auto-generated method stub
		SquareMatrix<E> copy = new SquareMatrix<E>(side);
		for (int i = 0; i < side; i++)
			System.arraycopy(m[i], 0, copy.m[i], 0, side);
		copy.isNull = this.isNull;
		copy.isOne = this.isOne;
		copy.isSymetric = this.isSymetric;
		copy.isSparse = this.isSparse;
		if (isSparse && this.sparesematrix1 != null) {
			copy.sparesematrix1 = new LinkedList[side];
			System.arraycopy(sparesematrix1, 0, copy.sparesematrix1, 0, side);
		}
		return copy;
		// return new SquareMatrix<E>(m.clone());
		// for (int j = 0; j < side; j++)
		// copy.m= m.clone();//matrix.m[i][j]=m[i][j];
	}
	
	/**
	 * Returns a (nice looking) string representation of this matrix suitable for diagnostic
	 * print-outs. The return String object should represent the entire Matrix, not just few of the
	 * entries there.
	 */
	public String toString() {
	
		StringBuilder result = new StringBuilder();
		if (m[0][0] instanceof Int) {
			if (isSparse) {
				final String ee = "0 ";
				for (int i = 0; i < side; i++) {
					for (int j = 0; j < side; j++) {
						if (((Int) m[i][j]).value() == 0)
							result.append(ee);
						else
							result.append(((Int) m[i][j]).value()).append(space);
					}
					result.append("\n");
				}
				return result.toString();
			} else if (isSymetric) {
				for (Ring<E>[] z : m) {
					for (Ring<E> e : z) {
						result.append(e.hashCode()).append(space);
					}
					result.append("\n");
				}
				return result.toString();
			} else {
				if (side > 2000) {
					StringThread t1 = new StringThread(side / 2, side);
					t1.run();
					for (int i = 0; i < side / 2; i++) {
						for (int j = 0; j < side; j++)
							result.append(((Int) m[i][j]).value()).append(space);
						result.append("\n");
					}
					while (t1.isAlive())
						continue;// System.out.println(t1.isAlive());
					return result.append(t1.toString()).toString();
				} else {
					for (Ring<E>[] z : m) {
						for (Ring<E> e : z) {
							result.append(((Int) e).value()).append(space);
						}
						result.append("\n");
					}
					return result.toString();
				}
			}
		} else {
			for (int i = 0; i < side; i++) {
				for (int j = 0; j < side; j++) {
					result.append(m[i][j].toString());// .append(" ");
					result.append(" ");
				}
				result.append("\n");
			}
			return result.toString();
		}
	}
	
	/**
	 * Returns a hash value for this matrix. Remember, two matrices that are equal (accorrding to
	 * equals()) should have identical hash values. Note: Hash value for a Matrix should be computed
	 * based on all the member elements.
	 */
	public int hashCode() {
	
		if (isSparse) {
			fillSparse();
			int code = 0;
			int c = 0;
			for (int i = 0; i < sparesematrix1.length; i++)
				for (int j = 0; j < sparesematrix1[i].size(); j++) {
					c = sparesematrix1[i].get(j).getColumn();
					code += m[i][c].hashCode();// sparesematrix1[i].get(j).getOb().hashCode()+;
				}
			return code * side * 10;
		}
		// else if(isNull)
		// return side*side;
		// else if (isOne)
		// return side*side+1;
		// if(isSparse)
		// {
		// final int g = this.side;
		// final int n = this.side;
		// int code = 0;
		// for (int i = g; --i >= 0;) {
		// for (int j = n; --j >= 0;) {
		// if(m[i][j]!=m[0][0].getNull())
		// code += m[i][j].hashCode();
		// }
		// }
		// return code;
		// }
		else if (isSymetric) {
			final int g = this.side;
			final int n = this.side / 2;
			int code = 0;
			for (int i = g; --i >= 0;)
				for (int j = n; --j >= i;)
					code += m[i][j].hashCode();// .hashCode();
			return code * 2;
		} else {
			final int g = this.side;
			final int n = this.side;
			int code = 0;
			for (int i = g; --i >= 0;) {
				for (int j = n; --j >= 0;) {
					code += m[i][j].hashCode();
				}
			}
			return code;
		}
	}
	
	private SquareMatrix<E> multSparse(SquareMatrix<E> B) {
	
		SquareMatrix<E> result = (SquareMatrix<E>) this.getNull();
		fillSparse();
		Ring<E> sum = m[0][0].getNull();
		for (int i = 0; i < sparesematrix1.length; i++) {
			for (int j = 0; j < sparesematrix1[i].size(); j++) {
				for (int k = 0; k < sparesematrix1[i].size(); k++) {
					int c = sparesematrix1[i].get(k).getColumn();
					sum = sum.plus(sparesematrix1[i].get(k).getOb().mult(B.getElementAt(c, j)));
				}
				result.m[i][j] = sum;
				sum = m[0][0].getNull();
			}
		}
		return result;
	}
	
	private boolean isSparse() {
	
		int sum = 0;
		double sparsness = 0.0;
		final Ring<E> Null = m[0][0].getNull();
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++)
				if (m[i][j] != Null)
					sum = sum + 1;
			sparsness = (double) ((double) sum / (double) (side * side));
			if ((double) 1 - sparsness < 0.002)
				return false;
		}
		return true;
	}
	
	/**
	 * Returns <tt>true</tt> if the two matrices have the same size and the same entries (defined by
	 * Ring.equals()).
	 */
	public boolean equals(Object o1) {
	
		if (o1 instanceof RMatrix) {
			@SuppressWarnings("unchecked")
			SquareMatrix<E> matrix = (SquareMatrix<E>) o1;
			if (matrix.side == side) {
				if (this.isSparse && matrix.isSparse && this.sparesematrix1 != null) {
					int c;
					for (int i = 0; i < sparesematrix1.length; i++)
						for (int j = 0; j < sparesematrix1[i].size(); j++) {
							c = sparesematrix1[i].get(j).getColumn();
							if (!matrix.m[i][c].equals(this.m[i][c]))
								return false;
						}
					return true;
				} else if (this.isSymetric && !matrix.isSymetric) {
					return false;
				}
				// else if(isSymetric)
				// { System.out.println("Anur1");
				//
				// for (int i = 0; i < side; i++)
				// for (int j = 0; j < i; j++)
				// if(!matrix.m[i][j].equals(this.m[i][j]) && !matrix.m[j][i].equals(this.m[i][j]))
				// return false;
				//
				// }
				// else if(matrix.isSymetric)
				// { System.out.println("Anur2");
				//
				// for (int i = 0; i < side; i++)
				// for (int j = 0; j <= i; j++)
				// if(!matrix.m[i][j].equals(this.m[i][j]) && !matrix.m[i][j].equals(this.m[j][i]))
				// return false;
				//
				// }
				else {
					for (int i = 0; i < side; i++)
						for (int j = 0; j < side; j++)
							if (!matrix.m[i][j].equals(this.m[i][j]))
								return false;
				}
			} else
				return false;
			return true;
		} else
			return false;
	}
	
	private class PlusThread extends Thread {
		
		// SquareMatrix<E> a1;
		SquareMatrix<E> a2;
		
		final SquareMatrix<E> result;
		
		int end;
		
		int starts;
		
		public PlusThread(SquareMatrix<E> res, SquareMatrix<E> b, int start1, int end1) {
		
			a2 = b;
			result = res;
			end = end1;
			starts = start1;
		}
		
		@SuppressWarnings("unchecked")
		public void run() {
		
			for (int i = starts; i < end; i++)
				for (int j = 0; j < side; j++)
					result.m[i][j] = m[i][j].plus((E) a2.m[i][j]);
			// result.m[i][j] = m[i][j].plus((E) a2.m[i][j]);
		}
	}
	
	// class PlusThreadInt extends Thread {
	// //SquareMatrix<E> a1;
	// SquareMatrix<E> a2;
	// final SquareMatrix<E> result;
	// int end;
	// int starts;
	// public PlusThreadInt(SquareMatrix<E> res, SquareMatrix<E> b, int start1, int end1)
	// {
	// a2=b;
	// result = res;
	// end = end1;
	// starts = start1;
	// }
	// @SuppressWarnings("unchecked")
	// public void run()
	// {
	// for (int i = starts; i < end; i++)
	// for(int j = 0; j < side; j++)
	// result.m[i][j] =(E) new Int( ((Int)m[i][j]).value() + ((Int)a2.m[i][j]).value());// )
	// //result.m[i][j] = m[i][j].plus((E) a2.m[i][j]);
	// //result.m[i][j] = m[i][j].plus((E) a2.m[i][j]);
	// }
	// }
	// private class PlusArrayThread extends Thread {
	// //SquareMatrix<E> a1;
	// int [][] A;
	// int [][] B;
	// int [][] C;
	// int end;
	// int starts;
	// public PlusArrayThread(int [][] a1, int[][]b1,int[][]c1, int start1, int end1)
	// {
	// A=a1;
	// B = b1;
	// C = c1;
	// end = end1;
	// starts = start1;
	// }
	// public void run()
	// {
	// for (int i = starts; i < end; i++)
	// for(int j = 0; j < end; j++)
	// C[i][j] = A[i][j] + B[i][j];
	// }
	// }
	private class StringThread extends Thread {
		
		private StringBuilder result;
		
		private int start;
		
		private int end;
		
		public StringThread(int start1, int end1) {
		
			result = new StringBuilder();
			start = start1;
			end = end1;
		}
		
		public void run() {
		
			for (int i = start; i < end; i++) {
				for (int j = 0; j < side; j++) {
					result.append(m[i][j].hashCode());
					result.append(space);
				}
				result.append("\n");
			}
		}
		
		public String toString() {
		
			return result.toString();
		}
	}
}
