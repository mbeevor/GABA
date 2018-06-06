package com.bignerdranch.android.gaba;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

/**
 * Created by Matthew on 26/05/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private Bundle recipe;
    private String recipeName;

    // boolean to control whether viewing from a phone or tablet
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // TODO: need to create a new recipe using intent; then set this into new bundle for fragment, using instance of:
//        Fragment fragment = new Fragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(key, value);
//        fragment.setArguments(bundle);
//        Intent intent = getIntent();
//
//        and in fragment
//
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            int myInt = bundle.getInt(key, defaultValue);
//        }


        recipe = new Recipe(intent.getStringExtra("recipeId"),
        intent.getStringExtra("recipeName"),
        intent.getParcelableArrayListExtra("ingredientsList"),
        intent.getParcelableArrayListExtra("stepsList"),
        intent.getStringExtra("numberServings"),
        intent.getStringExtra("recipeImage")
        );

        // update app name to name of recipe selected
        recipeName = intent.getStringExtra("recipeName");
        setTitle(recipeName);

        // determine if creating a one or two-pane display
        if (findViewById(R.id.instruction_linear_layout) != null) {

            twoPane = true;

            ArrayList<Steps> stepsArrayList = intent.getParcelableArrayListExtra("stepsList");

            // add right-hand-side fragment for two-pane
            FragmentManager fragmentManager = getFragmentManager();

            RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();

            // hide the navigation buttons when in two pane mode
//            RelativeLayout navigationButtons = findViewById(R.id.buttons);
//            navigationButtons.setVisibility(View.GONE);

        } else
            // we're in single-pane mode
            twoPane = false;


    }
}
