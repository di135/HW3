package com.example.hw3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hw3.dummy.CustomerContent;

public class EditDialog extends DialogFragment {

    private String address;
    private CustomerContent.Customer mCustomer;

    public EditDialog(CustomerContent.Customer customer) {
        this.address = customer.address;
        this.mCustomer = customer;
    }

    static EditDialog newInstance(CustomerContent.Customer customer){
        return new EditDialog(customer);
    }

    public interface OnEditDialogInteractionListener {
        void onEditDialogPositiveClick(DialogFragment dialog, CustomerContent.Customer customer);
        void onEditDialogNegativeClick(DialogFragment dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Edit "+ address +" address?");

        builder.setPositiveButton(getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnEditDialogInteractionListener mListener =  (OnEditDialogInteractionListener) getActivity();
                mListener.onEditDialogPositiveClick(EditDialog.this, mCustomer);
            }
        });

        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnEditDialogInteractionListener mListener =  (OnEditDialogInteractionListener) getActivity();
                mListener.onEditDialogNegativeClick(EditDialog.this);
            }
        });
        return builder.create();
    }
}
