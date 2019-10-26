package com.example.myheroapp.ui.AdminMainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.models.Client;
import com.example.myheroapp.ui.AdminMainActivity.viewholders.AdminMainViewHolder;
import com.example.myheroapp.ui.AdminMainActivity.viewholders.TitleViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AdminMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface AdminMainInterface {
        void OnCheckStockClicked(Client client);
    }

    private List<Object> mItems;
    private Context mContext;
    private AdminMainInterface  mAdminMainInterface;

    public AdminMainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = -1;
        if (mItems.get(position) instanceof Client) {
            viewType = 1;
        } else if (mItems.get(position) instanceof String) {
            viewType = 2;
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
            ((AdminMainViewHolder) holder).tvClientName.setText(String.format("%s\n%s",client.getName(), client.getLocation()));
            ((AdminMainViewHolder) holder).btnCheckStock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdminMainInterface.OnCheckStockClicked(client);
                }
            });
        } else if (viewType == 2) {
            ((TitleViewHolder) holder).tvTitle.setText((String) mItems.get(position));
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
}
