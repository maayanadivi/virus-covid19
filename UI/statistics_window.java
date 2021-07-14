//alice aidlin 206448326
//maayan nadivi 208207068
package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Country.Map;
import Country.Settlement;
import IO.StatisticsFile;
import Population.Sick;
import Simulation.Main;
import Virus.*;

public class statistics_window extends JFrame  {

	public class MapModel extends AbstractTableModel{
		private Map map;
		private final String[] columnNames = {"name","type","Color","Precent sick","Vaccineted","People","Dead"};

		public MapModel(Map map){
			this.map=map;
		}

		@Override
		public int getRowCount() {
			return map.getSettlementSize();
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Settlement s=map.at(rowIndex);
			return switch (columnIndex){
				case 0 -> s.get_Name();
				case 1 -> s.getType();
				case 2 -> s.get_RamzorColor();
				case 3 -> s.getPrecent();
				case 4 -> s.get_vaccinated();
				case 5 -> s.get_alive();
				case 6 -> s.get_dead();
				default -> null;
			};
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex > 0;
		}

		@Override
		public void fireTableDataChanged() {
			fireTableChanged(new TableModelEvent(this, 0,getRowCount()-1,TableModelEvent.ALL_COLUMNS,TableModelEvent.UPDATE));
		}


		public void getCurrentRow(int index) {
			System.out.println("choose :" + table.convertRowIndexToView(index));
			table.setRowSelectionInterval(index,index);
			//return table.getRowSorter().convertRowIndexToModel(table.getSelectedRow());
		}
	}

	
	private Map map;
	private List<Settlement> settlements;
	private String selectedSettlementName;
	private MapModel model;
	private TableRowSorter<MapModel> sorter;
	private JFrame f=new JFrame();
	private JPanel up=new JPanel();
	private JPanel down=new JPanel();
	private JPanel box=new JPanel();
	private JTable table;
	
	public statistics_window(Map map){
		super("Statistics Window");
		this.map=map;
		box.setLayout(new BoxLayout(box,BoxLayout.PAGE_AXIS));
		box.add(up);
		CreateTable();
		CreateUp();
		CreateDown();
		

		//box.add(new JScrollPane(table));
		box.add(down);
		box.setVisible(true);
		this.add(box);
		this.pack();
		this.setVisible(true);	
	}
	
	public void CreateTable(/*List<Settlement> settlements2*/) {

		model = new MapModel(map);
		table = new JTable(model);
		table.setRowSorter(sorter = new TableRowSorter<>(model));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 200));
		table.setFillsViewportHeight(true);
		box.add(new JScrollPane(table));

		/*String[] columns = new String[] {
				"name","type","color","precent sick","Vaccine doses","alive"};
		//table.setAutoCreateRowSorter(true);
		DefaultTableModel tmodel = new DefaultTableModel();

		table = new JTable(tmodel);
		
		int i=0;
		int count=0;
		for(String col:columns) {
			tmodel.addColumn(col);}
		for(var settlement:settlements) {
			count++;
			if(settlement==settlements2) {
				i=count-1;
			}
			tmodel.addRow(new String[] {
					settlement.get_Name(),
					settlement.getClass().toString().substring(14),
					settlement.get_RamzorColor().toString(),
					String.valueOf(settlement.contagiousPercent()),
					String.valueOf(settlement.get_vaccine_num()),
					String.valueOf(settlement.get_alive())});}
		if(settlements2!=null) {
			table.setRowSelectionInterval(i, i);
		}
		
		table.setRowSorter(sorter = new TableRowSorter<DefaultTableModel>(tmodel));
		table.setModel(tmodel);*/
	}

	public void CreateUp() {
		String[] cols={"none","name","type","color"};

		JComboBox col_select = new JComboBox(cols);
		col_select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i =col_select.getSelectedIndex() ;
				System.out.println(i);
			}
		});
		
		JTextField filter_text=new JTextField();
		filter_text.setPreferredSize(new Dimension(80,24));
		up.add(col_select);
		up.add(filter_text);
		
		
		// filtering
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				selectedSettlementName=table.getValueAt(selectedRow != -1 ? selectedRow:0 , 0).toString();}});
		filter_text.setToolTipText("Filter Name Column");
		filter_text.getDocument().addDocumentListener(new DocumentListener() 
		{
			public void insertUpdate(DocumentEvent e) 
			{
				try {
					int index=col_select.getSelectedIndex();
					if(index!=0) {	
						sorter.setRowFilter(RowFilter.regexFilter(filter_text.getText(), index-1));
					}	
				}
				catch (java.util.regex.PatternSyntaxException e3) 
				{				// If current expression doesn't parse, don't update.
				} 
			}
			public void removeUpdate(DocumentEvent e) 
			{ 
				try {

					int index=col_select.getSelectedIndex();
					if(index!=0) {
						sorter.setRowFilter(RowFilter.regexFilter(filter_text.getText(), index));
					}
				} 
				catch (java.util.regex.PatternSyntaxException e1) {
					// If current expression doesn't parse, don't update.
				}
			}

			public void changedUpdate(DocumentEvent e) 
			{ 
				try {

					int index=col_select.getSelectedIndex();
					if(index!=0) {
						sorter.setRowFilter(RowFilter.regexFilter(filter_text.getText(), index));
					}
				} 
				catch (java.util.regex.PatternSyntaxException e2) {
					// If current expression doesn't parse, don't update.
				}
			}
		});
	}

	/*public void updateTable() {
		SwingUtilities.invokeLater(()->CreateTable(settlements));
	}*/
	
	public void CreateDown() {
		//-----------------save---------------
		JButton save=new JButton("Save");
		save.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Map map = new Map();
					StatisticsFile csv=new StatisticsFile(map);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block

					e1.printStackTrace();}}});

		//-------------------addSick--------------
		JButton add=new JButton("Add Sick");
		add.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ChineseVariant Cv= new ChineseVariant();
				BritishVariant Bv=new BritishVariant();
				SouthAfricanVariant Sv= new SouthAfricanVariant();
				IVirus randomVirus;
				for(int i=0;i<map.getSettlements().length;++i) {
					if(map.getSettlements()[i].get_Name()==selectedSettlementName) {
						for(int j=0;j<(map.getSettlements()[i].get_Healty_people().size()*0.001);++j) {
							Random rand= new Random();
							double ran= rand.nextInt(3);
							if(ran==0) {
								randomVirus = VirusManager.contagion(Cv);
								if (randomVirus != null) {
									try {
										System.out.println("from  "+randomVirus.toString());
										Sick si = new Sick(map.getSettlements()[i].get_Healty_people().get(j), randomVirus, Simulation.Clock.now());
										map.getSettlements()[i].get_Healty_people().remove(j);
										map.getSettlements()[i].get_sick().add(j, si);

									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								else
									System.out.println("null");
							}
							else if(ran==1){
								randomVirus= VirusManager.contagion(Bv);
								if(randomVirus!=null) {
									try {
										System.out.println("from  "+randomVirus.toString());
										Sick si = new Sick(map.getSettlements()[i].get_Healty_people().get(j), randomVirus, Simulation.Clock.now());
										map.getSettlements()[i].get_Healty_people().remove(j);
										map.getSettlements()[i].get_sick().add(j, si);

									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								else
									System.out.println("null");

							}
							else {
								randomVirus = VirusManager.contagion(Sv);
								if (randomVirus != null) {
									try {
										System.out.println("from  "+randomVirus.toString());
										Sick si = new Sick(map.getSettlements()[i].get_Healty_people().get(j), randomVirus, Simulation.Clock.now());
										map.getSettlements()[i].get_Healty_people().remove(j);
										map.getSettlements()[i].get_sick().add(j, si);

									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								else
									System.out.println("null");
							}
						}
						//table.setAutoCreateRowSorter(true);
						map.getSettlements()[i].build_people();
						map.getSettlements()[i].calculateRamzorGrade();
						Main_window.update();

					}

				}
			}});

		//----------------vaccinate------------------
		JButton vaccinate=new JButton("Vaccinate");
		vaccinate.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JFrame f=new JFrame();
				JPanel p=new JPanel();
				JTextField text=new JTextField();

				//--------------------update--------------
				JButton update=new JButton("update");
				update.addActionListener(new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						int num_vaccinate=Math.abs(Integer.parseInt(text.getText()));
						for(Settlement s: map) {
							if(s.get_Name()==selectedSettlementName) {
								s.set_vaccine_num(num_vaccinate);
								System.out.println(s.get_vaccine_num());
							}
						}
						

						//------to do----------------
						//System.out.println(num_vaccinate);
					}});
				p.add(update);
				text.setPreferredSize( new Dimension( 80, 24));
				p.add(text);
				JDialog d= new JDialog(f,"sum of vaccinate");
				d.add(p);
				d.pack();
				d.setVisible(true);}});

		//------------------add-----------------------
		down.add(save);
		down.add(add);
		down.add(vaccinate);
		}

	public MapModel getModel(){return this.model;}
}

