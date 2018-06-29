package coding.com.moviesdbapp.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {

    public static final int CODE_MOVIE = 100;
    private MovieDbHelper mDbHelper;

    public static final UriMatcher sUriMatcher=  buildUriMatcher();

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_MOVIE,CODE_MOVIE);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

       switch(sUriMatcher.match(uri)){

           case CODE_MOVIE:
               cursor = mDbHelper.getReadableDatabase().query(

                            MovieContract.FavEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder

               );

              break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
       }
       cursor.setNotificationUri(getContext().getContentResolver(),uri);
       return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long id;

        switch(sUriMatcher.match(uri)){

            case CODE_MOVIE:

                 id = mDbHelper.getWritableDatabase().insert(
                        MovieContract.FavEntry.TABLE_NAME,
                        null,
                        values
                );

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(id>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numRowsDeleted;

        if(selection==null) selection="1";

        switch(sUriMatcher.match(uri)){

            case CODE_MOVIE:
                numRowsDeleted = mDbHelper.getWritableDatabase().delete(
                        MovieContract.FavEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
