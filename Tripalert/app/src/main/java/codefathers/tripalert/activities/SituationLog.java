package codefathers.tripalert.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import codefathers.tripalert.R;
import codefathers.tripalert.adapters.LogItemRecyclerAdapter;
import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.viewModels.HomeScreenViewModel;


public class SituationLog extends Fragment{

    private HomeScreenViewModel viewModel;
    public SituationLog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_situation_log, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.logItemRecyclerView);
        final LogItemRecyclerAdapter adapter = new LogItemRecyclerAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = ViewModelProviders.of(getActivity()).get(HomeScreenViewModel.class);
        viewModel.getLogItems().observe(this, new Observer<List<LogItem>>() {
            @Override
            public void onChanged(@Nullable List<LogItem> logItems) {
                adapter.setlogItems(logItems);
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
