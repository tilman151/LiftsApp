package krok.lifts;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by tilman on 08.10.16.
 */
public class MaxesAdapter extends android.support.v7.widget.RecyclerView.Adapter<MaxesAdapter.ViewHolder> {

    private final String[] mLifts;
    private  Max[] mMaxes;
    private final Drawable[] mImages;

    public MaxesAdapter(Context context, Max[] maxes){
        Resources resources = context.getResources();
        mLifts = resources.getStringArray(R.array.compound);
        TypedArray a = resources.obtainTypedArray(R.array.compoundDrawables);
        mImages = new Drawable[a.length()];
        for (int i = 0; i < mImages.length; i++){
            mImages[i] = a.getDrawable(i);
        }
        mMaxes = maxes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public TextView mMaxText;
        public ImageView mImageView;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_tile_content, parent, false));
            mTextView = (TextView) itemView.findViewById(R.id.tileTitle);
            mMaxText = (TextView) itemView.findViewById(R.id.tileMax);
            mImageView = (ImageView) itemView.findViewById(R.id.tileImage);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mMaxes[position].getLift());
        holder.mMaxText.setText(String.format(new Locale("en","us"),"%s kg",mMaxes[position].calculateMax()));

        holder.mImageView.setImageDrawable(mImages[position % mImages.length]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CompoundAnalysisActivity.class);
                intent.putExtra("Lift", mLifts[position % mLifts.length]);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mMaxes == null)? 0 : mMaxes.length;
    }

    public void setMaxes(Max[] maxes) {
        mMaxes = maxes;
    }
}
