package com.example.abhinav_pc.moviedb;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TV_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TVSectionsPagerAdapter tvSectionPagerAdapter;

    private ViewPager mViewPager;

    private TabLayout mtabLayout;

    public TV_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TV_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TV_Fragment newInstance(String param1, String param2) {
        TV_Fragment fragment = new TV_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tv_,container,false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbartv);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        tvSectionPagerAdapter = new TVSectionsPagerAdapter(this.getChildFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.containertv);
        mViewPager.setAdapter(tvSectionPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mtabLayout = (TabLayout) view.findViewById(R.id.tabstv);
        mtabLayout.setupWithViewPager(mViewPager);
        return view;
        //        return inflater.inflate(R.layout.fragment_tv_, container, false);
    }
    public class TVSectionsPagerAdapter extends FragmentPagerAdapter {

        public TVSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return popular_TVshow.newInstance(position + 1);
            } else if (position == 1) {
                return top_rated_TVshow.newInstance(position + 1);
            } else if (position == 2) {
                return airing_today_TVshow.newInstance(position + 1);
            } else {
                return on_air_TVshow.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Popular";
                case 1:
                    return "Top Rated";
                case 2:
                    return "Airing Today";
                case 3:
                    return "On Air";
            }
            return null;
        }
    }
}
