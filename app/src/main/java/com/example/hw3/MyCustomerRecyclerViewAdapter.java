package com.example.hw3;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.hw3.dummy.CustomerContent.Customer;

import java.util.List;

public class MyCustomerRecyclerViewAdapter extends RecyclerView.Adapter<MyCustomerRecyclerViewAdapter.ViewHolder> {

    private final List<Customer> mValues;
    public final CustomerFragment.OnListFragmentInteractionListener mListener;

    public MyCustomerRecyclerViewAdapter(List<Customer> items, CustomerFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Customer mCustomer = mValues.get(position);
        holder.mItem = mCustomer;
        holder.mCompanyView.setText(mCustomer.Company);
        //holder.mAvatarView.setImageResource(mCustomer.avatarNumber);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentClickInteraction(holder.mItem, v);

                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onListFragmentLongClickInteraction(holder.mItem);
                return false;
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteButtonClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final ImageView mAvatarView;
        public final TextView mCompanyView;
        public Customer mItem;
        public ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //int Rvalue = new Random().nextInt(200);
            //int Gvalue = new Random().nextInt(250);
            //int Bvalue = new Random().nextInt(250);

            //view.setBackgroundColor(Color.rgb(Rvalue, Gvalue, Bvalue));
            view.setBackgroundColor(Color.rgb(119, 250, 250));
            //mAvatarView = view.findViewById(R.id.contact_avatar);
            mCompanyView = (TextView) view.findViewById(R.id.customer_company);
            deleteButton = view.findViewById(R.id.customer_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCompanyView.getText() + "'";
        }
    }
}
