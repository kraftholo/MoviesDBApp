package coding.com.moviesdbapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import coding.com.moviesdbapp.Utilities.NetworkUtils;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {



    private static final String TAG = MoviesAdapter.class.getSimpleName();

    private ArrayList<Movie> listOfMovies;
    private final Context mContext;
    private final MoviesAdapterOnClickHandler mClickHandler;

    public MoviesAdapter(Context context , MoviesAdapterOnClickHandler handler ,ArrayList<Movie> list) {
        this.listOfMovies = list;
        mContext = context;
        mClickHandler = handler;                        //used to call the onClick in the MainActivity
    }

    public interface MoviesAdapterOnClickHandler{       //interface implemented in MainActivity
        void onClick(Movie movie);
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_block_item,viewGroup,false);

        return new MoviesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        Movie currMovie = listOfMovies.get(i);
        URL imageUrl = NetworkUtils.generateImageUrl(currMovie);

        Picasso.get().load(String.valueOf(imageUrl)).into((ImageView) moviesViewHolder.itemView.findViewById(R.id.posterImageView));

    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }

    //ViewHolder Class

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie clickedMovie = listOfMovies.get(adapterPosition);
            mClickHandler.onClick(clickedMovie);

        }
    }
}




