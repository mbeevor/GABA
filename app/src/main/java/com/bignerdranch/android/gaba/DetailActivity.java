package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

            recipeName = intent.getStringExtra("recipeName");
            Toast.makeText(this, "Recipe selected is " + recipeName, Toast.LENGTH_SHORT).show();
            setTitle(recipeName);


//
//        if (recipe != null) {
//            recipeName = recipe.getRecipeName();
//            Log.v("Recipe name ==", recipeName);
//            setTitle(recipeName);
//        } else

        if (findViewById(R.id.instruction_linear_layout) != null) {

            twoPane = true;

            // hide the navigation buttons when in two pane mode
            LinearLayout navigationButtons = findViewById(R.id.buttons);
            navigationButtons.setVisibility(View.GONE);

        } else
            // we're in single-pane mode
            twoPane = false;


    }
}
