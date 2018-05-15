package mz.ac.sigeup.sigeup_navigationview;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mz.ac.sigeup.sigeup_navigationview.app.GradeListAdapter;
import mz.ac.sigeup.sigeup_navigationview.app.MyApplication;
import mz.ac.sigeup.sigeup_navigationview.app.Notas;
import mz.ac.sigeup.sigeup_navigationview.helper.Grade;
import mz.ac.sigeup.sigeup_navigationview.helper.SwipeListAdapter;

//import static mz.ac.sigeup.sigeup_navigationview.R.id.listViewPortal;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PortalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PortalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PortalFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //private String TAG = MainActivity.class.getSimpleName();

    private static final String TAG = PortalFragment.class.getSimpleName();
    private static final String url = "http://sigeup-api.ga/api/pauta/";
    private String username = "X01763";


    private String URL_CADEIRAS = "http://sigeup-api.ga/api/pauta/";

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private SwipeListAdapter adapter;
    private List<Grade> gradeList;
    private String [] Subjects;

    private int offSet = 0;

    //private String studentID = "X01763";



    private OnFragmentInteractionListener mListener;

    public PortalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PortalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PortalFragment newInstance(String param1, String param2) {
        PortalFragment fragment = new PortalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static PortalFragment getInstance(){

        PortalFragment portalFragment = new PortalFragment();
        return portalFragment;
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

        //listView = (ListView) getView().findViewById(R.id.portalListView);
        /*listView = (ListView) getView().findViewById(R.id.portalListView);*/

       /* //swipeRefreshLayout =  (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout =   getView().findViewById(R.id.swipe_refresh_layout);

        gradeList = new ArrayList<>();

        //adapter = new SwipeListAdapter(this, gradeList);
        adapter = new SwipeListAdapter(this, gradeList);
        listView.setAdapter(adapter);

        //swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         *//*
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetchGrades();

                                    }
                                }
        );*/

         View view = inflater.inflate(R.layout.fragment_portal, container, false);

        String [] portalMenuItems = {"Tecnicas de expressao em LP", "Antropologia Cultural", "Fisica 1"};
        String [] portalMenuItems2 = {"12", "13", "14"};

        Grade grade1 = new Grade("Tecnicas de expressao em LP", "12");
        Grade grade2 = new Grade("Antropologia Cultural", "13");
        Grade grade3 = new Grade("Fisica 1", "14");
        Grade grade4 = new Grade("Analise Matematica 1", "14");
        Grade grade5 = new Grade("ALGA 1", "18");
        Grade grade6 = new Grade("Ingles Tecnico 1", "14");
        Grade grade7 = new Grade("Tema Transversal", "10");

        ArrayList<Grade> gradeList = new ArrayList<>();

        gradeList.add(grade1);
        gradeList.add(grade2);
        gradeList.add(grade3);
        gradeList.add(grade4);
        gradeList.add(grade5);
        gradeList.add(grade6);
        gradeList.add(grade7);

        //GradeListAdapter gradeListAdapter = new GradeListAdapter(this, getActivity().findViewById(R.layout.grade_view_layout), gradeList);
        //GradeListAdapter gradeListAdapter = new GradeListAdapter(this, R.layout.grade_view_layout, gradeList);


        GradeListAdapter gradeListAdapter = new GradeListAdapter(getActivity(), R.layout.grade_view_layout, gradeList);


        ListView listView = (ListView) view.findViewById(R.id.portalListView);

       /* ArrayAdapter <String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                portalMenuItems
        );*/


        listView.setAdapter(gradeListAdapter);


        return view;

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

/*    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.AvaliacaoRadioButton1:
                if (checked)
                    // Here will be Avaliacao route
                    break;
            case R.id.ExameRadioButton1:
                if (checked)
                    // Here will be Exame route
                    break;
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {

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

    private void fetchGrades() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        String url_sigeapi = URL_CADEIRAS + username;


        // Volley's json array request object
        StringRequest req = new StringRequest(Request.Method.GET, url_sigeapi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {

                            // looping through json and adding to grades list
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject gradeObj = new JSONObject(response);

                                    //JSONObject gradeObj = response.getJSONObject(i);

                                    JSONArray castArray = gradeObj.getJSONArray("pauta");

                                    gradeObj = castArray.getJSONObject(i);

                                    String teste1 = gradeObj.getString("teste1");
                                    String teste2 = gradeObj.getString("teste2");

                                    String cadeira1 = "Programacao 1";
                                    String cadeira2 = "Matematica Discreta 2";
                                    String cadeira3 = "Ingles Tecnico 2";


                                    //Grade grade = new Grade(teste1, teste2);
                                    Grade grade = new Grade(teste1, cadeira1);
                                    Grade grade2 = new Grade(teste2, cadeira2);
                                    Grade grade3 = new Grade(teste2, cadeira3);

                                    //gradeList.add(0, grade);
                                    gradeList.add(grade);
                                    gradeList.add(grade2);
                                    gradeList.add(grade3);

                                } catch (JSONException e) {

                                    Log.e(TAG, "JSON Parsing error is: " + e.getMessage());
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);


    }


}
