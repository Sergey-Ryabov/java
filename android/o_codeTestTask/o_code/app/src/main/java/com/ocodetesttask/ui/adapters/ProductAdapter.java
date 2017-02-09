package com.ocodetesttask.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ocodetesttask.R;
import com.ocodetesttask.SaveThread;
import com.ocodetesttask.model.Product;
import com.ocodetesttask.storage.Storage;

import java.util.List;

/**
 * Created by Сергей on 10.07.2015.
 */
public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;

    private TextView productName;
    private TextView productAppointment;
    private TextView productPrice;
    private TextView productQuantity;
    private ImageButton buyButton;

    private List<Product> productList;
    private Storage storage;
    private SaveThread saveThread;
    private Handler handler;

    private LayoutInflater inflater;
    private LinearLayout alertDialogLayout;

    public ProductAdapter(Context context, LayoutInflater inflater, List<Product> products,
                          Storage storage, SaveThread saveThread, Handler handler) {
        super(context, 0, products);
        this.context = context;
        this.inflater = inflater;
        productList = products;
        this.storage = storage;
        this.saveThread = saveThread;
        this.handler = handler;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = productList.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);

        productName = (TextView) convertView.findViewById(R.id.name);
        productAppointment = (TextView) convertView.findViewById(R.id.appointment);
        productPrice = (TextView) convertView.findViewById(R.id.price);
        productQuantity = (TextView) convertView.findViewById(R.id.quantity);


        buyButton = (ImageButton) convertView.findViewById(R.id.buyButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getQuantity() == 0) {
                    Toast.makeText(context, "At the moment there is no product to buy", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Buy dialog");
                    alertDialogLayout = (LinearLayout) inflater.inflate(R.layout.alert_dialog, null);
                    alertDialogBuilder.setView(alertDialogLayout);

                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    final TextView buyCountTextView = (TextView) alertDialogLayout.findViewById(R.id.buyCount);
                    buyCountTextView.setText("0/" + product.getQuantity() + " units");

                    SeekBar seekBar = (SeekBar) alertDialogLayout.findViewById(R.id.seekBar);
                    seekBar.setMax(product.getQuantity());
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            buyCountTextView.setText(Integer.toString(progress) + "/" + product.getQuantity() + " units");
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    Button buyButton = (Button) alertDialogLayout.findViewById(R.id.buyButton);
                    buyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int buyCount = Integer.parseInt(buyCountTextView.getText().toString().split("/")[0]);
                            if (buyCount > 0) {
                                product.setQuantity(product.getQuantity() - buyCount);
                                notifyDataSetChanged();

                                Runnable saveProductTask = new Runnable() {
                                    @Override
                                    public void run() {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                storage.saveProduct(product);
                                            }
                                        });
                                    }
                                };
                                saveThread.postTask(saveProductTask);
                            }
                            alertDialog.cancel();
                        }
                    });

                    Button cancelButton = (Button) alertDialogLayout.findViewById(R.id.cancelButton);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });

                    alertDialog.show();
                }
            }
        });

        productName.setText(productList.get(position).getName());
        productAppointment.setText(productList.get(position).getAppointment());
        productPrice.setText("" + productList.get(position).getPrice());
        productQuantity.setText("" + productList.get(position).getQuantity());
        return convertView;
    }

}
