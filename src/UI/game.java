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
        //��ʼ����Ϸ����
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
//��Ϸ�����ӷ�����
        BufferedReader br=null;
        PrintWriter prw=null;
        try {
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            prw=new PrintWriter(socket.getOutputStream());
            //��ʼ����������
            atk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    afterAtk=true;
                }
            });

            if(character1.getId()==4){
                skill.addActionListener(character1.buff(skill, character2));
            }
            //��Ҫ����
            while (true){
                String commend=br.readLine();
                character1.buffInitial();

                //��Ϊ�������غ�
                    if(commend.equals("you")){
                    //��ʼ����������
                    switch (character1.getId()){
                        case 1:skill.addActionListener(character1.buff(skill));
                            break;
                        case 2:if(character1.getHp()<90)skill.addActionListener(character1.buff(skill));
                            break;
                    }

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
                                System.out.println("������"+goal);
                                break;
                        }

                        }
                        if(afterAtk){
                            prw.println("next");
                            prw.println(goal);
                            //�����˺�
                            hurt=character1.getHurt(goal);
                            prw.println(hurt);
                            System.out.println("�˺���"+hurt);
                            prw.flush();
                            afterAtk=false;
                            break;
                        }
                    }
                }
                //��Ϊ�ܻ����غ�
                if (commend.equals("he")){
                    //��ʼ�����ؼ���
                    switch (character1.getId()){
                        case 3:skill.addActionListener(character1.buff(skill));isSkill=true;break;
                    }

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
                            hurt=character1.getHurt(Integer.parseInt(goal));
                            if(isSkill){
                                isSkill=false;
                                int guess= character1.getChoice();
                                if (guess==hurt){
                                    hurt=0;
                                    System.out.println("�ɹ��ֵ�");
                                }else System.out.println("�ֵ�ʧ��");
                            }
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

                if(character1.getHp()<=0){
                    System.out.println("����");
                    prw.println("bye");
                    prw.flush();
                    socket.close();
                    break;
                }
                if(character2.getHp()<=0){
                    System.out.println("Ӯ��");
                    prw.println("bye");
                    prw.flush();
                    socket.close();
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

