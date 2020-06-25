package com.example.androidbasedcourseware.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbasedcourseware.MainActivity;
import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.datalayer.DataAccessLayer;

public class StudentActivity extends AppCompatActivity {
    private Button back;
    private Button proceed;
    private TextView lblPhilsca;
    private TextView lblTName;
    private EditText studentId;
    private EditText studentName;
    private ImageView imgPhilsca;
    private ImageView imgInet;
    public boolean isStudent;

    private DataAccessLayer da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        da = new DataAccessLayer(this);

        lblPhilsca = (TextView)findViewById(R.id.lblPhilsca);
        lblPhilsca.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);
        lblTName = (TextView)findViewById(R.id.textView2);
        lblTName.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);

        imgPhilsca = (ImageView)findViewById(R.id.imgPhilsca);
        imgPhilsca.setImageResource(R.drawable.philsca_logo);
        imgInet = (ImageView)findViewById(R.id.imgInet);
        imgInet.setImageResource(R.drawable.philsca_inet_logo);

        studentId = (EditText)findViewById(R.id.txtStudentId);
        studentName = (EditText)findViewById(R.id.txtStudentName);

        back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTo = new Intent(StudentActivity.this, MainActivity.class);
                startActivity(intentTo);
            }
        });

        proceed = (Button)findViewById(R.id.btnContinue);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (studentId.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Student ID is required", Toast.LENGTH_SHORT).show();
                    return;
                } else if (studentName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Student Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                String student_id = studentId.getText().toString();
                String student_name = studentName.getText().toString();

                boolean validateStudent = validateIfStudentExists(student_id);
                if (!validateStudent) {
                    boolean insertStudent = da.insertStudent(student_id, student_name);
                    if (!insertStudent) {
                        Toast.makeText(getApplicationContext(), "Failed to add into students table", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                SharedPreferences sharedPreferences = getApplication().getSharedPreferences("Student", MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("studentName", student_name);
                edit.putString("studentId", student_id).commit();

                Intent intentTo = new Intent(StudentActivity.this, StudentMenuActivity.class);
//                intentTo.putExtra("studentName", student_name);
                startActivity(intentTo);
                finish();
            }
        });
    }

    private boolean validateIfStudentExists(String studentId) {
        boolean result = false;
        Cursor cursor = da.getStudentById(studentId);
        cursor.moveToFirst();

        int c = cursor.getCount();
        if (c > 0) {
            result = true;
        }

        return result;
    }
}