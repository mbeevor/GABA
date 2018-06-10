package com.bignerdranch.android.gaba.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matthew on 03/06/2018.
 */

public class Steps implements Parcelable {

    @SerializedName("id")
    private String stepId;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("description")
    private String longDescription;
    @SerializedName("videoURL")
    private String videoUrl;
    @SerializedName("thumbnailURL")
    private String thumbnailUrl;


    public Steps(String id, String summary, String description, String video, String thumb) {
        stepId = id;
        shortDescription = summary;
        longDescription = description;
        videoUrl = video;
        thumbnailUrl = thumb;
    }

    private Steps(Parcel parcel) {

        stepId = parcel.readString();
        shortDescription = parcel.readString();
        longDescription = parcel.readString();
        videoUrl = parcel.readString();
        thumbnailUrl = parcel.readString();

    }

    public String getStepId() {
        return stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(stepId);
        dest.writeString(shortDescription);
        dest.writeString(longDescription);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);


    }


    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>() {

        @Override
        public Steps createFromParcel(Parcel parcel) {
            return new Steps(parcel);
        }

        @Override
        public Steps[] newArray(int i) {
            return new Steps[0];
        }
    };
}