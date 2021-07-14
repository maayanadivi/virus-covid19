//alice aidlin 206448326
//maayan nadivi 208207068
package Population;
import Virus.*;

public class Convalescent extends Person {
//----------attr----------	
	IVirus virus;
//----------Ctor---------	
	public Convalescent(Sick person, IVirus virus) {
		super(person);
		this.virus=virus;
	}
//----------methods----------
	public double contagionProbability() {
		return 0.2;
	}
}
