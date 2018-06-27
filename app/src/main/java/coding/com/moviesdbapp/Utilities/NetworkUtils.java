package coding.com.moviesdbapp.Utilities;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import coding.com.moviesdbapp.Movie;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String MY_API_KEY = "?api_key=d7105a71684c22c5a4d93ae053f64cbb";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie"; //<<api_key>>
    private static final String LANGUAGE = "&language=en-US";
    private static final String BY_POPULAR= "/popular";
    private static final String BY_TOP_RATED= "/top_rated";


    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL generateUrl(Boolean byMostPopular){

        String str;

        if(byMostPopular){
            str = BASE_URL+BY_POPULAR+MY_API_KEY+ LANGUAGE;
        }else{
            str = BASE_URL+BY_TOP_RATED+MY_API_KEY+LANGUAGE;
        }

        try {
            URL retUrl = new URL(str);
            Log.v(TAG,"the url is "+retUrl.toString());
            return retUrl;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL generateImageUrl(Movie currMovie) {

        String str = IMG_BASE_URL+currMovie.getPosterPath();
        URL url;

        try {
            url =new URL(str);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
