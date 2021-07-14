//alice aidlin 206448326
//maayan nadivi 208207068
package UI;
import IO.SimulationFile;
import Location.Location;
import Location.Size;

import javax.swing.*;

import Country.Map;
import Country.Settlement;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class MaPanel extends JPanel {

	private  class CustomMouseClick implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			Point press=e.getPoint();
			int xSettlement;
			int ySettlement;
			int widthSettlement;
			int heightSettlement;

			for(int i=0;i<map.getSettlements().length;i++){
				xSettlement=map.getSettlements()[i].get_Location().get_position().get_x();
				ySettlement=map.getSettlements()[i].get_Location().get_position().get_y();
				widthSettlement=map.getSettlements()[i].get_Location().get_size().get_width();
				heightSettlement=map.getSettlements()[i].get_Location().get_size().get_height();

				if( (press.getX()>=xSettlement && press.getX()<=widthSettlement+xSettlement) &&(press.getY()>=ySettlement && press.getY()<=heightSettlement+ySettlement)){
					String SettlementChoosenName=map.getSettlements()[i].get_Name();
					List<Settlement> ChoosenSettlement = new ArrayList<Settlement>();
					ChoosenSettlement.add(map.getSettlements()[i]);
					Main_window.get_sWindow().getModel().getCurrentRow(i);
					Main_window.get_sWindow().setVisible(true);
				}
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}

	
	private Map map;
	private Location settlementLocation;
	private List<Settlement> connected;

	private int x_start_line;
	private int y_start_line;
	private int x_final_line;
	private int y_final_line;
	private int x_name;
	private int y_name;

	public MaPanel(Map map){
		super();
		this.map=map;
		this.addMouseListener(new CustomMouseClick());
	}

	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(map==null)
			return;
		
		//this.settlements = map.getSettlements();
		
		for(Settlement s:map) {
			connected = s.get_settlements_connected();
			Graphics2D gr = (Graphics2D) g;
			gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			gr.setBackground(Color.lightGray);
			gr.setColor(Color.BLACK);
			
			/*int xS = s.get_Location().get_Position_x();
			int yS = s.get_Location().getPosition_y();
			int widthS = s.get_Location().get_size().get_width();
			int heightS = s.get_Location().get_size().get_height();*/
			
			for(Settlement c:connected) {
				if(c!=null) {
					Line_Color_Dec decorator=new Line_Color_Dec(s,c);
					decorator.paint(gr);
					/*int xC = c.get_Location().get_Position_x();
					int yC = c.get_Location().getPosition_y();
					int widthC = c.get_Location().get_size().get_width();
					int heightC = c.get_Location().get_size().get_height();
					decorator.set_avg_color(gr);
					gr.drawLine((int)(xS+0.5*widthS),(int)(yS+0.5*heightS),(int)(xC+0.5*widthC),(int)(yC+0.5*heightC));*/
				}
			}
			
			gr.setColor(s.get_RamzorColor().get_color());
			settlementLocation = s.get_Location();
			gr.fillRect(settlementLocation.get_position().get_x(), settlementLocation.get_position().get_y(), settlementLocation.get_size().get_width(), settlementLocation.get_size().get_height());
			gr.setColor(Color.BLACK);
			x_name = settlementLocation.get_position().get_x();
			y_name = settlementLocation.get_position().get_y();
			gr.drawString(s.get_Name(), x_name, y_name);
			
		}
	}
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}
}
