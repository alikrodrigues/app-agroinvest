package br.net.agroinvestapp.configure.restClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import br.net.agroinvestapp.model.Orcamento;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;



public class EnviaEmailHttpRequest extends AsyncTask<Void,Void,Orcamento> {
    private String URL_SERVER = "http://54.207.91.56:8080/ws-agroinvest/orcamento/";
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
//            ClientHttpRequestFactory requestFactory = new Cl
//            HttpHeaders requestHeaders = new HttpHeaders();
//            requestHeaders.setContentType(new MediaType("application","json"));
//            HttpEntity<Orcamento> requestEntity = new HttpEntity<Orcamento>(orcamento, requestHeaders);

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            restTemplate.postForObject(URL_SERVER+email,orcamento,String.class);
            Log.e("Orcamento",orcamento.toString());

//            ResponseEntity<String> responseEntity = restTemplate.exchange(URL_SERVER+email, HttpMethod.POST, requestEntity, String.class);
//            String result = responseEntity.getBody();

            return orcamento;
        } catch (Exception e) {
            Log.e("NovoOrcamentoActivity", e.getMessage(), e);
        }

        return null;
    }


    @Override
    protected void onPostExecute(Orcamento orcamento) {
        Toast.makeText(activity,"Enviado !",Toast.LENGTH_SHORT).show();
    }
}
