//alice aidlin 206448326
//maayan nadivi 208207068
package Country;
import java.util.List;
import Location.Location;
import Population.Person;

public class City extends Settlement {
	
//----------Ctor----------
	public City (String name_City, Location location, List<Person> people, RamzorColor ramzorColor,Map map) {
		super(name_City, location, people, ramzorColor,map);
	}
	
	public City(City c) {
		super(c.get_Name(),c.get_Location(),c.get_People(),c.get_RamzorColor(),c.get_map());}
//----------methods----------			
	public RamzorColor calculateRamzorGrade() { 
		double C=this.get_RamzorColor().get_promo();
		double P=contagiousPercent();
		C=0.2*(Math.pow(4,1.25*P));
		this.set_RamzorColor(RamzorColor.calculate(C));
		return RamzorColor.calculate(C);
	}
	
	public String toString() {

		return super.toString()+"\nCity";
		}


	
}
