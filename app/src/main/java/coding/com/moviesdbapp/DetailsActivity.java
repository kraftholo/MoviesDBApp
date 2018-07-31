package coding.com.moviesdbapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import coding.com.moviesdbapp.Utilities.JsonUtils;
import coding.com.moviesdbapp.Utilities.NetworkUtils;

import static coding.com.moviesdbapp.Data.MovieContract.FavEntry.COLUMN_MOVIE_ID;
import static coding.com.moviesdbapp.Data.MovieContract.FavEntry.COLUMN_OVERVIEW;
import static coding.com.moviesdbapp.Data.MovieContract.FavEntry.COLUMN_POPULARITY;
import static coding.com.moviesdbapp.Data.MovieContract.FavEntry.COLUMN_POSTER_PATH;
import static coding.com.moviesdbapp.Data.MovieContract.FavEntry.COLUMN_RELEASE_DATE;
import static coding.com.moviesdbapp.Data.MovieContract.FavEntry.COLUMN_TITLE;
import static coding.com.moviesdbapp.Data.MovieContract.FavEntry.COLUMN_VOTE_AVERAGE;
import static coding.com.moviesdbapp.Data.MovieContract.FavEntry.COLUMN_VOTE_COUNT;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,TrailersAdapter.TrailersAdapterOnClickHandler {


    //todo: this class will implement cursor loader
    //todo: api call for the trailers + recycler view for trailers
    //todo: make a review activity which is launched on clicking the reviews button (pass movieId in intent)
    //todo: inside the review activity , recyclerView and a call to the api for reviews

    ArrayList<Movie> passedList;
    Movie movie;

    TextView mOriginalTitle,mPlotSynopsis,mUserRating,mReleaseDate;
    ImageView mThumbnailIV;
    ProgressBar mProgressBar;
    RecyclerView trailerRV;
    TrailersAdapter mTrailerAdapter;

    private static final String TAG = DetailsActivity.class.getSimpleName();

    private static final int DETAILS_LOADER_ID = 100;


    public static final String[] MOVIE_PROJECTION = {COLUMN_TITLE,COLUMN_MOVIE_ID,COLUMN_POPULARITY
                                                        ,COLUMN_VOTE_AVERAGE,COLUMN_VOTE_COUNT,COLUMN_OVERVIEW
                                                        ,COLUMN_POSTER_PATH,COLUMN_RELEASE_DATE};

    public static final int TITLE_INDEX= 0;
    public static final int MOVIE_ID_INDEX= 1;
    public static final int POPULARITY_INDEX= 2;
    public static final int VOTE_AVERAGE_INDEX= 3;
    public static final int VOTE_COUNT_INDEX= 4;
    public static final int OVERVIEW_INDEX= 5;
    public static final int POSTER_PATH_INDEX= 6;
    public static final int RELEASE_DATE_INDEX= 7;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        if(intent.hasExtra(MainActivity.MOVIE_CLICKED_KEY)){

            passedList = intent.getParcelableArrayListExtra(MainActivity.MOVIE_CLICKED_KEY);
            movie = passedList.get(0);
        }

        mOriginalTitle = findViewById(R.id.originalTitleTV);
        mPlotSynopsis = findViewById(R.id.plotSynopsisTV);
        mUserRating = findViewById(R.id.userRatingTV);
        mReleaseDate = findViewById(R.id.releaseDateTV);


        mThumbnailIV = findViewById(R.id.details_IV);
        mProgressBar = findViewById(R.id.details_progress_bar);



        mTrailerAdapter = new TrailersAdapter(this,this,null);

        trailerRV = findViewById(R.id.trailers_RV);

        trailerRV.setAdapter(mTrailerAdapter);
        trailerRV.setLayoutManager(new LinearLayoutManager(this));
        trailerRV.setVisibility(View.INVISIBLE);

        getLoaderManager().restartLoader(DETAILS_LOADER_ID,null,this);

    }

    private void setValues(){
        Picasso.get().load(String.valueOf(NetworkUtils.generateImageUrl(movie))).into(mThumbnailIV);
        mOriginalTitle.setText(movie.getOriginalTitle());
        mPlotSynopsis.setText(movie.getPlotSynopsis());
        mUserRating.setText(""+movie.getUserRating());
        mReleaseDate.setText(movie.getReleaseDate());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Cursor cursor;

        switch (id){
            case R.id.star:
            //todo: call the contentProvider to store or delete the movie from favourites




            break;
            case android.R.id.home:                         //up button functionality
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int loaderId, Bundle args) {
       return  new AsyncTaskLoader<String>(this) {

           String jsonStr;

           @Override
           protected void onStartLoading() {

               mProgressBar.setVisibility(View.VISIBLE);


               forceLoad();
           }

           @Override

           public String loadInBackground() {
               //todo(C): Here make the call to the api and  get info in form of jsonStr
               URL url = NetworkUtils.generateUrlForTrailers(movie.getMovieID());
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

        ArrayList<Trailer> newList = JsonUtils.generateTrailersList(data);

        setValues();
        mTrailerAdapter.swapList(newList);

        trailerRV.setVisibility(View.VISIBLE);



        mProgressBar.setVisibility(View.GONE);


    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onClick(String str) {
        //todo: launch the youtube video from here
    }
}
