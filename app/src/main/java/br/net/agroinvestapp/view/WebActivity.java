package br.net.agroinvestapp.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.preferences.PreferenciaClass;
import br.net.agroinvestapp.model.Preferencias;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.HashMap;

public class WebActivity extends AppCompatActivity {


    @BindView(R.id.webViewId) WebView webView;
    private PreferenciaClass pŕefenciasClasse;
    private String site;
    private Toolbar toolbar;
    private String epagri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        epagri = getIntent().getStringExtra("epagri");



        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbar.setBackgroundColor(Color.parseColor("#3fb566"));
         toolbar.setTitle("AgroInvest");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.globoRural:
                webView.loadUrl("http://g1.globo.com/economia/agronegocios/globo-rural/");
                return true;
            case R.id.globoAgro:
                webView.loadUrl("http://g1.globo.com/economia/agronegocios/");
                return true;
            case  R.id.epagri:
                webView.loadUrl("http://www.epagri.sc.gov.br/");
                return true;


            default:
            return super.onOptionsItemSelected(item);
        }


    }

    private Preferencias getSite(){
        Preferencias preferencias;
        pŕefenciasClasse = new PreferenciaClass(this);
        preferencias = pŕefenciasClasse.getPreferencias();
        return preferencias;
    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        String site="";
        if(epagri!=null){
            site = "http://www.epagri.sc.gov.br/";
        }else{
            if(getSite()!=null)
            site = getSite().getSitePreferido();
        }
        if(site !=null && site.length() > 2){
            this.site = site;
            webView.loadUrl(site);
        }else{
            webView.loadUrl("http://g1.globo.com/economia/agronegocios/globo-rural/");
            this.site = "http://g1.globo.com/economia/agronegocios/globo-rural/";
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        pŕefenciasClasse = new PreferenciaClass(this);
        Preferencias preferencias = new Preferencias();
        preferencias.setSitePreferido(webView.getUrl());
        pŕefenciasClasse.salvarPreferencias(preferencias);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        pŕefenciasClasse = new PreferenciaClass(this);
        Preferencias preferencias = new Preferencias();
        preferencias.setSitePreferido(webView.getUrl());
        pŕefenciasClasse.salvarPreferencias(preferencias);
        super.onDestroy();
    }
}
