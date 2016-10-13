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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by tilman on 12.10.16.
 */
public class InputMaxesAdapter extends android.support.v7.widget.RecyclerView.Adapter<InputMaxesAdapter.ViewHolder> {

    private final String[] mLifts;
    private final Drawable[] mDrawable;

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

    public InputMaxesAdapter(Context context) {
        Resources resources = context.getResources();
        mLifts = resources.getStringArray(R.array.compound);
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
    public void onBindViewHolder(InputMaxesAdapter.ViewHolder holder, int position) {
        holder.mLabel.setText(mLifts[position % mLifts.length]);
        holder.mImage.setImageDrawable(mDrawable[position % mDrawable.length]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
