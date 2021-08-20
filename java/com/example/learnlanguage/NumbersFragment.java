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


public class NumbersFragment extends Fragment {

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
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        media.pause();
                        media.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                        media.start();
                }
            };

    private void releaseMediaPlayer() {
        if (media != null) {
            media.release();
            media = null;
            audio.abandonAudioFocus(audioFocusChangeListener);
        }
    }


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.words_list, container, false);
        audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("One","lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("Two","otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("Three","tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("Four","oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("Five","massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("Six","temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("Seven","kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("Eight","kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("Nine","wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("Ten","na'aacha", R.drawable.number_ten, R.raw.number_ten));

        ListView list = rootView.findViewById(R.id.list);

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.numbers_category);
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
}