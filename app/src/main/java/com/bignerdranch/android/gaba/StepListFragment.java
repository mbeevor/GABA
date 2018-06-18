package com.bignerdranch.android.gaba;

import android.content.Context;
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
import java.util.List;

import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by mbeev on 08/06/2018.
 */

public class StepListFragment extends Fragment {

    public RecyclerView stepRecyclerview;
    public StepListAdapter stepListAdapter;
    public ArrayList<Steps> stepsList;
    public StepListAdapter.OnStepClickHandler onStepSelected;

    // empty constructor
    public StepListFragment() {    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onStepSelected = (StepListAdapter.OnStepClickHandler) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onRecipeClickListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle intent) {

        super.onCreateView(inflater, container, intent);
        intent = getArguments();
        if (getArguments() != null) {
            stepsList = intent.getParcelableArrayList(STEPS_LIST);
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
            public void onItemClick(List<Steps> steps, int position) {
                onStepSelected.onItemClick(stepsList, position);
            }

        });

        stepListAdapter.setData(stepsList);
        stepRecyclerview.setAdapter(stepListAdapter);
        return rootView;

    }

  }
