package com.bignerdranch.android.gaba;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

import static com.bignerdranch.android.gaba.Model.Keys.POSITION;
import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by Matthew on 10/06/2018.
 */

public class StepDetailFragment extends Fragment {

    public int position;
    private ArrayList<Steps> stepsList;

    public StepDetailFragment() {
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
            position = recipeBundleForFragment.getInt(POSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        TextView textView = rootView.findViewById(R.id.step_tv);

        if (textView != null) {

            Steps currentStep = stepsList.get(position);
            textView.setText(currentStep.getLongDescription());

        }
        return rootView;

    }

}
