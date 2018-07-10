package codefathers.tripalert.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codefathers.tripalert.R;
import codefathers.tripalert.adapters.FollowedTrackingsRecyclerAdapter;
import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.models.LogMessages;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.viewModels.HomeScreenViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FollowedTrackings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FollowedTrackings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowedTrackings extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private HomeScreenViewModel viewModel;
    private ConstraintLayout notFollowingLayout;
    private LinearLayout followingLayout;

    private OnFragmentInteractionListener mListener;

    public FollowedTrackings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowedTrackings.
     */
    public static FollowedTrackings newInstance(String param1, String param2) {
        FollowedTrackings fragment = new FollowedTrackings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_followed_trackings, container, false);
        this.followingLayout = (LinearLayout) v.findViewById(R.id.following);
        this.notFollowingLayout = (ConstraintLayout) v.findViewById(R.id.notFollowing);
        RecyclerView recyclerView = v.findViewById(R.id.trackingsRecyclerView);
        //set up recycler Adapter
        final FollowedTrackingsRecyclerAdapter adapter = new FollowedTrackingsRecyclerAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel =  ViewModelProviders.of(getActivity()).get(HomeScreenViewModel.class);
        viewModel.getFollowedTrackings().observe(this, new Observer<List<Tracking>>() {
            @Override
            public void onChanged(@Nullable List<Tracking> trackings) {

                adapter.setTrackings(trackings);
                adapter.setListener(new FollowedTrackingsRecyclerAdapter.FollowedTrackingsListener() {
                    @Override
                    public void unfollowOnClick(View v, int position) {
                        viewModel.unfollowTracking(trackings.get(position));
                        LogItem log = new LogItem(TrackingStatus.ABORTED, LogMessages.onUnfollow());
                        log.setCreator(viewModel.getUser().getPhoneNumber());
                        Map <String, Boolean> map  = new HashMap();
                        map.put(trackings.get(position).getCreator(),true);
                        log.setRecievers(map);
                        viewModel.addSituationLog(log);
                    }

                });
                if(trackings != null ){
                    followingLayout.setVisibility(View.VISIBLE);
                    notFollowingLayout.setVisibility(View.INVISIBLE);
                }

            }
        });

        return v;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
