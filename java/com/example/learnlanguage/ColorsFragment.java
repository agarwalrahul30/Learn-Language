package com.example.learnlanguage;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ColorsFragment extends Fragment {

    private MediaPlayer media;
    private MediaPlayer.OnCompletionListener completed = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager audio;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                        releaseMediaPlayer();

                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        media.pause();
                        media.seekTo(0);
                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        media.pause();
                        media.seekTo(0);
                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                        media.start();
                }
            };


    public ColorsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Red","wetetti", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("Green","chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("Brown","takaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("Gray","topoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("Black","kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("White","kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("Dusty Yellow","topiise", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("Mustard Yellow","chiwiite", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        ListView list = rootView.findViewById(R.id.list);

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.colors_category);
        list.setAdapter(itemsAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currentWord = words.get(i);
                releaseMediaPlayer();

                int result = audio.requestAudioFocus(audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    media = MediaPlayer.create(getActivity(), currentWord.getAudioResourceId());
                    media.start();
                    media.setOnCompletionListener(completed);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if(media != null){
            media.release();
            media = null;
            audio.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}