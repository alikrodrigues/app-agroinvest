package br.net.agroinvestapp.view;

import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.ConfiguracaoBanco;
import br.net.agroinvestapp.configure.adapter.TabAdapterMain;
import br.net.agroinvestapp.configure.helper.SlidingTabLayout;
import br.net.agroinvestapp.configure.restClient.AtualizaBancoHttoRequest;
import br.net.agroinvestapp.model.Insumo;

public class MainActivity extends AppCompatActivity {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private AtualizaBancoHttoRequest atualizaBancoHttoRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));

        //configurando adapter
        TabAdapterMain tabAdapter = new TabAdapterMain(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);
        atualizarBancoDadosLocal();



         }

    private void atualizarBancoDadosLocal(){
        atualizaBancoHttoRequest = new AtualizaBancoHttoRequest(this);
        atualizaBancoHttoRequest.execute();
    }

}
