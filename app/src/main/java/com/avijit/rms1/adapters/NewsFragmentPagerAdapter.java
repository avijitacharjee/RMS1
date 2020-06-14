package com.avijit.rms1.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.ui.news_fragments.NewsDynamicFragment;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Avijit Acharjee on 6/12/2020 at 9:06 PM.
 * Email: avijitach@gmail.com.
 */
public class NewsFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;
    List<String> tabTitle;
    List<News> newsList;
    public NewsFragmentPagerAdapter(FragmentManager fragmentManager, int numberOfTabs, List<String> tabTitle, List<News> newsList){
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numberOfTabs = numberOfTabs;
        this.tabTitle = tabTitle;
        this.newsList = newsList;
    }
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return NewsDynamicFragment.newInstance(tabTitle.get(position),new Gson().toJson(newsList));
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }
}
