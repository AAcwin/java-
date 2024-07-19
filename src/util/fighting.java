package util;

import UI.game;
import UI.logon;

import javax.swing.*;

import java.net.Socket;

import static UI.logon.frame;

public class fighting {

    public static void main(String[] args) throws InterruptedException {
        //��ȡ˫����ҵ���Ϣ
        message iniMess=new message();
        iniMess.start();
        try {
            //����ѡ��ɫ�����������Ϣ
            iniMess.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //��ӽ�ɫ
        character c1=new character();
        character c2=new character();

        c1.setName(iniMess.getNameMessage());
        c2.setName(iniMess.getNameMessage_());
        c1.setId(iniMess.getCharID());
        c2.setId(iniMess.getCharID_());


        //��ʼ��Ϸ
        game g2 = new game();
        g2.initial();
        g2.setSocket(iniMess.getSocket());
        g2.setCharacter1(c1);
        g2.setCharacter2(c2);


        frame.setContentPane(g2.panel1);
        frame.repaint();
        frame.setVisible(false);
        frame.setVisible(true);

        g2.start();
        g2.join();



    }

}
