package com.example.expensetrackerproject.graphing;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensetrackerproject.OnFragmentInteractionListener;
import com.example.expensetrackerproject.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

public class GraphingFragment extends Fragment
{
    private GraphingController controller;
    private String username;
    private int month;
    private int year;

    private BarChart barChart;

    private static final String ARG_PARAM1 = "username";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "year";

    private OnFragmentInteractionListener mListener;

    public GraphingFragment(){}

    public static GraphingFragment newInstance(String username, int month, int year) {
        GraphingFragment fragment = new GraphingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        args.putInt(ARG_PARAM2, month);
        args.putInt(ARG_PARAM3, year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
            year = getArguments().getInt(ARG_PARAM3);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Expenses");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graphing, container, false);


        controller = new GraphingController(this.getActivity());

        barChart = view.findViewById(R.id.barchart);

        displayExpenses();
//        displayIncomeVsExpenses();

        return view;
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

    public void displayExpenses()
    {
        BarData barData = controller.chartMonthlyTransactions(
                username, "expense", month, year);

        barChart.setData(barData);
    }

    public void displayIncomeVsExpenses()
    {
        BarData barData = controller.chartIncomeVsExpense(
                username, "expense", month, year);

        barChart.setData(barData);

    }
}
