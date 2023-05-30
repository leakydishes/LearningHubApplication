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
import android.widget.TextView;
import android.widget.Toast;

import com.example.learninghubapplication.R;
import com.example.learninghubapplication.data.TextModelDatabase;

public class TextDetails extends AppCompatActivity {
    //variables
    TextView display_text, display_name;
    Button btnReturnMenu, btnDelete, btnEmail;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_details);

        //get data from previous input
        Intent intent = getIntent();
        String userName = intent.getStringExtra("User");
        Log.e("result", "--->>" + userName);

        // set recycler view
        display_name = findViewById(R.id.display_name);
        display_text = findViewById(R.id.display_text);
        btnDelete = findViewById(R.id.btnDelete);
        btnReturnMenu = findViewById(R.id.btnReturnMenu);
        btnEmail = findViewById(R.id.btnEmail);

        String name = getIntent().getStringExtra("name");
        String text = getIntent().getStringExtra("textResult");

        display_name.setText(name);
        display_text.setText(text);

        //listener return to menu
        btnReturnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(TextDetails.this,
                        HomeActivity.class);
                intentHome.putExtra("User", userName);
                startActivity(intentHome);
            }
        });

        //listener delete item
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Button Pressed", "Remove");
                TextModelDatabase.getDatabase(getApplicationContext()).textModelDao()
                        .deleteByUserName(name);
                //create object
                String msg = "Deleted Item";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                // go back to main activity
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //listener send item email
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextDetails.this, SendCommunication.class);
                intent.putExtra("name", name);
                intent.putExtra("textResult", text);
                startActivity(intent);
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
                Intent intentHome = new Intent(TextDetails.this,
                        HomeActivity.class);
                intentHome.putExtra("User", userName);
                startActivity(intentHome);
                return true;
            case R.id.convert:
                Toast.makeText(this, "Convert selected", Toast.LENGTH_SHORT).show();
                Intent intentConvert = new Intent(TextDetails.this,
                        ReadText.class);
                intentConvert.putExtra("User", userName);
                startActivity(intentConvert);
                return true;
            case R.id.textList:
                Toast.makeText(this, "Text list selected", Toast.LENGTH_SHORT).show();
                // open new activity My Orders
                startActivity(new Intent(TextDetails.this, TextList.class));
                Intent intentList = new Intent(TextDetails.this,
                        TextList.class);
                intentList.putExtra("User", userName);
                startActivity(intentList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}