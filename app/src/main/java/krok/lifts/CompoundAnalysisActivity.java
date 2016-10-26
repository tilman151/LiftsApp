package krok.lifts;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class CompoundAnalysisActivity extends AppCompatActivity {

    private RecyclerView mCompoundRecycler;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound_analysis);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Lift");

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24px);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(title);
        }

        GraphView graph = (GraphView) findViewById(R.id.compoundGraph);
        GridLabelRenderer grid = graph.getGridLabelRenderer();
        grid.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        grid.setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        grid.setLabelsSpace(20);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 7),
                new DataPoint(4, 6)
        });
        series.setColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        series.setThickness(8);
        graph.addSeries(series);

        mCompoundRecycler = (RecyclerView) findViewById(R.id.compoundRecycler);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getBaseContext());
        mCompoundRecycler.setLayoutManager(mLayoutManager);

        // add divider
        DividerItemDecoration dividerDecoration = new DividerItemDecoration(mCompoundRecycler.getContext(),mLayoutManager.getOrientation());
        dividerDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        mCompoundRecycler.addItemDecoration(dividerDecoration);

        // specify an adapter
        mAdapter = new CompoundAdapter(mCompoundRecycler.getContext(), new int[1][2]);
        mCompoundRecycler.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
