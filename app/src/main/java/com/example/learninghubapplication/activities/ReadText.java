package com.example.learninghubapplication.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.learninghubapplication.R;
import com.example.learninghubapplication.data.TextModelDatabase;
import com.example.learninghubapplication.model.TextModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;

public class ReadText extends AppCompatActivity{
    //variables
    TextView textResult;
    Task<Text> result;
    Button btnReadImage, btnSend, btnReturnMenu;
    Uri imageUri;
    ImageView imageView;
    EditText inputName;

    String userName;

    // user storage permission variables
    private static final int STORAGE_PERMISSION_CODE = 113;
    private static final String TAG = "MyTag"; //error tag
    ActivityResultLauncher<Intent> activityResultLauncher;

    //variables for ML
    InputImage inputImage;
    TextRecognizer textRecogniser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_text);

        //get data from main activity user
        //get data from previous input
        userName = getIntent().getStringExtra("User");
        Log.e("User", "--->>" + userName);

        //set to screen
        textResult = findViewById(R.id.textResult);
        btnReadImage = findViewById(R.id.btnReadImage);
        btnSend = findViewById(R.id.btnSend);
        imageView = findViewById(R.id.imageView);
        inputName = findViewById(R.id.name);
        btnReturnMenu = findViewById(R.id.btnReturnMenu);

        //ML elements
        textRecogniser = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        //activity launcher as result is depreciated
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //get text from picture
                        Intent data = result.getData();
                        imageUri = data.getData();
                        imageView.setImageURI(imageUri);
                        convertImageToText(imageUri);
                    }
                });

        //click button read text
        btnReadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on click listener choose picture
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });

        //click button return to menu
        btnReturnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on click listener choose picture
                Intent intentHome = new Intent(ReadText.this,
                        HomeActivity.class);
                intentHome.putExtra("User", userName);
                startActivity(intentHome);
            }
        });


        //click button send text
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check text has been read
                String strUserName = textResult.getText().toString();
                if(TextUtils.isEmpty(strUserName)) {
                    Toast.makeText(ReadText.this, "Please choose Image",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(ReadText.this, "Sending available text",
                            Toast.LENGTH_SHORT).show();

                    // get user information
                    String name = inputName.getText().toString().trim();
                    // put text result into string
                    String result = textResult.getText().toString().trim();

                    //Create new object of database
                    TextModel textModel = new TextModel();

                    //insert into database
                    textModel.setName(name);
                    textModel.setText(result);

                    // insert into database
                    TextModelDatabase.getDatabase(getApplicationContext()).textModelDao()
                            .insert(textModel);

                    String msg = "Data added to database";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                    //bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    bundle.putString("name", name);

                    //get info from database

                    //start intent
                    Intent intent = new Intent(ReadText.this,
                            SendCommunication.class);
                    intent.putExtra("textResult", result);
                    intent.putExtra("name", name);
                    Log.e("result", "--->>" + result);

                    //reset variables
                    inputName.setText("");
                    textResult.setText("");
                    startActivity(intent);
                }
            }
        });
    }

    //prepare the input image
    private void convertImageToText(Uri imageUri) {
        try{
            // get text from image
            inputImage = InputImage.fromFilePath(getApplicationContext(), imageUri);

            //get text from user (input image)
            Task<Text> result = textRecogniser.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(@NonNull Text text) {
                            //task success
                            textResult.setText(text.getText());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //task fails
                            textResult.setError("Retry");
                            Toast.makeText(ReadText.this, "Please choose text first",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Exception e){
            Log.d(TAG, "convertImageToText: Error: " + e.getMessage());
        }
    }

    //android lifecycle
    protected void onResume() {
        super.onResume();
        //get permissions from user
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
    }

    //get user permissions
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(ReadText.this,
                permission) == PackageManager.PERMISSION_DENIED){

            //get permission
            ActivityCompat.requestPermissions(ReadText.this,
                    new String[] {permission}, requestCode);
        }else {
            //permission already granted
            Toast.makeText(ReadText.this, "Permission has already been granted",
                    Toast.LENGTH_SHORT).show();
        }
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
                Intent intentHome = new Intent(ReadText.this, HomeActivity.class);
                intentHome.putExtra("User", userName);
                startActivity(intentHome);
                return true;
            case R.id.convert:
                Toast.makeText(this, "Convert selected", Toast.LENGTH_SHORT).show();
                //current screen
                return true;
            case R.id.textList:
                Intent intentList = new Intent(ReadText.this, TextList.class);
                intentList.putExtra("User", userName);
                startActivity(intentList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}