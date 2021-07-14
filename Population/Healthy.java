//alice aidlin 206448326
//maayan nadivi 208207068
package Population;

import Location.Point;
import Simulation.Clock;
import Country.Settlement;

public class Healthy extends Person {
//----------Ctor-----------
	public Healthy(int age, Point location, Settlement settlement) {
		super(age,location,settlement);
	}
	public Healthy(){ }
//----------Cctor----------
    public Healthy(Person p){
        super(p);
    }	
//----------methods----------
    public Vaccinated vaccinate(){
        return new Vaccinated(this, Clock.now());
    }
	public double contagionProbability() {
		return 1.0;
	}
}
