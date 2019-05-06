package com.example.expensetrackerproject.expense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.expensetrackerproject.SavedPreferences;
import com.example.expensetrackerproject.graphing.GraphingFragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class ExpenseFragment extends Fragment implements View.OnClickListener
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

    private EditText input;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static final String ARG_PARAM1 = "param1";
    private OnFragmentInteractionListener mListener;

    public ExpenseFragment(){}

    public static ExpenseFragment newInstance(String username) {
        ExpenseFragment fragment = new ExpenseFragment();
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

        //Checks if first time launch, if so registers user and stores date
        if(SavedPreferences.isFirstLaunch(getActivity(), username)){
            SavedPreferences.registerUser(getActivity(), username);
            SavedPreferences.storeDate(getActivity(), username);
        }
        else if(!SavedPreferences.accessedToday(getActivity(), username)){
            String storedDate = SavedPreferences.getStoredDate(getActivity(), username);
            controller.balanceCheck(username, storedDate);

            SavedPreferences.storeDate(getActivity(), username);

        }


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

        }
    }
}
