package com.example.hw3.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CustomerContent {


    public static final List<Customer> Customers = new ArrayList<Customer>();

    public static int lastID;

    public static void addItem(Customer item) {
        Customers.add(item);
    }

    public static void deleteItem(int position) {

        Customer temp = Customers.get(position);
        int id = temp.ID;

        //TODO delete from database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Customers").document(Integer.toString(id)).delete();

        Customers.remove(position);
    }

    public static class Customer implements Parcelable {
        public final int ID;
        public final String address;
        public final String Company;
        public final String Contact;
        public final String Number;

        public Customer(int id, String address, String Company, String Contact, String Number) {
            this.ID = id;
            this.address = address;
            this.Company = Company;
            this.Contact = Contact;
            this.Number = Number;
        }

        public Customer(String address, String Company, String Contact, String Number) {
            this.ID = lastID;
            this.address = address;
            this.Company = Company;
            this.Contact = Contact;
            this.Number = Number;
        }

        protected Customer(Parcel in) {
            ID = in.readInt();
            address = in.readString();
            Company = in.readString();
            Contact = in.readString();
            Number = in.readString();
        }

        public static final Creator<Customer> CREATOR = new Creator<Customer>() {
            @Override
            public Customer createFromParcel(Parcel in) {
                return new Customer(in);
            }

            @Override
            public Customer[] newArray(int size) {
                return new Customer[size];
            }
        };

        public static void clear() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ID);
            dest.writeString(address);
            dest.writeString(Company);
            dest.writeString(Contact);
            dest.writeString(Number);
        }

    }

}
