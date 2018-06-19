package com.bignerdranch.android.gaba;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.gaba.Model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bignerdranch.android.gaba.Model.Keys.PLAYER_POSITION;
import static com.bignerdranch.android.gaba.Model.Keys.POSITION;
import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by Matthew on 10/06/2018.
 */

public class StepDetailFragment extends Fragment {

    public int position;
    private ArrayList<Steps> stepsList;
    @BindView(R.id.player_view)
    public SimpleExoPlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private Long playerPosition;
    private boolean getPlayerWhenReady;
    private String videoUrl;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    // save data on rotation
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(STEPS_LIST, stepsList);
        savedInstanceState.putInt(POSITION, position);
        savedInstanceState.putLong(PLAYER_POSITION, playerPosition);
        savedInstanceState.putBoolean("state", getPlayerWhenReady);
    }

    // create view using intent
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle recipeBundleForFragment = getArguments();
        if (recipeBundleForFragment != null) {
            stepsList = recipeBundleForFragment.getParcelableArrayList(STEPS_LIST);
            position = recipeBundleForFragment.getInt(POSITION);
        } else {
            stepsList = savedInstanceState.getParcelableArrayList(STEPS_LIST);
            position = savedInstanceState.getInt(POSITION);
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            getPlayerWhenReady = savedInstanceState.getBoolean("state");
        }

        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);
        ButterKnife.bind(this, rootView);

        // get position of current step
        Steps currentStep = stepsList.get(position);

        TextView textView = rootView.findViewById(R.id.step_tv);
        textView.setText(currentStep.getLongDescription());

        videoUrl = currentStep.getVideoUrl();
        // load video into media player, or hide if there isn't one
        if (TextUtils.isEmpty(videoUrl)) {
            playerView.setVisibility(View.GONE);
        } else {
            playerView.setVisibility(View.VISIBLE);
            initializePlayer();
        }

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (simpleExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <=23) || simpleExoPlayer == null) {
            initializePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }



    // method to initialise player
    private void initializePlayer() {
        if (simpleExoPlayer == null) {

            // start videos from beginning
            playerPosition = null;

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(simpleExoPlayer);


            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "GABA");
            Uri mediaUri = Uri.parse(videoUrl);
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(getPlayerWhenReady);
            simpleExoPlayer.seekTo(playerPosition);

            // fill screen if device is rotated
            if (getActivity().getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) {
                ViewGroup.LayoutParams params = playerView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                playerView.setLayoutParams(params);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

            }

        }


    }

    // method to release player
    private void releasePlayer() {

        if (simpleExoPlayer != null) {
            playerPosition = simpleExoPlayer.getCurrentPosition();
            getPlayerWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }


}
