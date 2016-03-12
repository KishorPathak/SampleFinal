package com.semicolon.stayfit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.semicolon.stayfit.adapter.CardAdapter;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class FoodCalorieFragment extends Fragment {
    public static final String TAG = FoodCalorieFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match
    private FoodCalInteractionListener mListener;
    private View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    public FoodCalorieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalorieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodCalorieFragment newInstance() {
        FoodCalorieFragment fragment = new FoodCalorieFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
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
            if (context instanceof FoodCalInteractionListener) {
                mListener = (FoodCalInteractionListener) context;
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
    public interface FoodCalInteractionListener {
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
        /*if(!firstTime) {
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.circularProgressBar);
            progressBar.setProgress(0);
            updateCalories(view);
        }*/
    }
}