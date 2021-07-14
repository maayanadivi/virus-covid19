//alice aidlin 206448326
//maayan nadivi 208207068
package Country;

import java.awt.Color;

public enum RamzorColor 
{
	
	Green(0.4,Color.GREEN,1),
	Yellow(0.6,Color.YELLOW,0.8),
	Orange(0.8,Color.ORANGE,0.6),
	Red(1, Color.RED,0.4);
	private Color c;
	private double trans_present;
	public double promo;
	
	RamzorColor(double promo, Color color,double trans_present) {
		this.promo = promo;
		this.c=color;	
		this.trans_present=trans_present;
	}
	
	public Color get_color() {
		return this.c;
	}
	
	public double get_trans_present() {
		return this.trans_present;
	}
	
	public double get_promo() {
		return this.promo;
	}
	
	public static RamzorColor calculate(double x) {
        if ((x>=0) && (x<=0.4))
        	return Green;
        else if ((x>0.4) && (x<=0.6))
        	return Yellow;
        else if ((x>0.6) && (x<=0.8))
        	return Orange;
        else
        	return Red;
	}
}
