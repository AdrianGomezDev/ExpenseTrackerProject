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

public class HelpFragment extends Fragment{


    private OnFragmentInteractionListener mListener;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Help.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Help");
        String content = "Need help navigating through our app and using it to the fullest?";
        View hview = inflater.inflate(R.layout.fragment_help, container, false);
        TextView help_content =  hview.findViewById(R.id.help_content);
        TextView points =  hview.findViewById(R.id.help_points);
        String pcontent= "\nExpense button is used to subtract from daily allowance. " +
                         "\nDeposit button adds money into savings. " +
                         "\nWithdarw button takes money from savings." ;

        points.setText(pcontent);
        TextView points_2 =  hview.findViewById(R.id.help_points_2);
        String pcontent_2= "\nThe graph demonstrates Income vs Expense based off of the month, and " +
                "is just a view, can not be edited by a user.  ";

        points_2.setText(pcontent_2);
        help_content.setText(content);
        return hview;
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

