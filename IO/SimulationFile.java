//alice aidlin 206448326
//maayan nadivi 208207068
package IO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Population.*;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;
import Country.*;
import Location.*;

public class SimulationFile {
	//----------attr----------
	private List<Settlement> population;
	private static SimulationFile obj;
	private static int count=0;
	private Factory f=new Factory();
	//----------ctor----------
	public SimulationFile(String fileName) {
		this.population = new ArrayList<>();
		try{
			//String fileName ="D:\\Microsoft VS Code\\java\\hw1_208207068_206448326\\src\\IO\\population.txt";
			File file = new File(fileName);
			FileReader fr= new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String st;
			ArrayList<String> connected=new ArrayList<String>();
			Map map= new Map();
			while((st=br.readLine()) != null)
			{
				String[] st_line=st.split(";");
				if (!(st_line[0].equals("#"))) {
					String type = st_line[0];
					System.out.println(type);
					Point pointc = new Point(Integer.valueOf(st_line[2]), Integer.valueOf(st_line[3]));
					Size sizec = new Size(Integer.valueOf(st_line[4]), Integer.valueOf(st_line[5]));

					Settlement s = f.Get_Settlement(type, st_line[1], new Location(pointc, sizec), null, RamzorColor.Green, map);
					System.out.println(s.get_Name());
					s.set_People(creat_Population(Integer.valueOf(st_line[6]), s));
					s.set_max_people(Integer.parseInt(st_line[6]));
					Contaminating01(s);//Defining 1 percent of the population as sick
					population.add(s);
				}
			}

			CreateConnections(fileName);  // creating the list of all connected settlements


		}catch(FileNotFoundException e) {
			System.err.printf("File not found");
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}
		
	}

	//----------get----------

	public List<Settlement> getPopulation() {
		return this.population;
	}

	public Settlement[] getPopulationArray(){
		return this.population.toArray(new Settlement[0]);
	}

	//----------set----------
	public void setPopulation(List<Settlement> population) {
		this.population = population;
	}

	//----------methods----------
	public static List<Person> creat_Population(int num, Settlement s){
		List<Person> people=new ArrayList<>();
		Random r = new Random();
		int temp=(int)r.nextGaussian();
		while (temp<=1) {
			temp=(int)r.nextGaussian();
		}
		for(int i=0;i<num;++i) {
			int age = (int) (temp*6+9);
			people.add(new Healthy(age,s.randomLocation(),s));
		}
		return people;}

	public static SimulationFile get_simulation(String file_path) {
		if(obj == null) {
			obj = new SimulationFile(file_path);
		}
		return obj;
	}
	
	public void CreateConnections(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner reader =new Scanner(file);
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] lines = line.split(";");
			if(lines[0].equals("#")) {
				Settlement source = null, destination = null;
		        for (var settlement : this.population) {
		            if (settlement.get_Name().equals(lines[1])) {
		                source = settlement;
		            }
		            if (settlement.get_Name().equals(lines[2])) {
		                destination = settlement;
		            }
		        }
		        source.Connect_Settelment(destination);
			}
			
		}
		
	}
	public void Contaminating01 (Settlement settlement){
		List<Person> people = settlement.get_People(); //population list
		int size = settlement.get_People().size() / 100;// 1%

		for (int i = 0; i < size; i++)
		{
			Random r=new Random();
			int choice= r.nextInt(3);
			System.out.println("The choice is = "+choice);
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

}