package util;

public class character {
    private String name;
    private int hp;
    private int id;

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
            return 9;
        }
        else if (goal>=0&&goal<20){
            return 8;
        }
        else if (goal>=20&&goal<45){
            return 7;
        }
        else if (goal>=45&&goal<80){
            return 6;
        }
        else if (goal>=80&&goal<120){
            return 5;
        }
        else if(goal>=120&&goal<170){
            return 4;
        }
        else if(goal>=170&&goal<200){
            return 3;
        }
        else if(goal>=200&&goal<230){
            return 2;
        }
        else {
            return 1;
        }
    }


    public int skill(int id,int hurt){
        switch (id){
            case 0:hurt=hurt+2;
                   return hurt;
            case 1:hurt=hurt*3;
                   return hurt;
        }
        return 0;
    }


}
