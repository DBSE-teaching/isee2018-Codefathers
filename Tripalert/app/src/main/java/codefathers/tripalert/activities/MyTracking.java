package codefathers.tripalert.activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import codefathers.tripalert.R;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.viewModels.HomeScreenViewModel;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.SHORTCUT_SERVICE;
import static java.lang.Integer.valueOf;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyTracking.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyTracking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTracking extends Fragment implements LocationListener {

    private ConstraintLayout notCreatedLayout;
    private LinearLayout createdLayout;
    private HomeScreenViewModel viewModel;
    private OnFragmentInteractionListener mListener;
    static final int TIME_LIMIT = 1;
    static final int LOCATION_LIMIT = 25;
    private android.location.Location myLocation;
    double myDestinationLatitude;
    double MyDestinationLongitude;
    private int timeStationary;
    private CountDownTimer myTimer;


    public MyTracking() {
        // Required empty public constructor
    }

    public static MyTracking newInstance() {
        MyTracking fragment = new MyTracking();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeScreenViewModel.class);
        //TODO: create a list that shows the followers.
        viewModel.getCreatedTracking().observe(this, new Observer<Tracking>() {
            @Override
            public void onChanged(@Nullable Tracking tracking) {

                if (tracking != null) {
                    TextView txt = (TextView) getView().findViewById(R.id.currStart);
                    TextView txt2 = (TextView) getView().findViewById(R.id.currDestination);
                    TextView txt3 = (TextView) getView().findViewById(R.id.currStartedAt);
                    TextView txt4 = (TextView) getView().findViewById(R.id.currEstimated);
                    txt.setText(tracking.getStartingPoint().getAddress());
                    txt2.setText(tracking.getDestination().getAddress());
                    txt4.setText(String.valueOf(tracking.getEstimatedTime()));
                    txt3.setText("TODO");
                    createdLayout.setVisibility(View.VISIBLE);
                    notCreatedLayout.setVisibility(View.INVISIBLE);
                    startGps(tracking);
                }else{
                    notCreatedLayout.setVisibility(View.VISIBLE);
                    createdLayout.setVisibility(View.INVISIBLE);


                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_tracking, container, false);
        this.createdLayout = (LinearLayout) v.findViewById(R.id.created);
        this.notCreatedLayout = (ConstraintLayout) v.findViewById(R.id.notCreated);
        Button settingsBtn = (Button) v.findViewById(R.id.newTrackingBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SpecifyDetails.class));
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void startGps(Tracking tracking){
        /*Location destination = tracking.getDestination();
        destination.getLang();
        destination.getLat();
        */

        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10,  this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);

        myDestinationLatitude = Double.parseDouble(tracking.getDestination().getLat());
        MyDestinationLongitude = Double.parseDouble(tracking.getDestination().getLang());
    }

    public void onFinish(){
        viewModel.changeCreatedTrackingtatus(TrackingStatus.FINISHED);
    }
    public void onDelay(){
        viewModel.changeCreatedTrackingtatus(TrackingStatus.DELAYED);
    }
    public void onAbort(){
        viewModel.changeCreatedTrackingtatus(TrackingStatus.ABORTED);
    }
    public void onNotResponding(){
        viewModel.changeCreatedTrackingtatus(TrackingStatus.NOT_RESPONDING);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

        try
        {
            if (getDistance(myLocation.getLatitude(),location.getLatitude() ,myLocation.getLongitude(),location.getLongitude(),0,0) > 20)
                myTimer.cancel();

            else if (getDistance(myLocation.getLatitude(),location.getLatitude() ,myLocation.getLongitude(),location.getLongitude(),0,0) != 0)
                return;
        }
        catch (Exception e) {}



        myLocation = location;
        timeStationary = 0;
        myTimer = new CountDownTimer(60000*TIME_LIMIT, 1000) {
            @Override
            public void onTick(long l) {
                timeStationary++;
            }

            @Override
            public void onFinish() {
                onDelay();
            }
        }.start();


        double distance = getDistance(myLocation.getLatitude(),myDestinationLatitude ,myLocation.getLongitude(),MyDestinationLongitude,0,0);
        if (distance <= LOCATION_LIMIT) onFinish();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private double getDistance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
        final int R = 6371; // aktina tis gis

        double latDistance = Math.toRadians(lat2 - lat1); //mathimatikes prakseis dianismetwn gia upologismo apostasis
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // metropi se metra

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
