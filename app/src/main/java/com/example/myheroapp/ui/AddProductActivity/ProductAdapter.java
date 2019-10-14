package com.example.myheroapp.ui.AddProductActivity;


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.myheroapp.R;
        import com.example.myheroapp.models.Product;

        import java.util.ArrayList;
        import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    public interface DeleteProductInterface{
        void OnProductDeleted(int productId);
    }

    private Context mContext;
    private List<Product> mItems;
    private DeleteProductInterface mDeleteProductInterface;

    public ProductAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final Product product = mItems.get(position);
        holder.tvProductName.setText(product.getName());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteProductInterface.OnProductDeleted(product.getId());
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

    public void setItems(List<Product> items){
        if(mItems == null){
            mItems = new ArrayList<>();
        }else{
            mItems.clear();
        }
        mItems.addAll(items);
    }

    public void setDeleteProductInterface(DeleteProductInterface deleteProductInterfacee){
        mDeleteProductInterface = deleteProductInterfacee;
    }

}
