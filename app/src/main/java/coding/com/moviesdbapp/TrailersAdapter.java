package coding.com.moviesdbapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder>{

    private Context mContext;
    private ArrayList<Trailer> trailersList;
    private TrailersAdapterOnClickHandler mClickHandler;

    public TrailersAdapter(Context mContext,TrailersAdapterOnClickHandler handler, ArrayList<Trailer> list) {
        this.mContext = mContext;
        this.trailersList = list;
        this.mClickHandler = handler;
    }

    public interface TrailersAdapterOnClickHandler{       //interface implemented in MainActivity
        void onClick(String str);
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_trailer_row,parent,false);

        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {

        holder.trailerName.setText(trailersList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView trailerName;

        public TrailersViewHolder(View itemView) {
            super(itemView);

          itemView.findViewById(R.id.trailer_nameTV);
          itemView.findViewById(R.id.play_button).setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Trailer clickedTrailer = trailersList.get(getAdapterPosition());
            mClickHandler.onClick(clickedTrailer.getKey());
        }
    }



}
