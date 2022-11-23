package com.example.uta.entities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uta.R;

import java.util.List;

public class FaqAdaper extends RecyclerView.Adapter<FaqAdaper.MyViewHolder> {

    private List<Faq> list;

    public FaqAdaper(List<Faq> list){
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_faq_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewQ, textViewA;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQ=itemView.findViewById(R.id.tv_faq1);
            textViewA=itemView.findViewById(R.id.tv_faq2);
        }

        public void setData(Faq faq){
            textViewQ.setText(faq.getQuestion());
            textViewA.setText(faq.getAnswer());
        }
    }
}
