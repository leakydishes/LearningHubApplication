package com.example.learninghubapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learninghubapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.smartreply.SmartReplyGenerator;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import com.google.mlkit.nl.smartreply.TextMessage;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    //variables
    Button btnImageToText, btnLibrary;
    ImageView user_image;
    TextView user_name;

    //smart reply variables
    Button btnSendMessage, btn_sign_out;
    TextView textReply;
    EditText etSendMessage;
    String userName;

    //list to store conversation history object for ML
    List<TextMessage> conversation; //store conversation for ML
    String UserUID="123456"; //user identifier
    SmartReplyGenerator smartReplyGenerator; //class for ML

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //get data from main activity user
        Intent intent = getIntent();
        String userName = intent.getStringExtra("User");
        Log.e("User", "--->>" + userName);

        //set to screen text and buttons
        btnImageToText = findViewById(R.id.btnImageToText);
        btnLibrary = findViewById(R.id.btnLibrary);
        user_image = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);

        // set to screen smart reply
        btnSendMessage = findViewById(R.id.btnSendMessage);
        textReply = findViewById(R.id.textReply);
        etSendMessage = findViewById(R.id.etSendMessage);
        btn_sign_out = findViewById(R.id.btn_sign_out);

        //get text result from ReadText activity
        String user = "Welcome, " + userName + "!";
        user_name.setText(user);
        Glide.with(HomeActivity.this).load(R.drawable.profile).into(user_image);

        //create a conversation history object
        conversation = new ArrayList<>();

        //generate client for smart reply
        smartReplyGenerator = com.google.mlkit.nl.smartreply.SmartReply.getClient();

        //smart reply listener
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etSendMessage.getText().toString().trim();
                //input message content, timestamp and users unique identifier
                conversation.add(TextMessage.createForRemoteUser(message,
                        System.currentTimeMillis(), "123456"));
                //get message replies
                smartReplyGenerator.suggestReplies(conversation).
                        addOnSuccessListener(new OnSuccessListener<SmartReplySuggestionResult>() {
                    @Override
                    public void onSuccess(SmartReplySuggestionResult smartReplySuggestionResult) {
                        if(smartReplySuggestionResult.getStatus()==SmartReplySuggestionResult
                                .STATUS_NOT_SUPPORTED_LANGUAGE){
                            // empty result or language not supported
                            textReply.setText("No Reply");
                        }else if(smartReplySuggestionResult.getStatus()==SmartReplySuggestionResult
                                .STATUS_SUCCESS){
                            //successful smart reply
                            String reply="";
                            for(SmartReplySuggestion suggestion:smartReplySuggestionResult
                                    .getSuggestions()){
                                //get suggestions
                                reply = reply + suggestion.getText() + "\n";
                            }
                            //set output to reply text view
                            textReply.setText(reply);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        textReply.setText("Error : " + e.getMessage());
                    }
                });

            }
        });

        //click Image to text button
        btnImageToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReadText = new Intent(HomeActivity.this, ReadText.class);
                startActivity(intentReadText);
                finish();
            }
        });

        //click Image to text button
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignOut = new Intent(HomeActivity.this,
                        MainActivity.class);
                startActivity(intentSignOut);
                finish();
            }
        });

        //click Button Library
        btnLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTextList = new Intent(HomeActivity.this,
                        TextList.class);
                startActivity(intentTextList);
                finish();
            }
        });
    }
        // create menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.bottom_navigation_menu, menu);
            return true;
        }
    // cases for menus
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
                //current screen
                return true;
            case R.id.convert:
                Toast.makeText(this, "Convert selected", Toast.LENGTH_SHORT).show();
                Intent intentConvert = new Intent(HomeActivity.this, ReadText.class);
                intentConvert.putExtra("User", userName);
                startActivity(intentConvert);
                return true;
            case R.id.textList:
                Toast.makeText(this, "Text list selected", Toast.LENGTH_SHORT).show();
                // open new activity My Orders
                Intent intentList = new Intent(HomeActivity.this, TextList.class);
                intentList.putExtra("User", userName);
                startActivity(intentList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}