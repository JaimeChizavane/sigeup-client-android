package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import mz.ac.sigeup.sigeup_navigationview.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SituacaoAcademicaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SituacaoAcademicaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SituacaoAcademicaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public String RadioItemSelected = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


   /* Spinner spinner = (Spinner) findViewById(R.id.year_spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.ano_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.);
    // Apply the adapter to the spinner
          spinner.setAdapter(adapter);*/

   Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    private OnFragmentInteractionListener mListener;

    public SituacaoAcademicaFragment() {
        // Required empty public constructor
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.AvaliacaoRadioButton1:
                if (checked)
                    // Here will be Avaliacao route

                    RadioItemSelected = "teste";
                    break;
            case R.id.ExameRadioButton1:
                if (checked)
                    // Here will be Exame route
                    RadioItemSelected = "exame";
                    break;
        }
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SituacaoAcademicaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SituacaoAcademicaFragment newInstance(String param1, String param2) {
        SituacaoAcademicaFragment fragment = new SituacaoAcademicaFragment();
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

        spinner = (Spinner)getView().findViewById(R.id.year_spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.ano_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

  /*      spinner.getOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                          {
                                              @Override
                                              public void


                                          }

        );*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_situacao_academica, container, false);

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
