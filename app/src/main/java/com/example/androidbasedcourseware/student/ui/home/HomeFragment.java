package com.example.androidbasedcourseware.student.ui.home;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidbasedcourseware.R;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String studentName = "";
    private static Bundle args = new Bundle();

    public static HomeFragment newInstance(String studentName) {
        HomeFragment h = new HomeFragment();
        Bundle b = new Bundle();
        b.putString("studentName", studentName);
        h.setArguments(b);
        args = b;

        return h;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentName = args.getString("studentName");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.textView);
        final ImageView philsca = root.findViewById(R.id.imgPhilsca);
        final ImageView inet = root.findViewById(R.id.imgInet);
        philsca.setImageResource(R.drawable.philsca_logo);
        inet.setImageResource(R.drawable.philsca_inet_logo);
        textView.setText(studentName);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(studentName);
//            }
//        });
        return root;
    }
}