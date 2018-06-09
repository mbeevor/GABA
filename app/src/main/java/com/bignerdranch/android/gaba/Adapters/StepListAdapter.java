package com.bignerdranch.android.gaba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Steps;
import com.bignerdranch.android.gaba.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbeev on 08/06/2018.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.SteplistAdapterViewHolder> {

    private List<Steps> stepsList;
    private Context context;

    //default constructor
    public StepListAdapter(Context thisContext) {
        context = thisContext;
        setHasStableIds(true);
    }

    @Override
    public SteplistAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForSteps = R.layout.list_item_detail;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutForSteps, parent, false);
        SteplistAdapterViewHolder viewHolder = new SteplistAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SteplistAdapterViewHolder holder, int position) {

        if (stepsList != null) {

            Steps steps = stepsList.get(position);
            String id = steps.getStepId();
            String summary = steps.getShortDescription();
            String description = steps.getLongDescription();
            String video = steps.getVideoUrl();
            String thumbnail = steps.getThumbnailUrl();

            SteplistAdapterViewHolder viewHolder = holder;
            viewHolder.idTextView.setText(id);

        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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

    public class SteplistAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView idTextView;

        public SteplistAdapterViewHolder(final View itemView) {
            super(itemView);

            if (itemView != null) {
                idTextView = itemView.findViewById(R.id.step_name_tv);
            }
        }
    }
}