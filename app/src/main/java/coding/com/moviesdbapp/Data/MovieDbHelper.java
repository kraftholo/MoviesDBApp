package coding.com.moviesdbapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favourites.db";

    private static final int DATABASE_VERSION = 1;


    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + MovieContract.FavEntry.TABLE_NAME + " (" +

                        /*
                         * FavEntry did not explicitly declare a column called "_ID". However,
                         * WeatherEntry implements the interface, "BaseColumns", which does have a field
                         * named "_ID". We use that here to designate our table's primary key.
                         */
                        MovieContract.FavEntry._ID                       + " INTEGER PRIMARY KEY AUTOINCREMENT, "   +

                        MovieContract.FavEntry.COLUMN_MOVIE_ID           + " INTEGER NOT NULL, "                    +

                        MovieContract.FavEntry.COLUMN_POPULARITY         + " REAL NOT NULL, "                       +

                        MovieContract.FavEntry.COLUMN_POSTER_PATH        + " TINYTEXT NOT NULL,"                    +

                        MovieContract.FavEntry.COLUMN_RELEASE_DATE       + " TINYTEXT NOT NULL, "                   +
                        MovieContract.FavEntry.COLUMN_TITLE              + " TINYTEXT NOT NULL, "                   +

                        MovieContract.FavEntry.COLUMN_VOTE_AVERAGE       + " REAL NOT NULL, "                       +
                        MovieContract.FavEntry.COLUMN_VOTE_COUNT         + " INTEGER NOT NULL, "                    +

                        MovieContract.FavEntry.COLUMN_OVERVIEW      + " TEXT NOT NULL, "                       +



                        " UNIQUE (" + MovieContract.FavEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        /*
         * After we've spelled out our SQLite table creation statement above, we actually execute
         * that SQL with the execSQL method of our SQLite database object.
         */
        db.execSQL(SQL_CREATE_WEATHER_TABLE);





    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
