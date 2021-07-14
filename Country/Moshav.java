//alice aidlin 206448326
//maayan nadivi 208207068
package Country;
import java.util.List;
import Location.*;
import Population.Person;

public class Moshav extends Settlement {
	private double C;
	private double P;

//----------Ctor----------	
	public Moshav (String name_yeshuv, Location location, List<Person> people, RamzorColor ramzorColor, Map map) {
		super(name_yeshuv, location, people, ramzorColor,map);
		C=this.get_RamzorColor().get_promo();
	}
	
	public Moshav(Moshav m) {
		super(m.get_Name(),m.get_Location(),m.get_People(),m.get_RamzorColor(),m.get_map());
		}
//----------methods----------			
	public RamzorColor calculateRamzorGrade() {
		P=contagiousPercent();
		C=0.3+ 3*(Math.pow((Math.pow(1.2,C)*(P-0.35)),5));
		this.set_RamzorColor(RamzorColor.calculate(C));
		return RamzorColor.calculate(C);
	}
	
	public String toString() {

		return super.toString()+"\nMoshav";
		}


	
}
