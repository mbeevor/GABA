package com.bignerdranch.android.gaba;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DetailFragment extends Fragment {

    private static final String RECIPE_LIST = "recipeList";
    private static final String POSITION = "position";

    // Tag for logging
    private static final String TAG = "DetailFragment";

    private List<Integer> recipeListId;
    private int position;


    public DetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {

            recipeListId = savedInstanceState.getIntegerArrayList(RECIPE_LIST);
            position = savedInstanceState.getInt(POSITION);

        }

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            final TextView textView = rootView.findViewById(R.id.recipe_name_heading_tv);
            textView.setText(recipeListId.get(position));

            return rootView;

    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(RECIPE_LIST, (ArrayList<Integer>) recipeListId);
        outState.putInt(POSITION, position);
    }
}
