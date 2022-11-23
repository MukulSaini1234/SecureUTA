package com.example.uta.entities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uta.R;
import com.example.uta.ViewModifyStudentActivity;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<User> userList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<User> userList){
        this.context=context;
        this.userList=userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_student_list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(userList.get(position),context);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUTAID, textViewName, textViewStatus;
        User user;
        Context context;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUTAID= itemView.findViewById(R.id.tv_utaid);
            textViewName= itemView.findViewById(R.id.tv_name);
            textViewStatus= itemView.findViewById(R.id.tv_status);

            itemView.setOnClickListener(e->{
                Intent intent=new Intent(context, ViewModifyStudentActivity.class);
                intent.putExtra("uid",user.getUid());
                intent.putExtra("title","Student profile");
                context.startActivity(intent);
            });
        }

        public void setData(User user, Context context){
            this.user=user;
            this.context=context;
            textViewUTAID.setText(user.getUtaId());
            textViewName.setText(user.getName());
            if(user.getStatus().equalsIgnoreCase("T")) {
                textViewStatus.setText("Approved");
            }else if(user.getStatus().equalsIgnoreCase("F")){
                textViewStatus.setText("Disapproved");
            }else {
                textViewStatus.setText("Not Approved");
            }
        }
    }
}
