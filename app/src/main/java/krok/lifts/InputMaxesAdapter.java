package krok.lifts;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by tilman on 12.10.16.
 */
public class InputMaxesAdapter extends android.support.v7.widget.RecyclerView.Adapter<InputMaxesAdapter.ViewHolder> implements View.OnFocusChangeListener {

    private final Max[] mMaxes;
    private Max[] mChangedMaxes;
    private final String[] mLifts;
    private final Drawable[] mDrawable;
    private final boolean mNoMaxes;

    private final static int WEIGHT = 0;
    private final static int REPS = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mLabel;
        public ImageView mImage;
        public TextInputLayout mReps, mWeight;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.max_layout, parent, false));
            int height = parent.getMeasuredHeight() / 4;
            int width = parent.getMeasuredWidth();
            itemView.setLayoutParams(new RecyclerView.LayoutParams(width, height));
            mLabel = (TextView) itemView.findViewById(R.id.maxLabel);
            mReps = (TextInputLayout) itemView.findViewById(R.id.maxReps);
            mWeight = (TextInputLayout) itemView.findViewById(R.id.maxWeight);
            mImage = (ImageView) itemView.findViewById(R.id.maxImage);
        }

    }

    public InputMaxesAdapter(Context context, Max[] maxes) {
        Resources resources = context.getResources();
        mLifts = resources.getStringArray(R.array.compound);
        if (maxes == null) {
            mMaxes = Max.getArray(mLifts);
            mNoMaxes = true;
        } else {
            mMaxes = maxes;
            mNoMaxes = false;
        }
        mChangedMaxes = Max.getArray(mLifts);
        TypedArray a = resources.obtainTypedArray(R.array.compoundDrawables);
        mDrawable = new Drawable[a.length()];
        for (int i = 0; i < mDrawable.length; i++){
            mDrawable[i] = a.getDrawable(i);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InputMaxesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(InputMaxesAdapter.ViewHolder holder, final int position) {
        if (!mNoMaxes) {
            holder.mWeight.getEditText().setText(String.format(new Locale("en","us"),"%s",mMaxes[position].calculateMax()));
            holder.mReps.getEditText().setText("1");
        }
        holder.mLabel.setText(mLifts[position]);
        holder.mImage.setImageDrawable(mDrawable[position % mDrawable.length]);

        holder.mWeight.getEditText().setTag(R.id.position, position);
        holder.mWeight.getEditText().setTag(R.id.type, WEIGHT);
        holder.mWeight.getEditText().setOnFocusChangeListener(this);

        holder.mReps.getEditText().setTag(R.id.position, position);
        holder.mReps.getEditText().setTag(R.id.type, REPS);
        holder.mReps.getEditText().setOnFocusChangeListener(this);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public Max[] getMaxes(){
        return mMaxes;
    }

    public Max[] getChangedMaxes() {
        Max[] result = new Max[4];
        for (int i = 0; i < mChangedMaxes.length; i++) {
            double weight = mChangedMaxes[i].calculateMax();
            if (Double.compare(weight,0.0) == 0)
                return null;
            if (Double.compare(weight, mMaxes[i].calculateMax()) == 0 ||
                    Double.compare(weight, -1) == 0)
                result[i] = null;
            else
                result[i] = mChangedMaxes[i];
        }
        return result;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            int position = (Integer) v.getTag(R.id.position);
            switch ((Integer) v.getTag(R.id.type)) {
                case WEIGHT:
                    String textWeight = ((EditText) v).getText().toString();
                    double weight = (textWeight.compareTo("") == 0)? -1 : Math.round(Double.valueOf(textWeight)*100)/100;
                    mChangedMaxes[position].setWeight(weight);
                    break;
                case REPS:
                    String textReps = ((EditText) v).getText().toString();
                    int reps = (textReps.compareTo("") == 0)? -1 : Integer.valueOf(textReps);
                    mChangedMaxes[position].setReps(reps);
            }
        }
    }

}
