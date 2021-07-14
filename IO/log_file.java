//alice aidlin 206448326
//maayan nadivi 208207068
package IO;
import Simulation.Main;

import java.io.*;
import java.nio.Buffer;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import Country.City;
import Country.Kibbutz;
import Country.Map;
import Country.Moshav;
import Country.Settlement;
//class for the statistic table
public class log_file {

    private static ArrayList<Memento> array_path=new ArrayList<Memento>();
    private  static String path;
    private static FileWriter logf;
    private static BufferedWriter br;
    private static File file;


    public log_file(String p) {

        path=p;
       file=new File(path);
        try {
            logf = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        br = new BufferedWriter(logf);
        Memento m=createMemento();
        addMement(m);

    }

    public static void setPath(String path){
        path=path;
    }
    public  static Memento createMemento(){
        return new Memento(path);
    }
    public static void setMemento(Memento memento){
        System.out.println("curr path"+ memento.getPath());
        path=memento.getPath();
    }
    public static void addMement(Memento m){
        array_path.add(m);
    }
    public static void updateMemento(){
        array_path.remove(array_path.size()-1);
        Memento m= array_path.get(array_path.size()-1);
        setMemento(m);
        try {
            update();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void update() throws IOException {
        file=new File(path);
        logf = new FileWriter(file, true);
        br = new BufferedWriter(logf);
    }
    public static boolean export(String infoToExport) {
        try {
            logf = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        br = new BufferedWriter(logf);
        try {
            br.append(infoToExport);
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
public static class Memento{
        private String path;
        public Memento(String path) {
            this.path=path;
        }
        public String getPath(){return path;}
}
}

