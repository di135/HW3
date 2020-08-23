package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.hw3.dummy.CustomerContent;


public class CustomerInfoFragment extends Fragment {

    public CustomerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_info, container, false);
    }

    public void displayCustomer(CustomerContent.Customer customer){
        FragmentActivity activity = getActivity();

        TextView addressEditTxt = activity.findViewById(R.id.address);
        TextView companyEditTxt = activity.findViewById(R.id.company);
        TextView contactEditTxt = activity.findViewById(R.id.contact);
        TextView numberEditTxt = activity.findViewById(R.id.number);

        String address = customer.address;
        String company = customer.Company;
        String contact = customer.Contact;
        String number = customer.Number;
        addressEditTxt.setText("Address: "+address);
        companyEditTxt.setText("Company: "+company);
        contactEditTxt.setText("Contact person: "+contact);
        numberEditTxt.setText("Telephone number: "+number);
    }

    public void displayEmptyCustomer(){
        FragmentActivity activity = getActivity();

        TextView addressEditTxt = activity.findViewById(R.id.address);
        TextView companyEditTxt = activity.findViewById(R.id.company);
        TextView contactEditTxt = activity.findViewById(R.id.contact);
        TextView numberEditTxt = activity.findViewById(R.id.number);

        addressEditTxt.setText("");
        companyEditTxt.setText("");
        contactEditTxt.setText("");
        numberEditTxt.setText("");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if(intent != null){
           CustomerContent.Customer recivedCustomer = intent.getParcelableExtra(MainActivity.CustomerExtra);
            if(recivedCustomer != null) {
                displayCustomer(recivedCustomer);
            }
        }
    }

}
