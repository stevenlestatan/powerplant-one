package com.example.androidbasedcourseware.instructor.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.student.ui.home.HomeViewModel;

public class HomeFragment extends Fragment {
    private String instructorName = "";
    private static Bundle args = new Bundle();

    public static HomeFragment newInstance(String instructorName) {
        HomeFragment h = new HomeFragment();
        Bundle b = new Bundle();
        b.putString("instructorName", instructorName);
        h.setArguments(b);
        args = b;

        return h;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instructorName = args.getString("instructorName");
    }
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home2, container, false);
        final TextView textView = root.findViewById(R.id.textView);
        final ImageView philsca = root.findViewById(R.id.imgPhilsca);
        final ImageView inet = root.findViewById(R.id.imgInet);
        philsca.setImageResource(R.drawable.philsca_logo);
        inet.setImageResource(R.drawable.philsca_inet_logo);
        textView.setText(instructorName);

        return root;
    }
}