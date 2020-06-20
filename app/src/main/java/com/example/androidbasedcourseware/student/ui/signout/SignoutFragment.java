package com.example.androidbasedcourseware.student.ui.signout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidbasedcourseware.MainActivity;
import com.example.androidbasedcourseware.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignoutFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_signout, container, false);
//    }
}