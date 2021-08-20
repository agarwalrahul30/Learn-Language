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

public class FamilyFragment extends Fragment {

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

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        // Inflate the layout for this fragment
        audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Father","epe", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("Mother","eta", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("Son","angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("Daughter","tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("Older Brother","taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("Younger Brother","chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("Older Sister","tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("Younger Sister","kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("GrandMother","ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("GrandFather","paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        ListView list = rootView.findViewById(R.id.list);

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.family_category);
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
        if(media != null) {
            media.release();
            media = null;

            audio.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}