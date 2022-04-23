package com.example.fifthteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import pl.droidsonroids.gif.GifImageView;

public class RoomActivity extends AppCompatActivity {

    private ArrayList<Person> person_list = new ArrayList<>();
    ArrayList<Monster> team1 = new ArrayList<>();
    ArrayList<Monster> team2 = new ArrayList<>();
    private boolean reset;
    private int goes_first;
    private boolean button_counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        String roomname_str = getIntent().getStringExtra("roomname_str");
        String username_str = getIntent().getStringExtra("username_str");

        TextView user1 = findViewById(R.id.user1);
        TextView user2= findViewById(R.id.user2);
        TextView room= findViewById(R.id.room);

        GifImageView user1_monster = findViewById(R.id.user1_monster);
        GifImageView user2_monster = findViewById(R.id.user2_monster);
        GifImageView attack = findViewById(R.id.attack);

        TextView description = findViewById(R.id.description);

        TextView health1 = findViewById(R.id.health1);
        TextView speed1 = findViewById(R.id.speed1);
        TextView attack1 = findViewById(R.id.attack1);

        TextView health2 = findViewById(R.id.health2);
        TextView speed2 = findViewById(R.id.speed2);
        TextView attack2 = findViewById(R.id.attack2);

        TextView go_back = findViewById(R.id.go_back);
        TextView attack_button = findViewById(R.id.attack_button);
        TextView winner = findViewById(R.id.winner);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference room_name = database.getReference().child(roomname_str);

        room_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                person_list.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String userId = ds.getKey();
                    DatabaseReference user_name = room_name.child(userId);
                    user_name.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Person person1 = snapshot.getValue(Person.class);
                            person_list.add(person1);
                            //check if two people in room
                            if (person_list.size()==2){
                                user1.setText(person_list.get(0).getName());
                                user2.setText(person_list.get(1).getName());
                                room.setText(roomname_str);
                                winner.setText("Winner:");
                                go_back.setText("Go Back");
                                attack_button.setText("Attack");
                                //set up team 1 and team 2 by monster rank
                                for (int i=1; i<6; i++){
                                    for (int j=0; j<5; j++){
                                        String order_str1 = (String) person_list.get(0).getTeam().get(j).getOrder();
                                        String order_str2 = (String) person_list.get(1).getTeam().get(j).getOrder();
                                        String order_str1a = String.valueOf(i);
                                        String order_str2a = String.valueOf(i);

                                        if (order_str1a.equals(order_str1)){
                                            team1.add(person_list.get(0).getTeam().get(j));
                                        }
                                        if (order_str2a.equals(order_str2)){
                                            team2.add(person_list.get(1).getTeam().get(j));
                                        }
                                    }
                                }
                                //initialize room with first rank monster and stats
                                if("Scissor".equals(team1.get(0).getType())){
                                    user1_monster.setBackgroundResource(R.drawable.scissor_user1);
                                }
                                if("Scissor".equals(team2.get(0).getType())){
                                    user2_monster.setBackgroundResource(R.drawable.scissor_user2);
                                }
                                if("Paper".equals(team1.get(0).getType())){
                                    user1_monster.setBackgroundResource(R.drawable.paper_user1);
                                }
                                if("Paper".equals(team2.get(0).getType())){
                                    user2_monster.setBackgroundResource(R.drawable.paper_user2);
                                }
                                if("Rock".equals(team1.get(0).getType())){
                                    user1_monster.setBackgroundResource(R.drawable.rock_user1);
                                }
                                if("Rock".equals(team2.get(0).getType())){
                                    user2_monster.setBackgroundResource(R.drawable.rock_user2);
                                }
                                health1.setText("Health: "+team1.get(0).getHealth());
                                health2.setText("Health: "+team2.get(0).getHealth());
                                speed1.setText("Speed: "+team1.get(0).getSpeed());
                                speed2.setText("Speed: "+team2.get(0).getSpeed());
                                attack1.setText("Attack: "+team1.get(0).getAttack());
                                attack2.setText("Attack: "+team2.get(0).getAttack());

                                description.setText(person_list.get(0).getName()+" picks "+team1.get(0).getType()+".\n"+person_list.get(1).getName()+" picks "+team2.get(0).getType()+".");

                                if(team1.get(0).getSpeed()>team2.get(0).getSpeed()){
                                    goes_first = 0;
                                }else if (team1.get(0).getSpeed()>team2.get(0).getSpeed()){
                                    goes_first = 1;
                                }else{
                                    if(Math.random() < 0.5) {
                                        goes_first = 0;
                                    }else{
                                        goes_first = 1;
                                    }
                                }
                                reset = false;
                                button_counter = false;
                                //BUTTON CLICK
                                attack_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.d("Before ","---------------");
                                        Log.d("size1",team1.size()+"");
                                        Log.d("size2",team2.size()+"");
                                        Log.d("reset ",reset+"");
                                        Log.d("button ",button_counter+"");
                                        Log.d("goes first ",goes_first+"");

                                        if (button_counter==false){
                                            if(team1.size()>0&&team2.size()>0){

                                                if (goes_first==0){
                                                    team1.get(0).attack(team2.get(0));
                                                    if (team1.get(0).getActivate_power()==true){
                                                        description.setText(
                                                                person_list.get(0).getName()+"'s "+team1.get(0).getType()+"\n activates its power! \n"+
                                                                person_list.get(0).getName()+"'s "+team1.get(0).getType()+"\n attacks \n"+person_list.get(1).getName()+"'s "+team2.get(0).getType()+".");
                                                    }else{
                                                        description.setText(person_list.get(0).getName()+"'s "+team1.get(0).getType()+"\n attacks \n"+person_list.get(1).getName()+"'s "+team2.get(0).getType()+".");
                                                    }
                                                    if("Scissor".equals(team1.get(0).getType())){
                                                        attack.setBackgroundResource(R.drawable.scissor_attack_user1);
                                                    }
                                                    if("Paper".equals(team1.get(0).getType())){
                                                        attack.setBackgroundResource(R.drawable.paper_attack_user1);
                                                    }
                                                    if("Rock".equals(team1.get(0).getType())){
                                                        attack.setBackgroundResource(R.drawable.rock_attack_user1);
                                                    }
                                                    if ("Rock".equals(team1.get(0).getType())&&team1.get(0).getActivate_power()==true){
                                                        goes_first=0;
                                                    }else{
                                                        goes_first=1;
                                                    }

                                                }else{
                                                    team2.get(0).attack(team1.get(0));
                                                    if (team2.get(0).getActivate_power()==true){
                                                        description.setText(
                                                                person_list.get(1).getName()+"'s "+team2.get(0).getType()+"\n activates its power! \n"+
                                                                person_list.get(1).getName()+"'s "+team2.get(0).getType()+"\n attacks \n"+person_list.get(0).getName()+"'s "+team1.get(0).getType()+".");

                                                    }else{
                                                        description.setText(person_list.get(1).getName()+"'s "+team2.get(0).getType()+"\n attacks \n"+person_list.get(0).getName()+"'s "+team1.get(0).getType()+".");
                                                    }
                                                    if("Scissor".equals(team2.get(0).getType())){
                                                        attack.setBackgroundResource(R.drawable.scissor_attack_user2);
                                                    }
                                                    if("Paper".equals(team2.get(0).getType())){
                                                        attack.setBackgroundResource(R.drawable.paper_attack_user2);
                                                    }
                                                    if("Rock".equals(team2.get(0).getType())){
                                                        attack.setBackgroundResource(R.drawable.rock_attack_user2);
                                                    }
                                                    if ("Rock".equals(team2.get(0).getType())&&team2.get(0).getActivate_power()==true){
                                                        goes_first=1;
                                                    }else{
                                                        goes_first=0;
                                                    }
                                                }

                                                Log.d("team1 health",team1.get(0).getHealth()+"");
                                                Log.d("team2 health",team2.get(0).getHealth()+"");
                                                if (team1.get(0).getHealth()<=0){
                                                    Log.d(".","team1 get health");
                                                    description.setText(person_list.get(1).getName()+"'s "+team2.get(0).getType()+"\n knocks out \n"+person_list.get(0).getName()+"'s "+team1.get(0).getType()+".");
                                                    health1.setText("Health: 0");
                                                    team1.remove(0);
                                                    reset = true;
                                                }else{
                                                    health1.setText("Health: "+team1.get(0).getHealth());
                                                    reset = false;
                                                }
                                                if (team2.get(0).getHealth()<=0){
                                                    Log.d("","team2 get health");
                                                    description.setText(person_list.get(0).getName()+"'s "+team1.get(0).getType()+"\n knocks out \n"+person_list.get(1).getName()+"'s "+team2.get(0).getType()+".");
                                                    health2.setText("Health: 0");
                                                    team2.remove(0);
                                                    reset= true;
                                                }else{
                                                    health2.setText("Health: "+team2.get(0).getHealth());
                                                    reset = false;
                                                }
                                                if (reset == true &&team1.size()>0 && team2.size()>0){
                                                    if(team1.get(0).getSpeed()>team2.get(0).getSpeed()){
                                                        goes_first = 0;
                                                    }else if (team1.get(0).getSpeed()>team2.get(0).getSpeed()){
                                                        goes_first = 1;
                                                    }else{
                                                        if(Math.random() < 0.5) {
                                                            goes_first = 0;
                                                        }else{
                                                            goes_first = 1;
                                                        }
                                                    }
                                                }
                                            }else{
                                                if(team1.size()>0){
                                                    winner.setText("Winner: \n"+person_list.get(0).getName());
                                                }else{
                                                    winner.setText("Winner: \n"+person_list.get(1).getName());
                                                }
                                            }
                                            button_counter = true;
                                        }else{
                                            if(team1.size()>0&&team2.size()>0&&reset==true){
                                                Log.d("asdasdsad","asdasdasdas");


                                                if("Scissor".equals(team1.get(0).getType())){
                                                    user1_monster.setBackgroundResource(R.drawable.scissor_user1);
                                                }
                                                if("Scissor".equals(team2.get(0).getType())){
                                                    user2_monster.setBackgroundResource(R.drawable.scissor_user2);
                                                }
                                                if("Paper".equals(team1.get(0).getType())){
                                                    user1_monster.setBackgroundResource(R.drawable.paper_user1);
                                                }
                                                if("Paper".equals(team2.get(0).getType())){
                                                    user2_monster.setBackgroundResource(R.drawable.paper_user2);
                                                }
                                                if("Rock".equals(team1.get(0).getType())){
                                                    user1_monster.setBackgroundResource(R.drawable.rock_user1);
                                                }
                                                if("Rock".equals(team2.get(0).getType())){
                                                    user2_monster.setBackgroundResource(R.drawable.rock_user2);
                                                }
                                                health1.setText("Health: "+team1.get(0).getHealth());
                                                health2.setText("Health: "+team2.get(0).getHealth());
                                                speed1.setText("Speed: "+team1.get(0).getSpeed());
                                                speed2.setText("Speed: "+team2.get(0).getSpeed());
                                                attack1.setText("Attack: "+team1.get(0).getAttack());
                                                attack2.setText("Attack: "+team2.get(0).getAttack());

                                                description.setText(person_list.get(0).getName()+" picks "+team1.get(0).getType()+".\n"+person_list.get(1).getName()+" picks "+team2.get(0).getType()+".");
                                                reset = false;
                                            }
                                            description.setText(person_list.get(0).getName()+" has "+team1.size()+" monsters.\n"+person_list.get(1).getName()+" has "+team2.size()+" monsters.");
                                            button_counter = false;

                                        }
                                        Log.d("After ","---------------");
                                        Log.d("size1",team1.size()+"");
                                        Log.d("size2",team2.size()+"");
                                        Log.d("reset ",reset+"");
                                        Log.d("button ",button_counter+"");

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(".", "user_name_node issues");
                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(".", "room_name_node issues"); //Don't ignore errors!
            }
        });


    }

}