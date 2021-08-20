package com.example.learnlanguage;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String TabTitles[] = new String[]{"Numbers","Family", "Colors", "Phrases"};
    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        }
        else if (position == 1){
            return new FamilyFragment();
        }
        else if(position == 2){
            return new ColorsFragment();
        }
        else
            return new PhrasesFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TabTitles[position];
    }
}
