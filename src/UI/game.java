package UI;

import util.character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class game extends Thread {
    public JPanel panel1;
    private JButton skill;
    private JButton atk;
    private JButton button3;
    private JButton button4;
    private JPanel center;
    private JPanel down;
    private JPanel up;
    private JLabel hp1;
    private JLabel hp2;
    private JLabel name1;
    private JLabel name2;
    private JPanel left;
    private JPanel right;
    drawPanel drawPanel1;
    drawPanel drawPanel2;
    boolean afterAtk;
    boolean isSkill;
    private int goal;
    private int hurt;
    private character character1;
    private character character2;

    public void setCharacter2(character character2) {
        this.character2 = character2;
    }

    public void setCharacter1(character character) {
        this.character1 = character;
    }

    public void initial(){
        //初始化游戏界面
       down.setPreferredSize(new Dimension(800,100));
       skill.setPreferredSize(new Dimension(100,100));
       up.setPreferredSize(new Dimension(800,150));
       button3.setPreferredSize(new Dimension(100,100));
       button4.setPreferredSize(new Dimension(100,100));
       hp1.setPreferredSize(new Dimension(100,100));
       hp2.setPreferredSize(new Dimension(100,100));
       drawPanel1=new drawPanel();
       drawPanel2=new drawPanel();
        left.setPreferredSize(new Dimension(150,500));
        left.add(drawPanel1);
        right.setPreferredSize(new Dimension(150,500));
        right.add(drawPanel2);


   }

     Socket socket;
    public void setSocket(Socket socket) {
        this.socket = socket;
    }



    @Override
    public void run() {
        afterAtk =false;
        isSkill = false;
        name1.setText(character1.getName());
        name2.setText(character2.getName());
        hp1.setText(Integer.toString(character1.getHp()));
        hp2.setText(Integer.toString(character2.getHp()));
//游戏端连接服务器
        BufferedReader br=null;
        PrintWriter prw=null;
        try {
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            prw=new PrintWriter(socket.getOutputStream());
            //初始化攻击按键
            atk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    afterAtk=true;
                }
            });
            //初始化技能按键
            skill.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isSkill=true;
                    skill.removeActionListener(this);
                }
            });

            //主要流程
            while (true){
                String commend=br.readLine();
                //作为进攻方回合
                if(commend.equals("you")){
                    System.out.println("1");
                    while (true){
                        for (int i=275;i+25>0;i--){
                            drawPanel1.setY(i);
                            drawPanel1.repaint();
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if(afterAtk){
                                goal=i;
                                break;
                        }

                        }
                        if(afterAtk){
                            prw.println("next");
                            prw.println(goal);
                            hurt=character1.getHurt(goal);
                            //使用技能
                            if(isSkill){
                                hurt=character1.skill(character1.getId(),hurt);
                            }
                            prw.println(hurt);
                            prw.flush();
                            afterAtk=false;
                            break;
                        }
                    }

                }
                //作为受击方回合
                if (commend.equals("he")){
                    System.out.println("1");
                    while (true){
                        for (int i=275;i+25>0;i--){
                            drawPanel2.setY(i);
                            drawPanel2.repaint();
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        String isStop="";
                        if (br.ready()){
                            isStop= br.readLine();
                        }
                        if(isStop.equals("stop")){
                            String goal=br.readLine();
                            drawPanel2.setY(Integer.parseInt(goal));
                            drawPanel2.repaint();
                            hurt=character2.getHurt(Integer.parseInt(goal));
                            prw.println(hurt);
                            prw.flush();
                            break;
                        }
                    }
                }

                if (commend.equals("conclution1")){
                    int finHurt=Integer.parseInt(br.readLine());
                    character2.setHp(character2.getHp()-finHurt);
                    hp2.setText(Integer.toString(character2.getHp()));
                    hp2.repaint();
                }
                if (commend.equals("conclution0")){
                    int finHurt=Integer.parseInt(br.readLine());
                    character1.setHp(character1.getHp()-finHurt);
                    hp1.setText(Integer.toString(character1.getHp()));
                    hp1.repaint();
                }

            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    public static void main(String[] args) {
//        game g=new game();
//        JFrame frame = new JFrame("game");
//        g.initial();
//        frame.setSize(800,600);
//        frame.setContentPane(g.panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }



}


//操控箭头移动
//class control extends Thread{
//
//    private drawPanel imag;
//    public control(drawPanel d) {
//        imag=d;
//    }
//
//    @Override
//    public void run() {
//        while (true){
//            for (int i=275;i+25>0;i--){
//                imag.setY(i);
//                imag.repaint();
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
//}