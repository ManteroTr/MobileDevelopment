package ru.mirea.stehovva.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Browser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Browser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WebView webView;
    private Button search;
    private EditText searchBar;

    public Browser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Browser.
     */
    // TODO: Rename and change types and number of parameters
    public static Browser newInstance(String param1, String param2) {
        Browser fragment = new Browser();
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
        View v = inflater.inflate(R.layout.fragment_browser, container, false);
        search = v.findViewById(R.id.button2);
        searchBar = v.findViewById(R.id.editTextText);
        webView = (WebView)v.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.mirea.ru/");
        View.OnClickListener OnClickSearch = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = searchBar.getText().toString();
                webView.loadUrl(url);
            }
        };
        search.setOnClickListener(OnClickSearch);
        return v;
    }

}