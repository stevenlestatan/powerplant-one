package com.example.androidbasedcourseware.quiz.turbo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.datalayer.DataAccessLayer;
import com.example.androidbasedcourseware.datalayer.ItemsCaterer;
import com.example.androidbasedcourseware.student.StudentMenuActivity;

import java.util.HashMap;
import java.util.Map;

public class TurboFifthItemActivity extends AppCompatActivity {
    private RadioGroup group;
    private DataAccessLayer da;

    private TextView is_passed, content;

    private TextView fifthQ;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;

    private String studentId;
    private String firstQuestionAnswer;
    private String secondQuestionAnswer;
    private String thirdQuestionAnswer;
    private String fourthQuestionAnswer;
    private String fifthQuestionAnswer = "";

    private String studentName = "";

    private Button submit;
    private Button back;
    private Button close;

    private LinearLayout axm, overbox, layout1;
    private ImageView aircraftMod;

    private Animation fromsmall, fromnothing, foraircraftmod, togo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turbo_fifth_item);
        da = new DataAccessLayer(this);

        layout1 = (LinearLayout)findViewById(R.id.aircraftxmodalTLI);
        axm = (LinearLayout)findViewById(R.id.aircraftxmodalTLI2);
        overbox = (LinearLayout)findViewById(R.id.overboxTLI);
        aircraftMod = (ImageView)findViewById(R.id.aircraftModBH);
        aircraftMod.setVisibility(View.GONE);
        fromsmall = AnimationUtils.loadAnimation(this, R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this, R.anim.fromnothing);
        foraircraftmod = AnimationUtils.loadAnimation(this, R.anim.foraircraftmod);
        togo = AnimationUtils.loadAnimation(this, R.anim.togo);

        layout1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        overbox.setAlpha(0);
        axm.setAlpha(0);

        is_passed = (TextView)findViewById(R.id.is_passed);
        content = (TextView)findViewById(R.id.content);

        fifthQ = (TextView)findViewById(R.id.turbo_fifth_question);
        a = (RadioButton)findViewById(R.id.radA);
        b = (RadioButton)findViewById(R.id.radB);
        c = (RadioButton)findViewById(R.id.radC);
        d = (RadioButton)findViewById(R.id.radD);

        boolean turboQuizSet = da.insertTurboQuizSet();
        if (turboQuizSet) {
            Cursor c = da.getTurboQuizSetByItemNo(5);
            if (c.getCount() > 0) {
                c.moveToFirst();
                fifthQ.setText(c.getString(c.getColumnIndex(da.COLUMN_QUIZ_SET_ITEM_QUESTIONS)));
            }
        }

        group = (RadioGroup)findViewById(R.id.radGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radA:
                        fifthQuestionAnswer = "A";
                        break;
                    case R.id.radB:
                        fifthQuestionAnswer = "B";
                        break;
                    case R.id.radC:
                        fifthQuestionAnswer = "C";
                        break;
                    case R.id.radD:
                        fifthQuestionAnswer = "D";
                        break;
                }
            }
        });

        submit = (Button)findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fifthQuestionAnswer.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please choose an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                firstQuestionAnswer = getIntent().getStringExtra("firstQuestionAnswer");
                secondQuestionAnswer = getIntent().getStringExtra("secondQuestionAnswer");
                thirdQuestionAnswer = getIntent().getStringExtra("thirdQuestionAnswer");
                fourthQuestionAnswer = getIntent().getStringExtra("fourthQuestionAnswer");

                SharedPreferences sharedPreferences = getSharedPreferences("Student", MODE_PRIVATE);
                String studentId = sharedPreferences.getString("studentId", "default");

                Map<Integer, String> map = new HashMap<>();
                map.put(1, firstQuestionAnswer);
                map.put(2, secondQuestionAnswer);
                map.put(3, thirdQuestionAnswer);
                map.put(4, fourthQuestionAnswer);
                map.put(5, fifthQuestionAnswer);

                final int[] itemCount = {0};
                final int[] itemPassed = {0};
                map.forEach((i, s) -> {
                    itemCount[0]++;
                    ItemsCaterer items = new ItemsCaterer(studentId, i, s);
                    da.insertTurboQuizResult(items);

                    Cursor c = da.getTurboCorrectAnswerByItemNo(i);
                    if (c.getCount() > 0) {
                        c.moveToFirst();

                        String correct_answer = c.getString(c.getColumnIndex(da.COLUMN_QUIZ_SET_CORRECT_ANSWER));
                        if (correct_answer.equals(s)) {
                            itemPassed[0]++;
                        }
                    }
                });

                boolean isPassed = da.insertToStatistics(studentId, "Turbocharger", itemPassed[0], itemCount[0]);
                if (!isPassed) {
                    is_passed.setText("FAILED!");
                    content.setText("I think you have not yet read all what discussed on the augmented discussions. Your score is " + itemPassed[0] + " out of "
                    + itemCount[0] + ".");
                } else {
                    is_passed.setText("PASSED!");
                    content.setText("Great job! Learning isn't that hard if you focus. Your score is " + itemPassed[0] + " out of "
                            + itemCount[0] + ".");
                }

                layout1.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                aircraftMod.setVisibility(View.VISIBLE);
                aircraftMod.startAnimation(foraircraftmod);

                overbox.setAlpha(1);
                overbox.startAnimation(fromnothing);

                axm.setAlpha(1);
                axm.startAnimation(fromsmall);
            }
        });

        back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TurboFifthItemActivity.this, TurboFourthItemActivity.class);
                startActivity(intent);
                finish();
            }
        });

        close = (Button)findViewById(R.id.btnClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overbox.startAnimation(togo);
                axm.startAnimation(togo);
                aircraftMod.startAnimation(togo);
                aircraftMod.setVisibility(View.GONE);

                ViewCompat.animate(axm).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();

                Intent intent = new Intent(TurboFifthItemActivity.this, StudentMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setIntentString(String studentName) {
        this.studentName = studentName;
    }
}