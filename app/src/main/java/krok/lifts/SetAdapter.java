package krok.lifts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by tilman on 20.10.16.
 */
public class SetAdapter extends android.support.v7.widget.RecyclerView.Adapter<SetAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private int mMaxItem;
    private int mItemCount;
    private ProgressNotifier mProgressNotifier;

    @Override
    public void onItemDismiss(int position) {
        notifyItemRemoved(position);
        mItemCount--;
        if (mItemCount == 0)
            mProgressNotifier.onFinish();
        else
            mProgressNotifier.onUpdate(mMaxItem-mItemCount);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.set_layout, parent, false));
            mTextView = (TextView) itemView.findViewById(R.id.setCaption);
        }
    }

    public SetAdapter(Context context, ProgressNotifier progressNotifier) {
        mMaxItem = 5;
        mItemCount = mMaxItem;
        mProgressNotifier = progressNotifier;
        mProgressNotifier.onBind(mMaxItem);
    }

    @Override
    public SetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SetAdapter.ViewHolder(LayoutInflater.from(parent.getContext()), parent);

    }

    @Override
    public void onBindViewHolder(SetAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText("5 x 100 kg");
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

}
