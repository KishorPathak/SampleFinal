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
public class DistanceCoveredFragment extends Fragment {
    public static final String TAG = DistanceCoveredFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private DistanceCoveredFragmentInteractionListener mListener;
    private View view;

    public DistanceCoveredFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DistanceCoveredFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DistanceCoveredFragment newInstance() {
        DistanceCoveredFragment fragment = new DistanceCoveredFragment();
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
        view = inflater.inflate(R.layout.fragment_distance_covered, container, false);
        updateDistanceCovered(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void updateDistanceCovered(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view, SectionsPagerAdapter.FragmentIndex.DISTANCE_COVERED);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(firstTime) {
            if (context instanceof DistanceCoveredFragmentInteractionListener) {
                mListener = (DistanceCoveredFragmentInteractionListener) context;
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
    public interface DistanceCoveredFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(View view, int layoutId);
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
            updateDistanceCovered(view);
        }
    }
}