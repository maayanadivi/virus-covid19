//alice aidlin 206448326
//maayan nadivi 208207068
package Population;
import Simulation.Clock;
import java.lang.Math;

public class Vaccinated extends Person {
//----------attr----------
	private long vaccinationTime;
//----------Ctor-----------	
	public Vaccinated(Person person, long vaccinationTime) {
		super (person);
		this.vaccinationTime=vaccinationTime;
		}
	public Vaccinated(){
        vaccinationTime =Clock.now();
    }
//----------methods----------	
	public double contagionProbability() {
		long t=Clock.now()-this.vaccinationTime;
		if (t<21)
			return Math.min(1, 0.56+0.15*Math.sqrt(21-t));
		return Math.max(0.05, 1.05/(t-14));
	}
}
