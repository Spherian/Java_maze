package point2d;

public class point2d {
	int x, y;

	public point2d(int inX, int inY) {
		this.x = inX;
		this.y = inY;
	}

	public String toString() {
		String results;
		results = "(" + x + "," + y + ")";
		return results;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean compare(point2d testPos) {
		boolean decision = false;
		if(testPos.getX() == this.x && testPos.getY() == this.y) {
			decision = true;
		}
		return decision;
	}

	public static void main(String[] args) {
		point2d pt1 = new point2d(4,5);
		String pt1Str = pt1.toString();
		System.out.println("Pt1: " + pt1Str);
		System.out.println("Pt1 x: " + pt1.x);
		System.out.println("Pt1 y: " + pt1.y);
	}

}
