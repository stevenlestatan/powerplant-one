package com.example.androidbasedcourseware.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.androidbasedcourseware.MainActivity;
import com.example.androidbasedcourseware.quiz.turbo.TurboFifthItemActivity;
import com.example.androidbasedcourseware.student.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidbasedcourseware.R;
import com.google.ar.core.Frame;

public class StudentMenuActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;

    private boolean backPressed = false;
    private String studentName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_discussion, R.id.nav_test, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FrameLayout layout = (FrameLayout)findViewById(R.id.layoutF);
        layout.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getSharedPreferences("Student", MODE_PRIVATE);
        studentName = sharedPreferences.getString("studentName", "default");

        HomeFragment home = (HomeFragment)getSupportFragmentManager().findFragmentById(R.id.nav_home);
        home = HomeFragment.newInstance(studentName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layoutF, home);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (!backPressed) {
            backPressed = true;
            Toast.makeText(this, "Please click back button again to return to login session", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressed = false;
                }
            }, 3000);
        } else {
            Intent intentTo = new Intent(StudentMenuActivity.this, MainActivity.class);
            startActivity(intentTo);
            finish();
        }
    }
}