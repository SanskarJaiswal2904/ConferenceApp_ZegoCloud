package com.example.easyconference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextInputEditText meetingIdInput, nameInput;
    MaterialButton joinBtn,createBtn;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("name_pref",MODE_PRIVATE);

        meetingIdInput = findViewById(R.id.meetId);
        nameInput = findViewById(R.id.nameId);
        joinBtn = findViewById(R.id.joinBtn);
        createBtn = findViewById(R.id.createBtn);

        nameInput.setText(sharedPreferences.getString("name",""));

        joinBtn.setOnClickListener(view -> {
            String meetingId = meetingIdInput.getText().toString();
            if (meetingId.length()!=10){
                meetingIdInput.setError("Invalid Meeting ID");
                meetingIdInput.requestFocus();
                return;
            }

            String name = nameInput.getText().toString();
            if (name.isEmpty()){
                nameInput.setError("Name is required to join the Meeting");
                nameInput.requestFocus();
                return;
            }
            startMeeting(meetingId, name);
        });
        createBtn.setOnClickListener(view -> {
            String name = nameInput.getText().toString();
            if (name.isEmpty()){
                nameInput.setError("Name is required to join the Meeting");
                nameInput.requestFocus();
                return;
            }
            startMeeting(getRandomMeetingID(),name);
        });
    }
    void startMeeting(String meetingId, String name){
        sharedPreferences.edit().putString("name",name).apply();
        String userId = UUID.randomUUID().toString();

        Intent intent = new Intent(MainActivity.this, ConferenceActivity.class);
        intent.putExtra("meeting_ID",meetingId);
        intent.putExtra("name",name);
        intent.putExtra("user_ID",userId);
        startActivity(intent);
    }

    private String getRandomMeetingID(){
        StringBuilder id = new StringBuilder();
        while (id.length()!=10){
            int random = new Random().nextInt(10);// 0 to 9
            id.append(random);
        }
        return id.toString();
    }
}