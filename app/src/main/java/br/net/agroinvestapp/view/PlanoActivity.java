package br.net.agroinvestapp.view;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.adapter.TabAdapterPlano;
import br.net.agroinvestapp.configure.helper.SlidingTabLayout;
import br.net.agroinvestapp.view.fragment.InsumosFragment;
import br.net.agroinvestapp.view.fragment.OnComunicatePlanos;
import br.net.agroinvestapp.view.fragment.OrcamentoFragment;


public class PlanoActivity extends AppCompatActivity implements OnComunicatePlanos {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    public String parametro;
    public String filtro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano);

        Intent it = getIntent();
        parametro = it.getStringExtra("parametro");
        filtro = it.getStringExtra("filtro");

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl);
        viewPager = (ViewPager) findViewById(R.id.vp);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));

        Bundle bundle = new Bundle();
        bundle.putString("parametro", parametro);
        bundle.putString("filtro",filtro);


        //configurando adapter
        TabAdapterPlano tabAdapter = new TabAdapterPlano(getSupportFragmentManager());
        tabAdapter.setBundle(bundle);
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);
    }


    @Override
    public void evento(String dado) {
        FragmentManager manager = getFragmentManager();
        OrcamentoFragment frag = (OrcamentoFragment) manager.findFragmentById(R.id.frag_orca);
        frag.change(dado);
    }
}
