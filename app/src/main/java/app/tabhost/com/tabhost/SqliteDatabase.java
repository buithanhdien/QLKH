package app.tabhost.com.tabhost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "product";
    public	static final String TABLE_PRODUCTS = "products";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEN = "column_ten";
    private static final String COLUMN_GIA = "column_gia";
    private static final String COLUMN_SOLUONG = "column_soluong";
    private static final String COLUMN_DVT = "column_dvt";
    private static final String COLUMN_GHICHU = "column_ghichu";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_PRODUCTS_TABLE = "CREATE	TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_TEN + " TEXT," +
                COLUMN_GIA + " DOUBLE," + COLUMN_SOLUONG + " INTEGER," + COLUMN_DVT + " TEXT," + COLUMN_GHICHU + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public List<Product> listProducts(){
        String sql = "select * from " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String ten = cursor.getString(1);
                double gia = Double.parseDouble(cursor.getString(2));
                int soluong = Integer.parseInt(cursor.getString(3));
                String dvt = cursor.getString(4);
                String ghichu = cursor.getString(5);
                storeProducts.add(new Product(id, ten, gia, soluong, dvt, ghichu));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public void addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN, product.getTen());
        values.put(COLUMN_GIA, product.getGia());
        values.put(COLUMN_SOLUONG, product.getSoluong());
        values.put(COLUMN_DVT, product.getDvt());
        values.put(COLUMN_GHICHU, product.getGhichu());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public void updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN, product.getTen());
        values.put(COLUMN_GIA, product.getGia());
        values.put(COLUMN_SOLUONG, product.getSoluong());
        values.put(COLUMN_DVT, product.getDvt());
        values.put(COLUMN_GHICHU, product.getGhichu());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(product.getId())});
    }

    public Product findProduct(String name){
        String query = "Select * FROM "	+ TABLE_PRODUCTS + " WHERE " + COLUMN_TEN + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Product mProduct = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String productTen = cursor.getString(1);
            double productGia = cursor.getDouble(2);
            int productSoluong = Integer.parseInt(cursor.getString(3));
            String productDvt = cursor.getString(4);
            String productGhichu = cursor.getString(5);
            mProduct = new Product(id, productTen, productGia, productSoluong, productDvt, productGhichu);
        }
        cursor.close();
        return mProduct;
    }

    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }

    public Cursor Distance() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor Distance = sqLiteDatabase.rawQuery("SELECT Sum(" + COLUMN_GIA + ") AS myTotal FROM " + TABLE_PRODUCTS, null);
        return Distance;
    }
}
