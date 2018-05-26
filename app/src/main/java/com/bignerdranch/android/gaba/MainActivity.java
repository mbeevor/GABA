package com.bignerdranch.android.gaba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        RecipeListFragment recipeListFragment = new RecipeListFragment();
//        fragmentManager.beginTransaction()
//                .add(R.id.fragment_container, recipeListFragment)
//                .commit();

    }

    @Override
    public void onRecipeSelected(int position) {

    }
}
