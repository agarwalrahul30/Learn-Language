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


public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.words_list, container, false);

        // Inflate the layout for this fragment
        audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Where are you going?","minto wuksus?", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?","tinne oyaase'ne?", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...","oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michekses?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good","kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","eenes'aa", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming","hee'eenem", R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming","eenem", R.raw.phrase_im_coming));
        words.add(new Word("Let's go","yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here","enni'nem", R.raw.phrase_come_here));

        ListView list = rootView.findViewById(R.id.list);

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.phrases_category);
        list.setAdapter(itemsAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currentWord = words.get(i);
                releaseMediaPlayer();

                int result = audio.requestAudioFocus(audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
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
        if(media != null) {
            media.release();
            media = null;

            audio.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}