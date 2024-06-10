package com.mastercoding.zoomwave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    TextInputEditText meetingIdInput , nameInput;
    MaterialButton joinBtn , createBtn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for share the meeting id
        sharedPreferences = getSharedPreferences("name_pref" , MODE_PRIVATE);

        meetingIdInput = findViewById(R.id.meeting_id_input);
        nameInput = findViewById(R.id.name_input);
        joinBtn = findViewById(R.id.join_btn);
        createBtn = findViewById(R.id.create_btn);

        nameInput.setText(sharedPreferences.getString("name" , ""));
        joinBtn.setOnClickListener((v)->{
            // for the meeting id
            String meetingId = meetingIdInput.getText().toString();
            if(meetingId.length() != 5){
                meetingIdInput.setError("Invalid Meeting Id");
                meetingIdInput.requestFocus();
                return;
            }
            // for the name
            String name = nameInput.getText().toString();
            if(name.isEmpty()){
                nameInput.setError("Name is required to join the meeting");
                nameInput.requestFocus();
                return ;
            }
            // for join meeting
            startMeeting(meetingId, name);
        });

        // for create meeting
        createBtn.setOnClickListener(v->{
            String name = nameInput.getText().toString();
            if(name.isEmpty()){
                nameInput.setError("Name is required to create the meeting");
                nameInput.requestFocus();
                return ;
            }
            startMeeting(getRandomMeetingID() , name);
        });

    }

    // for start meeting
    void startMeeting(String meetingID , String name){
        // save the name
        sharedPreferences.edit().putString("name" , name).apply();
        // uuid is generate the random number
        String userID = UUID.randomUUID().toString();

        // Intent to pass the variable //
        Intent intent = new Intent(MainActivity.this , conferenceActivity.class);
        intent.putExtra("meeting_ID" , meetingID);
        intent.putExtra("name" , name);
        intent.putExtra("user_id" , userID);
        startActivity(intent);
    }
     String getRandomMeetingID(){
        StringBuilder id = new StringBuilder();
        while (id.length() != 5){
            int random = new Random().nextInt(6);
            id.append(random);
        }
        return id.toString();
     }

}