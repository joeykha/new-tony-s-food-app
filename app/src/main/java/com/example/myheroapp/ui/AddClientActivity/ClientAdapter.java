package com.example.myheroapp.ui.AddClientActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.models.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder> {

    public interface DeleteClientInterface {
        void OnClientDeleted(int clientId, int position);
    }

    private Context mContext;
    private List<Client> mItems;
    private DeleteClientInterface mDeleteClientInterface;

    public ClientAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_client, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, final int position) {
        final Client client = mItems.get(position);
        holder.tvClientName.setText(client.getName());
        holder.tvLocation.setText(client.getLocation());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteClientInterface.OnClientDeleted(client.getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        int retInt = 0;
        if (mItems != null) {
            retInt = mItems.size();
        }
        return retInt;
    }

    public void setItems(List<Client> items) {
        if (mItems == null) {
            mItems = new ArrayList<>();
        } else {
            mItems.clear();
        }
        mItems.addAll(items);
    }

    public void deleteItem(int position) {
        List<Client> tmpItems = new ArrayList<>();
        if (mItems != null) {
            tmpItems.addAll(mItems);
            tmpItems.remove(position);
            setItems(tmpItems);
            notifyDataSetChanged();
        }
    }

    public void setDeleteClientInterface(DeleteClientInterface deleteClientInterface) {
        mDeleteClientInterface = deleteClientInterface;
    }

}
