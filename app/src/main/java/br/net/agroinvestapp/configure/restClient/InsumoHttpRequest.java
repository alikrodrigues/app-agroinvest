package br.net.agroinvestapp.configure.restClient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.adapter.InsumoAdapter;
import br.net.agroinvestapp.model.Insumo;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsumoHttpRequest extends AsyncTask<Void,Void,List<Insumo>> {
    private String URL_SERVER = "http://54.207.91.56:8080/ws-agroinvest/insumos/";
    private List<Insumo> insumos;
    private String parametro;
    private ListView listView;
    private ArrayAdapter<Insumo> adapter;
    private Activity activity;

    public InsumoHttpRequest(Activity activity, String parametro) {
        this.parametro = parametro;
        this.activity = activity;
    }

    @Override
    protected  List<Insumo> doInBackground(Void... params) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Insumo[] insumo = restTemplate.getForObject(URL_SERVER+parametro, Insumo[].class);
            insumos = Arrays.asList(insumo);
            return insumos;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Insumo> insumosList) {
        listView = (ListView) activity.findViewById(R.id.listView);
        adapter = new InsumoAdapter(activity,insumosList);
        listView.setAdapter(adapter);
    }

}
