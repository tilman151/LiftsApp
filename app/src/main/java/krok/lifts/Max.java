package krok.lifts;

import java.util.Date;

/**
 * Created by tilman on 01.11.16.
 */

public class Max {

    private String mLift;
    private float mWeight;
    private Date mDate;

    public Max(String lift, float weight, int date) {
        mLift = lift;
        mWeight = weight;
        mDate = new Date(date);
    }

    public Max(String lift, float weight, int reps, int date) {
        mLift = lift;
        mWeight = calculateMax(weight, reps);
        mDate = new Date(date);
    }

    public String getLift() {
        return mLift;
    }

    public float getWeight() {
        return mWeight;
    }

    public Date getDate() {
        return mDate;
    }

    public String toSQL() {
        return "INSERT INTO " + LiftsContract.Maxes.TABLE_NAME + " (" +
                LiftsContract.Maxes.COLUMN_NAME_DATE +
                LiftsContract.Maxes.COLUMN_NAME_LIFT +
                LiftsContract.Maxes.COLUMN_NAME_WEIGHT +
                " VALUES (" +
                mDate + "," +
                mLift + "," +
                mWeight +
                ");";
    }

    private float calculateMax(float weight, int reps) {
        return 0;
    }

}
