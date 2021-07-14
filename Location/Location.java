//alice aidlin 206448326
//maayan nadivi 208207068
package Location;

public class Location {

//----------attr----------
	private Point position;
	private Size size;
	
//----------Ctor----------
	public Location(Point position, Size size) {
		this.position=position;
		this.size=size;
		}
	
	public Location(){
        this.position = new Point();
        this.size = new Size();
    }
	
//----------get----------
	public Point get_position() {
		return position;
		}
	
	public Size get_size() {
		return size;
		}
	public int get_Position_x() {
        return position.get_x();
    }

    public int getPosition_y(){
        return position.get_y();
    }

	 
//----------methods----------
	public String toString() {
		return "Location\nposition: " + this.position.toString() + "\nsize: " + this.size.toString();
		}
	
	public boolean equals(Location other) {
		return (this.position.equals(other.position)) && (this.size.equals(other.size));
		}

}
