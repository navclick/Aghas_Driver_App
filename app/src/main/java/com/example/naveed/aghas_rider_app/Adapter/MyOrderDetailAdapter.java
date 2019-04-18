package com.example.naveed.aghas_rider_app.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.naveed.aghas_rider_app.Models.OrderItems;
import com.example.naveed.aghas_rider_app.R;

import java.util.List;

public class MyOrderDetailAdapter extends RecyclerView.Adapter<MyOrderDetailAdapter.ListViewHolder> {

    private List<OrderItems> myOrdersList;
    int count = 0;

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView txtItemName, txtPrice, txtCount;

        public ListViewHolder(View view) {
            super(view);
            txtCount = (TextView) view.findViewById(R.id.txt_count);
            txtItemName = (TextView) view.findViewById(R.id.txt_itemname);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
        }
    }

    public MyOrderDetailAdapter(List<OrderItems> objList) {
        this.myOrdersList = objList;
    }

    @Override
    public MyOrderDetailAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_orderdetailitems, parent, false);

        return new MyOrderDetailAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyOrderDetailAdapter.ListViewHolder holder, int position) {
        OrderItems myOrders = myOrdersList.get(position);

        count = count + 1;

        String itemname = myOrders.getItemName();
        String price = "Rs. " + myOrders.getQuantity().toString();

        holder.txtCount.setText(Integer.toString(count));
        holder.txtItemName.setText(itemname);
        holder.txtPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }
}