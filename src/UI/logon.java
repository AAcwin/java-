package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class logon {




    public JPanel panel1;
    private  JButton okButton;

    private JTextField name;
    private String nameMessage="";
    select g1=new select();
    public static JFrame frame = new JFrame("gameGUI");

    public void initial(){
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(name.getText());
                nameMessage=name.getText();


                frame.setContentPane(g1.panel1);
                frame.repaint();
                frame.setVisible(false);
                frame.setVisible(true);

            }
        });

        int width=800;
        int height=600;
        frame.setSize(width,height);
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width-width)/2,(screenSize.height-height)/2);
    }

    public  String getName(){
        return nameMessage;
    }

    public int getID(){
        return g1.getID();
    }



}
