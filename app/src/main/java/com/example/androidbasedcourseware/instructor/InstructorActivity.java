package com.example.androidbasedcourseware.instructor;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbasedcourseware.MainActivity;
import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.datalayer.DataAccessLayer;
import com.example.androidbasedcourseware.instructor.ui.home.HomeFragment;

public class InstructorActivity extends AppCompatActivity {
    private Button back;
    private Button proceed;
    private TextView lblPhilsca;
    private TextView lblTName;
    private ImageView imgPhilsca;
    private ImageView imgInet;
    private EditText instructorId;
    private EditText instructorName;
    public boolean isInstructor;

    private DataAccessLayer da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        da = new DataAccessLayer(this);

        lblPhilsca = (TextView)findViewById(R.id.lblPhilsca2);
        lblPhilsca.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);
        lblTName = (TextView)findViewById(R.id.textView3);
        lblTName.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);

        imgPhilsca = (ImageView)findViewById(R.id.imgPhilsca2);
        imgPhilsca.setImageResource(R.drawable.philsca_logo);
        imgInet = (ImageView)findViewById(R.id.imgInet2);
        imgInet.setImageResource(R.drawable.philsca_inet_logo);

        instructorId = (EditText)findViewById(R.id.txtInstructorId);
        instructorName = (EditText)findViewById(R.id.txtInstructorName);

        back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTo = new Intent(InstructorActivity.this, MainActivity.class);
                startActivity(intentTo);
            }
        });

        proceed = (Button)findViewById(R.id.btnContinue);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (instructorId.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Instructor ID is required", Toast.LENGTH_SHORT).show();
                    return;
                } else if (instructorName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Instructor Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                String instructor_id = instructorId.getText().toString();
                String instructor_name = instructorName.getText().toString();

                boolean isInstructorExists = da.validateIfInstructorExists(instructor_id);
                if (isInstructorExists) {
                    Cursor cursor = da.getInstructorById(instructor_id);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        String instructorId = cursor.getString(cursor.getColumnIndex(da.COLUMN_INSTRUCTOR_ID));
                        String instructorName = cursor.getString(cursor.getColumnIndex(da.COLUMN_INSTRUCTOR_NAME));
                        if (!instructorId.equals(instructor_id) || !instructor_name.equals(instructorName)) {
                            Toast.makeText(getApplicationContext(), "There is a default instructor and it is not you", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                Intent intent = new Intent(InstructorActivity.this, InstructorMenuActivity.class);
                intent.putExtra("instructorName", instructor_name);
                startActivity(intent);
                finish();
            }
        });
    }
}