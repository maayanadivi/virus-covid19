//alice aidlin 206448326
//maayan nadivi 208207068
package UI;

import Country.Settlement;

import java.awt.*;

public class Line_Color_Dec {
    Settlement one;
    Settlement two;
    private Color color;

    public Line_Color_Dec(Settlement a, Settlement b) {
      one=a;
      two=b;
    }

    public void set_avg_color(){
        int red = (one.getColor().getRed() + two.getColor().getRed()) / 2;
        int green = (one.getColor().getGreen() + two.getColor().getGreen()) / 2;
        int blue = (one.getColor().getBlue() + two.getColor().getBlue()) / 2;
        this.color = new Color(red, green, blue);

    }
    public void paint(Graphics gr){

        int xS = one.get_Location().get_Position_x();
        int yS = one.get_Location().getPosition_y();
        int widthS = one.get_Location().get_size().get_width();
        int heightS = one.get_Location().get_size().get_height();
        int xC = two.get_Location().get_Position_x();
        int yC = two.get_Location().getPosition_y();
        int widthC = two.get_Location().get_size().get_width();
        int heightC = two.get_Location().get_size().get_height();
        set_avg_color();
        gr.setColor(color);
        gr.drawLine((int)(xS+0.5*widthS),(int)(yS+0.5*heightS),(int)(xC+0.5*widthC),(int)(yC+0.5*heightC));
    }


}
