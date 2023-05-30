package com.example.learninghubapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.learninghubapplication.R;
import com.example.learninghubapplication.data.UserDAO;
import com.example.learninghubapplication.data.UserDataBase;
import com.example.learninghubapplication.model.User;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    //variables
    private UserDAO userDAO; //declare user class
    EditText name_edit, username_edit, password_edit, cfm_Password_edit, phone_edit;
    Button btn_sign_up, btn_sign_in, btnGetImage;
    ImageView userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // set to screen
        btnGetImage = findViewById(R.id.btnGetImage);
        userPhoto = findViewById(R.id.userPhoto);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        name_edit = findViewById(R.id.name_edit);
        username_edit = findViewById(R.id.username_edit);
        password_edit = findViewById(R.id.password_edit);
        cfm_Password_edit = findViewById(R.id.cfm_Password_edit);
        phone_edit = findViewById(R.id.phone_edit);

        // behaviour changes in Android 13+, with current SDK 33 and when changed to 22 Read data
        // with manifest permissions is denied. Appears to be outside the scope of content.
        // https://developer.android.com/about/versions/13/behavior-changes-13

        // click button add image
        final int PICK_IMAGE = 100;
        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        // click button sign in
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sign user up & start main
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        // set this user
        userDAO = Room.databaseBuilder(this, UserDataBase.class, "User")
                .allowMainThreadQueries().build().getUserDao();

        // click button sign up
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to string input
                String userName = username_edit.getText().toString().trim();
                String name = name_edit.getText().toString().trim();
                String password = password_edit.getText().toString().trim();
                String cfmPassword = cfm_Password_edit.getText().toString().trim();
                String phone = phone_edit.getText().toString().trim();

                if (userPhoto == null){
                    Toast.makeText(RegisterActivity.this, "Please set a profile image",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // passwords match
                if (password.equals(cfmPassword)) {
                    User user = new User(userName, name, password, phone);
                    userDAO.insert(user);
                    Intent moveToLogin = new Intent(RegisterActivity.this,
                            MainActivity.class);
                    startActivity(moveToLogin);
                }
                else {
                    Toast.makeText(RegisterActivity.this,
                            "Passwords don't match, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // methods for profile picture
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Uri imageUri;
        userPhoto = findViewById(R.id.userPhoto);
        if (resultCode == RESULT_OK && reqCode == 100){
            imageUri = data.getData();
            userPhoto.setImageURI(imageUri);
        }
    }
}
