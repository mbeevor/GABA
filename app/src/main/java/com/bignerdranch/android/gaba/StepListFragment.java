package com.bignerdranch.android.gaba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.gaba.Adapters.StepListAdapter;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

import static com.bignerdranch.android.gaba.Model.Keys.NUMBER_SERVINGS;
import static com.bignerdranch.android.gaba.Model.Keys.POSITION;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_ID;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_IMAGE;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_NAME;
import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by mbeev on 08/06/2018.
 */

public class StepListFragment extends Fragment {

    public RecyclerView stepRecyclerview;
    public StepListAdapter stepListAdapter;
    public ArrayList<Steps> stepsList;
    public StepListAdapter.OnStepClickHandler onStepSelected;
    private String recipeId;
    private String recipeName;
    private String numberServings;
    private String recipeImage;

    // empty constructor
    public StepListFragment() {    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle intent) {

        super.onCreateView(inflater, container, intent);
        intent = getArguments();
        if (getArguments() != null) {
            recipeId = intent.getString(RECIPE_ID);
            recipeName = intent.getString(RECIPE_NAME);
            stepsList = intent.getParcelableArrayList(STEPS_LIST);
            numberServings = intent.getString(NUMBER_SERVINGS);
            recipeImage = intent.getString(RECIPE_IMAGE);
            stepsList = intent.getParcelableArrayList(STEPS_LIST);
        }

        View rootView = inflater.inflate(R.layout.fragment_activity_list, container, false);

        stepRecyclerview = rootView.findViewById(R.id.list_recyclerview);

        // create Linear LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        stepRecyclerview.setLayoutManager(layoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        stepRecyclerview.setHasFixedSize(true);

        stepListAdapter = new StepListAdapter(getContext(), new StepListAdapter.OnStepClickHandler() {
            @Override
            public void onItemClick(View item, int position) {

                Fragment stepDetailFragment = new StepDetailFragment();
                Bundle stepBundleForFragment = new Bundle();
                stepBundleForFragment.putInt(POSITION, position);
                stepBundleForFragment.putParcelableArrayList(STEPS_LIST, stepsList);

                stepDetailFragment.setArguments(stepBundleForFragment);

                getFragmentManager().beginTransaction()
                        .replace(R.id.recipe_instruction_container, stepDetailFragment)
                        .commit();

            }
        });

        stepListAdapter.setData(stepsList);
        stepRecyclerview.setAdapter(stepListAdapter);
        return rootView;

    }

  }
