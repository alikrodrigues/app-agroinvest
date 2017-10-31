package br.net.agroinvestapp.configure.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import br.net.agroinvestapp.view.fragment.InsumosFragment;
import br.net.agroinvestapp.view.fragment.OrcamentoFragment;

public class TabAdapterPlano extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"INSUMOS","ORÇAMENTO"};
    private Bundle bundle;

    public TabAdapterPlano(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new InsumosFragment();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new OrcamentoFragment();
                fragment.setArguments(bundle);
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
