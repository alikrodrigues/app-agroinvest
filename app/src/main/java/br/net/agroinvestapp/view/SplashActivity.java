package br.net.agroinvestapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.preferences.PreferenciaClass;
import br.net.agroinvestapp.configure.restClient.AtualizaBancoHttoRequest;
import br.net.agroinvestapp.model.Preferencias;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    private Jpdroid dataBase;
    private AtualizaBancoHttoRequest atualizaBancoHttoRequest;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PreferenciaClass preferenciaClass = new PreferenciaClass(this);
        preferencias = preferenciaClass.getPreferencias();
        Date date = new Date(System.currentTimeMillis());
        String dataString = new SimpleDateFormat("MMyy").format(date);
        if(preferencias==null||preferencias.getUltAtualizacao()==null){
            try {
                atualizarBancoDadosLocal();
            } catch (Exception e) {

            }
        }
        if(preferencias!=null&&
                preferencias.getUltAtualizacao()!=null&&
                !preferencias.getUltAtualizacao().equals(dataString)) {

            try {
                atualizarBancoDadosLocal();
            } catch (Exception e) {

            }
        }
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
            mostraMain();
            }
        },2000);
    }


    public void mostraMain(){
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
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
