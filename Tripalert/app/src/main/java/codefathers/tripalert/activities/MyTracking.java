package codefathers.tripalert.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codefathers.tripalert.R;
import codefathers.tripalert.adapters.ContactsAdapter;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.models.LogMessages;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.viewModels.HomeScreenViewModel;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.SHORTCUT_SERVICE;
import static java.lang.Integer.valueOf;


/**
 * A simple {@link Fragment} subcolass.
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
    private android.location.Location myLocation;
    double myDestinationLatitude;
    double MyDestinationLongitude;
    private int timeStationary;
    private double totalTimePassed;
    private CountDownTimer delayTimer, estimatedTimer;
    private boolean hasDelayed = false;
    private int count;
    private Tracking cachedTracking;

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
        notCreatedLayout.setVisibility(View.INVISIBLE);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeScreenViewModel.class);
        if(viewModel.getCreatedTracking().getValue() == null)notCreatedLayout.setVisibility(View.VISIBLE);
        viewModel.getCreatedTracking().observe(this, new Observer<Tracking>() {
            @Override
            public void onChanged(@Nullable Tracking tracking) {
                if (tracking != null) {
                    cachedTracking = tracking;
                    TextView txt = (TextView) getView().findViewById(R.id.currStart);
                    TextView txt2 = (TextView) getView().findViewById(R.id.currDestination);
                    TextView txt4 = (TextView) getView().findViewById(R.id.currEstimated);
                    Button abortBtm = (Button) getView().findViewById(R.id.abortButton);
                    abortBtm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            abortClick();
                        }
                    });
                    Button reistBtn = (Button) getView().findViewById(R.id.reestimatebtn);
                    reistBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reestimateClick();
                        }
                    });
                    createAppUserView(tracking.getFollowersToList(),viewModel.contacts);
                    txt.setText(tracking.getStartingPoint().getAddress());
                    txt2.setText(tracking.getDestination().getAddress());
                    txt4.setText(String.valueOf(tracking.getEstimatedTime()));
                    createdLayout.setVisibility(View.VISIBLE);
                    notCreatedLayout.setVisibility(View.INVISIBLE);

                    if (count == 0 && tracking.getStatus() != TrackingStatus.EMERGENCY) {
                        startGps(tracking);
                        globalTimerStart(tracking.getEstimatedTime());
                    }

                    if (tracking.getStatus() == TrackingStatus.DELAYED) {
                        getView().findViewById(R.id.delayMessage).setVisibility(View.VISIBLE);
                        TextView text = (TextView) getView().findViewById(R.id.delayTxt);
                        text.setText(getString(R.string.delayMessage));
                    } else if (tracking.getStatus() == TrackingStatus.NOT_RESPONDING) {
                        getView().findViewById(R.id.delayMessage).setVisibility(View.VISIBLE);
                        TextView text = (TextView) getView().findViewById(R.id.delayTxt);
                        text.setText(getString(R.string.notRespondingMessage));
                    } else if (tracking.getStatus() == TrackingStatus.EMERGENCY) {
                        CardView card = (CardView)getView().findViewById(R.id.delayMessage);
                        card.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            card.setCardBackgroundColor(getActivity().getColor(R.color.colorEmergency));
                        }
                        TextView text = (TextView) getView().findViewById(R.id.delayTxt);
                        text.setText(getString(R.string.emergencyMessage));
                        onTrackingTerminate();
                    } else {
                        getView().findViewById(R.id.delayMessage).setVisibility(View.GONE);

                    }
                    count++;

                } else {
                    count = 0;
                    notCreatedLayout.setVisibility(View.VISIBLE);
                    createdLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void globalTimerStart(int time) {
        totalTimePassed = 0.0;

        try {
            estimatedTimer.cancel();
        } catch (Exception e) {
            estimatedTimer = new CountDownTimer((long) (60000 * time + viewModel.DELAY_LIMIT), 1000) {
                @Override
                public void onTick(long l) {
                    totalTimePassed++;
                }

                @Override
                public void onFinish() {
                    Toast.makeText(getActivity(), "YOU ARE LATE:" + totalTimePassed, Toast.LENGTH_LONG).show();
                    hasDelayed = true;
                    onDelay();
                }
            }.start();
        }
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

    public void startGps(Tracking tracking) {

        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 2, this);

        myDestinationLatitude = Double.parseDouble(tracking.getDestination().getLat());
        MyDestinationLongitude = Double.parseDouble(tracking.getDestination().getLang());

    }

    public void onFinish() {
        viewModel.changeCreatedTrackingtatus(TrackingStatus.FINISHED);
        viewModel.addSituationLog(new LogItem(TrackingStatus.FINISHED, LogMessages.onArrived(cachedTracking.getDestination().getAddress())));
    }

    public void onResumeMoving() {
        if (TrackingStatus.STARTED != cachedTracking.getStatus()) {
            viewModel.changeCreatedTrackingtatus(TrackingStatus.STARTED);
            viewModel.addSituationLog(new LogItem(TrackingStatus.STARTED, LogMessages.onResume()));
        }
    }

    public void onDelay() {
        if (TrackingStatus.DELAYED != cachedTracking.getStatus()) {
            viewModel.changeCreatedTrackingtatus(TrackingStatus.DELAYED);
            viewModel.addSituationLog(new LogItem(TrackingStatus.DELAYED, LogMessages.delayed()));
        }
    }

    public void onAbort(String reason) {
        viewModel.changeCreatedTrackingtatus(TrackingStatus.ABORTED);
        viewModel.addSituationLog(new LogItem(TrackingStatus.ABORTED, LogMessages.onAbort(reason)));
        onTrackingTerminate();
    }

    public void onReestimate(int time) {
        viewModel.changeCreatedTrackingtatus(TrackingStatus.STARTED);
        viewModel.addSituationLog(new LogItem(TrackingStatus.STARTED, LogMessages.onEstimateAgain(time)));
        viewModel.changeCreatedTrackingEstimatedTime(time);
        //reload.
        startActivity(new Intent(getActivity(), HomeScreen.class));
    }

    public void abortClick() {
        EditText reason = new EditText(getActivity());
        FrameLayout layout = new FrameLayout(getActivity());
        layout.addView(reason, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setView(layout);
        b.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
            // do something with picker.getValue()
            onAbort(reason.getText().toString());
        }).setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = b.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setView(layout);
        dialog.show();
    }

    public void reestimateClick() {
        NumberPicker newTime = new NumberPicker(getActivity());
        newTime.setMinValue(1);
        newTime.setMaxValue(50);
        FrameLayout layout = new FrameLayout(getActivity());
        layout.addView(newTime, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setView(layout);
        b.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
            // do something with picker.getValue()
            onReestimate(newTime.getValue());
        }).setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = b.create();
        dialog.setView(layout);
        dialog.show();
    }

    public void onNotResponding() {
        if (TrackingStatus.NOT_RESPONDING != cachedTracking.getStatus()) {
            viewModel.changeCreatedTrackingtatus(TrackingStatus.NOT_RESPONDING);
            viewModel.addSituationLog(new LogItem(TrackingStatus.NOT_RESPONDING, LogMessages.getOnNotResponding()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

        try {
            if (getDistance(myLocation.getLatitude(), location.getLatitude(), myLocation.getLongitude(), location.getLongitude(), 0, 0) > viewModel.TIME_LOCATTION_LIMIT)
                delayTimer.cancel();

            else if (getDistance(myLocation.getLatitude(), location.getLatitude(), myLocation.getLongitude(), location.getLongitude(), 0, 0) != 0)
                return;
        } catch (Exception e) {

        }

        viewModel.setCurrentLocation(Double.toString(location.getLongitude()),Double.toString(location.getLatitude()));
        myLocation = location;
        if (cachedTracking.getStatus()!= TrackingStatus.DELAYED) onResumeMoving();
        timeStationary = 0;
        delayTimer = new CountDownTimer(60000 * viewModel.TIME_LIMIT, 1000) {
            @Override
            public void onTick(long l) {
                timeStationary++;
            }

            @Override
            public void onFinish() {
                onNotResponding();
            }
        }.start();


        double distance = getDistance(myLocation.getLatitude(), myDestinationLatitude, myLocation.getLongitude(), MyDestinationLongitude, 0, 0);
        if (distance <= viewModel.LOCATION_LIMIT) {
            onTrackingTerminate();
            onFinish();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    public void onTrackingTerminate() {
        if(estimatedTimer != null){
            estimatedTimer.cancel();
        }
        if(delayTimer !=null) {
            delayTimer.cancel();
        }
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return; //koitaei na dei an exei adeies
        lm.removeUpdates(this); //stamatei na pairnei tin thesi tou kinitou
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

    private void createAppUserView(List<AppUser> contacts, Map<String,String> contactsDict) {
        ListView listView = (ListView) getView().findViewById(R.id.followersListView);
        final ContactsAdapter adapter = new ContactsAdapter(getActivity(), contacts, true,contactsDict);
        listView.setAdapter(adapter);
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
