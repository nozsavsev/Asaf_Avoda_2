package com.example.asaffinal3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //load shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        ((EditText)findViewById(R.id.schoolName)).setText(sharedPreferences.getString("schoolName", ""));
        ((EditText)findViewById(R.id.principalName)).setText(sharedPreferences.getString("principalName", ""));
        ((EditText)findViewById(R.id.schoolPrice)).setText(sharedPreferences.getString("schoolPrice", ""));

    }

    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView logo = (ImageView) findViewById(R.id.schoolLogo);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri mImageUri = data.getData();
            logo.setImageURI(mImageUri);
        } else if (requestCode == 0 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri mImageUri = data.getData();
            logo.setImageURI(mImageUri);
        }
    }


    public void onSubmit(View view) {

        //save data to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("schoolName", ((EditText)findViewById(R.id.schoolName)).getText().toString());
        editor.putString("principalName", findViewById(R.id.principalName).toString());
        editor.putString("schoolPrice", findViewById(R.id.schoolPrice).toString());


    }


    public void changeLogo(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change logo");
        builder.setMessage("You want to make a photo or pick a file?");

        builder.setPositiveButton("Pick file", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

        builder.setNegativeButton("make photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //make a photo from camera
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }


}