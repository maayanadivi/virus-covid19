//alice aidlin 206448326
//maayan nadivi 208207068
package Country;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

import IO.SimulationFile;
import IO.log_file;
import Population.Person;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;

public class Map implements Iterable<Settlement>{

    private Settlement[] settlements;
    private AtomicBoolean isplay=new AtomicBoolean();
	private AtomicBoolean isstop=new AtomicBoolean();
	private AtomicBoolean ispause=new AtomicBoolean();
	private AtomicBoolean ismapload=new AtomicBoolean();
	private CyclicBarrier cyclicBarrier;
	private log_file log;
	private String LogPAth=null;


	//----------default ctor----------
    public Map(){
		settlements= new Settlement[4];
		ismapload.set(false);
		isplay.set(false);
		ispause.set(true);
		isstop.set(false);

	}

    //----------Ctor----------
    public Map(Settlement[] settlements){
        this.settlements = settlements;
		ismapload.set(false);
		isplay.set(false);
		ispause.set(true);
		isstop.set(false);

    }

    //----------get----------
    public Settlement[] getSettlements() {
        return settlements;
    }
    
    public int getSettlementSize() {
    	return settlements.length;
    }

    public boolean get_isplay(){
    	return isplay.get();
	}
	public boolean get_ispause(){
		return ispause.get();
	}
	public boolean get_isstop(){
		return isstop.get();
	}
	public boolean get_ismapload(){
		return ismapload.get();
	}
	public CyclicBarrier get_cyclic(){
		return cyclicBarrier;
	}

	public void setLogPAth(String logPAth) {
		LogPAth = logPAth;
	}
	public String getLogPAth() {
		return this.LogPAth;
	}
	public log_file get_log(){
    	return log;
	}

		//----------set----------
	public void set_log(log_file log){
    	this.log=log;
	}
    public void setSettlements(Settlement[] settlements) {
        this.settlements = settlements;
    }

	public void set_isplay(boolean b){
		isplay.set(b);
	}
	public void set_ispause(boolean b){
		ispause.set(b);
	}
	public void set_isstop(boolean b){
		isstop.set(b);
	}
	public void set_ismapload(boolean b){
		ismapload.set(b);
	}
	public void set_cyclic(CyclicBarrier c){
		cyclicBarrier=c;
	}

	public void init_map(){
    	for(var s: settlements){
    		s.set_map(this);
		}
	}

//	public void closeFile() throws IOException {
//    	log.close_br();
//	}
	public void startThread(){
		Thread[] settlementThreads = new Thread[getSettlements().length];
		for (int i = 0; i < settlementThreads.length; i++) {
			settlementThreads[i] = new Thread(getSettlements()[i]);
		}

		for (Thread settlementThread : settlementThreads) {
			settlementThread.start();
		}
	}
	public void print()
	{
		for (Settlement s:settlements){
			System.out.println(s);;
		}

	}
    public static Map load_map(String fileName) {
		//loading the map
		//SimulationFile s = new SimulationFile(fileName);
		Map map = new Map(SimulationFile.get_simulation(fileName).getPopulationArray());
		

		//Contaminating 1% of each settlement
		for (var settlement : map.getSettlements())
		{
			List<Person> people = settlement.get_People(); //population list

			int size = settlement.get_People().size() / 100;// 1% 

			for (int i = 0; i < size; i++) 
			{
				int choice= new Random().nextInt(2);
				IVirus virus;
				if (choice==0)
					virus=new BritishVariant();
				else if(choice==1)
					virus=new ChineseVariant();
				else  
					virus=new SouthAfricanVariant();
				people.set(i, people.get(i).contagion(virus));
			}
			settlement.set_People(people);
		}

		// ---------check 
		return map;
	}


	public Settlement at(int rowIndex) {
    	return settlements[rowIndex];
	}

	@Override
	public Iterator<Settlement> iterator() {
		return Arrays.asList(this.settlements).iterator();
	}
}
