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
import android.widget.TextView;
import android.widget.Toast;

import com.example.learninghubapplication.R;
import com.example.learninghubapplication.databinding.ActivitySendCommunicationBinding;

public class SendCommunication extends AppCompatActivity {
    //variables
    Button btnSend, btnReturnMenu;
    TextView inputMessage;
    EditText inputEmailAddress, inputSubject;
    Boolean checkEmailSent = false;
    String userName;

    //binding
    ActivitySendCommunicationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_communication);

        //get data from previous input
        Intent intent = getIntent();
        String result = intent.getStringExtra("textResult");
        String name = intent.getStringExtra("name");
        Log.e("result", "--->>" + result);

        //set binding
        binding = ActivitySendCommunicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set to screen
        btnSend = findViewById(R.id.btnSend);
        inputMessage = findViewById(R.id.inputMessage);
        inputEmailAddress = findViewById(R.id.inputEmailAddress);
        inputSubject = findViewById(R.id.inputSubject);
        btnReturnMenu = findViewById(R.id.btnReturnMenu);

        //get text result from ReadText activity
        inputMessage.setText(result);
        String subject = "ML Image to text: " + name;
        inputSubject.setText(subject);

        //boolean to check email sent before progressing screens
        checkEmailSent = false;

        //click send button
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user data
                if (!inputEmailAddress.getText().toString().isEmpty()
                        && !inputSubject.getText().toString().isEmpty()
                        && !inputMessage.getText().toString().isEmpty()) {

                    //get text
                    String inputEmailAddress = binding.inputEmailAddress.getText().toString();
                    String inputSubject = binding.inputSubject.getText().toString();
                    String inputMessage = binding.inputMessage.getText().toString();

                    //set intent to send email
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{inputEmailAddress});
                    intent.putExtra(Intent.EXTRA_SUBJECT, inputSubject);
                    intent.putExtra(Intent.EXTRA_TEXT, inputMessage);

                    //find applications to send mail
                    intent.setType("message/rfc822");

                    //check application is available for mail
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        //start mail application
                        checkEmailSent = true;
                        startActivity(intent);
                    } else {
                        Toast.makeText(SendCommunication.this, "No Mail app installed",
                                Toast.LENGTH_SHORT).show();
                        Log.i("mail to", "Unable to send email");
                    }
                } else {
                    Toast.makeText(SendCommunication.this, "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                    Log.i("mail to", "Missing fields");
                }
            }
        });

        //click return to menu
        btnReturnMenu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmailSent = true){
                    checkEmailSent = false; //reset to false

                    //return to menu after email sent
                    Intent intentHome = new Intent(SendCommunication.this,
                            HomeActivity.class);
                    intentHome.putExtra("User", userName);
                    startActivity(intentHome);
                } else {
                    Toast.makeText(SendCommunication.this, "Send Email First",
                            Toast.LENGTH_SHORT).show();
                }
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
                Intent intentHome = new Intent(SendCommunication.this,
                        HomeActivity.class);
                intentHome.putExtra("User", userName);
                startActivity(intentHome);
                return true;
            case R.id.convert:
                Toast.makeText(this, "Convert selected", Toast.LENGTH_SHORT).show();
                Intent intentConvert = new Intent(SendCommunication.this,
                        ReadText.class);
                intentConvert.putExtra("User", userName);
                startActivity(intentConvert);
                return true;
            case R.id.textList:
                Toast.makeText(this, "Text list selected", Toast.LENGTH_SHORT).show();
                Intent intentTextList = new Intent(SendCommunication.this,
                        TextList.class);
                intentTextList.putExtra("User", userName);
                startActivity(intentTextList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}