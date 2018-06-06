package com.bignerdranch.android.gaba;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;


public class DetailFragment extends Fragment {

    public RecyclerView detailRecyclerView;
    CardView ingredientsCardView;
    ArrayList<Steps> stepsList;

    // Tag for logging
    private static final String TAG = "DetailFragment";

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();


        Bundle bundle = getArguments();
        stepsList = bundle.getParcelableArrayList("stepsList");

        final View rootView = inflater.inflate(R.layout.fragment_activity_detail, container, false);

        ingredientsCardView = rootView.findViewById(R.id.ingredients_card_view);
        ingredientsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Click!", Toast.LENGTH_SHORT).show();
            }
        });

        detailRecyclerView = rootView.findViewById(R.id.detail_list_recylerview);

        // create Linear LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        detailRecyclerView.setLayoutManager(layoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        detailRecyclerView.setHasFixedSize(true);

        stepsList = new ArrayList<>();

//        stepsAdapter = new RecipeListAdapter(getContext(), new RecipeListAdapter.OnRecipeClickListener() {
//            @Override
//            public void onRecipeSelected(List<Recipe> recipes, int position) {
//                onRecipeClickListener.onRecipeSelected(recipeList, position);
//            }
//
//        });
//
//        recyclerView.setAdapter(recipeListAdapter);



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


}
