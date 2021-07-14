//alice aidlin 206448326
//maayan nadivi 208207068
package Population;

import Location.Point;
import Country.Settlement;
import Virus.IVirus;
import Simulation.Clock;
import Virus.*;


public abstract class Person {

//----------attr----------
	private int age = 0;
	private Point location;
	private Settlement settlement;
	
//----------Ctor----------
	public Person(int age, Point location, Settlement settlement) {
		this.age = age;
		this.location = location;
		this.settlement = settlement;
	}

	public Person() {
    this.age = 0;
    this.location = new Point();
	}

//----------COPYCtor----------

	public Person(Person other) {
		this.age = other.age;
		this.location = other.location;
		this.settlement = other.settlement;
		}
	
//----------get----------
	public int get_age() {
		return age;
		}
	public Point get_location() {
		return location;
		}
	public Settlement get_settlement() {
		return settlement;
		}
	public int getIndexInSettlement(){
        return this.settlement.get_People().indexOf(this);
    }
//----------set----------
	public void set_age(int age) {
		this.age=age;
		}
	public void set_location(Point location) {
		this.location=location;
		}
	public void set_settlement(Settlement settlement) {
		this.settlement=settlement;
		}
//----------Methods----------
	public abstract double contagionProbability();
	
	public Person contagion(IVirus virus) {
		if(!(this instanceof Sick)){
			long time = Clock.now();
			int x = Virus.findvirus(virus);
			System.out.println(" the virue index is ="+x);
			Sick sick = new Sick(this,time, VirusManager.contagion(virus));
			return sick;
		}
		else{
			System.out.println("Error");
		}
		return this;
	}
	
	public boolean equals(Object other) { 
		if (other instanceof Person) {
			Person p= (Person) other;
			if ((this.get_age()==p.get_age()) && (this.get_location().equals(p.get_location())&&this.get_settlement().equals(((Person) other).get_settlement())))
				return true;
			}
		return false;
		}
	
	
	public String toString() {

		return "Age: " + this.get_age()+"\nLocation: "+this.get_location().toString()+"\nSettelment: "+this.get_settlement().toString();
		}
	

}
