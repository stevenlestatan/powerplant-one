package com.example.androidbasedcourseware.instructor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.androidbasedcourseware.MainActivity;
import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.instructor.ui.home.HomeFragment;
import com.example.androidbasedcourseware.student.StudentMenuActivity;
import com.google.android.material.navigation.NavigationView;

public class InstructorMenuActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;

    private boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_statistics, R.id.nav_test, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FrameLayout layout = (FrameLayout)findViewById(R.id.layoutF);
        layout.setVisibility(View.GONE);

        String instructorName = getIntent().getStringExtra("instructorName");
        HomeFragment home = new HomeFragment();
        home = HomeFragment.newInstance(instructorName);

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
            Intent intentTo = new Intent(InstructorMenuActivity.this, MainActivity.class);
            startActivity(intentTo);
            finish();
        }
    }
}