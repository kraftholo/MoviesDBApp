package coding.com.moviesdbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import coding.com.moviesdbapp.Utilities.NetworkUtils;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<Movie> passedList;
    Movie movie;

    TextView mOriginalTitle,mPlotSynopsis,mUserRating,mReleaseDate;
    ImageView mThumbnailIV;

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        if(intent.hasExtra(MainActivity.MOVIE_CLICKED_KEY)){
            Log.e(TAG, "does have the arraylist passed" );
            passedList = intent.getParcelableArrayListExtra(MainActivity.MOVIE_CLICKED_KEY);
            movie = passedList.get(0);
        }

        mOriginalTitle = findViewById(R.id.originalTitleTV);
        mPlotSynopsis = findViewById(R.id.plotSynopsisTV);
        mUserRating = findViewById(R.id.userRatingTV);
        mReleaseDate = findViewById(R.id.releaseDateTV);

        mThumbnailIV = findViewById(R.id.details_IV);

        setValues();

    }

    private void setValues(){
        Picasso.get().load(String.valueOf(NetworkUtils.generateImageUrl(movie))).into(mThumbnailIV);
        mOriginalTitle.setText(movie.getOriginalTitle());
        mPlotSynopsis.setText(movie.getPlotSynopsis());
        mUserRating.setText(""+movie.getUserRating());
        mReleaseDate.setText(movie.getReleaseDate());
    }



}
