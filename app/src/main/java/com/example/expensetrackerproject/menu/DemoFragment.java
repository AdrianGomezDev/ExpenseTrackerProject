package com.example.expensetrackerproject.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetrackerproject.OnFragmentInteractionListener;
import com.example.expensetrackerproject.R;
import com.example.expensetrackerproject.expense.ExpenseController;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoFragment extends Fragment implements View.OnClickListener
{
    private ExpenseController controller;

    private String username;

    private TextView savings;
    private TextView allowance;
    private TextView expenses;
    private TextView balance;

    private Button btnExpense;
    private Button btnDeposit;
    private Button btnWithdraw;
    private Button btnDemo;

    private EditText input;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static final String ARG_PARAM1 = "param1";
    private OnFragmentInteractionListener mListener;

    public DemoFragment(){}
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of Demo fragment .
     */
    public static DemoFragment newInstance(String username) {
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Expenses");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        controller = new ExpenseController(this.getActivity());

        savings = view.findViewById(R.id.savings);
        displaySavings();
        allowance = view.findViewById(R.id.allowance);
        displayAllowance();
        expenses = view.findViewById(R.id.spent);
        displayExpenses();
        balance = view.findViewById(R.id.balance);
        displayBalance();

        input = view.findViewById(R.id.input);

        btnExpense = view.findViewById(R.id.expense);
        btnExpense.setOnClickListener(this);
        btnDeposit = view.findViewById(R.id.deposit);
        btnDeposit.setOnClickListener(this);
        btnWithdraw = view.findViewById(R.id.withdraw);
        btnWithdraw.setOnClickListener(this);

        btnDemo = view.findViewById(R.id.btn_demo);
        btnDemo.setVisibility(View.VISIBLE);
        btnDemo.setOnClickListener(this);

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

    public void displaySavings()
    {
        double amount = controller.getSavings(username);
        String displayAmount = String.valueOf(decimalFormat.format(amount));
        savings.setText(displayAmount);
    }

    public void displayAllowance()
    {
        double amount = controller.getAllowance(username);
        String displayAmount = String.valueOf(decimalFormat.format(amount));
        allowance.setText(displayAmount);
    }

    public void displayExpenses()
    {
        double amount = controller.getTodaysExpenses(username);
        String displayAmount = String.valueOf(decimalFormat.format(amount));
        expenses.setText(displayAmount);
    }

    public void displayBalance()
    {
        double allowed = controller.getAllowance(username);
        double expense = controller.getTodaysExpenses(username);

        double diff = allowed - expense;
        String displayAmount = String.valueOf(decimalFormat.format(diff));
        balance.setText(displayAmount);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.expense: {
                String text = input.getText().toString();
                if (!text.isEmpty())
                {
                    double amount = Double.parseDouble(text);
                    controller.insertTransaction(username, "expense", amount);

                    displayExpenses();
                    displayBalance();

                }
                break;
            }

            case R.id.deposit: {
                String text = input.getText().toString();
                if (!text.isEmpty())
                {
                    double amount = Double.parseDouble(text);
                    controller.insertTransaction(username, "savings", amount);

                    displaySavings();
                }
                break;
            }

            case R.id.withdraw:{
                String text = input.getText().toString();
                if(!text.isEmpty())
                {
                    double amount = Double.parseDouble(text);
                    if(amount <= controller.getSavings(username)) {
                        controller.insertTransaction(username, "withdrawal", amount);
                        displaySavings();
                    }
                    else
                        Toast.makeText(this.getActivity(), "Withdrawal amount exceeds Savings", Toast.LENGTH_LONG).show();

                }
            }
            case R.id.btn_demo:
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String todaysDate = dateFormat.format(date);

                controller.balanceCheck(username, todaysDate);
                displaySavings();
                displayAllowance();
                displayExpenses();
                displayBalance();
            }

        }
    }
}
