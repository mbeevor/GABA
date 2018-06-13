package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Keys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mbeev on 08/06/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    @BindView(R.id.buttons)
    RelativeLayout buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        recipeName = intent.getStringExtra(Keys.RECIPE_NAME);
        ingredientsList = intent.getParcelableArrayListExtra(Keys.INGREDIENTS_LIST);
        setTitle(recipeName);

        // create new fragment if not previously created
        if (savedInstanceState == null) {

            // hide navigation buttons when showing ingredients
            buttons.setVisibility(View.GONE);

            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment ingredientsFragment = new IngredientsFragment();

            Bundle recipeBundleForFragment = new Bundle();

            recipeBundleForFragment.putString(Keys.RECIPE_NAME, recipeName);
            recipeBundleForFragment.putParcelableArrayList(Keys.INGREDIENTS_LIST, ingredientsList);

            ingredientsFragment.setArguments(recipeBundleForFragment);

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_instruction_container, ingredientsFragment)
                    .commit();


        }

    }


}
