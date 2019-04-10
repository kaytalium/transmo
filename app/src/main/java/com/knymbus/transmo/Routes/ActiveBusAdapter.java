package com.knymbus.transmo.Routes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.knymbus.transmo.R;


public class ActiveBusAdapter extends FirestoreRecyclerAdapter<ActiveBus, ActiveBusAdapter.BusHolder> {

    //private vars
    private Context $context;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ActiveBusAdapter(@NonNull FirestoreRecyclerOptions<ActiveBus> options, Context mContext) {
        super(options);
        $context = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull BusHolder holder, int position, @NonNull ActiveBus model) {
        holder.busNumber.setText(model.getBusNunber());
    }

    @NonNull
    @Override
    public BusHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_result_view, viewGroup,false);
        return new BusHolder(v);
    }


    /**
     * Inner Holder class to pass data to the view
     */
    public class BusHolder extends RecyclerView.ViewHolder {

        private View mView;
        TextView busNumber;
        TextView origin;
        TextView originDepartureTime;
        TextView destination;
        TextView destinationTime;
        TextView busStatus;
        TextView timeStatus;


        public BusHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            busNumber = mView.findViewById(R.id.bus_number);

        }
    }
}
