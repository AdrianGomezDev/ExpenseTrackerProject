package com.example.expensetrackerproject.menu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensetrackerproject.OnFragmentInteractionListener;
import com.example.expensetrackerproject.R;
import com.example.expensetrackerproject.login.LoginController;

public class ProfileFragment extends Fragment{

    private LoginController controller;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String username;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String username) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        controller =  new LoginController(getActivity());

        final EditText fname = view.findViewById(R.id.profile_fname);
        final EditText lname = view.findViewById(R.id.profile_lname);
        final EditText email = view.findViewById(R.id.profile_email);
        final EditText age = view.findViewById(R.id.profile_age);
        final EditText pass = view.findViewById(R.id.profile_password);
        Button submit = view.findViewById(R.id.btn_profile_submit);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String f = fname.getText().toString();
                String l = lname.getText().toString();
                String e = email.getText().toString();
                String a = age.getText().toString();
                String p = pass.getText().toString();
                if(!f.isEmpty() && !l.isEmpty() && !e.isEmpty() && !a.isEmpty() && !p.isEmpty()){
                    if(controller.login(username, p)){
                        int ageNum = Integer.parseInt(a);
                        controller.updateProfile(username, f, l, e, ageNum);
                        Toast.makeText(getActivity(), "Profile has been updated", Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(getActivity(), "Confirmation password is incorrect", Toast.LENGTH_LONG).show();
                    }

                }
                else
                    Toast.makeText(getActivity(), "All fields must be completed", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("Home", uri);
        }
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

