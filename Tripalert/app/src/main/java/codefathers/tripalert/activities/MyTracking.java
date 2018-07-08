package codefathers.tripalert.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import codefathers.tripalert.R;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.viewModels.HomeScreenViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyTracking.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyTracking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTracking extends Fragment {

    private ConstraintLayout notCreatedLayout;
    private LinearLayout createdLayout;
    private HomeScreenViewModel viewModel;
    private OnFragmentInteractionListener mListener;
    static final int TIME_LIMIT = 1;


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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
