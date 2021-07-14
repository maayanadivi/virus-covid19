//alice aidlin 206448326
//maayan nadivi 208207068
package Location;

public class Size {
	
//----------attr----------
	private int width = 0;
	private int height = 0;
	
//----------Ctor----------
	public Size (int width, int height) {
		this.width = width;
		this.height = height;
	}
	
    public Size(){
    	this.width = 0;
        this.height = 0;
    }

//----------get----------
	public int get_width() {
		return width;
	}

	public int get_height() {
		return height;
	}

//----------set----------
	public void set_width(int width) {
		this.width = width;
	}

	public void set_height(int height) {
		this.height = height;
	}

//----------methods----------
	public String toString() {

		return "width: "+ this.width + "\nheight" + this.height ;
	}

	public boolean equals(Size other) {
		return (this.width==other.width) && (this.height==other.height);
	}

}
