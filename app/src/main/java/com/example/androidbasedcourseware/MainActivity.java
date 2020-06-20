package com.example.androidbasedcourseware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbasedcourseware.instructor.InstructorActivity;
import com.example.androidbasedcourseware.student.StudentActivity;

public class MainActivity extends AppCompatActivity {
    private Button instructor;
    private Button student;
    private ImageView imgPhilsca;
    private ImageView imgInet;
    private TextView lblPhilsca;
    private TextView lblTName;

    private boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblPhilsca = (TextView)findViewById(R.id.lblPhilsca);
        lblPhilsca.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);
        lblTName = (TextView)findViewById(R.id.textView);
        lblTName.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);

        imgPhilsca = (ImageView)findViewById(R.id.imgPhilsca);
        imgPhilsca.setImageResource(R.drawable.philsca_logo);
        imgInet = (ImageView)findViewById(R.id.imgInet);
        imgInet.setImageResource(R.drawable.philsca_inet_logo);

        student = (Button)findViewById(R.id.btnStudent);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentActivity studentAct = new StudentActivity();
                studentAct.isStudent = true;
                String name = "student";
                intentTo(name);
            }
        });

        instructor = (Button)findViewById(R.id.btnProf);
        instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstructorActivity instructorAct = new InstructorActivity();
                instructorAct.isInstructor = true;
                String name = "instructor";
                intentTo(name);
            }
        });
    }

    private void intentTo(String name) {
        if (name.equals("student")) {
            Intent intentStudent = new Intent(MainActivity.this, StudentActivity.class);
            startActivity(intentStudent);
        } else {
            Intent intentInstructor = new Intent(MainActivity.this, InstructorActivity.class);
            startActivity(intentInstructor);
        }
    }

    @Override
    public void onBackPressed() {
        if (!backPressed) {
            backPressed = true;
            Toast.makeText(this, "Please click back button again to exit from the application", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressed = false;
                }
            }, 3000);
        } else {
            Intent intentTo = new Intent(Intent.ACTION_MAIN);
            intentTo.addCategory(Intent.CATEGORY_HOME);
            intentTo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentTo);
            finish();
        }
    }
}