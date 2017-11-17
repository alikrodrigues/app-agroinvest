package br.net.agroinvestapp.configure.JpdroidSQL;

import android.content.Context;
import android.widget.Toast;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.model.Insumo;
import br.net.agroinvestapp.model.Orcamento;

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
        bancodeDados.addEntity(Orcamento.class);
    }

    public static Jpdroid getBancodeDados(Context contexto) {
        if (bancodeDados == null){
            iniciaBanco(contexto);
    }
        return bancodeDados;
    }
}
