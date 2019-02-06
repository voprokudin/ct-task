package p.vasylprokudin.customertimes.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import p.vasylprokudin.customertimes.data.network.ColumnName;
import p.vasylprokudin.customertimes.data.network.DescribeResponse;
import p.vasylprokudin.customertimes.data.network.MainData;
import p.vasylprokudin.customertimes.data.network.QueryResponse;

import static p.vasylprokudin.customertimes.ui.fragments.fragment_results.FragmentResultsPresenter.getMainData;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABESE_NAME = "Accounts.db";
    public static final String TABLE_NAME = "Account";
    private  DescribeResponse describeBody;
    private ContentValues contentValues = new ContentValues();
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context, DescribeResponse response) {
        super(context, DATABESE_NAME, null, 1);
        this.describeBody = response;
        sqLiteDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + getColumnNamesAndTypes());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private String getColumnNamesAndTypes(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");

        List<ColumnName> columnNames = describeBody.getColumnNames();

        for (int i = 0; i < columnNames.size(); i ++){
            stringBuilder.append(columnNames.get(i).getName() + " TEXT");
            if (i != columnNames.size() - 1){
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append(")");
        return  stringBuilder.toString();
    }

    public boolean isTableExists() {
        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_NAME+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public boolean isInsertedDataIfColumnInTableExists(QueryResponse queryBody) {
        sqLiteDatabase = getWritableDatabase();
        boolean writeRow = false;
        boolean existsColumn = false;
        Cursor mCursor = null;
        List<MainData> records = queryBody.getRecords();
        try {
            //Query 1 row. Check if column exists
            mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " LIMIT 0", null);
            //getColumnIndex() gives us the index (0 to ...) of the column - otherwise we get a -1
            for (int j = 0; j < queryBody.getRecords().size(); j ++){
                MainData mainData = records.get(j);
                for (int i = 0; i < getFieldsNames().size(); i ++){
                    //if any of the keys of array named records[] matches the column name in DB
                    if (mCursor.getColumnIndex(getFieldsNames().get(i)) != -1){
                        putDataToContentValues(i, mainData);
                        if (!existsColumn)
                            existsColumn = true;
                    }
                }
                if (existsColumn){
                    long res = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
                    if (res != - 1){
                        if (!writeRow)
                        writeRow = true;
                    }
                }
            }
            return writeRow;
        } catch (Exception Exp) {
            return false;
        } finally {
            if (mCursor != null) mCursor.close();
        }
    }

    public boolean isEmptyTable(){
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " +TABLE_NAME, null);
        if (mCursor != null){
            mCursor.moveToFirst();
            try {
                if (mCursor.getInt (0) == 0) {
                    //not empty
                    return false;
                }
                else return true;
            }catch (CursorIndexOutOfBoundsException e){
                //empty
                return true;
            }
            finally {
                mCursor.close();
            }
        }
        return true;
    }

    private void putDataToContentValues(int i, MainData mainData) {
        Object contentValue = getMainData(mainData, getFieldsNames().get(i));
        contentValues.put(getFieldsNames().get(i), String.valueOf(contentValue));
    }

    public List<String> getFieldsNames(){
        MainData main_data = new MainData();
        List<String> fieldsNames = new ArrayList<>();
        for(Field field : main_data.getClass().getFields()){
            String fieldName = field.getName();
            fieldsNames.add(fieldName);
        }
        return fieldsNames;
    }

    public Cursor getAllData(){
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Account", null);
        return cursor;
    }

    public List<String> getColumnNames() {
        List<String> column_names = new ArrayList<>();
        List<ColumnName> columnNames = describeBody.getColumnNames();
        for (int i = 0; i < columnNames.size(); i ++){
            column_names.add(columnNames.get(i).getName());
        }
        return column_names;
    }

}
