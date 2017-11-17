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

    public HashMap<String,String> getPreferencias(){
        HashMap<String,String> dadosPreferencia = new HashMap<>();
        dadosPreferencia.put("SITE",sharedPreferences.getString("SITE",null));


        return dadosPreferencia;
    }


}
