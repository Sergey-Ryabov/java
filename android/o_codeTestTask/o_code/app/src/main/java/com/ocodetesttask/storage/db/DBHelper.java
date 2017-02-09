package com.ocodetesttask.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Сергей on 11.07.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "oCodeTestTaskDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE appointment_catalog("
                + "id integer primary key autoincrement,"
                + "name varchar not null unique);");

        db.execSQL("CREATE TABLE product("
                + "name varchar not null,"
                + "appointment_id integer,"
                + "price double not null,"
                + "quantity integer not null);");

        //добавление записи - общее назначение продукта (назначение продукта по умолчанию)
        db.execSQL("INSERT INTO appointment_catalog(name)"
                + "VALUES('Common');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
