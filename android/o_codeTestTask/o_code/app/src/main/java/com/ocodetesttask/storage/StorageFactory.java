package com.ocodetesttask.storage;

import android.content.Context;

import com.ocodetesttask.storage.db.DBStorage;
import com.ocodetesttask.storage.xml.XMLStorage;

/**
 * Created by Сергей on 13.07.2015.
 */
public class StorageFactory {

    public static Storage create(Context context, String storageTechnology){
        if(storageTechnology != null && "".equalsIgnoreCase(storageTechnology.trim())){
            if("db".equalsIgnoreCase(storageTechnology)){
                return new DBStorage(context);
            }else{
                return new XMLStorage(context);
            }
        }else {
            return new DBStorage(context);
        }
    }
}
