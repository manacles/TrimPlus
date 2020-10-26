package com.tbs.trimplus.module.bible.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.module.bible.fragment.BibleViewPagerFragment;

import java.util.ArrayList;

public class BibleViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Catalog> selectedCatalogs;

    public BibleViewPagerAdapter(@NonNull Context context, ArrayList<Catalog> selectedCatalogs) {
        super((FragmentActivity) context);
        this.selectedCatalogs = selectedCatalogs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        BibleViewPagerFragment fragment = new BibleViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return selectedCatalogs.size();
    }
}
