package com.example.expensetrackerproject.menu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.expensetrackerproject.OnFragmentInteractionListener;
import com.example.expensetrackerproject.R;

public class AboutFragment extends Fragment{
    private OnFragmentInteractionListener mListener;

    public AboutFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment About.
     */
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("About");
        String content = "Expense Tracker is an app that will help keep you on track with your spending goals and help keep you aware of what your daily allowance is.The  app does the math for you!";
        // Inflate the layout for this fragment
        View aview = inflater.inflate(R.layout.fragment_about, container, false);
        TextView about_content =  aview.findViewById(R.id.About_content);
        TextView points =  aview.findViewById(R.id.points);
        String pcontent= "\n        We have an expense tracker for the day in which you have the ability to deposit or withdraw from savings amount, and creating expenses which update the amount you have left to spend for the day!" +
                "\n\n      Our graphs will help show your daily spending trends for the month, so you can improve or keep with great habits from the month. \n";
        points.setText(pcontent);
        about_content.setText(content);
        return aview;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
