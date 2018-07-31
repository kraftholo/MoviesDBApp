package coding.com.moviesdbapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import coding.com.moviesdbapp.Utilities.JsonUtils;
import coding.com.moviesdbapp.Utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,MoviesAdapter.MoviesAdapterOnClickHandler{



    
    //todo: android:authorities in strings.xml

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MOVIEDB_LOADER_ID = 123;

    private boolean sortStatus=true;         //true -> mostPopular  // false -> TopRated

    public static final String MOVIE_CLICKED_KEY = "clicked-movie";
    private static final String SORTORDER_KEY = "sort-order-key";
    private static final String MOVIE_LIST_KEY = "movie-list-key";
    private ArrayList<Movie> moviesList;

    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingBar;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){

            if(savedInstanceState.containsKey(SORTORDER_KEY)){

                sortStatus=savedInstanceState.getBoolean(SORTORDER_KEY);

            }
            if(savedInstanceState.containsKey(MOVIE_LIST_KEY)){
               moviesList =savedInstanceState.getParcelableArrayList(MOVIE_LIST_KEY);
            }
        }else{
            Log.d(TAG, "SAVEDINSTANCESTATE WAS NULL");
            sortStatus=true;
        }

        mRecyclerView = findViewById(R.id.moviesRecyclerView);
        mLoadingBar = findViewById(R.id.progressBar);

        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingBar.setVisibility(View.INVISIBLE);

        mAdapter = new MoviesAdapter(this,this,null);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        getLoaderManager().restartLoader(MOVIEDB_LOADER_ID,null,this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_item_most_popular:
                Log.d(TAG, "onOptionsItemSelected: Sorting by MOST POPULAR ");
                sortStatus = true;
                recreate();
                break;

            case R.id.menu_item_top_rated:
                Log.d(TAG, "onOptionsItemSelected: Sorting by TOP RATED");
                sortStatus = false;
                recreate();
                break;
            case R.id.menu_item_favourites:
                Log.d(TAG, "onOptionsItemSelected: show FAVOURITES");
                //todo: Make the call to the contentProvider to get the db and change my arrayList given to the adapter

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String jsonStr;

            @Override
            protected void onStartLoading() {

                mLoadingBar.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);


                forceLoad();
            }

            @Override

            public String loadInBackground() {
                //todo(C): Here make the call to the api and  get info in form of jsonStr
                URL url = NetworkUtils.generateUrl(sortStatus);
                try {
                    jsonStr = NetworkUtils.getResponseFromHttpUrl(url);

                    return jsonStr;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        //todo(C): here from the jsonStr make ur arraylist of movie objects ,make recyclerview visible
        moviesList = JsonUtils.generateMovieList(data);

        mAdapter.swapList(moviesList);


        mRecyclerView.setVisibility(View.VISIBLE);
        mLoadingBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SORTORDER_KEY,sortStatus);
        outState.putParcelableArrayList(MOVIE_LIST_KEY,moviesList);
    }

    @Override
    public void onClick(Movie movie) {
        Intent startDetailsAct = new Intent(MainActivity.this,DetailsActivity.class);

        ArrayList<Movie> passedAL = new ArrayList<Movie>();
        passedAL.add(movie);

        startDetailsAct.putParcelableArrayListExtra(MOVIE_CLICKED_KEY,passedAL);

//        Bundle bundle = new Bundle();
//        bundle.putParcelable(MOVIE_CLICKED_KEY,movie);
//        startDetailsAct.putExtras(bundle);

        getBaseContext().startActivity(startDetailsAct);
    }
}
