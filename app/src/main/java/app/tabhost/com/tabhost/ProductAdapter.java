package app.tabhost.com.tabhost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

    private Context context;
    private List<Product> listProducts;

    private SqliteDatabase mDatabase;

    public ProductAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product singleProduct = listProducts.get(position);

        DecimalFormat precision = new DecimalFormat("0 đ");

        holder.ten.setText(singleProduct.getTen());
        holder.gia.setText(String.valueOf(precision.format(singleProduct.getGia())));
        holder.soluong.setText(String.valueOf(singleProduct.getSoluong()));
        holder.dvt.setText(singleProduct.getDvt());
        holder.ghichu.setText(singleProduct.getGhichu());

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteProduct(singleProduct.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }


    private void editTaskDialog(final Product product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText tenField = (EditText)subView.findViewById(R.id.enter_ten);
        final EditText giaField = (EditText)subView.findViewById(R.id.enter_gia);
        final EditText soluongField = (EditText)subView.findViewById(R.id.enter_soluong);
        final EditText dvtField = (EditText)subView.findViewById(R.id.enter_dvt);
        final EditText ghichuField = (EditText)subView.findViewById(R.id.enter_ghichu);


        if(product != null){
            tenField.setText(product.getTen());
            giaField.setText(String.valueOf(product.getGia()));
            soluongField.setText(String.valueOf(product.getSoluong()));
            dvtField.setText(product.getDvt());
            ghichuField.setText(product.getGhichu());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    final String ten = tenField.getText().toString();
                    final double gia = Double.parseDouble(String.valueOf(giaField.getText().toString()));
                    final int soluong = Integer.parseInt(soluongField.getText().toString());
                    final String dvt = dvtField.getText().toString();
                    final String ghichu = ghichuField.getText().toString();

                    if (TextUtils.isEmpty(ten) || gia <= 0 || soluong <= 0 || TextUtils.isEmpty(dvt)) {
                        Toast.makeText(context, "Không được để trống!!!", Toast.LENGTH_LONG).show();
                    } else {
                        mDatabase.updateProduct(new Product(product.getId(), ten, gia, soluong, dvt, ghichu));
                        //refresh the activity
                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }

                }catch(NumberFormatException e){
                    Toast.makeText(context, "Không được để trống!!!", Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
