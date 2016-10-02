package vinodhkumar.hw3_mt16062_storagedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by VinodhKumar on 02/10/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static Context context;
    public static final String DATABASE_NAME = "StudentDetail.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table studentInfo " +
                        "(id integer primary key, name text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public Boolean insertInfo(int rollNo, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",rollNo);
        contentValues.put("name",name);
        try {
            db.insertOrThrow("studentInfo", null, contentValues);
            Toast.makeText(context, "Row Inserted!", Toast.LENGTH_SHORT).show();
            return true;
        }catch (Exception e){
            Toast.makeText(context.getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Cursor getInfo(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from studentInfo where id="+id+"", null );
        return res;
    }

    public boolean updateInfo (Integer id, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        try {
            db.update("studentInfo", contentValues, "id = ? ", new String[]{Integer.toString(id)});
            Toast.makeText(context, "Row Updated!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public Integer deleteInfo (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            return db.delete("studentInfo",
                    "id = ? ",
                    new String[]{Integer.toString(id)});
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return  -1;

        }
    }

}
