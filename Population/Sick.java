//alice aidlin 206448326
//maayan nadivi 208207068
package Population;

import java.util.Random;

import Virus.IVirus;

public class Sick extends Person {
//----------attr----------
	private long contagiousTime;
	private IVirus virus;
//----------Ctor----------
	public Sick(Person person, IVirus virus,long contagiousTime) throws Exception {
		super(person);
		this.virus = virus;
		this.contagiousTime=contagiousTime;
			
		}
	public Sick(Sick other)
	{
		super(other.get_age(), other.get_location(), other.get_settlement());
		this.contagiousTime=other.get_contagiousTime();
		this.virus= other.getVirus();

	}
//----------Cctor----------
    public Sick(Person p, long contagiousTime, IVirus virus){
        super(p);
        this.contagiousTime = contagiousTime;
        this.virus = virus;
    }
//----------get----------	
	public long get_contagiousTime() {
		return contagiousTime;
	}
    public IVirus getVirus() {
        return virus;
    }
//----------set----------
    public void setVirus(IVirus virus) {
        this.virus = virus;
    }
    
//----------methods----------
	
    public Person recover() {
		
		return new Convalescent(this, virus);
	
		}
	
	public boolean tryToDie()
	{
		double p=this.virus.tryToKill_indouble(this);
		Random rand = new Random();
		double ran=rand.nextDouble();
		if (p>ran)
			return true;
		return false;
			
			}
	
	public double contagionProbability() {
			return 1.0;
		}
	
	public boolean equals(Object other) {
		if (other instanceof Person) {
			Person p= (Person) other;
			if ((this.get_age()==p.get_age()) && (this.get_location().equals(p.get_location())&&this.get_settlement().equals(((Person) other).get_settlement())))
				return true;
			}
		return false;
		}
	
	
}
