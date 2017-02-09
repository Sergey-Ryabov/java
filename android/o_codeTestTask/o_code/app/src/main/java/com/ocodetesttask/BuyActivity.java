package com.ocodetesttask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ocodetesttask.model.Product;
import com.ocodetesttask.storage.Storage;
import com.ocodetesttask.storage.StorageFactory;
import com.ocodetesttask.ui.adapters.ProductAdapter;

import java.util.List;


public class BuyActivity extends Activity {

    private Context context;
    private ListView productListView;
    private ProductAdapter productAdapter;

    private List<Product> productList;

    private Storage storage;

    private Handler handler = new Handler();
    private SaveThread saveThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buy);
        context = this;

        TextView mTitleTextView = (TextView) findViewById(R.id.title_text);
        mTitleTextView.setText("Product market");

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);

        ImageButton addProductButton = (ImageButton) findViewById(R.id.addProductButton);
        addProductButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SaleActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        storage = StorageFactory.create(context, "db");
        productList = storage.loadProducts();

        saveThread = new SaveThread("saveThread");
        saveThread.start();
        saveThread.prepareHandler();

        productListView = (ListView) findViewById(R.id.producListView);
        productAdapter = new ProductAdapter(context, getLayoutInflater(), productList, storage, saveThread, handler);
        productListView.setAdapter(productAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            final Product product = data.getParcelableExtra("product");
            int productIndex = getProductIndex(product);
            if (productIndex == -1) {
                productList.add(product);
            } else {
                productList.get(productIndex).setQuantity(
                        productList.get(productIndex).getQuantity() + product.getQuantity());
                product.setQuantity(productList.get(productIndex).getQuantity());
            }
            productAdapter.notifyDataSetChanged();


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
    }


    @Override
    public void onDestroy() {
        saveThread.quit();

        super.onDestroy();
    }

    /**
     * @param product searched product
     * @return found index of the product. -1 if productList NOT contain this product
     */
    private int getProductIndex(Product product) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).equals(product)) {
                return i;
            }
        }
        return -1;
    }

}
