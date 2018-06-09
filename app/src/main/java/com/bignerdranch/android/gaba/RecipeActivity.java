package com.bignerdranch.android.gaba;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

/**
 * Created by Matthew on 26/05/2018.
 */

public class RecipeActivity extends AppCompatActivity {

    private String recipeId;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private CardView ingredientsCardView;
    private ArrayList<Steps> stepsList;
    private String numberServings;
    private String recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        recipeId = intent.getStringExtra("recipeId");
        recipeName = intent.getStringExtra("recipeName");
        ingredientsList = intent.getParcelableArrayListExtra("ingredientsList");
        stepsList = intent.getParcelableArrayListExtra("stepsList");
        numberServings = intent.getStringExtra("numberServings");
        recipeImage = intent.getStringExtra("recipeImage");

        // update app name to name of recipe selected
        setTitle(recipeName);

        // find ingredients card to create onclicklistener later
        ingredientsCardView = findViewById(R.id.ingredients_card_view);

        // determine if creating a one or two-pane display
        if (findViewById(R.id.instruction_linear_layout) != null) {

            // create new fragment if not previously created
            if (savedInstanceState == null) {

                //hide mediaplayer frame for default view which shows only ingredients
                FrameLayout mediaPlayerCardView = findViewById(R.id.media_container);
                mediaPlayerCardView.setVisibility(View.GONE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment detailFragment = fragmentManager.findFragmentById(R.id.recipe_instruction_container);

                if (detailFragment == null) {

                    detailFragment = new DetailFragment();

                    Bundle recipeBundleForFragment = new Bundle();

                    recipeBundleForFragment.putString("recipeId", recipeId);
                    recipeBundleForFragment.putString("recipeName", recipeName);
                    recipeBundleForFragment.putParcelableArrayList("ingredientsList", ingredientsList);
                    recipeBundleForFragment.putParcelableArrayList("stepsList", stepsList);
                    recipeBundleForFragment.putString("numberServings", numberServings);
                    recipeBundleForFragment.putString("recipeImage", recipeImage);

                    detailFragment.setArguments(recipeBundleForFragment);

                    fragmentManager.beginTransaction()
                            .add(R.id.recipe_instruction_container, detailFragment)
                            .commit();


                }


            }

        } else {
            // we're in single-pane mode
            ingredientsCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle listOfIngredients = new Bundle();
                    listOfIngredients.putString("recipeName", recipeName);
                    listOfIngredients.putParcelableArrayList("ingredientsList", ingredientsList);

                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtras(listOfIngredients);
                    startActivity(intent);

                }
            });


        }
    }
}
