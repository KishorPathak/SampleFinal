package com.semicolon.stayfit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class CalorieFragment extends Fragment {
    public static final String TAG = CalorieFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match
    private CalorieFragmentInteractionListener mListener;
    private View view;

    public CalorieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalorieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalorieFragment newInstance() {
        CalorieFragment fragment = new CalorieFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calorie, container, false);
        updateCalories(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void updateCalories(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view, SectionsPagerAdapter.FragmentIndex.CALORIES_EXPENDED);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(firstTime) {
            if (context instanceof CalorieFragmentInteractionListener) {
                mListener = (CalorieFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement DistanceCoveredFragmentInteractionListener");
            }
            firstTime = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface CalorieFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(View rootView, int layoutId);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart()" + isAdded() + "-" + isMenuVisible() + "-" + isHidden() + "-" + isVisible());
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()" + isAdded() + "-" + isMenuVisible() + "-" + isHidden() + "-" + isVisible());
        super.onResume();
    }

    private boolean firstTime = true;
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        Log.d(TAG, "Menu Visibility " + view);
        if(!firstTime) {
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.circularProgressBar);
            progressBar.setProgress(0);
            updateCalories(view);
        }
    }
}