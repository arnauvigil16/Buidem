package com.example.buidemapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        if (position == 0) {
            return new fragment_Maquinas();
        }

        if (position == 1) {
            return new fragment_Tipus_maquina();
        }

        if (position == 2){
            return new fragment_Zonas();
        }

        if (position == 3){
            return new fragment_Mapas();
        }

        else {
            return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
