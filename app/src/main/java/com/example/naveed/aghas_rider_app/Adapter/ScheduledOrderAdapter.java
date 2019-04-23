package com.example.naveed.aghas_rider_app.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.naveed.aghas_rider_app.Models.OrderVM;
import com.example.naveed.aghas_rider_app.R;

import java.util.List;

public class ScheduledOrderAdapter extends RecyclerView.Adapter<ScheduledOrderAdapter.ListViewHolder> {

    private List<OrderVM> myOrdersList;
    int count = 0;

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView txtOrderId, txtTotal, txtCustomerName, txtAddress;

        public ListViewHolder(View view) {
            super(view);
            txtOrderId = (TextView) view.findViewById(R.id.txt_orderid);
            //txtTotal = (TextView) view.findViewById(R.id.txt_total);
            txtCustomerName = (TextView) view.findViewById(R.id.txt_customername);
            txtAddress = (TextView) view.findViewById(R.id.txt_address);
        }
    }

    public ScheduledOrderAdapter(List<OrderVM> objList) {
        this.myOrdersList = objList;
    }

    @Override
    public ScheduledOrderAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_scheduledorders, parent, false);

        return new ScheduledOrderAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduledOrderAdapter.ListViewHolder holder, int position) {
        OrderVM myOrders = myOrdersList.get(position);

        count = count + 1;

        String orderid  = myOrders.getOrderId().toString();
        String total = "Rs. " + myOrders.getTotal().toString();
        String customername = myOrders.getCustomerName();
        String address = myOrders.getAddress();

        holder.txtOrderId.setText(orderid);
        //holder.txtTotal.setText(total);
        holder.txtCustomerName.setText(customername);
        holder.txtAddress.setText(address);
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }
}