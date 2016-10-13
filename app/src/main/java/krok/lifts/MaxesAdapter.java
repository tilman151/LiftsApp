package krok.lifts;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by tilman on 08.10.16.
 */
public class MaxesAdapter extends android.support.v7.widget.RecyclerView.Adapter<MaxesAdapter.ViewHolder> {

    private final String[] mLifts;

    public MaxesAdapter(Context context){
        Resources resources = context.getResources();
        mLifts = resources.getStringArray(R.array.compound);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_tile_content, parent, false));
            mTextView = (TextView) itemView.findViewById(R.id.tile_title);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mLifts[position % mLifts.length]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
