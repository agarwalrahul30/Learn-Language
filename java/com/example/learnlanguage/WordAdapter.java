package com.example.learnlanguage;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        this.colorResourceId = colorResourceId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView DefaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        DefaultTextView.setText(currentWord.getDefaultTrans());

        TextView MiwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        MiwokTextView.setText(currentWord.getMiwokTrans());

        ImageView image = listItemView.findViewById(R.id.image);

        if(currentWord.hasImage()) {
            image.setImageResource(currentWord.getImageResourceId());
            image.setVisibility(View.VISIBLE);
        }
        else
            image.setVisibility(View.GONE);

        View textContainer = listItemView.findViewById(R.id.textContainer);

        int color = ContextCompat.getColor(getContext(), colorResourceId); //id -> color
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
