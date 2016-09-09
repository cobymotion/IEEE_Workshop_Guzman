package client.ieee.adhoc.webserviceclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by coby on 9/9/16.
 */
public class FragmentInicial extends Fragment {

    public FragmentInicial() {
        // Required empty public constructor
    }

    public static FragmentInicial newInstance() {
        FragmentInicial fragment = new FragmentInicial();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_main, container, false);
    }

}
