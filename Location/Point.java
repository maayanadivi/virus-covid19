//alice aidlin 206448326
//maayan nadivi 208207068
package Location;

public class Point {
	
//----------attr----------
	private int x = 0;
	private int y = 0;
	
//----------Ctor----------
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
    public Point(){
        this.x = 0;
        this.y = 0;
    }

//----------get----------
	public int get_x() {
		return x;
	}

	public int get_y() {
		return y;
	}

//----------set----------
	public void set_x(int x) {
		this.x = x; 
		}
	  
	public void set_y(int y) { 
		this.y = y; 
		}
	 
//----------methods----------
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

	public boolean equals(Object other) {
		if (other instanceof Point)
			{Point p = (Point) other;
			return (this.x ==p.x) && (this.y == p.y);}
		return false;
	}

}
