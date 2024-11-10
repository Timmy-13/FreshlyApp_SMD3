package com.assignment.freshly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.freshly.Activities.LandingPageVendor.ProductDetailActivity;
import com.assignment.freshly.R;
import com.assignment.freshly.Entity.Product;

import java.util.List;

public class ListViewAdapterVendor extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> productList;

    public ListViewAdapterVendor(Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.productList = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_item_vendor, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_icon);
        TextView title = convertView.findViewById(R.id.title);
        TextView description = convertView.findViewById(R.id.description);

        title.setText(productList.get(position).getTitle());
        description.setText(productList.get(position).getDescription());
        imageView.setImageResource(0);

        if (productList.get(position).getImagePath() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productList.get(position).getImagePath() , 0, productList.get(position).getImagePath().length);
            imageView.setImageBitmap(bitmap);
        }
        else {
            imageView.setImageResource(R.drawable.ic_default_image);
        }


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
