package br.net.agroinvestapp.configure.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import br.net.agroinvestapp.model.Preferencias;

import java.util.HashMap;

public class PreferenciaClass {

    private static final String ARQUIVO_PREFERENCIAS = "Agroinvest.preferencia";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final int MODO_PRIVADO = 0;
    private Preferencias preferencias;

    public PreferenciaClass(Context context) {
        sharedPreferences = context.getSharedPreferences(ARQUIVO_PREFERENCIAS,MODO_PRIVADO);
        editor = sharedPreferences.edit();
    }


    public void salvarPreferencias(Preferencias preferencias){
        editor.putString("SITE",preferencias.getSitePreferido());
        editor.commit();
    }
    public  void salvarEmail(Preferencias preferencias){
        editor.putString("EMAIL",preferencias.getEmailPreferido());
        editor.commit();
    }
    public void salvarLastUpdate(Preferencias preferencias){
        editor.putString("LASTUP",preferencias.getUltAtualizacao());
        editor.commit();
    }

    public Preferencias getPreferencias(){
        Preferencias preferencias = new Preferencias();
        preferencias.setSitePreferido(sharedPreferences.getString("SITE",null));
        preferencias.setEmailPreferido(sharedPreferences.getString("EMAIL",null));
        preferencias.setUltAtualizacao(sharedPreferences.getString("LASTUP",null));
        return  preferencias;
    }





}
