package com.example.learninghubapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.example.learninghubapplication.R;
import com.example.learninghubapplication.data.UserDAO;
import com.example.learninghubapplication.data.UserDataBase;
import com.example.learninghubapplication.model.User;

public class MainActivity extends AppCompatActivity {

    // variables for user and database
    UserDAO userDb; //declare user class
    UserDataBase userDataBase; //declare database
    Button btn_sign_up, btn_sign_in;
    EditText username_input, password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get image from sign up
        if(getIntent().hasExtra("byteArray")) {
            ImageView imv= new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),
                    0, getIntent().getByteArrayExtra("byteArray").length);
            imv.setImageBitmap(bitmap);
        }

        // set to screen
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);

        // user database build with class
        userDataBase = Room.databaseBuilder(this, UserDataBase.class, "User")
                .allowMainThreadQueries()
                .build();

        // set db user data database
        userDb = userDataBase.getUserDao();

        // click button sign in
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to string input
                String userName = username_input.getText().toString().trim();
                String password = password_input.getText().toString().trim();

                // get data from user class
                User user = userDb.getUser(userName, password);

                // check user exists
                if (user != null) {
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    i.putExtra("User", userName);
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "Unknown User, try again or Sign up", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // click button sign up
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to sign up/ register page
                Intent intent = new Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}