package com.bignerdranch.android.gaba;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.gaba.Model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

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
    private static MediaSessionCompat mediaSession;
    @BindView(R.id.thumbnail_iv)
    public ImageView thumbnailImage;
    private SimpleExoPlayer simpleExoPlayer;
    private Long playerPosition;
    private boolean playWhenReady;
    private String videoUrl;
    private PlaybackStateCompat.Builder playbackStateBuilder;
    private ComponentListener componentListener;
    private int currentWindow;

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

        if (simpleExoPlayer != null) {
            playerPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
        }

        savedInstanceState.putParcelableArrayList(STEPS_LIST, stepsList);
        savedInstanceState.putInt(POSITION, position);
        savedInstanceState.putLong(PLAYER_POSITION, playerPosition);
        savedInstanceState.putInt("current_window", currentWindow);
        savedInstanceState.putBoolean("state", playWhenReady);
    }

    // create view using intent
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){
            stepsList = savedInstanceState.getParcelableArrayList(STEPS_LIST);
            position = savedInstanceState.getInt(POSITION);
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            currentWindow = savedInstanceState.getInt("current_window");
            playWhenReady = savedInstanceState.getBoolean("state");
        }  else {
            Bundle recipeBundleForFragment = getArguments();
                stepsList = recipeBundleForFragment.getParcelableArrayList(STEPS_LIST);
                position = recipeBundleForFragment.getInt(POSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);
        ButterKnife.bind(this, rootView);

        // get position of current step
        Steps currentStep = stepsList.get(position);

        TextView textView = rootView.findViewById(R.id.step_tv);
        textView.setText(currentStep.getLongDescription());

        String thumbnailUrl = currentStep.getThumbnailUrl();

        // load thumbnail image, or hide if there isn't one
        if (TextUtils.isEmpty(thumbnailUrl)) {
            thumbnailImage.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
        } else {
            thumbnailImage.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(thumbnailUrl)
                    .into(thumbnailImage);
        }

        videoUrl = currentStep.getVideoUrl();

        // load video into media player, or hide if there isn't one
        if (TextUtils.isEmpty(videoUrl)) {
            playerView.setVisibility(View.GONE);
        } else {
            thumbnailImage.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
            componentListener = new ComponentListener();
            initializeMediaSession();
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
            playerPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            releasePlayer();
            mediaSession.setActive(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            playerPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23) || simpleExoPlayer == null) {
            initializePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            playerPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }


    // method to initialise player
    private void initializePlayer() {

        if (simpleExoPlayer == null) {

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.setPlayWhenReady(true);
            if (playerPosition != null) {
                simpleExoPlayer.seekTo(currentWindow, playerPosition);
            } else {
                simpleExoPlayer.seekTo(0, 0);
            }
            simpleExoPlayer.addListener(componentListener);
        }

            // Prepare the MediaSource.
            Uri mediaUri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(mediaUri);
            simpleExoPlayer.prepare(mediaSource, true, false);


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

    private MediaSource buildMediaSource(Uri mediaUri) {

        return new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), Util.getUserAgent(getContext(), "GABA")), new DefaultExtractorsFactory(), null, null);

    }


    private void initializeMediaSession() {

        mediaSession = new MediaSessionCompat(getContext(), StepDetailFragment.class.getSimpleName());
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);

        playbackStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(playbackStateBuilder.build());
        mediaSession.setCallback(new MediaSessionCallback());
        mediaSession.setActive(true);

    }

    // method to release player
    private void releasePlayer() {

        if (simpleExoPlayer != null) {
            playerPosition = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.removeListener(componentListener);
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }


    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            simpleExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            simpleExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            simpleExoPlayer.seekTo(0);
        }


    }


    private class ComponentListener implements ExoPlayer.EventListener {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
                playbackStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, simpleExoPlayer.getCurrentPosition(), 1f);
            } else if (playbackState == ExoPlayer.STATE_READY) {
                playbackStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, simpleExoPlayer.getCurrentPosition(), 1f);
            }

            mediaSession.setPlaybackState(playbackStateBuilder.build());

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }
    }
}
