package com.semicolon.stayfit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public static class FragmentDescription {
        private int index;
        private String title;

        public int getIndex() {
            return index;
        }

        public String getTitle() {
            return title;
        }

        public FragmentDescription(int index, String title) {
            this.index = index;
            this.title = title;
        }
    }

    public FragmentDescription[] getFragmentDescriptions() {
        return fragmentDescriptions;
    }

    private FragmentDescription[] fragmentDescriptions = {
            new FragmentDescription(FragmentIndex.STEP_COUNT, "Step Count"),
            new FragmentDescription(FragmentIndex.DISTANCE_COVERED, "Distance Covered"),
            new FragmentDescription(FragmentIndex.CALORIES_EXPENDED, "Calories Expended"),
            new FragmentDescription(FragmentIndex.CALORIES_EXPENDED, "Food Calories")
    };

    public static class FragmentIndex {
        public static final int STEP_COUNT = 0;
        public static final int DISTANCE_COVERED = 1;
        public static final int CALORIES_EXPENDED = 2;
        public static final int FOOD_CALORIES = 3;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position < fragmentDescriptions.length) {
            switch (position) {
                case FragmentIndex.STEP_COUNT: fragment = new StepCountFragment(); break;
                case FragmentIndex.DISTANCE_COVERED: fragment = new DistanceCoveredFragment(); break;
                case FragmentIndex.CALORIES_EXPENDED: fragment = new CalorieFragment(); break;
                case FragmentIndex.FOOD_CALORIES: fragment = new FoodCalorieFragment(); break;
                //default: fragment = PlaceholderFragment.newInstance(position);
            }
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentDescriptions.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position < fragmentDescriptions.length) {
            return fragmentDescriptions[position].getTitle();
        }

        return super.getPageTitle(position);
    }
}