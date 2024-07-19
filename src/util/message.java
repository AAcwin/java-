package util;

import UI.logon;

import javax.swing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static UI.logon.frame;

public class message extends Thread{
    public logon g=new logon();
    private String nameMessage;
    private String nameMessage_;
    private int charID;
    private int charID_;
    private int hp;
    private Socket socket;

    public int getHp() {
        return hp;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public int getCharID() {
        return charID;
    }

    public String getNameMessage_() {
        return nameMessage_;
    }

    public int getCharID_() {
        return charID_;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
//获取用户填入的信息
        g.initial();

        frame.setContentPane(g.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while (true){
            nameMessage=g.getName();
            charID=g.getID();
            if(charID!=0){
            break;}
        }
//连接网络

        try {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            socket=new Socket(address,2024);

            BufferedReader br1=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //发生数据给服务端
            PrintWriter prw1=new PrintWriter(socket.getOutputStream());

            String input="";
            while (!(input = br1.readLine()).equals("begin")){
            }
            System.out.println("准备ok");

            //发送玩家昵称
            prw1.println(nameMessage);
            System.out.println("发送昵称");
            //发送角色id
            prw1.println(charID);
            prw1.flush();
            System.out.println("发送id");

            //接受对方信息
            while (!(input = br1.readLine()).equals("ready")) ;
            nameMessage_= br1.readLine();
            charID_=Integer.parseInt(br1.readLine());



        } catch (IOException e) {
            e.printStackTrace();
        }







    }
}
