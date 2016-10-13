package krok.lifts;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class StartScreen extends AppCompatActivity implements TileContentFragment.OnFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

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

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 2);
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        mRecyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MaxesAdapter(mRecyclerView.getContext());
        mRecyclerView.setAdapter(mAdapter);


        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.maxes_nav){
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
                }else {
                    expandButton.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate));
                    detailView.setVisibility(View.VISIBLE);
                }
            }
        });
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

    @Override
    public void onTileFragmentInteraction(Uri uri) {

    }
}
