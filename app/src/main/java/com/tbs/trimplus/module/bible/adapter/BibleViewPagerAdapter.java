package com.tbs.trimplus.module.bible.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.module.bible.fragment.BibleViewPagerFragment;

import java.util.ArrayList;

public class BibleViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Catalog> selectedCatalogs;
    private Context context;

    public BibleViewPagerAdapter(@NonNull Context context, ArrayList<Catalog> selectedCatalogs) {
        super((FragmentActivity) context);
        this.selectedCatalogs = selectedCatalogs;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new BibleViewPagerFragment(context, selectedCatalogs.get(position), position);
    }

    @Override
    public int getItemCount() {
        return selectedCatalogs.size();
    }
}
