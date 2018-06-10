package com.bignerdranch.android.gaba.Utilities;

import com.bignerdranch.android.gaba.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Matthew on 10/06/2018.
 */

public interface GetDataService {

    @GET("/baking.json")
    Call<List<Recipe>> getAllRecipes();
}
