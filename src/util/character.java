package util;

import com.sun.source.doctree.EscapeTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static UI.logon.frame;

public class character {
    private String name;
    private int hp=100;
    private int id=5;
    private int buff1=1;
    private  int buff2=0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //-25~275
    public int getHurt(int goal){
        if(goal>=-25&&goal<-15){
            return 10;
        }
        else if (goal>=-15&&goal<0){
            return 9*buff1+buff2;
        }
        else if (goal>=0&&goal<20){
            return 8*buff1+buff2;
        }
        else if (goal>=20&&goal<45){
            return 7*buff1+buff2;
        }
        else if (goal>=45&&goal<80){
            return 6*buff1+buff2;
        }
        else if (goal>=80&&goal<120){
            return 5*buff1+buff2;
        }
        else if(goal>=120&&goal<170){
            return 4*buff1+buff2;
        }
        else if(goal>=170&&goal<200){
            return 3*buff1+buff2;
        }
        else if(goal>=200&&goal<230){
            return 2*buff1+buff2;
        }
        else {
            return buff1 +buff2;
        }
    }

    public int getChoice() {
        return choice;
    }

    private int choice;

    public ActionListener buff(JButton jButton){
        ActionListener actionListener = null;

        switch (id){
            //1号的技能
            case 1: actionListener=new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buff2=2;
                    System.out.println("yes");
                    jButton.removeActionListener(this);
                }
            };break;
            //2号的技能
            case 2:actionListener=new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        buff1=3;
                        System.out.println("yes");
                        jButton.removeActionListener(this);

                }
            };break;
            //3号的技能
            case 3:actionListener=new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(frame, "请猜对手会对你造成的伤害:", "博一博单车变摩托", JOptionPane.QUESTION_MESSAGE);
                    choice=Integer.parseInt(input);
                    jButton.removeActionListener(this);
                }
            };break;
        }


        return actionListener;

    }

    public ActionListener buff(JButton jButton,character enemy){
        ActionListener actionListener=null;
        actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buff1=-1;
                buff2=(hp-enemy.getHp())*4;
                jButton.removeActionListener(this);
            }
        };
        return actionListener;

    }

    public void buffInitial(){
        buff1=1;
        buff2=0;
    }


}
