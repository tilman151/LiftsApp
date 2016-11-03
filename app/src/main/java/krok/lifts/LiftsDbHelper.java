package krok.lifts;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tilman on 28.10.16.
 */

public class LiftsDbHelper extends SQLiteOpenHelper {

    private static LiftsDbHelper mHelper;

    public static final String DATABASE_PATH = "/data/data/krok.lifts/databases/";
    public static final String DATABASE_NAME = "lifts.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_LIFTS =
            "CREATE TABLE " + LiftsContract.Lifts.TABLE_NAME + " (" +
                    LiftsContract.Lifts._ID + " INTEGER PRIMARY KEY," +
                    LiftsContract.Lifts.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    LiftsContract.Lifts.COLUMN_NAME_REPSCHEME + " INTEGER," +
                    LiftsContract.Lifts.COLUMN_NAME_COMPOUND + " INTEGER )";
    public static final String SQL_CREATE_MAXES =
            "CREATE TABLE " + LiftsContract.Maxes.TABLE_NAME + " (" +
                    LiftsContract.Maxes._ID + " INTEGER PRIMARY KEY," +
                    LiftsContract.Maxes.COLUMN_NAME_LIFT + " INTEGER," +
                    LiftsContract.Maxes.COLUMN_NAME_WEIGHT + " REAL," +
                    "FOREIGN KEY(" + LiftsContract.Maxes.COLUMN_NAME_LIFT + ") REFERENCES " + LiftsContract.Lifts.TABLE_NAME + "(" + LiftsContract.Lifts._ID + ") )";
    public static final String SQL_CREATE_SETS =
            "CREATE TABLE " + LiftsContract.Sets.TABLE_NAME + " (" +
                    LiftsContract.Sets._ID + " INTEGER PRIMARY KEY," +
                    LiftsContract.Sets.COLUMN_NAME_LIFT + " INTEGER," +
                    LiftsContract.Sets.COLUMN_NAME_WORKOUT + " INTEGER," +
                    LiftsContract.Sets.COLUMN_NAME_ORDER + " INTEGER," +
                    LiftsContract.Sets.COLUMN_NAME_REPS + " INTEGER," +
                    LiftsContract.Sets.COLUMN_NAME_WEIGHT + " REAL," +
                    "FOREIGN KEY(" + LiftsContract.Sets.COLUMN_NAME_LIFT + ") REFERENCES " + LiftsContract.Lifts.TABLE_NAME + "(" + LiftsContract.Lifts._ID + ")," +
                    "FOREIGN KEY(" + LiftsContract.Sets.COLUMN_NAME_WORKOUT + ") REFERENCES " + LiftsContract.Workouts.TABLE_NAME + "(" + LiftsContract.Workouts._ID + ") )";
    public static final String SQL_CREATE_WORKOUTS =
            "CREATE TABLE " + LiftsContract.Workouts.TABLE_NAME + " (" +
                    LiftsContract.Workouts._ID + " INTEGER PRIMARY KEY," +
                    LiftsContract.Workouts.COLUMN_NAME_DATE + " INTEGER," +
                    LiftsContract.Workouts.COLUMN_NAME_CYCLE + " INTEGER," +
                    LiftsContract.Workouts.COLUMN_NAME_WEEK + " INTEGER," +
                    LiftsContract.Workouts.COLUMN_NAME_DONE + " INTEGER," +
                    "FOREIGN KEY(" + LiftsContract.Workouts.COLUMN_NAME_CYCLE + ") REFERENCES " + LiftsContract.Cycles.TABLE_NAME + "(" + LiftsContract.Cycles._ID + ") )";
    public static final String SQL_CREATE_CYCLES =
            "CREATE TABLE " + LiftsContract.Cycles.TABLE_NAME + " (" +
                    LiftsContract.Cycles._ID + " INTEGER PRIMARY KEY," +
                    LiftsContract.Cycles.COLUMN_NAME_PRESS + " INTEGER," +
                    LiftsContract.Cycles.COLUMN_NAME_DEAD + " INTEGER," +
                    LiftsContract.Cycles.COLUMN_NAME_BENCH + " INTEGER," +
                    LiftsContract.Cycles.COLUMN_NAME_SQUAT + " INTEGER," +
                    "FOREIGN KEY(" + LiftsContract.Cycles.COLUMN_NAME_PRESS + ") REFERENCES " + LiftsContract.Maxes.TABLE_NAME + "(" + LiftsContract.Maxes._ID + ")," +
                    "FOREIGN KEY(" + LiftsContract.Cycles.COLUMN_NAME_DEAD + ") REFERENCES " + LiftsContract.Maxes.TABLE_NAME + "(" + LiftsContract.Maxes._ID + ")," +
                    "FOREIGN KEY(" + LiftsContract.Cycles.COLUMN_NAME_BENCH + ") REFERENCES " + LiftsContract.Maxes.TABLE_NAME + "(" + LiftsContract.Maxes._ID + ")," +
                    "FOREIGN KEY(" + LiftsContract.Cycles.COLUMN_NAME_SQUAT + ") REFERENCES " + LiftsContract.Maxes.TABLE_NAME + "(" + LiftsContract.Maxes._ID + " ) )";

    private final Context mContext;
    private static boolean mLock = false;

    private LiftsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public static synchronized LiftsDbHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new LiftsDbHelper(context.getApplicationContext());
        }
        while (mLock)
        {

        }
        return mHelper;
    }

    public Max[] getCurrentMaxes() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(
                LiftsContract.MaxesXlifts.VIEW_NAME,                        // FROM
                new String[] {LiftsContract.MaxesXlifts.COLUMN_NAME_LIFT,  // SELECT
                        LiftsContract.MaxesXlifts.COLUMN_NAME_WEIGHT,
                        LiftsContract.MaxesXlifts.COLUMN_NAME_DATE,
                        LiftsContract.MaxesXlifts.COLUMN_NAME_LIFTID},
                null,                                                       // WHERE
                null, // ==
                LiftsContract.MaxesXlifts.COLUMN_NAME_LIFT,                // GROUP BY
                "MAX(" + LiftsContract.MaxesXlifts.COLUMN_NAME_DATE + ")", // HAVING
                LiftsContract.MaxesXlifts.COLUMN_NAME_LIFTID,              // ORDER BY
                null);                                                      // LIMIT

        c.moveToFirst();
        Max[] maxes = new Max[c.getCount()];

        for (int i = 0; i < maxes.length; i++) {
            maxes[i] = new Max(c.getString(0), c.getFloat(1), c.getInt(2));
            c.moveToNext();
        }

        c.close();

        return maxes;
    }

    public void createDataBase() throws IOException {
        mLock = true;
        this.getReadableDatabase();
        try {
            copyDataBase();
        } catch (IOException e) {
            throw new Error("Can't copy database");
        }
        mLock = false;
    }

    private void copyDataBase() throws IOException {
        InputStream input = mContext.getAssets().open(DATABASE_NAME);

        String outputPath = DATABASE_PATH + DATABASE_NAME;
        OutputStream output = new FileOutputStream(outputPath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0)
            output.write(buffer, 0, length);

        output.flush();
        output.close();
        input.close();

        Log.d("DATABASE: ", "copyDataBase: DB created");
    }

}
