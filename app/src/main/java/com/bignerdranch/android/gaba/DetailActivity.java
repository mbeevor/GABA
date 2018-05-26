package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Matthew on 26/05/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private String recipeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        if (recipe != null) {
            recipeName = recipe.getRecipeName();
        }

        setTitle(recipeName);
    }
}
