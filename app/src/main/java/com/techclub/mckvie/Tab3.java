package com.techclub.mckvie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tab3 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<object, Tab3.NewsViewHolder> mPeopleRVAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        final Context context = getActivity().getApplicationContext();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices/Events");
        mDatabase.keepSynced(true);
        mPeopleRV = (RecyclerView) view.findViewById(R.id.myRecycleView1);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Notices/Events");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<object>().setQuery(personsQuery, object.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<object, Tab3.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(Tab3.NewsViewHolder holder, final int position, final object model) {
                holder.setTitle(model.getTitle());
                holder.setTime(model.getTime());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = model.getUrl();
                        if(!url.equals("none")) {
                            Intent intent = new Intent(context, webview.class);
                            intent.putExtra("id", url);
                            startActivity(intent);
                        }
                    }
                });

                holder.setIsRecyclable(false);
            }

            @Override
            public Tab3.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_row, parent, false);

                return new Tab3.NewsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);

        return view;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        private String date1;

        public NewsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title.substring(0,1).toUpperCase() + title.substring(1));
        }

        public void setTime(String time){
            ImageView newLogo = (ImageView) mView.findViewById(R.id.new_logo);
            date1 = time;

            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date post_date = format.parse(date1);
                Calendar c = Calendar.getInstance();
                c.setTime(post_date);
                c.add(Calendar.DATE, 7);
                Date futureDate = c.getTime();
                Date currentDate = Calendar.getInstance().getTime();

                if (!currentDate.after(futureDate)) {
                    newLogo.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
