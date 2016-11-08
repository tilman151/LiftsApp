package krok.lifts;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;
import java.util.GregorianCalendar;

public class MaxesActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxes);

        // Find RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.inputMaxesRecycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Max[] maxes = null;

        // Check if called from missing maxes dialog
        if (!getIntent().getBooleanExtra("MISSING_MAXES",false)) {
            maxes = LiftsDbHelper.getInstance(getApplicationContext()).getCurrentMaxes();
        }

        // specify an adapter
        mAdapter = new InputMaxesAdapter(mRecyclerView.getContext(), maxes);
        mRecyclerView.setAdapter(mAdapter);


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
            supportActionBar.setTitle("Maxes");
        }

        // Set Fab behavior
        FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.maxesFab);
        fab.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        ((View) v.getParent()).requestFocus();
        Max[] changedMaxes = ((InputMaxesAdapter) mAdapter).getChangedMaxes();
        if (changedMaxes == null) {
            showErrorDialog();
            return;
        }
        new SaveMaxesTask().execute(changedMaxes);
        Intent intent = new Intent(getBaseContext(), StartScreen.class);
        startActivity(intent);
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MaxesActivity.this);
        builder.setTitle("Error");
        builder.setMessage("Enter maxes bigger than zero.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    private class SaveMaxesTask extends AsyncTask<Max[], Integer, Boolean> {

        private Dialog dialog = new ProgressDialog(MaxesActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setTitle("Saving maxes...");
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Max[]... maxes) {
            LiftsDbHelper helper = LiftsDbHelper.getInstance(getApplicationContext());
            helper.saveMaxes(maxes[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
        }

    }
}
