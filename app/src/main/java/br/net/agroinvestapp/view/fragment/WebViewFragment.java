package br.net.agroinvestapp.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import br.net.agroinvestapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    private WebView webView;

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_web_view, container, false);

    webView = (WebView) view.findViewById(R.id.idWebView);
    webView.loadUrl("http://g1.globo.com/economia/agronegocios/");

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        webView.stopLoading();
        webView.clearCache(true);
        webView.clearHistory();
        webView.destroy();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.stopLoading();
        webView.clearCache(true);
        webView.clearHistory();
        webView.destroy();
    }


}
