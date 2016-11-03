package krok.lifts;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class StartScreen extends AppCompatActivity {

    SharedPreferences mPrefs;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // Get shared preferences
        mPrefs = getSharedPreferences("com.krok.lifts", MODE_PRIVATE);

        if (mPrefs.getBoolean("firstRun", true)) {
            new CreateDatabaseTask().execute(LiftsDbHelper.getInstance(this.getBaseContext()));
            mPrefs.edit().putBoolean("firstRun", false).apply();
        }

        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ImageView cardImg = (ImageView) this.findViewById(R.id.nextWorkoutImg);
        Picasso.with(this.getBaseContext()).load(R.drawable.dead).into(cardImg);

        // Find RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.maxesRecycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(this, 2);
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        mRecyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new MaxesAdapter(mRecyclerView.getContext(), null);
        mRecyclerView.setAdapter(mAdapter);

        // Retrieve maxes asyncronous
        new GetMaxesTask().execute(LiftsDbHelper.getInstance(getBaseContext()));

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.getMenu().getItem(0).setChecked(true);

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.maxes_nav) {
                            Intent intent = new Intent(getBaseContext(), MaxesActivity.class);
                            startActivity(intent);
                        }
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        // Set Action for FAB
        FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.nextWorkoutFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);
                startActivity(intent);
            }
        });

        // Set Action for expandButton
        final ImageButton expandButton = (ImageButton) this.findViewById(R.id.expandButton);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoTransition transition = new AutoTransition();
                transition.setDuration(150);
                TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.card_view), transition);
                View detailView = findViewById(R.id.detailView);
                if (detailView.getVisibility() == View.VISIBLE) {
                    expandButton.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_back));
                    detailView.setVisibility(View.GONE);
                } else {
                    expandButton.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate));
                    detailView.setVisibility(View.VISIBLE);
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private class CreateDatabaseTask extends AsyncTask<LiftsDbHelper, Integer, Boolean> {

        private Dialog dialog = new ProgressDialog(StartScreen.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setTitle("Preparing for first run...");
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(LiftsDbHelper... helper) {
            try {
                helper[0].createDataBase();
            } catch (IOException e) {
                throw new Error("First run: Unable to create database");
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
        }

    }

    private class GetMaxesTask extends AsyncTask<LiftsDbHelper, Integer, Max[]> {

        @Override
        protected Max[] doInBackground(LiftsDbHelper... helper) {
            return helper[0].getCurrentMaxes();
        }

        @Override
        protected void onPostExecute(Max[] maxes) {
            if (maxes.length != 0)
                ((MaxesAdapter) mAdapter).setmMaxes(maxes);
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartScreen.this);
                builder.setTitle("No Maxes defined...");
                builder.setMessage("We need to calculate your strength.");
                builder.setCancelable(false);
                builder.setPositiveButton("Let's go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getBaseContext(), MaxesActivity.class);
                        startActivity(intent);
                    }
                });
                builder.create().show();
            }
        }
    }
}
