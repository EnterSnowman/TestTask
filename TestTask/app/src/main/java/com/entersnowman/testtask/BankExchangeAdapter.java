package com.entersnowman.testtask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entersnowman.testtask.models.BankExchange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Valentin on 18.02.2017.
 */

public class BankExchangeAdapter extends RecyclerView.Adapter<BankExchangeAdapter.BankExchangeHolder>{
    public ArrayList<BankExchange> getBankExchanges() {
        return bankExchanges;
    }

    public void setBankExchanges(ArrayList<BankExchange> bankExchanges) {
        this.bankExchanges = bankExchanges;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    ArrayList<BankExchange> bankExchanges;
    Context context;
    @Override
    public BankExchangeAdapter.BankExchangeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exchange_item, parent, false);
        BankExchangeHolder bankExchangeHolder = new BankExchangeHolder(v);
        return bankExchangeHolder;
    }

    @Override
    public void onBindViewHolder(BankExchangeAdapter.BankExchangeHolder holder, int position) {
        holder.date.setText(new SimpleDateFormat("dd.MM.yyyy").format(bankExchanges.get(position).getDate()));
        holder.usd.setText(Double.toString( bankExchanges.get(position).getUSDexchange()));
        holder.eur.setText(Double.toString( bankExchanges.get(position).getEURexchange()));
        holder.rur.setText(Double.toString( bankExchanges.get(position).getRURexchange()));
    }

    @Override
    public int getItemCount() {
        return bankExchanges.size();
    }

    public class BankExchangeHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView usd;
        TextView eur;
        TextView rur;
        public BankExchangeHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            usd = (TextView) itemView.findViewById(R.id.usd);
            eur = (TextView) itemView.findViewById(R.id.eur);
            rur = (TextView) itemView.findViewById(R.id.rur);
        }
    }
}
