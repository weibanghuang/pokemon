package com.example.fifthteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = new String[]{"Rock","Paper","Scissor"};
        String[] items1 = new String[]{"1","2","3","4","5"};

        Spinner dropdown1 = findViewById(R.id.type1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown1.setAdapter(adapter1);

        Spinner dropdown2 = findViewById(R.id.type2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown2.setAdapter(adapter2);

        Spinner dropdown3 = findViewById(R.id.type3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown3.setAdapter(adapter3);

        Spinner dropdown4 = findViewById(R.id.type4);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown4.setAdapter(adapter4);

        Spinner dropdown5 = findViewById(R.id.type5);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown5.setAdapter(adapter5);

        Spinner order1 = findViewById(R.id.order1);
        ArrayAdapter<String> order_drop1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        order1.setAdapter(order_drop1);

        Spinner order2 = findViewById(R.id.order2);
        ArrayAdapter<String> order_drop2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        order2.setAdapter(order_drop2);

        Spinner order3 = findViewById(R.id.order3);
        ArrayAdapter<String> order_drop3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        order3.setAdapter(order_drop3);

        Spinner order4 = findViewById(R.id.order4);
        ArrayAdapter<String> order_drop4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        order4.setAdapter(order_drop4);

        Spinner order5 = findViewById(R.id.order5);
        ArrayAdapter<String> order_drop5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        order5.setAdapter(order_drop5);

        EditText roomName = findViewById(R.id.room);

        EditText userName = findViewById(R.id.name);

        TextView createroom = findViewById(R.id.createroom);

        TextView joinroom = findViewById(R.id.joinroom);

        TextView health1 = findViewById(R.id.health1);
        TextView health2 = findViewById(R.id.health2);
        TextView health3 = findViewById(R.id.health3);
        TextView health4 = findViewById(R.id.health4);
        TextView health5 = findViewById(R.id.health5);
        TextView speed1 = findViewById(R.id.speed1);
        TextView speed2 = findViewById(R.id.speed2);
        TextView speed3 = findViewById(R.id.speed3);
        TextView speed4 = findViewById(R.id.speed4);
        TextView speed5 = findViewById(R.id.speed5);
        TextView attack1 = findViewById(R.id.attack1);
        TextView attack2 = findViewById(R.id.attack2);
        TextView attack3 = findViewById(R.id.attack3);
        TextView attack4 = findViewById(R.id.attack4);
        TextView attack5 = findViewById(R.id.attack5);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        joinroom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String roomname_str = roomName.getText().toString().toUpperCase();
                String username_str = userName.getText().toString().toUpperCase();
                DatabaseReference room_name = database.getReference().child(roomname_str);
                room_name.onDisconnect().removeValue();

                Monster monster1 = new Monster(
                        getDouble(health1),
                        getDouble(speed1),
                        getDouble(attack1),
                        dropdown1.getSelectedItem().toString(),
                        order1.getSelectedItem().toString());

                Monster monster2 = new Monster(
                        getDouble(health2),
                        getDouble(speed2),
                        getDouble(attack2),
                        dropdown2.getSelectedItem().toString(),
                        order2.getSelectedItem().toString());

                Monster monster3 = new Monster(
                        getDouble(health3),
                        getDouble(speed3),
                        getDouble(attack3),
                        dropdown3.getSelectedItem().toString(),
                        order3.getSelectedItem().toString());

                Monster monster4 = new Monster(
                        getDouble(health4),
                        getDouble(speed4),
                        getDouble(attack4),
                        dropdown4.getSelectedItem().toString(),
                        order4.getSelectedItem().toString());

                Monster monster5 = new Monster(
                        getDouble(health5),
                        getDouble(speed5),
                        getDouble(attack5),
                        dropdown5.getSelectedItem().toString(),
                        order5.getSelectedItem().toString());
                Person person = new Person(
                        username_str,
                        monster1,
                        monster2,
                        monster3,
                        monster4,
                        monster5);

                Map<String, Object> person_map = new HashMap<String,Object>();
                person_map.put(username_str, person);
                room_name.updateChildren(person_map);
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                intent.putExtra("roomname_str", roomname_str);
                intent.putExtra("username_str", username_str);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

//        Log.d(".","Rock Health:" + rock.getHealth());

    }

    public static Double getDouble(TextView textview){
        return Double.parseDouble(textview.getText().toString());
    }

}