package com.example.fifthteen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class Person {

    private String name;
    private ArrayList<Monster> team = new ArrayList<>();

//    public Person(HashMap<String, String> map){
//        this.name = map.get("name");
//    }
    public Person(){

    }

    public Person(String name, Monster monster1, Monster monster2, Monster monster3, Monster monster4, Monster monster5){
        this.name = name;
        this.team.add(monster1);
        this.team.add(monster2);
        this.team.add(monster3);
        this.team.add(monster4);
        this.team.add(monster5);
    }

    public String getName(){ return this.name; }

    public ArrayList<Monster> getTeam(){
        return this.team;
    }

}
