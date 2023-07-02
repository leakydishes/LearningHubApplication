package com.example.learninghubapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.learninghubapplication.R;
import com.example.learninghubapplication.data.TextModelDatabase;
import com.example.learninghubapplication.model.TextModel;
import com.example.learninghubapplication.viewModels.TextModelAdapter;

import java.util.ArrayList;
import java.util.List;

public class TextList extends AppCompatActivity implements TextModelAdapter.ItemClickListener{
    // variables
    RecyclerView recyclerView;
    Button btnReturnMenu;
    private List<TextModel> textModel;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_list);

        //get data from previous input
        userName = getIntent().getStringExtra("User");
        Log.e("text list result", "--->>" + userName);

        // set recycler view
        recyclerView = findViewById(R.id.recyclerView);
        btnReturnMenu = findViewById(R.id.btnReturnMenu);

        // get data
        getData();

        //listener
        btnReturnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(TextList.this,
                        HomeActivity.class);
                intentHome.putExtra("User", userName);
                startActivity(intentHome);
            }
        });
    }


    //get data for recycler view
    private void getData() {
        // set view model
        textModel = new ArrayList<>();
        textModel = TextModelDatabase.getDatabase(getApplicationContext())
                .textModelDao()
                .getTextList();

        // Lookup the recyclerview in activity layout
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerView);

        // Create adapter passing in the sample user data
        TextModelAdapter adapter = new TextModelAdapter(textModel, this, this);

        // Attach the adapter to the recyclerview to populate items
        recyclerview.setAdapter(adapter);

        // Set layout manager to position the items
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(TextModel textModel) {
        //start new activity
        Intent intent = new Intent(TextList.this, TextDetails.class);

        //get data clicked on
        String newName = textModel.getName();
        String newText = textModel.getText();

        //put extra intent
        intent.putExtra("User", userName);
        intent.putExtra("name", newName);
        intent.putExtra("textResult", newText);
        startActivity(intent);
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
                Intent intentHome = new Intent(TextList.this,
                        HomeActivity.class);
                intentHome.putExtra("User", userName);
                startActivity(intentHome);
                return true;
            case R.id.convert:
                Toast.makeText(this, "Convert selected", Toast.LENGTH_SHORT).show();
                Intent intentConvert = new Intent(TextList.this,
                        ReadText.class);
                intentConvert.putExtra("User", userName);
                startActivity(intentConvert);
                return true;
            case R.id.textList:
                Toast.makeText(this, "Text list selected", Toast.LENGTH_SHORT).show();
                // current screen
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}