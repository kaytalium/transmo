package com.knymbus.transmo.Routes;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.knymbus.transmo.Helper.Helper;
import com.knymbus.transmo.Helper.SystemInterface;
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
        holder.busNumber.setText(model.getBusNumber());
        holder.origin.setText(model.getOrigin().code);
        holder.busStatus.setText(model.getStatus());
        holder.originDepartureTime.setText(Helper.DateFormatter(SystemInterface.DateTimeFormat.timeFormat,model.getOrigin().scheduleDepartureTime.toDate()));

        holder.destination.setText(model.getDestination().code);
        holder.destinationTime.setText(Helper.DateFormatter(SystemInterface.DateTimeFormat.timeFormat,model.getDestination().scheduleArrivalTime.toDate()));

//        Estimated time of departure
        TimeManager timeManager = new TimeManager();
        timeManager.timeLaspe(model.getOrigin().scheduleDepartureTime,holder.timeStatus, holder.timeStatusUnit);

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
        TextView timeStatusUnit;


        public BusHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            busNumber = mView.findViewById(R.id.bus_number);
            busNumber.setText("");

            origin = mView.findViewById(R.id.departure_from);
            origin.setText("");

            originDepartureTime = mView.findViewById(R.id.departure_from_time);
            originDepartureTime.setText("");

            destination = mView.findViewById(R.id.destination_to);
            destination.setText("");

            destinationTime = mView.findViewById(R.id.destination_to_time);
            destinationTime.setText("");

            timeStatus = mView.findViewById(R.id.eta);
            timeStatus.setText("");

            timeStatusUnit = mView.findViewById(R.id.eta_min);
            timeStatusUnit.setText("");

            busStatus = mView.findViewById(R.id.bus_status);
            busStatus.setText("");




        }
    }
}
