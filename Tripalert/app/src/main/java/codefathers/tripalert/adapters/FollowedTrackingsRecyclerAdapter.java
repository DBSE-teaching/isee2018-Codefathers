package codefathers.tripalert.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import codefathers.tripalert.models.Tracking;

import codefathers.tripalert.R;

public class FollowedTrackingsRecyclerAdapter extends RecyclerView.Adapter<FollowedTrackingsRecyclerAdapter.ViewHolder> {
    private static final String TAG = "FollowedTrackingsRecycl";
    private LayoutInflater mInflater;           //i have no idea what this is
    private List<Tracking> mTrackings;      //cached copies of trackings
    //the view holder holds the seperate views.
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView phoneNumber;
        TextView startingPoint;
        TextView destination;
        CardView card;
        Button unfollow;
        public ViewHolder(View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.recPhoneNumber);
            startingPoint = itemView.findViewById(R.id.recStartingPoint);
            destination = itemView.findViewById(R.id.recDestination);
            card = itemView.findViewById(R.id.recCard);
            unfollow = itemView.findViewById(R.id.unfollowButton);


        }
    }

    public FollowedTrackingsRecyclerAdapter(Context context){mInflater = LayoutInflater.from(context); }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_tracking,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mTrackings != null ){
            Tracking current = mTrackings.get(position);
            /* holder.phoneNumber.setText(current.getCreator().getPhoneNumber());
            holder.destination.setText(current.getDestination().getAddress());
            holder.startingPoint.setText(current.getStartingPoint().getAddress());
*/
            holder.phoneNumber.setText(current.getCreator());
            holder.destination.setText(current.getDestination().getAddress());
            holder.startingPoint.setText(current.getStartingPoint().getAddress());
            int color;
            switch (current.getStatus()){
                case 2: color = ContextCompat.getColor(holder.card.getContext(),R.color.colorDelayPauseNotResponding);break;
                case 3: color = ContextCompat.getColor(holder.card.getContext(),R.color.colorEmergency);break;
                default: color = ContextCompat.getColor(holder.card.getContext(),R.color.colorEverythingFine);
            }
            holder.phoneNumber.setTextColor(color);
            holder.card.setCardBackgroundColor(color);
            holder.unfollow.setTextColor(ContextCompat.getColor(holder.card.getContext(),R.color.colorTextOnBlack));
        }

    }

    public void setTrackings(List<Tracking> trackings){
        mTrackings = trackings;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mTrackings != null)
            return mTrackings.size();
        else return 0;
    }




}

