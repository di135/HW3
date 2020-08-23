package com.example.hw3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.hw3.dummy.CustomerContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.hw3.dummy.CustomerContent.Customers;

public class MainActivity extends AppCompatActivity implements
        CustomerFragment.OnListFragmentInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener,
        EditDialog.OnEditDialogInteractionListener {

   // public static String customerExtra;
    int selectedDeleteItem = -1;
    public static  String CustomerExtra = "CustomerExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCustomerActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getFirebaseData();

    }
    public void getFirebaseData(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Customers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            Customers.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());

                                Map<String, Object> customer = new HashMap<>();
                                customer = document.getData();

                                Object oId = customer.get("ID");
                                Object oAddress = customer.get("address");
                                Object oCompany = customer.get("Company");
                                Object oContact = customer.get("Contact");
                                Object oNumber = customer.get("Number");

                                int id = Integer.parseInt(oId.toString());
                                String address = oAddress.toString();
                                String company = oCompany.toString();
                                String contact = oContact.toString();
                                String number = oNumber.toString();

                                CustomerContent.Customer customerFromFireBase = new CustomerContent.Customer(id, address, company, contact, number);

                                CustomerContent.addItem(customerFromFireBase);
                                CustomerContent.lastID = id+1;
                            }
                            ((CustomerFragment) getSupportFragmentManager().findFragmentById(R.id.CustomerFragment)).notifyDataChange();
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    public void startCustomerInfoActivity(CustomerContent.Customer customer){
        Intent intent = new Intent(this, CustomerInfoActivity.class);
        intent.putExtra(CustomerExtra, customer);
        startActivity(intent);
    }

    public void startEditCustomerActivity(CustomerContent.Customer customer){
        Intent intent = new Intent(this, EditCustomerActivity.class);
        intent.putExtra(CustomerExtra, customer);
        startActivity(intent);
    }

    @Override
    public void onListFragmentClickInteraction(CustomerContent.Customer item, View view) {

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayCustomerInFragment(item);
        } else {
            startCustomerInfoActivity(item);
        }
    }

    @Override
    public void onListFragmentLongClickInteraction(CustomerContent.Customer item) {
        EditDialog.newInstance(item).show(getSupportFragmentManager(), getString(R.string.callingdialog));
    }

    @Override
    public void onDeleteButtonClick(int position) {
        selectedDeleteItem = position;
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (selectedDeleteItem >= 0 && selectedDeleteItem < Customers.size()) {
            CustomerContent.deleteItem(selectedDeleteItem);


            ((CustomerFragment) getSupportFragmentManager().findFragmentById(R.id.CustomerFragment)).notifyDataChange();

            CustomerInfoFragment customerInfoFragment = ( (CustomerInfoFragment) getSupportFragmentManager().findFragmentById(R.id.CustomerInfoFragment));
            if(customerInfoFragment != null){
                customerInfoFragment.displayEmptyCustomer();
            }
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onEditDialogPositiveClick(DialogFragment dialog, CustomerContent.Customer customer) {

        startEditCustomerActivity(customer);

    }

    @Override
    public void onEditDialogNegativeClick(DialogFragment dialog) {

    }

    public void displayCustomerInFragment(CustomerContent.Customer customer){
        CustomerInfoFragment customerInfoFragment = ( (CustomerInfoFragment) getSupportFragmentManager().findFragmentById(R.id.CustomerInfoFragment));
        if(customerInfoFragment != null){
            customerInfoFragment.displayCustomer(customer);
        }
    }




}
