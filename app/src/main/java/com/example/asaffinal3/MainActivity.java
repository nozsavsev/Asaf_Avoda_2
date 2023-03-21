package com.example.asaffinal3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        //load shared preferences into text boxes
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        ((EditText) findViewById(R.id.schoolName)).setText(sharedPreferences.getString("schoolName", ""));
        ((EditText) findViewById(R.id.principalName)).setText(sharedPreferences.getString("principalName", ""));
        ((EditText) findViewById(R.id.schoolPrice)).setText(sharedPreferences.getString("schoolPrice", ""));

        //get rating from survey activity if exists
        Intent intent = getIntent();
        if (intent.getStringExtra("rating") != null) {
            ((TextView) findViewById(R.id.rating)).setText("Rating is: " + intent.getStringExtra("rating"));
        }

    }

    public void contactUs(View view) {
        //launches contact us activity
        Intent intent = new Intent(this, ContactUs.class);
        startActivity(intent);
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAKE_IMAGE_REQUEST = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView logo = (ImageView) findViewById(R.id.schoolLogo);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri mImageUri = data.getData();
            logo.setImageURI(mImageUri);
        } else if (requestCode == MAKE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            logo.setImageBitmap(imageBitmap);
        }
    }


    public void onSave(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save data");
        builder.setMessage("You want to save entered values?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("schoolName", ((EditText) findViewById(R.id.schoolName)).getText().toString());
                editor.putString("principalName", ((EditText) findViewById(R.id.principalName)).getText().toString());
                editor.putString("schoolPrice", ((EditText) findViewById(R.id.schoolPrice)).getText().toString());
                editor.apply();

                Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onSubmit(View view) {

        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
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
                startActivityForResult(intent, MAKE_IMAGE_REQUEST);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}