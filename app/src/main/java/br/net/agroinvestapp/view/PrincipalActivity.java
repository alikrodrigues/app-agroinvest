package br.net.agroinvestapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.JpdroidSQL.ConfiguracaoBanco;
import br.net.agroinvestapp.configure.Permissao;
import br.net.agroinvestapp.configure.restClient.AtualizaBancoHttoRequest;
import br.net.agroinvestapp.model.Orcamento;

public class PrincipalActivity extends AppCompatActivity {

    private AtualizaBancoHttoRequest atualizaBancoHttoRequest;
    private Toolbar toolbar;
    private String[] permissoesNescessarias = new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Permissao.validaPermissao(1,this,permissoesNescessarias);


        try {
            atualizarBancoDadosLocal();
        }catch (Exception e){

        }
    }


    public void novoOrcamento(View view){
        Intent intent = new Intent(this, PesquisaActivity.class);
        startActivity(intent);
    }

    public void listaOrcamentos(View view){
        Jpdroid bancoLocal = ConfiguracaoBanco.getBancodeDados(this);
        Cursor cursor = bancoLocal.createQuery(Orcamento.class);
        if(cursor.getCount()>0){
            Intent intent = new Intent(this, ListaOrcamentosActivity.class);
            startActivity(intent);
        }else Toast.makeText(this," Não há orçamentos salvos !",Toast.LENGTH_SHORT).show();

    }

    public void inforAgro(View view){
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);

    }

    public void noticias(View view){
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }


    private void atualizarBancoDadosLocal(){
        if(verificaConexao()) {
            atualizaBancoHttoRequest = new AtualizaBancoHttoRequest(this);
            atualizaBancoHttoRequest.execute();
        }else
            Toast.makeText(this,"Conecte-se a internet para coletar os preços",Toast.LENGTH_LONG).show();
    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

}
