package com.bignerdranch.android.gaba;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {

    // Tag for logging
    private static final String TAG = "DetailFragment";

    public DetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_detail_list, container, false);

            final TextView textView = rootView.findViewById(R.id.recipe_name_heading_tv);

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
