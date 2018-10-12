package app.tabhost.com.tabhost;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView ten;
    public TextView gia;
    public TextView soluong;
    public TextView dvt;
    public TextView ghichu;
    public ImageView deleteProduct;
    public  ImageView editProduct;

    public ProductViewHolder(View itemView) {
        super(itemView);
        ten = (TextView)itemView.findViewById(R.id.product_ten);
        gia = (TextView)itemView.findViewById(R.id.product_gia);
        soluong = (TextView)itemView.findViewById(R.id.product_soluong);
        dvt = (TextView)itemView.findViewById(R.id.product_dvt);
        ghichu = (TextView)itemView.findViewById(R.id.product_ghichu);
        deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
        editProduct = (ImageView)itemView.findViewById(R.id.edit_product);
    }
}
