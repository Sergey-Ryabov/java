package com.ocodetesttask.storage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ocodetesttask.model.Product;
import com.ocodetesttask.storage.Storage;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Сергей on 10.07.2015.
 */
public class DBStorage implements Storage {

    private DBHelper dbHelper;

    public DBStorage(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public List<Product> loadProducts() {
        List<Product> productList = new LinkedList<Product>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c;
        String table = "product JOIN appointment_catalog ON product.appointment_id = appointment_catalog.id";
        String columns[] = {"product.name AS name",
                "appointment_catalog.name AS appointment",
                "product.price AS price",
                "product.quantity AS quantity"};
        c = db.query(table, columns, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    productList.add(new Product(c.getString(c.getColumnIndex("name")),
                            c.getString(c.getColumnIndex("appointment")),
                            c.getDouble(c.getColumnIndex("price")),
                            c.getInt(c.getColumnIndex("quantity"))));
                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        this.dbHelper.close();

        return productList;
    }

    @Override
    public void saveProducts(List<Product> products) {
        for (Product product : products) {
            saveProduct(product);
        }
    }

    @Override
    public void saveProduct(Product product) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long appointmentId = getAppointmentId(product.getAppointment(), getAppointmentSet(db));
        cv.put("name", product.getName());
        cv.put("appointment_id", appointmentId);
        cv.put("price", product.getPrice());
        cv.put("quantity", product.getQuantity());

        int updCount = db.update("product", cv, "name = ? AND appointment_id = ? AND price = ?",
                new String[]{product.getName(), Long.toString(appointmentId), Double.toString(product.getPrice())});
        if (updCount == 0) {
            db.insert("product", null, cv);
        }
        db.close();
        dbHelper.close();
    }

    private Set<String> getAppointmentSet(SQLiteDatabase db) {
        Cursor c;
        String table = "appointment_catalog";
        String columns[] = {"appointment_catalog.name AS appointment",
                "appointment_catalog.name AS appointment"};
        c = db.query(table, columns, null, null, null, null, null);
        Set<String> appointmentSet = new LinkedHashSet<String>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    appointmentSet.add(c.getString(c.getColumnIndex("appointment")));
                } while (c.moveToNext());
            }
        }
        c.close();
        return appointmentSet;
    }


    private long getAppointmentId(String appointment, Set<String> appointmentSet) {
        String[] appointmentArray = new String[appointmentSet.size()];
        appointmentArray = appointmentSet.toArray(appointmentArray);
        for (int i = 0; i < appointmentArray.length; i++) {
            if (appointment.equalsIgnoreCase(appointmentArray[i])) {
                return i + 1;
            }
        }
        return insertAppointment(appointment);
    }

    private long insertAppointment(String appointment) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("name", appointment);
        return db.insert("appointment_catalog", null, cv);
    }
}
