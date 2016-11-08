package krok.lifts;

import android.widget.EditText;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tilman on 01.11.16.
 */

public class Max {

    private String mLift;
    private double mWeight;
    private int mReps;
    private Date mDate;

    public Max(String lift) {
        mLift = lift;
        mWeight = -1;
        mReps = 1;
        mDate = GregorianCalendar.getInstance().getTime();
    }

    public Max(String lift, double weight, long date) {
        mLift = lift;
        mWeight = weight;
        mReps = 1;
        mDate = new Date(date);
    }

    public Max(String lift, double weight, int reps, long date) {
        mLift = lift;
        mWeight = weight;
        mReps = reps;
        mDate = new Date(date);

    }

    public void setLift(String lift) {
        mLift = lift;
    }

    public void setWeight(double weight) {
        mWeight = weight;
    }

    public void setReps(int reps) {
        mReps = reps;
    }

    public String getLift() {
        return mLift;
    }

    public int getReps() {
        return mReps;
    }

    public double getWeight() {
        return mWeight;
    }

    public Date getDate() {
        return mDate;
    }

    public double calculateMax() {
        double maxWeight = (mReps == 1)? mWeight : mWeight * mReps * 0.0333 + mWeight;
        return Math.round(maxWeight*100)/100;
    }

    public static Max[] getArray(String[] lifts) {
        Max[] result = new Max[lifts.length];
        for (int i = 0; i < lifts.length; i++) {
            result[i] = new Max(lifts[i]);
        }
        return result;
    }
}
