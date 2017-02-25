package com.testtask.webservice.utils;


import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;

/**
 * Created by ryabov on 04.07.2016.
 */
public class Base64Utils {

    private Base64Utils() {}

    public static String encode(String value) throws Exception {
        return  DatatypeConverter.printBase64Binary
                (value.getBytes(Charset.forName("UTF-8")));
    }

    public static String decode(String value) throws Exception {
        byte[] decodedValue = DatatypeConverter.parseBase64Binary(value);
        return new String(decodedValue, Charset.forName("UTF-8"));
    }

}
