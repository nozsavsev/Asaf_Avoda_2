package com.example.asaffinal3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }





    public void sendEmail(View view) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + ((EditText)findViewById(R.id.email)).getText().toString()));
        intent.putExtra(Intent.EXTRA_SUBJECT, ((EditText)findViewById(R.id.subject)).getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, ((EditText)findViewById(R.id.body)).getText().toString());
        startActivity(intent);

    }




}