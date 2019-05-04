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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public GraphingFragment(){}

    public static GraphingFragment newInstance(String param1, String param2) {
        GraphingFragment fragment = new GraphingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Expenses");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);


        controller = new GraphingController(this.getActivity());
        username = getActivity().getIntent().getStringExtra("USERNAME");
        month = getActivity().getIntent().getIntExtra("MONTH", 0);
        year = getActivity().getIntent().getIntExtra("YEAR", 0);

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
