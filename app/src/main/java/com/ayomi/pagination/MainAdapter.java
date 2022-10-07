package com.ayomi.pagination;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<MainData> dataArrayList;
    private Activity activity;
    private View view;
    private Context context;

    public MainAdapter(ArrayList<MainData> dataArrayList, Activity activity, Context context) {
        this.dataArrayList = dataArrayList;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.list_tems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainData data = dataArrayList.get(position);

        Picasso.get().load(data.getImage()).into(holder.imageView);

        holder.fullname.setText(data.getName());
        holder.fullname1.setText(data.getName1());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname, fullname1;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.txtFullName);
            fullname1 = itemView.findViewById(R.id.txtFullName1);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
