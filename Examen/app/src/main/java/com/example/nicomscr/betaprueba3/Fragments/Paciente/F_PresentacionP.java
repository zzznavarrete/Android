package com.example.nicomscr.betaprueba3.Fragments.Paciente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicomscr.betaprueba3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_PresentacionP extends Fragment {


    public F_PresentacionP() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__presentacion, container, false);
    }

}
