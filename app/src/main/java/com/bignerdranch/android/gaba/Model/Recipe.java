package com.bignerdranch.android.gaba.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Matthew on 22/05/2018.
 */

public class Recipe implements Parcelable {

    private String recipeId;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<Steps> stepsList;
    private String numberServings;
    private String recipeImage;

    public Recipe(String id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, String servings, String image) {

        recipeId = id;
        recipeName = name;
        ingredientsList = ingredients;
        stepsList = steps;
        numberServings = servings;
        recipeImage = image;
    }

    private Recipe(Parcel parcel) {

        recipeId = parcel.readString();
        recipeName = parcel.readString();
        parcel.readList(ingredientsList, Ingredients.class.getClassLoader());
        parcel.readList(stepsList, Steps.class.getClassLoader());
        numberServings = parcel.readString();
        recipeImage = parcel.readString();


    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public ArrayList<Ingredients> getIngredientsList() { return ingredientsList;}

    public ArrayList<Steps> getStepsList() { return stepsList;}

    public String getNumberServings() {return numberServings;}

    public String getRecipeImage() {return recipeImage;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(recipeId);
        dest.writeString(recipeName);
        dest.writeList(ingredientsList);
        dest.writeList(stepsList);
        dest.writeString(numberServings);
        dest.writeString(recipeImage);

    }


    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[0];
        }
    };
}
