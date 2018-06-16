package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Keys;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bignerdranch.android.gaba.Model.Keys.INGREDIENTS_CARD;
import static com.bignerdranch.android.gaba.Model.Keys.INGREDIENTS_LIST;
import static com.bignerdranch.android.gaba.Model.Keys.POSITION;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_NAME;
import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by mbeev on 08/06/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<Steps> stepsList;
    @BindView(R.id.buttons)
    RelativeLayout buttons;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.previous_button)
    Button previousButton;
    private int position;
    private String ingredientsCard;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipeName = savedInstanceState.getString(RECIPE_NAME);
        ingredientsList = savedInstanceState.getParcelableArrayList(INGREDIENTS_LIST);
        stepsList = savedInstanceState.getParcelableArrayList(STEPS_LIST);
        position = savedInstanceState.getInt(POSITION, position);
        ingredientsCard = savedInstanceState.getString(INGREDIENTS_CARD);
        // change the title to the recipe name
        setTitle(recipeName);
        // hide buttons if showing ingredients list
        if (ingredientsCard != null) {
            buttons.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // check if fragment already exists, get intent if not
        if (savedInstanceState == null) {

            Intent intent = getIntent();
            recipeName = intent.getStringExtra(RECIPE_NAME);
            ingredientsList = intent.getParcelableArrayListExtra(INGREDIENTS_LIST);
            stepsList = intent.getParcelableArrayListExtra(STEPS_LIST);
            position = intent.getIntExtra(POSITION, position);
            ingredientsCard = intent.getStringExtra(INGREDIENTS_CARD);
            // change the title to the recipe name
            setTitle(recipeName);

            // using intent, determine if showing ingredients or recipe step
            if (ingredientsCard == null) {

                showRecipeStep();

            } else {

                showIngredients();
            }

        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get size of steps list
                final int size = stepsList.size();

                // check if two more positions to show; otherwise hide 'next' button
                if (position + 1 < size) {
                    position = position + 1;
                    nextButton.setVisibility(View.VISIBLE);
                    previousButton.setVisibility(View.VISIBLE);
                    showRecipeStep();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_more_steps, Toast.LENGTH_SHORT).show();
                    nextButton.setVisibility(View.GONE);
                }
            }

        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if two more positions to show; otherwise hide 'next' button
                if (position - 1 < 0) {
                    Toast.makeText(getApplicationContext(), R.string.no_previous_steps, Toast.LENGTH_SHORT).show();
                    previousButton.setVisibility(View.GONE);
                } else {
                    position = position - 1;
                    nextButton.setVisibility(View.VISIBLE);
                    previousButton.setVisibility(View.VISIBLE);
                    showRecipeStep();
                }
            }

        });

    }

    public void showRecipeStep() {

        Fragment stepDetailFragment = new StepDetailFragment();
        Bundle stepBundleForFragment = new Bundle();
        stepBundleForFragment.putInt(POSITION, position);
        stepBundleForFragment.putParcelableArrayList(STEPS_LIST, stepsList);
        stepDetailFragment.setArguments(stepBundleForFragment);

        fragmentManager.beginTransaction()
                .replace(R.id.recipe_instruction_container, stepDetailFragment)
                .commit();
    }

    public void showIngredients() {
        // hide navigation buttons when showing ingredients
        buttons.setVisibility(View.GONE);

        Fragment ingredientsFragment = new IngredientsFragment();
        Bundle recipeBundleForFragment = new Bundle();
        recipeBundleForFragment.putParcelableArrayList(Keys.INGREDIENTS_LIST, ingredientsList);
        ingredientsFragment.setArguments(recipeBundleForFragment);

        fragmentManager.beginTransaction()
                .replace(R.id.recipe_instruction_container, ingredientsFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RECIPE_NAME, recipeName);
        outState.putInt(POSITION, position);
        outState.putParcelableArrayList(STEPS_LIST, stepsList);
        outState.putString(INGREDIENTS_CARD, ingredientsCard);
        outState.putParcelableArrayList(INGREDIENTS_LIST, ingredientsList);
    }
}
