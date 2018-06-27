package coding.com.moviesdbapp.Utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import coding.com.moviesdbapp.Movie;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String JSON_ARRAY_RESULTS = "results";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String PLOT_SYNOPSIS_KEY = "overview";
    private static final String USER_RATING_KEY = "vote_average";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String RELEASE_DATE_KEY = "release_date";


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

                retMovieslist.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retMovieslist;
    }
}
