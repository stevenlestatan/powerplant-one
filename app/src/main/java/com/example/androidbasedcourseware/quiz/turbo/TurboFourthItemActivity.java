package com.example.androidbasedcourseware.quiz.turbo;

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

public class TurboFourthItemActivity extends AppCompatActivity {
    private RadioGroup group;
    private DataAccessLayer da;

    private TextView secondQ;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;

    private String firstQuestionAnswer = "";
    private String secondQuestionAnswer = "";
    private String thirdQuestionAnswer = "";
    private String fourthQuestionAnswer = "";

    private Button next;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turbo_fourth_item);
        da = new DataAccessLayer(this);

        secondQ = (TextView)findViewById(R.id.turbo_fourth_question);
        a = (RadioButton)findViewById(R.id.radA);
        b = (RadioButton)findViewById(R.id.radB);
        c = (RadioButton)findViewById(R.id.radC);
        d = (RadioButton)findViewById(R.id.radD);

        boolean turboQuizSet = da.insertTurboQuizSet();
        if (turboQuizSet) {
            Cursor c = da.getTurboQuizSetByItemNo(4);
            if (c.getCount() > 0) {
                c.moveToFirst();
                secondQ.setText(c.getString(c.getColumnIndex(da.COLUMN_QUIZ_SET_ITEM_QUESTIONS)));
            }
        }

        group = (RadioGroup)findViewById(R.id.radGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radA:
                        secondQuestionAnswer = "A";
                        break;
                    case R.id.radB:
                        secondQuestionAnswer = "B";
                        break;
                    case R.id.radC:
                        secondQuestionAnswer = "C";
                        break;
                    case R.id.radD:
                        secondQuestionAnswer = "D";
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

                Intent intent = new Intent(TurboFourthItemActivity.this, TurboFifthItemActivity.class);
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
                Intent intent = new Intent(TurboFourthItemActivity.this, TurboThirdItemActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}