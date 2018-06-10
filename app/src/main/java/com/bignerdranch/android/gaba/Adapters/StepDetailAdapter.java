package com.bignerdranch.android.gaba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.gaba.Model.Steps;
import com.bignerdranch.android.gaba.R;

import java.util.ArrayList;

/**
 * Created by mbeev on 10/06/2018.
 */

public class StepDetailAdapter extends RecyclerView.Adapter<StepDetailAdapter.StepDetailAdapterViewHolder> {

    private ArrayList<Steps> stepsList;
    private Context context;

    public StepDetailAdapter(Context thisContext) {
        context = thisContext;
        setHasStableIds(true);
    }

    @Override
    public StepDetailAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForIngredients = R.layout.list_item_step_detail;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(layoutForIngredients, parent, false);
        StepDetailAdapterViewHolder viewHolder = new StepDetailAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepDetailAdapterViewHolder holder, int position) {

        Steps steps = stepsList.get(position);
        String id = steps.getStepId();
        String description = steps.getLongDescription();
        String video = steps.getVideoUrl();
        String thumbnail = steps.getThumbnailUrl();

        StepDetailAdapterViewHolder viewHolder = holder;
        viewHolder.stepTextView.setText(description);
    }

    @Override
    public int getItemCount() {

        if (stepsList == null) {
            return 0;
        } else {
            return stepsList.size();
        }
    }

    public void setData(ArrayList<Steps> steps) {

        stepsList = steps;
        notifyDataSetChanged();
    }

    public class StepDetailAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView stepTextView;

        public StepDetailAdapterViewHolder(final View itemView) {
            super(itemView);

            if (itemView != null) {
                stepTextView = itemView.findViewById(R.id.step_tv);

            }
        }
    }
}
