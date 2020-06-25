package com.example.androidbasedcourseware.discussion.bell_bearing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.discussion.turbo.CompressorCoverActivity;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class CageActivity extends AppCompatActivity {
    private ArFragment arFragment;
    private ModelRenderable renderable;
    private TransformableNode transformableNode;

    private Button nextPage;
    private Button back;
    private Button cage;
    private Button close;

    private LinearLayout axm, overbox;
    private ImageView aircraftMod;

    private Animation fromsmall, fromnothing, foraircraftmod, togo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cage);

        axm = (LinearLayout)findViewById(R.id.aircraftxmodal);
        overbox = (LinearLayout)findViewById(R.id.overbox);
        aircraftMod = (ImageView)findViewById(R.id.aircraftMod);
        aircraftMod.setVisibility(View.GONE);
        fromsmall = AnimationUtils.loadAnimation(this, R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this, R.anim.fromnothing);
        foraircraftmod = AnimationUtils.loadAnimation(this, R.anim.foraircraftmod);
        togo = AnimationUtils.loadAnimation(this, R.anim.togo);
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ux_cage_fragment);

        ModelRenderable.builder()
                .setSource(this, R.raw.cage)
                .build()
                .thenAccept(modelRenderable1 -> renderable = modelRenderable1)
                .exceptionally(throwable -> {
                    Toast toast = Toast.makeText(this, "Unable to load any renderable", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return null;
                });

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            transformableNode = new TransformableNode(arFragment.getTransformationSystem());
            transformableNode.setParent(anchorNode);
            transformableNode.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);
            transformableNode.select();

            nextPage.setEnabled(true);
            cage.setEnabled(true);
        });

        axm.setAlpha(0);
        overbox.setAlpha(0);

        nextPage = (Button)findViewById(R.id.btnNext);
        nextPage.setEnabled(false);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CageActivity.this, InnerRingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        close = (Button)findViewById(R.id.btn_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overbox.startAnimation(togo);
                axm.startAnimation(togo);
                aircraftMod.startAnimation(togo);
                aircraftMod.setVisibility(View.GONE);

                ViewCompat.animate(axm).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();
            }
        });

        back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CageActivity.this, BallActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cage = (Button)findViewById(R.id.btnCage);
        cage.setEnabled(false);
        cage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aircraftMod.setVisibility(View.VISIBLE);
                aircraftMod.startAnimation(foraircraftmod);

                overbox.setAlpha(1);
                overbox.startAnimation(fromnothing);

                axm.setAlpha(1);
                axm.startAnimation(fromsmall);
            }
        });

        nextPage.bringToFront();
        back.bringToFront();
    }
}