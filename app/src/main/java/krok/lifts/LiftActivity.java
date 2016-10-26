package krok.lifts;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LiftActivity extends AppCompatActivity implements ProgressNotifier {

    public RecyclerView mSetsRecycler;
    public ProgressBar mProgressBar;
    public ImageView mImage;

    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private LayerDrawable mDoneLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lift);

        Intent intent = getIntent();
        TextView title = (TextView) findViewById(R.id.liftTitle);
        title.setText(intent.getCharSequenceExtra("Lift"));
        mImage = (ImageView) findViewById(R.id.liftImage);
        Drawable drawable = getDrawable(R.drawable.dead);
        mImage.setImageDrawable(drawable);

        mSetsRecycler = (RecyclerView) findViewById(R.id.setRecycler);
        mProgressBar = (ProgressBar) findViewById(R.id.liftProgressBar);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getBaseContext());
        mSetsRecycler.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new SetAdapter(mSetsRecycler.getContext(), this);
        mSetsRecycler.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback((SetAdapter) mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mSetsRecycler);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Disable Scrolling Toolbar
        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        p.setScrollFlags(0);
        toolbar.setLayoutParams(p);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24px);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Lift");
        }

        Drawable[] layers =  new Drawable[2];
        layers[0] = mImage.getDrawable();
        layers[1] = getBaseContext().getDrawable(R.drawable.ic_done_overlay);
        mDoneLayer = new LayerDrawable(layers);
    }

    @Override
    public void onBind(int maxProgress) {
        mProgressBar.setMax(maxProgress);
        mProgressBar.setProgress(0);
    }

    @Override
    public void onUpdate(int progress) {
        mProgressBar.setProgress(progress);
    }

    @Override
    public void onFinish() {
        mProgressBar.setProgress(mProgressBar.getMax());
        mImage.setImageDrawable(mDoneLayer);

    }

}
