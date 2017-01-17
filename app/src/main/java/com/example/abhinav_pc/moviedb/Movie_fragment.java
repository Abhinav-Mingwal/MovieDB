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


public class Movie_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MoviesSectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private TabLayout mtabLayout;
    public Movie_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Movie_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Movie_fragment newInstance(String param1, String param2) {
        Movie_fragment fragment = new Movie_fragment();
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
        View view=inflater.inflate(R.layout.fragment_movie_fragment,container,false);
        mSectionsPagerAdapter = new MoviesSectionsPagerAdapter(this.getChildFragmentManager());
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarMovie);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        setSupportActionBar(toolbar);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.containerMovie);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mtabLayout = (TabLayout) view.findViewById(R.id.tabsMovie);
        mtabLayout.setupWithViewPager(mViewPager);
        return view;
    }
    public class MoviesSectionsPagerAdapter extends FragmentPagerAdapter {
        public boolean isMovie;
        public MoviesSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if (position == 0) {
                return popular_movies.newInstance(position + 1);
            } else if (position == 1) {
                return top_rated_movies.newInstance(position + 1);
            } else if (position == 2) {
                return upcoming_movies.newInstance(position + 1);
            } else {
                return InTheater_movies.newInstance(position + 1);
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
                    return "Upcoming";
                case 3:
                    return "In Theaters";
            }
            return null;
        }
    }

}
