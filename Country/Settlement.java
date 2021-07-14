//alice aidlin 206448326
//maayan nadivi 208207068
package Country;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import IO.*;
import Location.*;
import Location.Point;
import Population.*;
import Simulation.Clock;
import Virus.*;


public abstract class Settlement implements Runnable {



	//----------attr-----------
	private double count_dead;
	private Map map;
	private final String name;
	private Location location;
	private List<Person> people= new ArrayList<Person>();
	private RamzorColor ramzorColor;
	private int max_people;
	private int vaccine_num=0;
	private ArrayList<Settlement> settlements_conected;
	private List<Sick> sick_pepole= new ArrayList<Sick>();
	private List<Healthy> healthy_people= new ArrayList<Healthy>();
	private List<Vaccinated> vaccinated_people=new ArrayList<Vaccinated>();
	private List <Convalescent> convalescent_people=new ArrayList<Convalescent>();

	private int dead=0;

	//----------Ctor----------
	public Settlement(String name, Location location, List<Person> people, RamzorColor ramzorColor,Map map) {
		this.name=name;
		this.location=location;
		this.people=people;
		this.ramzorColor=ramzorColor;
		settlements_conected=new ArrayList<Settlement>();
		this.map=map;
	}
	public Settlement(Settlement s) {
		this.name = s.get_Name();
		this.location = s.get_Location();
		this.people = s.people;
		this.ramzorColor = s.get_RamzorColor();
		settlements_conected=new ArrayList<Settlement>();

	}


	@Override
	public void run(){
		int i=0;
		while(!map.get_isstop()){
			synchronized (map) {
				while (map.get_ispause()) {
					try {
						map.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
				//System.out.println("start sim no."+i);
				ArrangeArrays();

				infection_attempts();
				recover();
				try_transfer();
				try_vaccinate();
				kill_people();
				if(this.map.getLogPAth() != null){
					try {
						WtriteToLog(this.map.getLogPAth());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				//***
				calculateRamzorGrade();
				System.out.println(this.toString());
				try {
					this.map.get_cyclic().await();
				} catch (InterruptedException e) {
					//e.printStackTrace();
				} catch (BrokenBarrierException e) {
					//e.printStackTrace();
				}
				i++;
		}
	}

	//----------get funcs-----------
	public String get_Name() {
		return name;
	}
	public Location get_Location() {
		return location;
	}
	public List<Person> get_People() {
		return this.people;
	}
	public RamzorColor get_RamzorColor() {
		return ramzorColor;
	}
	public Color getColor(){
		return ramzorColor.get_color();
	}
	public List<Healthy> get_Healty_people(){
		return this.healthy_people;
	}
	public List<Vaccinated> get_Vaccinated_people(){return this.vaccinated_people;}
	public List<Convalescent> get_Convalescent_people(){return this.convalescent_people;}
	public double get_max_people() {
		return this.max_people;
	}
	public int get_vaccine_num() {
		return vaccine_num;
	}
	public List<Sick> get_sick() {
		return sick_pepole;
	}
	public ArrayList<Settlement> get_settlements_connected() {
		return settlements_conected;
	}
	public int get_vaccinated() {
		ArrangeArrays();
		return vaccinated_people.size();
	}
	public String getType(){
		if (this instanceof City)
			return "City";
		else if(this instanceof Kibbutz)
			return "Kibbutz";
		else
			return "Moshav";
	}

	//----------set----------
	public void set_People(List<Person> p) {

		this.people=p;
		if(p!=null) {
			ArrangeArrays();
		}
	}
	public void set_Location(Location location) {
		this.location = location;
	}
	public void set_RamzorColor(RamzorColor ramzorColor) {
		this.ramzorColor = ramzorColor;
	}
	public synchronized void set_vaccine_num(int vaccine_num) {
		this.vaccine_num += vaccine_num;
	}
	public void set_max_people(int x){
		this.max_people= (int) (x*1.3);
	}
	public void set_map(Map map){
		this.map=map;
	}

	//---------methods----------
	public abstract RamzorColor calculateRamzorGrade();
	public void Connect_Settelment(Settlement s) {
		this.settlements_conected.add(s);
	}
	public synchronized void ArrangeArrays() {
		this.sick_pepole.clear();
		this.healthy_people.clear();
		this.vaccinated_people.clear();
		this.convalescent_people.clear();
		if(people.size()!=0){
			for(var p:this.people) {

				if(p instanceof Sick) {
					this.sick_pepole.add((Sick)p);
				}
				else if(p instanceof Healthy) {
					this.healthy_people.add((Healthy) p);
				}
				else if(p instanceof Vaccinated){
					this.vaccinated_people.add((Vaccinated) p);
				}
				else {
					this.convalescent_people.add((Convalescent) p);
				}
			}
		}
	}
	public void build_people() {
		this.people.clear();
		this.people.addAll(this.healthy_people);
		this.people.addAll(this.sick_pepole);
		this.people.addAll(this.vaccinated_people);
		this.people.addAll(this.convalescent_people);
	}
	public double contagiousPercent() {
		int count=0;
		for (int i=0;i<people.size();++i)
		{
			if (people.get(i) instanceof Sick)
				++count;
		}
		return (double) count/people.size();
	}
	public String getPrecent(){
		return 100*contagiousPercent()+" %";
	}
	public Point randomLocation() {
		int x;//width
		int y;//height
		//Assuming the point is lower left in relation to the locality
		Random rand = new Random();
		x = rand.nextInt(this.location.get_size().get_width())+this.location.get_position().get_x();
		y = rand.nextInt(this.location.get_size().get_height())+(this.location.get_position().get_y()-this.location.get_size().get_height());

		return new Point(x,y);
	}
	public boolean addPerson(Person p) {
		if(this.people.add(p)) {
			ArrangeArrays();
			return true;
		}
		else return false;
	}
	public boolean removePerson(Person p){
		for(int i=0;i<people.size();i++) {
			if (people.get(i).equals(p)) {
				people.remove(i);
				return true;
			}
		}
		return false;
	}
	public boolean transferPerson(Person p, Settlement s) {
		double x;
		x=this.ramzorColor.get_trans_present()*s.ramzorColor.get_trans_present();
		Random rand= new Random();
		double ran = rand.nextDouble();
		if(s.people.size()==s.max_people) {
			return false;
		}
		if(x<ran) {
			return false;
		}
		Settlement first = this;
		Settlement second = s;

		int idThis=System.identityHashCode(this);
		int idOther=System.identityHashCode(s);

		if(idOther>idThis){
			first=s;
			second=this;
		}
		synchronized (first) {
			synchronized (second) {
				this.removePerson(p);
				s.addPerson(p);
				p.set_settlement(s);
				return true;
			}
		}
	}
	public boolean equals(Object Other) {
		if (Other instanceof Settlement) {
			Settlement o= (Settlement) Other;
			if ((this.get_Name()==o.get_Name()) && (this.get_Location().equals(o.get_Location())&&this.get_RamzorColor().promo==o.get_RamzorColor().promo))
				return true;
		}
		return false;
	}
	public int get_dead() {
		return this.dead;
	}
	public int get_alive() {
		return this.people.size();
	}
	public int set_dead() {
		return this.dead+=1;
	}
	public void Change(Person p,Sick s){
		for(int i=0;i<people.size();i++){
			if(people.get(i).equals(p)){
				Person sick = people.get(i).contagion(s.getVirus());
				people.set(i, sick);
			}
		}
	}
	public void ChangeRecover(Person p,Convalescent convalescent){
		for(int i=0;i<people.size();i++){
			if(people.get(i).equals(p)){
				people.set(i, convalescent);

			}
		}
	}
	public synchronized void WtriteToLog(String LogPAth) throws IOException {
		synchronized (this.getClass()) {

			if (count_dead + 0.01 <= get_dead() / (double) people.size()) {

				count_dead += 0.01;
				log_file.export(write(this));

			}
		}
	}

	public String write(Settlement s) throws IOException {

		StringBuilder item = new StringBuilder();
		synchronized (this) {

			item.append(LocalTime.now());
			item.append("\n");
			item.append("name: " + s.get_Name());
			item.append("\n");
			item.append("sick people: " + s.get_sick().size());
			item.append("\n");
			item.append("dead people: " + s.get_dead() + "\n");
			item.append("\n");
			//write.append(item.toString());

		}
		return item.toString();
	}
	public synchronized String toString(){

		return "\n--------------------------------------------\n"+ "Name : " + get_Name() // + "\nThe location on Map is : " + get_Location().toString()
				+ "\npopulation: " + get_People().size()+"\n" + "ramzorColor: "+get_RamzorColor()
				+ " Number of sicks: " + get_sick().size()+" Number of healthy: "+get_Healty_people().size()
				+" Number of vacc: "+get_Vaccinated_people().size()+" Number of con: "+get_Convalescent_people().size();
	}
	public synchronized void infection_attempts(){
		int size= (int) (sick_pepole.size()*0.2);
		int count=0;
		//System.out.printf("befor: %s '%s': %d Contagious %d\n",getType(), get_Name(), count,sick_pepole.size());
		for (int i = 0; i < size; i++)
		{
			if(sick_pepole.size()==0)
				return;
			Sick random_sick= sick_pepole.get(new Random().nextInt(sick_pepole.size()));
			for(int j=0; j<3;++j) {
				Person healty_person= healthy_people.get(new Random().nextInt(healthy_people.size()));
				IVirus randomVirus= VirusManager.contagion(random_sick.getVirus());
				if (randomVirus.tryToContagion(random_sick, healty_person)) {
					Change(healty_person,random_sick);
					count++;
				}
			}
		}
		ArrangeArrays();
		//System.out.printf("after:%s '%s': %d Contagious \n",getType(), get_Name(), count);
	}
	public synchronized void recover(){
		for (var sick : sick_pepole) {
			if(sick_pepole.size()==0)
				return;
			int numberOfDays=Math.round((int)Clock.now() - sick.get_contagiousTime() / Clock.get_ticks_per_day());
			if (numberOfDays >= 25) {
				Convalescent convalescent = (Convalescent) sick.recover();
				ChangeRecover(sick,convalescent);
			}
		}
		ArrangeArrays();
	}
	public synchronized void try_transfer(){
		int sizee = (int) (people.size() * 0.03);
		for (int j = 0; j < sizee; ++j) {
			if (settlements_conected.size()==0)
				return;
			int peopleIndex = new Random().nextInt(get_People().size() );
			int connectIndex = new Random().nextInt(get_settlements_connected().size() );
			if (people == null)
				return;
			transferPerson(people.get(peopleIndex), settlements_conected.get(connectIndex));
		}
		ArrangeArrays();
	}
	private synchronized void try_vaccinate(){
		ArrangeArrays();
		if (healthy_people.size() == 0)
				return;
		while(this.vaccine_num > 0){
			for(int i=0;i<people.size();i++){
				if(people.get(i) instanceof Healthy){
					people.set(i,((Healthy) people.get(i)).vaccinate());
					this.vaccine_num--;
					break;
				}
			}
		}
		ArrangeArrays();
	}
	public synchronized void kill_people(){
		ArrangeArrays();
		for(var p:sick_pepole){
			if (p.getVirus().tryToKill(p)){
				this.removePerson(p);
				this.set_dead();
			}
		}
		ArrangeArrays();
	}
	public Map get_map(){
		return this.map;
	}
}


