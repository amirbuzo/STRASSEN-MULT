package redbull;

public class Bool implements QRing<Bool> {
	
	private boolean n;
	
	public Bool(boolean n) {
	
		this.n = n;
	}
	
	@Override
	public Bool mult(Bool e) {
	
		return new Bool(this.n && e.n);
	}
	
	@Override
	public Bool plus(Bool e) {
	
		return new Bool(this.n || e.n);
	}
	
	@Override
	public Bool getNull() {
	
		return new Bool(false);
	}
	
	@Override
	public Bool getOne() {
	
		return new Bool(true);
	}
	
	@Override
	public Bool getCopy() {
	
		return new Bool(this.n);
	}
	
	public int hashCode() {
	
		if (this.n)
			return 3;
		else
			return 7;
	}
	
	@Override
	public boolean equals(Object o) {
	
		if (o instanceof Bool) {
			Bool d = (Bool) o;
			return n == d.n;
		}
		return false;
	}
	
	@Override
	public String toString() {
	
		return "" + n;
	}
}
