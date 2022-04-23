package com.example.fifthteen;
import java.util.Random;

public class Monster {
    private double health;
    private double speed;
    private double attack;
    private String type;
    private boolean activate_power = false;

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private String weakness;
    private String order;


    public Monster(){

    }
    public Monster(double health, double speed, double attack, String type, String order){
        this.health = health;
        this.speed = speed;
        this.attack = attack;
        this.type = type;
        this.order = order;
        if (type == "Paper"){
            this.weakness = "Scissor";
        }else if (type == "Rock"){
            this.weakness = "Paper";
        }else{
            this.weakness = "Rock";
        }

    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth(){
        return this.health;
    }

    public double getSpeed(){
        return this.speed;
    }

    public double getAttack(){
        return this.attack;
    }

    public String getType(){
        return this.type;
    }

    public String getWeakness(){
        return this.weakness;
    }

    public String getOrder() { return order; }

    public boolean getActivate_power() { return this.activate_power; }

    public void attack(Monster monster2){
        if (this.getWeakness().equals(monster2.getType())){
            if("Scissor".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    monster2.setHealth(monster2.getHealth()-(0.5*1.5*this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(0.5*this.getAttack()));
                    activate_power=false;
                }
            }
            if("Paper".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    Double health_absorb = Math.min(monster2.getHealth(),0.5*this.getAttack());
                    this.setHealth(this.getHealth()+health_absorb);
                    monster2.setHealth(monster2.getHealth()-(0.5*this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(0.5*this.getAttack()));
                    activate_power=false;
                }
            }
            if("Rock".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    monster2.setHealth(monster2.getHealth()-(0.5*this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(0.5*this.getAttack()));
                    activate_power=false;
                }
            }
        }else if (this.getType().equals(monster2.getWeakness())){
            if("Scissor".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    monster2.setHealth(monster2.getHealth()-(2*1.5*this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(2*this.getAttack()));
                    activate_power=false;
                }
            }
            if("Paper".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    Double health_absorb = Math.min(monster2.getHealth(),2*this.getAttack());
                    this.setHealth(this.getHealth()+health_absorb);
                    monster2.setHealth(monster2.getHealth()-(2*this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(2*this.getAttack()));
                    activate_power=false;
                }
            }
            if("Rock".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    monster2.setHealth(monster2.getHealth()-(2*this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(2*this.getAttack()));
                    activate_power=false;
                }
            }
        }else{
            if("Scissor".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    monster2.setHealth(monster2.getHealth()-(1.5*this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(this.getAttack()));
                    activate_power=false;
                }
            }
            if("Paper".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    Double health_absorb = Math.min(monster2.getHealth(),this.getAttack());
                    this.setHealth(this.getHealth()+health_absorb);
                    monster2.setHealth(monster2.getHealth()-(this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(this.getAttack()));
                    activate_power=false;
                }
            }
            if("Rock".equals(this.getType())){
                Random random = new Random();
                int i = random.nextInt(10 - 1 + 1) + 1;
                if(i==10){
                    monster2.setHealth(monster2.getHealth()-(this.getAttack()));
                    activate_power=true;
                }else{
                    monster2.setHealth(monster2.getHealth()-(this.getAttack()));
                    activate_power=false;
                }
            }
        }

    }

}
