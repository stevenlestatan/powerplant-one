package com.example.androidbasedcourseware.student.ui.testknowledge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.discussion.turbo.TurboActivity;
import com.example.androidbasedcourseware.quiz.turbo.TurboFirstItemActivity;

public class TestKnowledgeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_testknowledge, container, false);
        final ImageView philsca = root.findViewById(R.id.imgPhilsca);
        final ImageView inet = root.findViewById(R.id.imgInet);
        philsca.setImageResource(R.drawable.philsca_logo);
        inet.setImageResource(R.drawable.philsca_inet_logo);

        final Button bearing = root.findViewById(R.id.btnBearing);
        bearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final Button turbo = root.findViewById(R.id.btnTurbo);
        turbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent turbo = new Intent(getContext(), TurboFirstItemActivity.class);
                startActivity(turbo);
            }
        });

        return root;
    }
}