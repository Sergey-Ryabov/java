package com.ocodetesttask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ocodetesttask.model.Product;

/**
 * Created by Сергей on 10.07.2015.
 */
public class SaleActivity extends Activity {

    private Context context;
    private EditText productName;
    private EditText productAppointment;
    private EditText productPrice;
    private EditText productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sale);

        context = this;

        ImageButton addProductButton = (ImageButton) findViewById(R.id.addProductButton);
        addProductButton.setVisibility(View.GONE);

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        productName = (EditText) findViewById(R.id.name);
        productAppointment = (EditText) findViewById(R.id.appointment);
        productPrice = (EditText) findViewById(R.id.price);
        productQuantity = (EditText) findViewById(R.id.quantity);

        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productName.getText().toString() == null
                        || "".equals(productName.getText().toString().trim())) {
                    Toast.makeText(context, "Enter Product name", Toast.LENGTH_SHORT).show();
                    return;
                }
                String appointment = productAppointment.getText().toString();
                if (appointment == null || "".equals(appointment.trim())) {
                    appointment = "Common";
                }
                double price;
                if (productPrice.getText().toString() == null
                        || "".equals(productPrice.getText().toString().trim())) {
                    price = 0;
                } else {
                    price = Double.parseDouble(productPrice.getText().toString());
                }
                int quantity;
                if (productQuantity.getText().toString() == null
                        || "".equals(productQuantity.getText().toString().trim())) {
                    quantity = 1;
                } else {
                    quantity = Integer.parseInt(productQuantity.getText().toString());
                }
                Intent intent = new Intent();
                intent.putExtra("product", new Product(productName.getText().toString(),
                        appointment, price, quantity));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
