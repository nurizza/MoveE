package id.sch.smktelkom_mlg.privateassignment.xirpl428.movee;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl428.movee.adapter.SourceAdapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl428.movee.model.Source;
import id.sch.smktelkom_mlg.privateassignment.xirpl428.movee.model.SourcesResponse;
import id.sch.smktelkom_mlg.privateassignment.xirpl428.movee.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl428.movee.service.VolleySingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {
    ArrayList<Source> mList = new ArrayList<>();
    SourceAdapter mAdapter;


    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new SourceAdapter(this.getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        downloadDataSource();
    }

    private void downloadDataSource() {
        //String url = "https://newsapi.org/v1/sources?language=en";
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=514d065d4c32940cb075d32601dfb329";

        GsonGetRequest<SourcesResponse> myRequest = new GsonGetRequest<SourcesResponse>
                (url, SourcesResponse.class, null, new Response.Listener<SourcesResponse>() {

                    @Override
                    public void onResponse(SourcesResponse response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        if (response.page.equals("1")) {
                            //fillColor(response.results);
                            mList.addAll(response.results);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(myRequest);

    }

}
