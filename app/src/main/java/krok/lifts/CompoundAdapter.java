package krok.lifts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tilman on 26.10.16.
 */
public class CompoundAdapter extends RecyclerView.Adapter<CompoundAdapter.ViewHolder> {

    private int[][] mDataSet;

    public CompoundAdapter(Context context, int[][] dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mMax.setText("100kg");
        holder.mDate.setText("11.11.2016");
        holder.mWeight.setText("70kg");
        holder.mReps.setText("5 Reps");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mDate;
        public TextView mWeight;
        public TextView mReps;
        public TextView mMax;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.compound_layout, parent, false));
            mDate = (TextView) itemView.findViewById(R.id.compoundDate);
            mWeight = (TextView) itemView.findViewById(R.id.compoundWeight);
            mReps = (TextView) itemView.findViewById(R.id.compoundReps);
            mMax = (TextView) itemView.findViewById(R.id.compoundMax);
        }
    }
}
