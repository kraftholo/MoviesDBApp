package coding.com.moviesdbapp.Utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import coding.com.moviesdbapp.Movie;
import coding.com.moviesdbapp.Trailer;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String JSON_ARRAY_RESULTS = "results";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String PLOT_SYNOPSIS_KEY = "overview";
    private static final String USER_RATING_KEY = "vote_average";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String ID_KEY = "id";
    private static final String POPULARITY_KEY = "popularity";
    private static final String VOTE_COUNT_KEY = "vote_count";





    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_KEY = "key";
    private static final String TRAILER_ID = "id";

    public static ArrayList<Movie> generateMovieList(String jsonStr) {

        ArrayList<Movie> retMovieslist = new ArrayList<Movie>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonMovieArray = jsonObject.getJSONArray(JSON_ARRAY_RESULTS);


            for (int i = 0; i < jsonMovieArray.length(); i++) {
                Movie movie = new Movie();
                JSONObject movieObj = (JSONObject) jsonMovieArray.get(i);

                Log.d(TAG, "loading movie into arrayList" + movieObj.getString(ORIGINAL_TITLE_KEY));

                movie.setOriginalTitle(movieObj.getString(ORIGINAL_TITLE_KEY));
                movie.setPlotSynopsis(movieObj.getString(PLOT_SYNOPSIS_KEY));
                movie.setPosterPath(movieObj.getString(POSTER_PATH_KEY));
                movie.setReleaseDate(movieObj.getString(RELEASE_DATE_KEY));
                movie.setUserRating((float) movieObj.getDouble(USER_RATING_KEY));
                movie.setPopularity(movieObj.getDouble(POPULARITY_KEY));
                movie.setMovieID(movieObj.getInt(ID_KEY));
                movie.setVoteCount(movieObj.getInt(VOTE_COUNT_KEY));

                retMovieslist.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retMovieslist;
    }

    public static ArrayList<Trailer> generateTrailersList(String jsonStr) {

        ArrayList<Trailer> retTrailerslist = new ArrayList<Trailer>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonTrailerArray = jsonObject.getJSONArray(JSON_ARRAY_RESULTS);


            for (int i = 0; i < jsonTrailerArray.length(); i++) {
                Trailer trailer = new Trailer();
                JSONObject trailerObj = (JSONObject) jsonTrailerArray.get(i);

                Log.d(TAG, "loading trailer into arrayList" + trailerObj.getString(TRAILER_NAME));

                trailer.setId(trailerObj.getString(TRAILER_ID));
                trailer.setKey(trailerObj.getString(TRAILER_KEY));
                trailer.setName(trailerObj.getString(TRAILER_NAME));


                retTrailerslist.add(trailer);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retTrailerslist;
    }
}
