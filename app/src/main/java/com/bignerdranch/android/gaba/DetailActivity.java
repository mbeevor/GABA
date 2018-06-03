package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.bignerdranch.android.gaba.Model.Recipe;

/**
 * Created by Matthew on 26/05/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private String recipeName;

    // boolean to control whether viewing from a phone or tablet
    private boolean twoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (findViewById(R.id.instruction_linear_layout) != null) {

            twoPane = true;

            // hide the navigation buttons when in two pane mode
            LinearLayout navigationButtons = findViewById(R.id.buttons);
            navigationButtons.setVisibility(View.GONE);

        } else
            // we're in single-pane mode
            twoPane = false;

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        if (recipe != null) {
            recipeName = recipe.getRecipeName();
        }

        setTitle(recipeName);
    }
}
