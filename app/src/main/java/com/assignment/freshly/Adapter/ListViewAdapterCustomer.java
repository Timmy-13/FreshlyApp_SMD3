package com.assignment.freshly.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.freshly.Activities.LandingPageVendor.ProductDetailActivity;
import com.assignment.freshly.Entity.Product;
import com.assignment.freshly.R;

import java.util.List;

public class ListViewAdapterCustomer extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> productList;

    public ListViewAdapterCustomer(Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.productList = products;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_item_customer, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_icon);
        TextView title = convertView.findViewById(R.id.title);
        TextView description = convertView.findViewById(R.id.description);
        Button addToCart = convertView.findViewById(R.id.btn_add_to_cart);
        if (productList.get(position).getImagePath() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productList.get(position).getImagePath() , 0, productList.get(position).getImagePath().length);
            imageView.setImageBitmap(bitmap);
        }
        else {
            imageView.setImageResource(R.drawable.ic_default_image);
        }

        title.setText(productList.get(position).getTitle());
        description.setText(productList.get(position).getDescription());

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("Cart_Details", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Product_Id_"+productList.get(position).getP_id(), productList.get(position).getP_id());
                editor.apply();
                Toast.makeText(getContext(),productList.get(position).getTitle() + " Added To Cart Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", productList.get(position).getP_id());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
