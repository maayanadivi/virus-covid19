//alice aidlin 206448326
//maayan nadivi 208207068
package Virus;
import Population.*;


public interface IVirus {
	
	double contagionProbability(Person p);
	boolean tryToContagion(Person p1, Person p2);
	boolean tryToKill(Sick person);
	double tryToKill_indouble (Sick person);
	double calculate_distance(Person p1, Person p2);
	 boolean isequals(IVirus v);
	
}
