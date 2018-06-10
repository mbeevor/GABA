package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

/**
 * Created by mbeev on 08/06/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        recipeName = intent.getStringExtra("recipeName");
        ingredientsList = intent.getParcelableArrayListExtra("ingredientsList");
        setTitle(recipeName);

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

                recipeBundleForFragment.putString("recipeName", recipeName);
                recipeBundleForFragment.putParcelableArrayList("ingredientsList", ingredientsList);

                detailFragment.setArguments(recipeBundleForFragment);

                fragmentManager.beginTransaction()
                        .add(R.id.recipe_instruction_container, detailFragment)
                        .commit();

            }
        }

    }


}
