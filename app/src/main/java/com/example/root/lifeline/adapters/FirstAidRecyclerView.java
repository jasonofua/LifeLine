package com.example.root.lifeline.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.lifeline.Activities.DefaultListDetail;
import com.example.root.lifeline.Constant.IntentExtras;
import com.example.root.lifeline.R;
import com.example.root.lifeline.models.FirstAid;

import java.util.ArrayList;

public class FirstAidRecyclerView extends RecyclerView.Adapter<FirstAidRecyclerView.MyViewHolder> {

    ArrayList<FirstAid> firstAidArrayList;
    Context context;

    public FirstAidRecyclerView(ArrayList<FirstAid> firstAidArrayList, Context context) {
        this.firstAidArrayList = firstAidArrayList;
        this.context = context;
    }

    @Override
    public FirstAidRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FirstAidRecyclerView.MyViewHolder holder, int position) {
        FirstAid firstAid = firstAidArrayList.get(position);
        holder.firstAidName.setText(firstAid.getFirstAidName());
        holder.firstAidImage.setImageResource(firstAid.getFirstAidImage());

    }

    @Override
    public int getItemCount() {
        return firstAidArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView firstAidImage;
        private TextView firstAidName;
        public MyViewHolder(View itemView) {
            super(itemView);
            firstAidImage = (ImageView)itemView.findViewById(R.id.img_firstAidImage);
            firstAidName = (TextView)itemView.findViewById(R.id.tv_firstAidName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DefaultListDetail.class);
                    intent.putExtra(IntentExtras.ITEM_ID,firstAidArrayList.get(getAdapterPosition()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }

    public void setFilter(ArrayList<FirstAid> student) {
        firstAidArrayList = new ArrayList<>();
        firstAidArrayList.addAll(student);
        notifyDataSetChanged();
    }
}
