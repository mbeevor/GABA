package com.bignerdranch.android.gaba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.gaba.Model.Steps;
import com.bignerdranch.android.gaba.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mbeev on 08/06/2018.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.SteplistAdapterViewHolder> {

    private List<Steps> stepsList;
    private Context context;
    private OnStepClickHandler onStepClickHandler;

    public interface OnStepClickHandler {
        void onItemClick(View item, int position);
    }

    //default constructor
    public StepListAdapter(Context thisContext, OnStepClickHandler handler) {
        context = thisContext;
        onStepClickHandler = handler;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SteplistAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForSteps = R.layout.list_item_step;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(layoutForSteps, parent, false);
        SteplistAdapterViewHolder viewHolder = new SteplistAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SteplistAdapterViewHolder holder, int position) {

        if (stepsList != null) {

            Steps steps = stepsList.get(position);
            String summary = steps.getShortDescription();

            SteplistAdapterViewHolder viewHolder = holder;
            viewHolder.summaryTextView.setText(summary);

        }

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

    public class SteplistAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_name_tv) private TextView summaryTextView;

        private SteplistAdapterViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (stepsList != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onStepClickHandler.onItemClick(view, position);
                }
            }

        }
    }
}