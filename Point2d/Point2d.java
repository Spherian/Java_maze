package Point2d;

public class Point2d {
	int x, y;

	public Point2d(int inX, int inY) {
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

	public boolean compare(Point2d testPos) {
		boolean decision = false;
		if(testPos.getX() == this.x && testPos.getY() == this.y) {
			decision = true;
		}
		return decision;
	}

	public class Adjacent {
		Point2d n,s,e,w;
		public Point2d getN() {
			return new Point2d(Point2d.this.x-1, Point2d.this.y);
		}
		public Point2d getS() {
			return new Point2d(Point2d.this.x+1, Point2d.this.y);
		}
		public Point2d getE() {
			return new Point2d(Point2d.this.x, Point2d.this.y+1);
		}
		public Point2d getW() {
			return new Point2d(Point2d.this.x, Point2d.this.y-1);
		}
		public Point2d[] getAdjacent() {
			Point2d[] arr = {getN(), getS(), getE(), getW()};
			return arr;
		}
	}

	public static void main(String[] args) {
		Point2d pt1 = new Point2d(4,5);
		String pt1Str = pt1.toString();
		System.out.println("Pt1: " + pt1Str);
		System.out.println("Pt1 x: " + pt1.x);
		System.out.println("Pt1 y: " + pt1.y);

		Adjacent adj = pt1.new Adjacent();
		Point2d[] adjPos = adj.getAdjacent();
		for(int i=0; i<adjPos.length; i++) {
			System.out.println(i + ": " + adjPos[i]);
		}
	}

}
