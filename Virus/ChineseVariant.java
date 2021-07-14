//alice aidlin 206448326
//maayan nadivi 208207068
package Virus;

import java.util.Random;

import Population.*;

public class ChineseVariant implements IVirus {
	
//----------methods----------
	public double contagionProbability(Person p) {
		double x;
		if (p.get_age()<=18)
			 x=0.2;
		else if(p.get_age()>18&&p.get_age()<=55)
			x=0.5;
		else
			x=0.7;
		return p.contagionProbability()*x;
	
	}
	
	public double calculate_distance(Person p1, Person p2){
        return Math.sqrt((p1.get_location().get_x()-p2.get_location().get_x())+(p1.get_location().get_y()-p2.get_location().get_y()));
        }
	
	public boolean tryToContagion(Person p1, Person p2) {
		if (p2 instanceof Sick)
			return false;
		else {
			double x;
			x= p2.contagionProbability()*(Math.min(1, 0.14*Math.pow(Math.E, 2-0.25*calculate_distance(p1,p2))));
			Random rand= new Random();
			double ran = rand.nextDouble();
			if (x>ran) {
                Sick s = (Sick) p2.contagion(this);
                p2.get_settlement().removePerson(p2);
                p2.get_settlement().addPerson(s);

				return true;
				}
			return false;
		}
	}
	
	public boolean tryToKill(Sick p_sick) {

		double p;
		if (p_sick.get_age()<=18)
			 p=0.001;
		else if(p_sick.get_age()>18&&p_sick.get_age()<=55)
			p=0.05;
		else
			p=0.1;
		long t=p_sick.get_contagiousTime();
		double temp=Math.max(0,p-0.01*p*Math.pow(t-15, 2));
		Random rand= new Random();
		double ran= rand.nextDouble();
		if (ran>=temp)
			return true; 
		return false;
			
	}
	public double tryToKill_indouble(Sick p_sick) {

		double p;
		if (p_sick.get_age()<=18)
			 p=0.001;
		else if(p_sick.get_age()>18&&p_sick.get_age()<=55)
			p=0.05;
		else
			p=0.1;
		long t=p_sick.get_contagiousTime();
		double temp=Math.max(0,p-0.01*p*Math.pow(t-15, 2));
		return temp;
		
	}

	public boolean isequals(IVirus v) {
		return (this.toString().equals(v.toString()));

	}

	public String toString() {
		return "ChineseVariant";
	}

}
