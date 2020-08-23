package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.hw3.dummy.CustomerContent;


public class CustomerEditFragment extends Fragment {

    private int id;
    private String address;
    private String company;
    private String contact;
    private String number;

    private EditText addressEditTxt;
    private EditText companyEditTxt;
    private EditText contactEditTxt;
    private EditText numberEditTxt;
    private Button saveChangesBtn;

    public CustomerEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity();

        addressEditTxt = activity.findViewById(R.id.address);
        companyEditTxt = activity.findViewById(R.id.company);
        contactEditTxt = activity.findViewById(R.id.contact);
        numberEditTxt = activity.findViewById(R.id.number);

        saveChangesBtn = activity.findViewById(R.id.saveChangesButton);

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        Intent intent = getActivity().getIntent();
        if(intent != null){
            CustomerContent.Customer recivedCustomer= intent.getParcelableExtra(MainActivity.CustomerExtra);
            if(recivedCustomer != null) {
                displayCustomer(recivedCustomer);
            }
        }

    }

    public void saveChanges(){


        address = addressEditTxt.getText().toString();
        company = companyEditTxt.getText().toString();
        contact = contactEditTxt.getText().toString();
        number = numberEditTxt.getText().toString();

        // TODO save changes to db
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Customers").document(Integer.toString(id));

        docRef.update("Address", address,
                            "Company", company,
                            "Contact", contact,
                            "Number", number);

        getActivity().finish();
    }

    public void displayCustomer(CustomerContent.Customer customer){

        id = customer.ID;
        address = customer.address;
        company = customer.Company;
        contact = customer.Contact;
        number = customer.Number;

        addressEditTxt.setText(address);
        companyEditTxt.setText(company);
        contactEditTxt.setText(contact);
        numberEditTxt.setText(number);
    }
}
