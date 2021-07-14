//alice aidlin 206448326
//maayan nadivi 208207068
package UI;
import IO.*;
import Population.Sick;
import Simulation.Clock;
import Simulation.Main;

import Virus.*;
import Country.Map;
import Country.Settlement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

// main menu
public class Main_window extends JFrame{


	private static SimulationFile loadSimulationte;
	protected Map map;
	private static List<Settlement> settlements;
	private static MaPanel maPanel;
	private static Settlement selectedSettlement;
	private static statistics_window s_window;
	
	
	private static final JSlider speed=new JSlider();//create slider
	private static JPanel box=new JPanel();//panel box to all the panel
	private static JPanel map_panel=new JPanel();// panel mid
	private static int tick;
	public int sleepingTime=50;
	private static Main_window main_window= null;

	static JFrame frame =new JFrame ("Main Window");// Title frame
	private static int value;

	private Main_window() {



		super("Main window");
		//Map map=new Map();
		JPanel menu=new JPanel();//panel up
		JPanel simulation=new JPanel();// panel speed simulation


		box.setLayout(new BoxLayout(box,BoxLayout.PAGE_AXIS));//panel box to all the panel
		box.add(menu);//add menu to box panel
		box.add(map_panel);//add map menu to box panel
		//maPanel=new MaPanel(this.map);
		//map_panel.add(maPanel);
		map_panel.setPreferredSize(new Dimension(300,300));

		box.add(simulation);//add simulation to box panel
		box.setPreferredSize( new Dimension( 500, 500 ) );//update window size
		frame.add(box);//add box to frame
		
	

		//----------------slider-------------

		simulation.add(speed);// add slider to simulation
		//Turn on labels at major tick marks.
		speed.setMajorTickSpacing(10);
		speed.setMinorTickSpacing(1);
		speed.setPaintTicks(true);
		speed.setPaintLabels(true);
		value=speed.getValue();
		String print="value: "+value;
		JTextArea s=new JTextArea();
		s.setText(print);//add the text
		speed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				value = speed.getValue();//the simulation speed that choose
				s.setText("value: "+ value);
				//System.out.println(value);
				setSleep(value);
				}});

	box.add(s);// add the text to the box
	menuBar();//go to menu bar
	frame.pack();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);// can see the frame 
}
	public static Main_window getinstance(){
		if (main_window==null){
			main_window=new Main_window();
		}
		return main_window;
	}
public void menuBar() {
	JMenuBar menuss=new JMenuBar();//create menu
	JMenu file=new JMenu("File");
	JMenu simulation=new JMenu("Simulation");
	JMenu help=new JMenu("Help");
	menuss.add(file);//add file to menu
	menuss.add(simulation);//add simulation to menu
	menuss.add(help);//add help to menu
	JMenuItem statistics=new JMenuItem("Statistics");//
	statistics.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			statistics.setEnabled(true);//can push this button
			Main_window.s_window.setVisible(true);}});
	statistics.setEnabled(false);//can't push this button
	JMenuItem stop=new JMenuItem("Stop");
	//-----------load----------------
	JMenuItem play=new JMenuItem("Play");
	JMenuItem load=new JMenuItem("Load");
	load.addActionListener(new AbstractAction(){// function that load the file
		public void actionPerformed(ActionEvent e) {
			FileDialog fc = new FileDialog(frame,"UPDATE FILE: ",FileDialog.LOAD);
			fc.setVisible(true);
			play.setEnabled(true);
			if(fc.getFile()==null)
				return;
			File file =new File(fc.getDirectory(),fc.getFile());
			System.out.println("file: " +file.getPath());
			//SimulationFile s;
			//String[] a=file.getPath().split("\\.");
			String a=file.getPath();

			loadSimulationte=SimulationFile.get_simulation(a);
			settlements=loadSimulationte.getPopulation();
			System.out.println(a);
			String [] line=a.split("\\.");
			String p=line[line.length-1];
			if(p.compareTo("txt")==0) {
				map=Map.load_map(file.getPath());
				map.init_map();
				map.print();
				maPanel=new MaPanel(map);
				map_panel.add(maPanel);
				SwingUtilities.updateComponentTreeUI(frame);
				map.set_ismapload(true);
				load.setEnabled(false);
				map.set_cyclic(new CyclicBarrier(map.getSettlements().length, new Runnable() {
					@Override
					public void run() {
						synchronized (map) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									Main_window.update();
								}
							});

							Clock.nextTick();

							try {
								Thread.sleep(100 *getSleep());
							} catch (InterruptedException interruptedException) {
								//interruptedException.printStackTrace();

							}
						}
					}
				}));
				SwingUtilities.updateComponentTreeUI(frame);
				System.out.println(map.get_ispause());
				map.startThread();

			}
			else
			{
				load.setEnabled(true);
				JOptionPane.showConfirmDialog(frame, "invalid file", "Error",JOptionPane.DEFAULT_OPTION);
			}
			stop.setEnabled(true);
			statistics.setEnabled(true);
			//map_panel=new MaPanel(map.getSettlements());
			//map_panel.add(new MaPanel());
			//box.add(maPanel);
			//----------------stasts table-------------
			System.out.println("create sts win");
			s_window=new statistics_window(map);
			s_window.setVisible(false);


		}});


	JMenuItem edit=new JMenuItem("Edit Mutations");
	edit.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			edit_Mutations_window();}});

	//------------------log--------------------
	JMenuItem log=new JMenuItem("log");
	log.addActionListener(new AbstractAction(){// function that load the file
		public synchronized void actionPerformed(ActionEvent e) {
			LocalDate date = LocalDate.now();
			String text = date.format(DateTimeFormatter.BASIC_ISO_DATE);
			LocalDate today = LocalDate.parse(text, DateTimeFormatter.BASIC_ISO_DATE);

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
					str = str+"\\logfile"+today+".log";
					//++counter;
				}
			}
			String path= str;
			map.setLogPAth(path);
			log_file lf=new log_file(path);

		}});
	//----------------------undo-----------------
	JMenuItem undo=new JMenuItem("Undo");
	undo.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			log_file.updateMemento();
		}
	});
	//----------------------exit-----------------
	JMenuItem exit=new JMenuItem("Exit");
	exit.setEnabled(true);
	exit.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent e) {		
			System.exit(0);}});

	//-----------add--------------
	file.add(load);
	file.add(statistics);
	file.add(edit);
	file.add(log);
	file.add(undo);
	file.add(exit);


	//---------pause--------------
	JMenuItem pause=new JMenuItem("Pause");
	pause.setEnabled(false);

	//----------play----------------

	play.setEnabled(false);
	play.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			pause.setEnabled(true);
			play.setEnabled(false);
			map.set_isplay(true);
			map.set_ispause(false);
			map.set_isstop(false);
			synchronized (map){
				map.notifyAll();
			}

						
		}});

	//----------- pause more----------------
	pause.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			play.setEnabled(true);
			pause.setEnabled(false);
			map.set_isplay(false);
			map.set_ispause(true);
			map.set_isstop(false);
		
		}});

	// -----------stop----------------------
	stop.setEnabled(false);
	stop.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			exit.setEnabled(true);
			load.setEnabled(true);
			statistics.setEnabled(false);
			play.setEnabled(true);
			pause.setEnabled(false);
			map.set_isplay(false);
			map.set_ispause(false);
			map.set_isstop(true);


			map.setSettlements(new Settlement[0]);

		}});

	//---------------------ticks------------------
	JMenuItem set=new JMenuItem("Set Ticks Per Day");
	set.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			set_ticks_per_day();}});

	//---------------------add----------------------
	simulation.add(play);
	simulation.add(pause);
	simulation.add(stop);
	simulation.add(set);

	//-------------helppp---------------------
	JMenuItem helpp=new JMenuItem("Help");
	helpp.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			JFrame f=new JFrame();
			JPanel p=new JPanel();
			p.setBackground(Color.PINK);
			String text="Welcome to the Ministry of Health system.\nThis system reveals the information about the spread of viruses in the country.\n As an artist you can see what color your city and other cities are. You will know where there is a risk of infection.\n In addition you will discover a lot of vital statistical information.\nIf you want statistical information, click on the 'Statistical' button.\nIf you want to see the colors of the cities click on start and see the map.Health to everyone!";
			JTextArea thetext = new JTextArea(19,19);
			thetext.setEditable(false);
			thetext.append(text);
			p.add(thetext);
			p.setPreferredSize( new Dimension( 700, 250));
			JDialog d= new JDialog(f,"Help",true);
			d.add(p);
			d.pack();
			d.setVisible(true);}});

	//-------------about---------------------
	JMenuItem about=new JMenuItem("About");
	about.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			JFrame f=new JFrame();
			JPanel p=new JPanel();
			p.setBackground(Color.PINK);
			String text="  Alice Aidlin 206448326\n  Maayan Nadivi 208207068\n  update: 20:30 26/4/21";
			JTextArea thetext = new JTextArea(20,20);
			thetext.setEditable(false);
			thetext.append(text);
			thetext.setBackground(Color.PINK);
			p.add(thetext);
			p.setPreferredSize( new Dimension( 250, 150 ) );
			JDialog d= new JDialog(f,"About",false);
			d.add(p);
			d.pack();
			d.setVisible(true);}});
	
	//-------------add---------------------
	help.add(helpp);
	help.add(about);
	frame.setJMenuBar(menuss);
}//end menu bar
//----------------------------------------------------------------------------

public static void edit_Mutations_window() {
	JFrame f=new JFrame();
	String colum[]={Virus.getBritishVariantName(),Virus.getChineseVariantName(),Virus.getSouthAfricanVariantName()};
	JDialog d= new JDialog(f,"Mutations Window",true);
	JPanel panel= new JPanel();
	//Object[][] data = {{false,false,false},{false,false,false},{false,false,false}};
	TableA model = new TableA(VirusManager.getData(), colum);
	JTable table = new JTable(model);
	table.setFillsViewportHeight(true);
	table.setPreferredScrollableViewportSize(new Dimension(500,70));
	d.add(new RowedTableScroll(table,colum));
	panel.add(new RowedTableScroll(table, colum));	

	model.addTableModelListener(new TableModelListener() {
		public void tableChanged(TableModelEvent e) {
			//-----------------------to do--------------

			System.out.println("mainwindow 385");
			VirusManager.SetData(model.getData());

		}});
	d.add(panel);
	d.pack();
	d.setVisible(true);
}//end edit_Mutations_window

public static void set_ticks_per_day(){
	JFrame f=new JFrame();
	SpinnerModel s= new SpinnerNumberModel();
	JSpinner spinner = new JSpinner(s);
	JPanel p = new JPanel();
	p.setPreferredSize( new Dimension( 200, 100));
	JButton b = new JButton("update");
	spinner.setPreferredSize( new Dimension( 30, 24));
	p.add(spinner);
	p.add(b);
	f.add(p);

	//---------------update--------------------
	b.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println(spinner.getValue());
			Simulation.Clock.set_ticks_per_day((int)(spinner.getValue()));
		}});
	JDialog d= new JDialog(f,"ticks per day",true);
	d.add(p);
	d.pack();
	d.setVisible(true);
}

public void setSleep(int time){
	sleepingTime=time;
}

public int getSleep(){
	return sleepingTime;
}

public Map getMap(){return this.map;}
public static void update() {
	maPanel.repaint();
	s_window.getModel().fireTableDataChanged();

}
public static statistics_window get_sWindow() {
	return s_window;
}
}

