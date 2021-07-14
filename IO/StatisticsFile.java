//alice aidlin 206448326
//maayan nadivi 208207068
package IO;
import Simulation.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import Country.City;
import Country.Kibbutz;
import Country.Map;
import Country.Moshav;
import Country.Settlement;
//class for the statistic table
public class StatisticsFile {
	//static int counter=1;
	public StatisticsFile(Map map) throws FileNotFoundException 
	{			
		JFileChooser file = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        file.setDialogTitle("where do you want to save your Excel file: ");
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);       
        String str = null;     
        int returnValue = file.showSaveDialog(null);       
        if (returnValue == JFileChooser.APPROVE_OPTION) 
        {
            if (file.getSelectedFile().isDirectory()) 
            {
                str =  file.getSelectedFile().toString();         
                //setting name to the file
                str = str+"\\StatisticTableInExel.csv";
                //++counter;
            }
        }        
        File f = new File(str);
        try(PrintWriter write = new PrintWriter(f);) {
        	StringBuilder item =new StringBuilder();
        	item.append("city/kibbutz/moshav");
        	item.append(",");
        	item.append("name: ");
        	item.append(",");
        	item.append("population");
        	item.append(",");
        	item.append("color");
        	item.append(",");
        	item.append("vaccineted");
        	item.append(",");
        	item.append("healty people");
        	item.append(",");
        	item.append("sick people");
			item.append(",");
			item.append("dead people");
        	item.append("\n");
        	Settlement [] set = map.getSettlements();
        	for(int i=0;i<map.getSettlementSize();++i) {
        		if(set[i] instanceof Kibbutz) {
        			item.append("kibbutz");
        			item.append(",");
        		}else if(set[i] instanceof City) {
        			item.append("city");
        			item.append(",");
        		}else if(set[i] instanceof Moshav) {
        			item.append("moshav");
        			item.append(",");
        		}
        		item.append(set[i].get_Name());
        		item.append(",");
        		item.append(set[i].get_People().size());
        		item.append(",");
        		item.append(set[i].get_RamzorColor());
        		item.append(",");
        		item.append(set[i].get_vaccinated());
        		item.append(",");
        		item.append(set[i].get_Healty_people().size());//------------healty people
        		item.append(",");
        		item.append(set[i].get_sick().size());//-------------sick people
        		item.append(",");
				item.append(set[i].get_dead());//-------------sick people
				item.append("\n");

        	}
        	write.write(item.toString());
        	write.close();
        }catch (Exception e) {
			// TODO: handle exception
        	//System.out.println("hi");
        	System.out.println(e.getMessage());
		}
	}
}