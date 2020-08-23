package com.example.hw3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw3.dummy.CustomerContent.Customer;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        addCustomer();
    }

    @SuppressLint("WrongViewCast")
    private void addCustomer() {

        Button addButton;

        addButton = findViewById(R.id.addCustomerButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText addressEditTxt;
                EditText CompanyEditTxt;
                EditText ContactEditTxt;
                EditText NumberEditTxt;

                addressEditTxt = findViewById(R.id.newAddress);
                CompanyEditTxt = findViewById(R.id.newCompany);
                ContactEditTxt = findViewById(R.id.newContact);
                NumberEditTxt = findViewById(R.id.newNumber);

                String address;
                String company;
                String contact;
                String number;

                address = addressEditTxt.getText().toString();
                company = CompanyEditTxt.getText().toString();
                contact = ContactEditTxt.getText().toString();
                number = NumberEditTxt.getText().toString();


                if(company.isEmpty()){
                    CompanyEditTxt.setError(getString(R.string.thisFieldCannotBeEmpty));
                    return;
                }


                Customer newCustomer= new Customer(address, company, contact, number);


                // TODO firabase adding data
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Customers").document(Integer.toString(newCustomer.ID)).set(newCustomer);


                addressEditTxt.setText("");
                CompanyEditTxt.setText("");
                ContactEditTxt.setText("");
                NumberEditTxt.setText("");

                finish(); //close this activity and go bac to main activity
            }
        });
    }


    private int checkDate (String date){

        int result = -1;

        if( date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") ){
            result = 1;
        }

        return result;
    }
}
