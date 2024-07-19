package UI;

import javax.swing.*;
import java.awt.*;

public class drawPanel extends JPanel {
    private int y;

    public void setY(int y) {
        this.y = y;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        g.drawRect(50,0,50,300);
        g.drawImage(new ImageIcon("src/image/se.png").getImage(),50,0,50,300,this);
        //从下往上y轴：(275 , -25)
        g.drawImage(new ImageIcon("src/image/jiantou.png").getImage(),0,y,50,50,this);

    }
}
