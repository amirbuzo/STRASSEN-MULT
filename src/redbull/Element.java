package redbull;

public class Element<E> {
	
	private Ring<E> ob;
	
	private int column;
	
	public Element(Ring<E> e, int col) {
	
		ob = e;
		column = col;
	}
	
	public void setOb(Ring<E> ob) {
	
		this.ob = ob;
	}
	
	public Ring<E> getOb() {
	
		return ob;
	}
	
	public void setColumn(int column) {
	
		this.column = column;
	}
	
	public int getColumn() {
	
		return column;
	}
}
