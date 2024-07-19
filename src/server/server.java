package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class server {

    static ArrayList<Socket> players=new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //链接网络
        ServerSocket server=new ServerSocket(2024);

        while (true){
            Socket socket1=server.accept();
            System.out.println("连接成功(1)");
            players.add(socket1);
            Socket socket2=server.accept();
            System.out.println("连接成功(2)");
            players.add(socket2);
            compete compete=new compete(socket1,socket2);
            compete.start();
        }




    }

    public static class compete extends Thread{
        private Socket s1;
        private Socket s2;


        public compete(Socket s1, Socket s2) throws IOException {
            this.s1 = s1;
            this.s2 = s2;
        }


        @Override
        public void run() {
            PrintWriter prout1;
            PrintWriter prout2;
            BufferedReader bf1 = null;
            BufferedReader bf2 = null;
            //开始对战
            try {
                 prout1 =new PrintWriter(s1.getOutputStream());
                 prout2 =new PrintWriter(s2.getOutputStream());

                bf1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
                bf2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));

                prout1.println("begin");
                prout1.flush();
                prout2.println("begin");
                prout2.flush();

                System.out.println("begin");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //对于1号玩家
            try {
            //获取昵称
            String name1=bf1.readLine();
            //获取角色id
            int id1= Integer.parseInt(bf1.readLine());
                System.out.println("获得1号信息");

                   prout2.println("ready");
                   //输出信息给2号玩家
                   prout2.println(name1);
                   prout2.println(id1);
                   prout2.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //对于2号玩家
            try {
                //获取昵称
                String name2=bf2.readLine();
                //获取角色id
                int id2= Integer.parseInt(bf2.readLine());
                System.out.println("获得2号信息");

                prout1.println("ready");
                //输出信息给2号玩家
                prout1.println(name2);
                prout1.println(id2);
                prout1.flush();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            PrintWriter pw1= null;
            PrintWriter pw2=null;
            try {
                pw1 = new PrintWriter(s1.getOutputStream());
                pw2=new PrintWriter(s2.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //与客户端1交互动
            pw1.println("you");
            pw1.flush();
            //与客户端2互动
            pw2.println("he");
            pw2.flush();

            System.out.println("开始游戏");

            while (true){
                //读取server端命令
                String commend1="";
                String commend2="";
                try {
                    if(bf1.ready()) {
                        commend1 = bf1.readLine();
                    }
                    if(bf2.ready()) {
                        commend2 = bf2.readLine();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //对1号玩家
                if(commend1.equals("next")){
                    String goal="";
                    String hurt1="";
                    try {
                        goal=bf1.readLine();
                        hurt1=bf1.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //发送进攻结束指令
                    pw2.println("stop");
                    pw2.println(goal);
                    pw2.flush();
                    //获得回馈
                    String hurt2="";
                    try {
                        hurt2=bf2.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    //结算此回合
                    int conclusion=(Integer.parseInt(hurt1)+Integer.parseInt(hurt2))/2;
                    pw1.println("conclution1");
                    pw2.println("conclution0");
                    pw1.println(conclusion);
                    pw2.println(conclusion);
                    pw1.flush();
                    pw2.flush();

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //开启下一个回合
                    pw2.println("you");
                    pw1.println("he");
                    pw2.flush();
                    pw1.flush();

                }
                //对2号玩家
                if(commend2.equals("next")){
                    String goal="";
                    String hurt2="";
                    try {
                        goal=bf2.readLine();
                        hurt2=bf2.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //发送进攻结束指令
                    pw1.println("stop");
                    pw1.println(goal);
                    pw1.flush();
                    //获得回馈
                    String hurt1="";
                    try {
                        hurt1=bf1.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    //结算此回合
                    int conclusion=(Integer.parseInt(hurt1)+Integer.parseInt(hurt2))/2;
                    pw2.println("conclution1");
                    pw1.println("conclution0");
                    pw2.println(conclusion);
                    pw1.println(conclusion);
                    pw2.flush();
                    pw1.flush();

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //开启下一个回合
                    pw1.println("you");
                    pw2.println("he");
                    pw1.flush();
                    pw2.flush();

                }
            }




        }
    }



}
