package com.example.expensetrackerproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensetrackerproject.R;
import com.example.expensetrackerproject.data.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class NewUserActivity extends AppCompatActivity
{


    private EditText createUserName;      //not explicitly necessary for validation, may be needed later
    private EditText createPassword;
    private EditText createRetypePass;
    private EditText createFirstName;
    private EditText createLastName;
    private EditText createEmail;
    private EditText createAge;
    //private EditText createIncome;
    //private EditText createSavings;

    private Button btnCancel;
    private Button btnSave;
    private Button btnReturn2login;

    private LoginController controller;

    List<String> fieldData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        controller = new LoginController(this);
        fieldData = new ArrayList<>();


        createUserName = (EditText)findViewById(R.id.create_username);
        createPassword = (EditText)findViewById(R.id.create_password);
        createRetypePass = (EditText)findViewById(R.id.create_retypePassword);
        createFirstName = (EditText)findViewById(R.id.create_firstname);
       createLastName = (EditText)findViewById(R.id.create_lastname);
        createEmail = (EditText)findViewById(R.id.create_email);
        createAge = (EditText)findViewById(R.id.create_age);
        //createIncome = (EditText)findViewById(R.id.create_income);
        //createSavings = (EditText)findViewById(R.id.create_savings);

        //cancel button
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return2login();
            }
        });

        //return to login button
        btnReturn2login = (Button)findViewById(R.id.btn_return);
        btnReturn2login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return2login();
            }
        });

        //save button
        btnSave = (Button)findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields(fieldData) && validatePassword() && validateEmail() && validateAge()) {

                    Toast.makeText(getApplicationContext(), "New user added", Toast.LENGTH_LONG).show();
                    btnReturn2login.setVisibility(View.VISIBLE);

                    controller.addUser(fieldData);
                }

            }
        });
    }


    /**
     * Returns to main activity
     */
    private void return2login()
    {
        Intent intentReturnToMain = new Intent(getApplicationContext(), LoginActivity.class);
        intentReturnToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentReturnToMain);
    }

    /**
     * Validates all fields completed, displays toast if invalid
     *
     * @param fieldData  user input text fields
     * @return boolean  true if all complete, false otherwise
     */
    private boolean validateFields(List<String> fieldData)
    {
        fieldData.clear();

        int[] ids = new int[]{
                R.id.create_username,
                R.id.create_password,
                R.id.create_retypePassword,
                R.id.create_firstname,
                R.id.create_lastname,
                R.id.create_email,
                R.id.create_age,
                R.id.create_income,
                R.id.create_savings
        };
        for(int id : ids){
            EditText editText = (EditText)findViewById(id);
            String field = editText.getText().toString();
            if(field.equals("")){
                Toast.makeText(getApplicationContext(), "All fields must be completed", Toast.LENGTH_LONG).show();
                return false;
            }
            fieldData.add(field);

        }

        return true;
    }






    /**
     * Validates password and retype password match, displays toast if invalid
     *
     * @return boolean  true if match, false otherwise
     */
    private boolean validatePassword()
    {
         if(!createPassword.getText().toString().equals(createRetypePass.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Validates email format matches *@*.*, displays toast if invalid
     *
     * @return boolean true if format matches, false otherwise
     */
    private boolean validateEmail()
    {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(createEmail.getText().toString());
        if(!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Validates age between 1 and 99, displays toast if invalid
     *
     * @return boolean  true if in range, false otherwise
     */
    private boolean validateAge()
    {
        if(TextUtils.isDigitsOnly(createAge.getText()) && !createAge.getText().toString().equals(""))
        {
            int age = Integer.parseInt(createAge.getText().toString());
            if (age < 1 || age > 99) {
                Toast.makeText(getApplicationContext(), "Invalid Age", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Invalid Age", Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
