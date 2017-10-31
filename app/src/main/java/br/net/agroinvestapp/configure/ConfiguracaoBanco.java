package br.net.agroinvestapp.configure;

import android.content.Context;
import android.widget.Toast;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.model.Insumo;

public class ConfiguracaoBanco {

    private static Jpdroid bancodeDados;

    public static void iniciaBanco(Context contexto){
        try{
        bancodeDados = Jpdroid.getInstance();
        bancodeDados.setContext(contexto);
        bancodeDados.setDatabaseVersion(12);
        bancodeDados.setDatabaseName("agroinvest");
        iniciaTabelas();
        bancodeDados.open();
        }catch (Exception e){
            Toast.makeText(contexto,"Erro ao Iniciar Banco", Toast.LENGTH_LONG).show();
        }
    }

    private static void iniciaTabelas(){

        bancodeDados.addEntity(Insumo.class);
    }

    public static Jpdroid getBancodeDados(Context contexto) {
        if (bancodeDados == null){
            iniciaBanco(contexto);
    }
        return bancodeDados;
    }
}
