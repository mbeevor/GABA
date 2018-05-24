package com.bignerdranch.android.gaba;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Matthew on 22/05/2018.
 */

public class Recipe implements Parcelable {

    private String recipeId;
    private String recipeName;

    public Recipe(String id, String name) {

        recipeId = id;
        recipeName = name;

    }

    private Recipe(Parcel parcel) {

        recipeId = parcel.readString();
        recipeName = parcel.readString();

    }

    public String getRecipeId() {
        return recipeId;
    }

    public String setRecipeId(String id) {
        recipeId = id;
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String setRecipeName(String name) {
        recipeName = name;
        return recipeName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(recipeId);
        dest.writeString(recipeName);

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
