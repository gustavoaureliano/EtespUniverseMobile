package com.etespuniverse.mobile;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class Ajuda extends AppCompatActivity {

    private MaterialToolbar topAppBar;
    private LinearLayout item, content, expand;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        topAppBar = findViewById(R.id.topAppBar);
        item = findViewById(R.id.item1);
        content = findViewById(R.id.content1);
        icon = findViewById(R.id.icon1);
        expand = findViewById(R.id.expand);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (content.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(item, new AutoTransition());
                    content.setVisibility(View.GONE);
                    icon.setImageResource(R.drawable.ic_round_expand_more_24);
                } else {
                    TransitionManager.beginDelayedTransition(item, new AutoTransition());
                    content.setVisibility(View.VISIBLE);
                    icon.setImageResource(R.drawable.ic_round_expand_less_24);
                }
            }
        });

    }
}