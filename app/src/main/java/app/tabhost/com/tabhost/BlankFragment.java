package app.tabhost.com.tabhost;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class BlankFragment extends Fragment {

    private SqliteDatabase mDatabase;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView productView = (RecyclerView)rootView.findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);

        mDatabase = new SqliteDatabase(getActivity());
        List<Product> allProducts = mDatabase.listProducts();

        if(allProducts.size() > 0){
            productView.setVisibility(View.VISIBLE);
            ProductAdapter mAdapter = new ProductAdapter(getActivity(), allProducts);
            productView.setAdapter(mAdapter);

        }else {
            productView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        productView.setLayoutManager(llm);

        return rootView;
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText tenField = (EditText)subView.findViewById(R.id.enter_ten);
        final EditText giaField = (EditText)subView.findViewById(R.id.enter_gia);
        final EditText soluongField = (EditText)subView.findViewById(R.id.enter_soluong);
        final EditText dvtField = (EditText)subView.findViewById(R.id.enter_dvt);
        final EditText ghichuField = (EditText)subView.findViewById(R.id.enter_ghichu);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    final String ten = tenField.getText().toString();
                    final double gia = Double.parseDouble(giaField.getText().toString());
                    final int soluong = Integer.parseInt(soluongField.getText().toString());
                    final String dvt = dvtField.getText().toString();
                    final String ghichu = ghichuField.getText().toString();


                    if (TextUtils.isEmpty(ten) || gia <= 0 || soluong <= 0 || TextUtils.isEmpty(dvt)) {
                        Toast.makeText(getActivity(), "Không được để trống!!!", Toast.LENGTH_LONG).show();
                    } else {
                        Product newProduct = new Product(ten, gia, soluong, dvt, ghichu);
                        mDatabase.addProduct(newProduct);

                        //refresh the activity
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }

                }catch(NumberFormatException e){
                    Toast.makeText(getActivity(), "Không được để trống!!!", Toast.LENGTH_LONG).show();
                }

                }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

}
