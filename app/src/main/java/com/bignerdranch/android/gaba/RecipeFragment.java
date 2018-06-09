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

/**
 * Created by mbeev on 08/06/2018.
 */

public class RecipeFragment extends Fragment {

    public RecyclerView stepRecyclerview;
    public StepListAdapter stepListAdapter;
    ArrayList<Steps> stepList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            stepList = savedInstanceState.getParcelableArrayList("ingredientsList");

        };
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_activity_recipe, container, false);

        stepRecyclerview = rootView.findViewById(R.id.step_list_recylerview);

        // create Linear LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        stepRecyclerview.setLayoutManager(layoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        stepRecyclerview.setHasFixedSize(true);

        stepListAdapter = new StepListAdapter(getContext());
        stepListAdapter.setData(stepList);
        stepRecyclerview.setAdapter(stepListAdapter);
        return rootView;
    }
}
