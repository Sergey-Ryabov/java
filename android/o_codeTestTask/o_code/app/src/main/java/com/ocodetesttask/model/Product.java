package com.ocodetesttask.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Сергей on 10.07.2015.
 */
public class Product implements Parcelable {

    private String name;
    private String appointment;
    private double price;
    private int quantity;

    public Product(String name, String appointment, double price, int quantity) {
        this.name = name;
        this.appointment = appointment;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static final Parcelable.Creator<Product> CREATOR = new Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source.readString(), source.readString(),
                    source.readDouble(), source.readInt());
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(appointment);
        parcel.writeDouble(price);
        parcel.writeInt(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        if (appointment != null ? !appointment.equals(product.appointment) : product.appointment != null)
            return false;
        if (!name.equals(product.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + (appointment != null ? appointment.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
