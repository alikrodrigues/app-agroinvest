package br.net.agroinvestapp.configure.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import br.net.agroinvestapp.view.fragment.PesquisaFragment;
import br.net.agroinvestapp.view.fragment.WebViewFragment;

public class TabAdapterMain extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"PESQUISA","NOTICIAS"};

    public TabAdapterMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new PesquisaFragment();
                break;
            case 1:
                fragment = new WebViewFragment();
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
}
