package com.chrissha.moviepop.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 5/6/16.
 */
public class MovieData implements Parcelable {
    public String tmdbID;
    public String title;
    public String posterURL;
    public String summary;
    public String releaseDate;
    public String backdropURL;
    public String rating;
    public int length;

    public MovieData()
    {
        super();
    }

    protected MovieData(Parcel parcel)
    {
        tmdbID = parcel.readString();
        title = parcel.readString();
        posterURL = parcel.readString();
        summary = parcel.readString();
        releaseDate = parcel.readString();
        backdropURL = parcel.readString();
        rating = parcel.readString();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(tmdbID);
        parcel.writeString(title);
        parcel.writeString(posterURL);
        parcel.writeString(summary);
        parcel.writeString(releaseDate);
        parcel.writeString(backdropURL);
        parcel.writeString(rating);
    }
}
