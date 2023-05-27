package com.example.appmongodb.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmongodb.API.RetrofitUser;
import com.example.appmongodb.API.UserAPI;
import com.example.appmongodb.CartActivity;
import com.example.appmongodb.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class OrdersAdapter  extends RecyclerView.Adapter<OrdersAdapter.MyViewHolderOrder>{

    List<Order> orderList;

    public OrdersAdapter(List<Order> orderList) {this.orderList = orderList;}

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserAPI myAPI;

    @NonNull
    @Override
    public OrdersAdapter.MyViewHolderOrder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_orders_table_view, viewGroup, false);

        return new OrdersAdapter.MyViewHolderOrder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.MyViewHolderOrder myViewHolder, int i) {
//        myViewHolder.name.setText(productList.get(i).getName());
//        myViewHolder.price.setText(productList.get(i).getPrice());
//        myViewHolder.description.setText(productList.get(i).getDescription());

        myViewHolder.name.setText(orderList.get(i).getProd_names());
        myViewHolder.price.setText("Price: " + orderList.get(i).getPrice() + " EUR");
        myViewHolder.datums.setText(orderList.get(i).getDatums());

//        myViewHolder.price.setText((cartList.get(i).getPrice()).toString());
//        myViewHolder.amount.setText(String.valueOf(cartList.get(i).getAmount()));
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class MyViewHolderOrder extends RecyclerView.ViewHolder {
        CardView c_root_view;
        TextView name, price, datums;
        //
        public MyViewHolderOrder(@NonNull View itemView) {
            super(itemView);


            c_root_view = (CardView) itemView.findViewById(R.id.profile_root_view);
            name = (TextView) itemView.findViewById(R.id.order_txt_name);
            datums = (TextView) itemView.findViewById(R.id.order_txt_d);
            price = (TextView) itemView.findViewById(R.id.order_txt_price);
//            Retrofit retrofitUser = RetrofitUser.getInstance();
//            myAPI = retrofitUser.create(UserAPI.class);
        }
    }
}
