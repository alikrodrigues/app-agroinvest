package br.net.agroinvestapp.configure.restClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import br.net.agroinvestapp.configure.preferences.PreferenciaClass;
import br.net.agroinvestapp.model.Orcamento;
import br.net.agroinvestapp.model.Preferencias;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


//Classe Assincrona que comunica com o Web Service API
public class EnviaEmailHttpRequest extends AsyncTask<Void,Void,Orcamento> {
    private String URL_SERVER = "http://www.agroinvest.net.br:8080/ws-agroinvest/orcamento/";
    private Activity activity;
    private String email;
    private Orcamento orcamento;

    public EnviaEmailHttpRequest(Activity activity, String email,Orcamento orcamento) {
        this.activity = activity;
        this.email = email;
        this.orcamento = orcamento;
    }

    @Override
    protected  Orcamento doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.postForObject(URL_SERVER+email,orcamento,String.class);
            return orcamento;
        } catch (Exception e) {
            Log.e("NovoOrcamentoActivity", e.getMessage(), e);
        }

        return null;
    }


    @Override
    protected void onPostExecute(Orcamento orcamento) {
        PreferenciaClass preferenciaClass = new PreferenciaClass(activity);
        Preferencias preferencias = new Preferencias();
        preferencias.setEmailPreferido(email);
        preferenciaClass.salvarEmail(preferencias);
        Toast.makeText(activity,"Enviado !",Toast.LENGTH_SHORT).show();
    }
}
