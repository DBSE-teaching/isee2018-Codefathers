package codefathers.tripalert.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import codefathers.tripalert.R;
import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.models.TrackingStatus;

public class LogItemRecyclerAdapter extends RecyclerView.Adapter<LogItemRecyclerAdapter.ViewHolder> {
    private List<LogItem> mlogItems;
    private LayoutInflater mInflater;

    public LogItemRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_logitem, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mlogItems != null) {
            LogItem current = mlogItems.get(position);
            holder.creator.setText(current.getCreator());
            holder.time.setText(current.getCreatedAt());
            holder.message.setText(current.getMessage());
            int color;
            switch (current.getStatus()) {
                case TrackingStatus.DELAYED:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorDelayPauseNotResponding);
                    break;
                case TrackingStatus.NOT_RESPONDING:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorDelayPauseNotResponding);
                    break;
                case TrackingStatus.EMERGENCY:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorEmergency);
                    break;
                case TrackingStatus.ABORTED:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorDestructiveAction);
                    break;
                case TrackingStatus.FINISHED:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorArrived);
                    break;
                default:
                    color = ContextCompat.getColor(holder.card.getContext(), R.color.colorEverythingFine);
            }
            holder.creator.setTextColor(color);
            holder.card.setBackgroundColor(color);
        }
        ;
    }

    @Override
    public int getItemCount() {
        if (mlogItems != null) {
            return mlogItems.size();
        } else {
            return 0;
        }

    }

    public void setlogItems(List<LogItem> logItems) {
        mlogItems = logItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView creator;
         TextView time;
         TextView message;
         CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            creator = (TextView) itemView.findViewById(R.id.msgCreator);
            time = (TextView) itemView.findViewById(R.id.createdAt);
            message = (TextView) itemView.findViewById(R.id.message);
            card = (CardView) itemView.findViewById(R.id.cardMessage);


        }
    }
}
