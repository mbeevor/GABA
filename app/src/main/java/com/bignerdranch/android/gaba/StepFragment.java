package com.bignerdranch.android.gaba;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.gaba.Adapters.StepDetailAdapter;
import com.bignerdranch.android.gaba.Adapters.StepListAdapter;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by Matthew on 10/06/2018.
 */

public class StepFragment extends Fragment {

    public RecyclerView stepRecyclerview;
    public StepDetailAdapter stepDetailAdapter;
    private ArrayList<Steps> stepsList;

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle recipeBundleForFragment) {
        recipeBundleForFragment = getArguments();
        if (recipeBundleForFragment != null) {
            stepsList = recipeBundleForFragment.getParcelableArrayList(STEPS_LIST);
        };

        View rootView = inflater.inflate(R.layout.fragment_ingredients_list, container, false);

        stepRecyclerview = rootView.findViewById(R.id.ingredients_recycler_view);

        // create Linear LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        stepRecyclerview.setLayoutManager(layoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        stepRecyclerview.setHasFixedSize(true);

        stepDetailAdapter = new StepDetailAdapter(getContext());
        stepDetailAdapter.setData(stepsList);
        stepRecyclerview.setAdapter(stepDetailAdapter);
        return rootView;

    }

}
