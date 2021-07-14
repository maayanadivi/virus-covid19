//alice aidlin 206448326
//maayan nadivi 208207068
package Country;
import java.util.List;
import Location.Location;
import Population.Person;

public class Kibbutz extends Settlement {

//----------Ctor----------
	public Kibbutz (String name_Kibbutz, Location location, List<Person> people, RamzorColor ramzorColor, Map map) {
		super(name_Kibbutz, location, people, ramzorColor, map);
	}
	
	public Kibbutz(Kibbutz k) {
		super(k.get_Name(),k.get_Location(),k.get_People(),k.get_RamzorColor(), k.get_map());}
//----------methods----------			
	public RamzorColor calculateRamzorGrade() { 
		double C=this.get_RamzorColor().get_promo();
		double P=contagiousPercent();
		C=0.45+1*(Math.pow(Math.pow(1.5, C)*(P-0.4), 3));
		this.set_RamzorColor(RamzorColor.calculate(C));
		return RamzorColor.calculate(C);
	}

	public String toString() {

		return super.toString()+ "\nKibbutz";
		}
}
