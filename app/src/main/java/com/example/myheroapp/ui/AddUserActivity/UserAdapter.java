package com.example.myheroapp.ui.AddUserActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.models.Schedule;
import com.example.myheroapp.models.User;
import com.example.myheroapp.ui.AddScheduleActivity.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    public interface UserInterface {
        void OnUserDeleted(int userId, int position);
        void OnUserUpdated(int userId);
    }

    private List<User> mItems;
    private Context mContext;
    private UserInterface mUserInterface;

    public UserAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        final User user = mItems.get(position);
        holder.tvUsername.setText(user.getUserName());
        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserInterface.OnUserUpdated(user.getId());
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserInterface.OnUserDeleted(user.getId(), position);
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

    public void setItems(List<User> items){
        if(mItems == null){
            mItems = new ArrayList<>();
        }else{
            mItems.clear();
        }
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        List<User> tmpItems = new ArrayList<>();
        if(mItems != null){
            tmpItems.addAll(mItems);
            tmpItems.remove(position);
            setItems(tmpItems);
            notifyDataSetChanged();
        }
    }

    public void setUserInterface(UserInterface userInterface) {
        this.mUserInterface = userInterface;
    }
}
