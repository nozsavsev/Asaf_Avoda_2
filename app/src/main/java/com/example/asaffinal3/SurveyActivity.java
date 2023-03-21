package com.example.asaffinal3;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);


        EditText editTextNumber = findViewById(R.id.schoolRating);

        editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int value = parseInt(s.toString());

                    if (value > 100) {
                        editTextNumber.setText("100");
                    } else if (value < 1) {
                        editTextNumber.setText("1");
                    }

                } catch (NumberFormatException e) {
                    editTextNumber.setText("1");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    public void submitSurvey(View view) {
        int inRat = 0;
        try {
            inRat = parseInt(((EditText) findViewById(R.id.schoolRating)).getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Please enter a valid rating", Toast.LENGTH_SHORT).show();
            return;
        }

        int rating = (inRat + ((SeekBar) findViewById(R.id.schoolJobPlacement)).getProgress()) / 2;

        rating += ((Switch) findViewById(R.id.schoolPrice)).isChecked() ? 100 : 50;

        System.out.println("submitSurvey: " + rating);



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save data");
        builder.setMessage("You want to save entered values?");

        String finalRating =  Integer. toString(rating);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
                intent.putExtra("rating", finalRating);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}