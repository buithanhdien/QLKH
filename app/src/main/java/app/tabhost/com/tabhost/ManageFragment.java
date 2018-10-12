package app.tabhost.com.tabhost;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ManageFragment extends Fragment {

    private SqliteDatabase mDatabase;

    @SuppressLint("ValidFragment")
    ManageFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage, container, false);
        Button btnTong = (Button)rootView.findViewById(R.id.button_tong);
        Button btnXuat = (Button)rootView.findViewById(R.id.button_xuatexcel);
        final TextView txtTong = (TextView)rootView.findViewById(R.id.textView_tong);
        mDatabase = new SqliteDatabase(getActivity());

        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor distance = mDatabase.Distance();
                String result = "";
                NumberFormat formatter = new DecimalFormat("#0 đ");

                // get column value
                if (distance.moveToNext())
                    result = String.valueOf(formatter.format(distance.getDouble(distance.getColumnIndex("myTotal"))));

                txtTong.setText(result);
            }
        });

        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = new SqliteDatabase(getActivity());//here CredentialDb is my database. you can create your db object.
                File exportDir = new File(Environment.getExternalStorageDirectory(), "Excel_QLKH");

                if (!exportDir.exists())
                {
                    exportDir.mkdirs();
                }

                File file = new File(exportDir, "xlsfilename.xls");

                try
                {
                    file.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    SQLiteDatabase sql_db = mDatabase.getReadableDatabase();//here create a method ,and return SQLiteDatabaseObject.getReadableDatabase();
                    Cursor curCSV = sql_db.rawQuery("SELECT * FROM "+mDatabase.TABLE_PRODUCTS,null);
                    //csvWrite.writeNext(curCSV.getColumnNames());

                    while(curCSV.moveToNext())
                    {
                        //Which column you want to export you can add over here...
                        String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2), curCSV.getString(3), curCSV.getString(4)};
                        csvWrite.writeNext(arrStr);
                    }

                    csvWrite.close();
                    curCSV.close();
                }
                catch(Exception sqlEx)
                {
                    Log.e("Error:", sqlEx.getMessage(), sqlEx);
                }

            }
        });

        return rootView;

    }


}
