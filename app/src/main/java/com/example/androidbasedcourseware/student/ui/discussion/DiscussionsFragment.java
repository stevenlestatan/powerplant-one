package com.example.androidbasedcourseware.student.ui.discussion;

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
import com.example.androidbasedcourseware.discussion.bell_bearing.BellbearingActivity;
import com.example.androidbasedcourseware.discussion.turbo.TurboActivity;

public class DiscussionsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_discussions, container, false);
        final ImageView philsca = root.findViewById(R.id.imgPhilsca);
        final ImageView inet = root.findViewById(R.id.imgInet);
        philsca.setImageResource(R.drawable.philsca_logo);
        inet.setImageResource(R.drawable.philsca_inet_logo);

        final Button bearing = root.findViewById(R.id.btnBearing);
        bearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bearing = new Intent(getContext(), BellbearingActivity.class);
                startActivity(bearing);
            }
        });

        final Button turbo = root.findViewById(R.id.btnTurbo);
        turbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent turbo = new Intent(getContext(), TurboActivity.class);
                turbo.putExtra("fromDiscussion", true);
                startActivity(turbo);
            }
        });

        return root;
    }
}