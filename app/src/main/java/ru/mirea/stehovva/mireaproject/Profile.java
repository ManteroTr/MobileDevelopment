package ru.mirea.stehovva.mireaproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText firstName;
    EditText lastName;
    EditText middleName;
    EditText group;
    EditText phone;
    EditText email;
    Button save;
    String mainKeyAlias;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        firstName = v.findViewById(R.id.editTextText5);
        lastName = v.findViewById(R.id.editTextText6);
        middleName = v.findViewById(R.id.editTextText7);
        group = v.findViewById(R.id.editTextText8);
        phone = v.findViewById(R.id.editTextPhone2);
        email = v.findViewById(R.id.editTextTextEmailAddress);
        save = v.findViewById(R.id.button);

        sharedPref = getActivity().getSharedPreferences("mirea_settings", getActivity().MODE_PRIVATE);
        editor = sharedPref.edit();

        firstName.setText(sharedPref.getString("firstName", ""));
        lastName.setText(sharedPref.getString("lastName", ""));
        middleName.setText(sharedPref.getString("middleName", ""));
        group.setText(sharedPref.getString("group", ""));
        phone.setText(sharedPref.getString("phone", ""));
        email.setText(sharedPref.getString("email", ""));



        View.OnClickListener OnClickSave = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editor.putString("firstName", firstName.getText().toString());
                editor.putString("lastName", lastName.getText().toString());
                editor.putString("middleName", middleName.getText().toString());
                editor.putString("group", group.getText().toString());
                editor.putString("phone", phone.getText().toString());
                editor.putString("email", email.getText().toString());
                editor.apply();
            }
        };
        save.setOnClickListener(OnClickSave);


        return v;
    }


}