package com.example.androidbasedcourseware.quiz.ball_bearing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.datalayer.DataAccessLayer;
import com.example.androidbasedcourseware.discussion.bell_bearing.OuterRingActivity;
import com.example.androidbasedcourseware.discussion.turbo.PistonActivity;
import com.example.androidbasedcourseware.quiz.turbo.TurboFirstItemActivity;
import com.example.androidbasedcourseware.quiz.turbo.TurboSecondItemActivity;
import com.example.androidbasedcourseware.student.StudentMenuActivity;

public class BallbearingFirstItemActivity extends AppCompatActivity {
    private RadioGroup group;
    private DataAccessLayer da;

    private TextView firstQ;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;

    private String studentId;
    private String firstQuestionAnswer = "";

    private Button next;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballbearing_first_item);
        da = new DataAccessLayer(this);

        firstQ = (TextView)findViewById(R.id.b_first_question);
        a = (RadioButton)findViewById(R.id.radA);
        b = (RadioButton)findViewById(R.id.radB);
        c = (RadioButton)findViewById(R.id.radC);
        d = (RadioButton)findViewById(R.id.radD);

        boolean turboQuizSet = da.insertBeallBearingQuizSet();
        if (turboQuizSet) {
            Cursor c = da.getBallBearingQuizSetByItemNo(1);
            if (c.getCount() > 0) {
                c.moveToFirst();
                firstQ.setText(c.getString(c.getColumnIndex(da.COLUMN_QUIZ_SET_ITEM_QUESTIONS)));
            }
        }

        group = (RadioGroup)findViewById(R.id.radGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radA:
                        firstQuestionAnswer = "A";
                        break;
                    case R.id.radB:
                        firstQuestionAnswer = "B";
                        break;
                    case R.id.radC:
                        firstQuestionAnswer = "C";
                        break;
                    case R.id.radD:
                        firstQuestionAnswer = "D";
                        break;
                }
            }
        });

        next = (Button)findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstQuestionAnswer.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please choose an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(BallbearingFirstItemActivity.this, BallbearingSecondItemActivity.class);
                intent.putExtra("firstQuestionAnswer", firstQuestionAnswer);
                startActivity(intent);
                finish();
            }
        });

        back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fromDiscussion = getIntent().getBooleanExtra("fromDiscussion", false);
                boolean fromMenu = getIntent().getBooleanExtra("fromMenu", false);

                if (fromDiscussion && !fromMenu) {
                    Intent intent = new Intent(BallbearingFirstItemActivity.this, OuterRingActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(BallbearingFirstItemActivity.this, StudentMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}