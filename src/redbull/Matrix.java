/*
 * File: SquareMatrix.java Author: Amir Buzo Date: 22 march 2011
 */
package redbull;

/**
 * This class implements the QRMatrix interface and represent a matrix.
 * 
 * @author Amir Buzo
 *
 */
public class Matrix<E extends QRing<E>> implements QRMatrix<E> {
	
	private final int r;
	
	private final int c;
	
	private QRing<E>[][] m;
	
	private QRing<E>[] column;
	
	private boolean isColumn = false;
	
	private boolean isRow = false;
	
	@SuppressWarnings("unchecked")
	public Matrix(int rows, int columns) {
	
		// Creates empty matrix with size (rows x columns)
		if (rows > 0 && columns > 0) {
			r = rows;
			c = columns;
			if (c == 1) {
				isColumn = true;
				column = new QRing[r];
			} else {
				m = new QRing[r][c];
				if (r == 1)
					isRow = true;
			}
		} else
			throw new IndexOutOfBoundsException("The index is out of range");
	}
	
	@SuppressWarnings("unchecked")
	public Matrix(QRing<E>[][] values) {
	
		if (values == null)// if(values.length<1||values[0].length<1)
			throw new IndexOutOfBoundsException("The index is out of range");
		else {
			r = values.length;
			c = values[0].length;
			if (c == 1) {
				isColumn = true;
				column = new QRing[r];
				for (int j = 0; j < r; j++)
					column[0] = values[j][0];// System.arraycopy(values[j][0], 0, column[j], 0, r);
			} else {
				m = new QRing[r][c];
				this.m = values.clone();
				if (r == 1)
					isRow = true;
			}
		}
	}
	
	@Override
	public int rows() {
	
		// TODO Auto-generated method stub
		return r;
	}
	
	@Override
	public int columns() {
	
		// TODO Auto-generated method stub
		return c;
	}
	
	@Override
	public double sparseness() {
	
		// TODO Auto-generated method stub
		if (this.isColumn) {
			int sparse = 0;
			for (int i = 0; i < r; i++)
				if (column[i].equals(column[0].getNull()))
					sparse++;
			return (double) ((double) sparse / (double) (r * c));
		} else {
			int sparse = 0;
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					if (m[i][j].equals(m[0][0].getNull())) {
						sparse++;
					}
				}
			}
			return (double) ((double) sparse / (double) (r * c));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E getElementAt(int row, int col) {
	
		// TODO Auto-generated method stub
		// if((row > r || row < 0) || (col > c || col < 0))
		// {
		// throw new IndexOutOfBoundsException("Index is out of bounds");
		// }
		// else
		// {
		// return (E) m[row][col];
		// }
		if (column != null)// System.out.println("Amir");
		{
			return (E) column[row];
		} else if (m[row][col] != null)
			return (E) m[row][col];
		else
			throw new IndexOutOfBoundsException("Index is out of bounds");
	}
	
	@Override
	public void setElementAt(E elem, int row, int col) {
	
		// TODO Auto-generated method stub
		if (!(row < 0 || row > r || col < 0 || col > c)) {
			if (isColumn)
				column[row] = elem;
			else
				m[row][col] = elem;
		} else
			throw new IndexOutOfBoundsException("Indexes are out of bounds");
	}
	
	@Override
	public QRMatrix<E> getColumn(int column) {
	
		// TODO Auto-generated method stub
		if (column < c && column >= 0) {
			if (this.isColumn) {
				Matrix<E> matrix1 = new Matrix<E>(r, 1);
				for (int i = 0; i < r; i++)
					matrix1.column = this.column.clone();
				return matrix1;
			} else {
				Matrix<E> matrix1 = new Matrix<E>(r, 1);
				for (int i = 0; i < r; i++) {
					matrix1.m[i][0] = m[i][column];
				}
				return matrix1;
			}
		} else
			throw new IndexOutOfBoundsException("Column is out of bounds");
	}
	
	@Override
	public QRMatrix<E> getRow(int row) {
	
		// TODO Auto-generated method stub
		if (row < r && row >= 0) {
			if (isColumn) {
				Matrix<E> matrix1 = new Matrix<E>(1, c);
				for (int j = 0; j < c; j++)
					matrix1.column[0] = column[row];
				return matrix1;
			} else {
				Matrix<E> matrix1 = new Matrix<E>(1, c);
				for (int j = 0; j < c; j++)
					matrix1.m[0][j] = m[row][j];
				return matrix1;
			}
		} else
			throw new IndexOutOfBoundsException("Row is out of bounds");
	}
	
	@Override
	public Matrix<E> getTranspose() {
	
		// TODO Auto-generated method stub
		if (isColumn) {
			Matrix<E> matrix1 = new Matrix<E>(1, c);
			for (int i = 0; i < r; i++)
				matrix1.m[0] = column;
			return matrix1;
		} else {
			Matrix<E> matrix1 = new Matrix<E>(c, r);
			for (int i = 0; i < r; i++)
				for (int j = 0; j < c; j++)
					matrix1.m[j][i] = m[i][j];
			return matrix1;
		}
	}
	
	@Override
	public boolean isSquare() {
	
		// TODO Auto-generated method stub
		if (r == c)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean isDiagonal() {
	
		if (c == r) {
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					if (i != j) {
						if (!m[i][j].equals(m[0][0].getNull()))
							return false;
					} else if (m[i][j].equals(m[0][0].getNull()))
						return false;
				}
			}
			return true;
		} else
			return false;
	}
	
	public String toString() {
	
		if (isColumn) {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < r; i++)
				result.append(m[i].toString() + "\n");
			return result.toString();
		} else {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < r; i++) {
				result.append("| ");
				for (int j = 0; j < c; j++) {
					result.append(m[i][j] + " ");
				}
				result.append("|\n");
			}
			return result.toString();
		}
	}
	
	@Override
	public boolean isSymmetric() {
	
		// TODO Auto-generated method stub
		if (c == r) {
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					if (i == j) {
						continue;
					} else if (!m[i][j].equals(m[j][i])) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public QRMatrix<E> mult(QRMatrix<E> e) {
	
		int row1 = e.rows();
		int co1 = e.columns();
		Matrix<E> ma1;
		if (c == row1) {
			if (isColumn) {
				QRing<E> sum = this.m[0][0].getNull();
				ma1 = new Matrix<E>(r, co1);
				for (int i = 0; i < r; i++) {
					for (int j = 0; j < co1; j++) {
						for (int k = 0; k < c; k++) {
							sum = sum.plus(column[k].mult(e.getElementAt(k, j)));
						}
						ma1.m[i][j] = sum;
						sum = this.m[0][0].getNull();
					}
				}
				return ma1;
			} else {
				QRing<E> sum = this.m[0][0].getNull();
				ma1 = new Matrix<E>(r, co1);
				for (int i = 0; i < r; i++) {
					for (int j = 0; j < co1; j++) {
						for (int k = 0; k < c; k++) {
							sum = sum.plus(m[i][k].mult(e.getElementAt(k, j)));
						}
						ma1.m[i][j] = sum;
						sum = this.m[0][0].getNull();
					}
				}
				return ma1;
			}
		} else {
			throw new NotCompatibleException("These two matrices can not be multiplied.");
		}
	}
	
	// @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	@Override
	public QRMatrix<E> plus(QRMatrix<E> e) {
	
		// TODO Auto-generated method stub
		Matrix<E> B = (Matrix<E>) e;
		if (r != B.r || c != B.c)
			throw new NotCompatibleException("These matrices can not be added");
		else {
			if (this.isColumn) {
				Matrix<E> matrix = new Matrix<E>(r, c);
				for (int i = 0; i < r; i++)
					matrix.column[i] = column[i].plus((E) B.column[i]);
				return matrix;
			} else if (this.isRow) {
				Matrix<E> matrix = new Matrix<E>(r, c);
				for (int i = 0; i < r; i++)
					matrix.m[0][i] = m[0][i].plus((E) B.m[0][i]);
				return matrix;
			} else {
				Matrix<E> matrix = new Matrix<E>(r, c);
				for (int i = 0; i < r; i++)
					for (int j = 0; j < c; j++)
						matrix.m[i][j] = m[i][j].plus((E) B.m[i][j]);
				return matrix;
			}
			// else if(this.isRow)
			// {
			// Matrix<E> matrix = new Matrix<E>(r, c);
			// for (int i = 0; i < r; i++)
			// matrix.rowq[i] = rowq[i].plus((E) B.rowq[i]);
			//
			// return matrix;
			// }
		}
	}
	
	@Override
	public QRMatrix<E> getCopy() {
	
		// TODO Auto-generated method stub
		Matrix<E> matrix = new Matrix<E>(r, c);
		if (isColumn) {
			for (int i = 0; i < r; i++)
				System.arraycopy(column[i], 0, matrix.column[i], 0, c);
		} else {
			for (int i = 0; i < r; i++)
				System.arraycopy(m[i], 0, matrix.m[i], 0, c);
		}
		return matrix;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object m1) {
	
		if (m1 instanceof Matrix) {
			Matrix<E> matrix = (Matrix<E>) m1;
			if (matrix.c != c || matrix.rows() != r)
				return false;
			else {
				if (this.isColumn) {
					// if(column[0] instanceof Int)
					// {
					// for (int i = 0; i < r; i++)
					// if( ((Int)column[i]).value() != ((Int)matrix.column[i]).value())
					// return false;
					// }
					// else
					// {
					for (int i = 0; i < r; i++)
						if (!column[i].equals(matrix.column[i]))
							return false;
					// }
				} else if (this.isRow) {
					// if(matrix.m[0][0] instanceof Int)
					// { for (int i = 0; i < r; i++)
					// if(((Int)matrix.m[0][i]).value() != ((Int)m[0][i]).value())
					// return false;
					// }
					// else
					for (int i = 0; i < r; i++)
						if (!(matrix.m[0][i].equals(m[0][i])))
							return false;
				} else
					for (int i = 0; i < r; i++)
						for (int j = 0; j < c; j++)
							if (!(matrix.m[i][j].equals(m[i][j])))
								return false;
			}
			return true;
		} else
			return false;
	}
	
	public int hashCode() {
	
		int hash = 0;
		if (isColumn) {
			for (int i = 0; i < r; i++)
				hash = hash + column[i].hashCode();
			Integer.parseInt(hash + "" + c);
			return hash;
		} else {
			int row = 0;
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					row = row + this.m[i][j].hashCode();
				}
				row = Integer.parseInt(row + "" + i);
				hash = hash + (row / (i + 1));
				row = 0;
			}
			int column = 0;
			for (int i = 0; i < c; i++) {
				for (int j = 0; j < r; j++) {
					column = column + this.m[j][i].hashCode() + j;
				}
				column = Integer.parseInt(column + "" + i);
				hash = hash + (column / (i + 1));
				column = 0;
			}
			return hash;
		}
	}
}
