package com.example.myheroapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.models.Client;
import com.example.myheroapp.models.viewholder_models.UserSchedule;
import com.example.myheroapp.ui.AdminMainActivity.viewholders.AdminMainViewHolder;
import com.example.myheroapp.ui.AdminMainActivity.viewholders.TitleViewHolder;
import com.example.myheroapp.ui.UserMainActivity.UserScheduleViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String NO_SCHEDULE_FOUND_TAG = "No Schedule Found";


    public interface AdminMainInterface {
        void OnCheckStockClicked(Client client);
    }

    public interface UserMainInterface {
        void OnCheckInClicked(Client client);
    }

    private List<Object> mItems;
    private Context mContext;
    private AdminMainInterface mAdminMainInterface;
    private UserMainInterface mUserMainInterface;

    public ClientAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = -1;
        Object object = mItems.get(position);
        if (object instanceof Client) {
            viewType = 1;
        } else if (object instanceof UserSchedule) {
            viewType = 2;
        } else if (object instanceof String) {
            viewType = 3;
        }
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_admin_main, parent, false);
                return new AdminMainViewHolder(view);
            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_user_schedule, parent, false);
                return new UserScheduleViewHolder(view);
            case 3:
                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_title, parent, false);
                return new TitleViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_admin_main, parent, false);
                return new AdminMainViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == 1) {
            final Client client = (Client) mItems.get(position);
            AdminMainViewHolder adminMainViewHolder = (AdminMainViewHolder) holder;
            adminMainViewHolder.tvClientName.setText(String.format("%s\n%s", client.getName(), client.getLocation()));
            adminMainViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdminMainInterface.OnCheckStockClicked(client);
                }
            });


        } else if (viewType == 2) {
            UserScheduleViewHolder userScheduleViewHolder = (UserScheduleViewHolder) holder;
            final UserSchedule userSchedule =(UserSchedule) mItems.get(position);
            userScheduleViewHolder.tvClient.setText(userSchedule.getClient().getName());
            userScheduleViewHolder.tvLocation.setText(userSchedule.getClient().getLocation());
            userScheduleViewHolder.tvDate.setText(userSchedule.getScheduleDate());
            userScheduleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUserMainInterface.OnCheckInClicked(userSchedule.getClient());
                }
            });
        } else if (viewType == 3) {
            if (mItems.get(position).equals(NO_SCHEDULE_FOUND_TAG)) {
                ((TitleViewHolder) holder).tvTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                ((TitleViewHolder) holder).divider.setVisibility(View.GONE);
                ((TitleViewHolder) holder).tvTitle.setText((String) mItems.get(position));
            } else {
                ((TitleViewHolder) holder).tvTitle.setText((String) mItems.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        int retInt = 0;
        if (mItems != null) {
            retInt = mItems.size();
        }
        return retInt;
    }

    public void setItems(List<Object> items) {
        if (mItems == null) {
            mItems = new ArrayList<>();
        } else {
            mItems.clear();
        }
        mItems.addAll(items);
    }

    public void setAdminMainInterface(AdminMainInterface adminMainInterface) {
        this.mAdminMainInterface = adminMainInterface;
    }

    public void setUserMainInterface(UserMainInterface userMainInterface) {
        mUserMainInterface = userMainInterface;
    }
}
