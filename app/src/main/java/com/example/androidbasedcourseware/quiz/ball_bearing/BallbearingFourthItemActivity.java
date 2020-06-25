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
import com.example.androidbasedcourseware.quiz.turbo.TurboFifthItemActivity;
import com.example.androidbasedcourseware.quiz.turbo.TurboFourthItemActivity;
import com.example.androidbasedcourseware.quiz.turbo.TurboThirdItemActivity;

public class BallbearingFourthItemActivity extends AppCompatActivity {
    private RadioGroup group;
    private DataAccessLayer da;

    private TextView fourthQ;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;

    private String studentId;
    private String firstQuestionAnswer = "";
    private String secondQuestionAnswer = "";
    private String thirdQuestionAnswer = "";
    private String fourthQuestionAnswer = "";

    private Button next;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballbearing_fourth_item);
        da = new DataAccessLayer(this);

        fourthQ = (TextView)findViewById(R.id.b_fourth_question);
        a = (RadioButton)findViewById(R.id.radA);
        b = (RadioButton)findViewById(R.id.radB);
        c = (RadioButton)findViewById(R.id.radC);
        d = (RadioButton)findViewById(R.id.radD);

        boolean turboQuizSet = da.insertBeallBearingQuizSet();
        if (turboQuizSet) {
            Cursor c = da.getBallBearingQuizSetByItemNo(4);
            if (c.getCount() > 0) {
                c.moveToFirst();
                fourthQ.setText(c.getString(c.getColumnIndex(da.COLUMN_QUIZ_SET_ITEM_QUESTIONS)));
            }
        }

        group = (RadioGroup)findViewById(R.id.radGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radA:
                        fourthQuestionAnswer = "A";
                        break;
                    case R.id.radB:
                        fourthQuestionAnswer = "B";
                        break;
                    case R.id.radC:
                        fourthQuestionAnswer = "C";
                        break;
                    case R.id.radD:
                        fourthQuestionAnswer = "D";
                        break;
                }
            }
        });

        next = (Button)findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fourthQuestionAnswer.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please choose an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(BallbearingFourthItemActivity.this, BallbearingFifthItemActivity.class);
                firstQuestionAnswer = getIntent().getStringExtra("firstQuestionAnswer");
                secondQuestionAnswer = getIntent().getStringExtra("secondQuestionAnswer");
                thirdQuestionAnswer = getIntent().getStringExtra("thirdQuestionAnswer");
                intent.putExtra("firstQuestionAnswer", firstQuestionAnswer);
                intent.putExtra("secondQuestionAnswer", secondQuestionAnswer);
                intent.putExtra("thirdQuestionAnswer", thirdQuestionAnswer);
                intent.putExtra("fourthQuestionAnswer", fourthQuestionAnswer);
                startActivity(intent);
                finish();
            }
        });

        back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BallbearingFourthItemActivity.this, BallbearingThirdItemActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}