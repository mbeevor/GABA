package com.bignerdranch.android.gaba.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matthew on 03/06/2018.
 */

public class Ingredients implements Parcelable {

    @SerializedName("quantity")
    private String itemQuantity;
    @SerializedName("measure")
    private String itemMeasure;
    @SerializedName("ingredient")
    private String itemIngredient;


        public Ingredients(String quantity, String measure, String ingredient) {
            itemQuantity = quantity;
            itemMeasure = measure;
            itemIngredient = ingredient;
        }

        private Ingredients(Parcel parcel) {

            itemQuantity = parcel.readString();
            itemMeasure = parcel.readString();
            itemIngredient = parcel.readString();

       }

        public String getItemQuantity() {
            return itemQuantity;
        }

        public String getItemMeasure() {
            return itemMeasure;
        }

    public String getItemIngredient() {
        return itemIngredient;
    }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(itemQuantity);
            dest.writeString(itemMeasure);
            dest.writeString(itemIngredient);

        }


        public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {

            @Override
            public Ingredients createFromParcel(Parcel parcel) {
                return new Ingredients(parcel);
            }

            @Override
            public Ingredients[] newArray(int i) {
                return new Ingredients[i];
            }
        };
    }
