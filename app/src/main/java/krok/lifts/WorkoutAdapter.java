package krok.lifts;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tilman on 13.10.16.
 */
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private final String[] mLifts;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImage;
        public TextView mTitle;
        public TextView mMaxWeight;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.workout_layout, parent, false));
            mImage = (ImageView) itemView.findViewById(R.id.liftImage);
            mTitle = (TextView) itemView.findViewById(R.id.liftTitle);
            mMaxWeight = (TextView) itemView.findViewById(R.id.liftMaxWeight);
        }
    }

    public WorkoutAdapter(Context context) {
        Resources resources = context.getResources();
        mLifts = resources.getStringArray(R.array.compound);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText(mLifts[position % mLifts.length]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
