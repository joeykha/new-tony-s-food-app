package com.example.myheroapp.ui.AddScheduleActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.models.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    public interface DeleteScheduleInterface{
        void OnScheduleDeleted(int scheduleId);
    }

    private List<Schedule> mItems;
    private Context mContext;
    private DeleteScheduleInterface mDeleteScheduleInterface;

    public ScheduleAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleViewHolder(LayoutInflater.from(mContext), R.layout.view_holder_schedule, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, final int position) {
        final Schedule schedule = mItems.get(position);
        holder.tvUsername.setText(String.valueOf(schedule.getUser()));
        holder.tvLocation.setText(String.valueOf(schedule.getLocation()));
        holder.tvDate.setText(schedule.getSch_Date());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteScheduleInterface.OnScheduleDeleted(schedule.getId());
                mItems.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        int retInt = 0;
        if(mItems != null){
            retInt = mItems.size();
        }
        return retInt;
    }

    public void setItems(List<Schedule> items){
        if(mItems == null){
            mItems = new ArrayList<>();
        }else{
            mItems.clear();
        }
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setDeleteScheduleInterface(DeleteScheduleInterface deleteScheduleInterface) {
        mDeleteScheduleInterface = deleteScheduleInterface;
    }
}
