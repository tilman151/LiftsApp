package krok.lifts;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
        public RecyclerView mSetsRecycler;
        public ProgressBar mProgressBar;

        private LayerDrawable mDoneLayer;

        public ViewHolder(LayoutInflater inflater, final ViewGroup parent) {
            super(inflater.inflate(R.layout.workout_layout, parent, false));
            mImage = (ImageView) itemView.findViewById(R.id.liftImage);
            mTitle = (TextView) itemView.findViewById(R.id.liftTitle);
            mMaxWeight = (TextView) itemView.findViewById(R.id.liftMaxWeight);
            mSetsRecycler = (RecyclerView) itemView.findViewById(R.id.setRecycler);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.liftProgressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), LiftActivity.class);
                    intent.putExtra("Lift", mTitle.getText());
                    parent.getContext().startActivity(intent);
                }
            });

            Drawable[] layers =  new Drawable[2];
            layers[0] = mImage.getDrawable();
            layers[1] = itemView.getContext().getDrawable(R.drawable.ic_done_overlay);
            mDoneLayer = new LayerDrawable(layers);
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
        return 3;
    }
}
