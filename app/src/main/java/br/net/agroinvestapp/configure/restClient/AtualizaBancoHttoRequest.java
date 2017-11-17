package br.net.agroinvestapp.configure.restClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.com.rafael.jpdroid.exceptions.JpdroidException;
import br.net.agroinvestapp.configure.JpdroidSQL.ConfiguracaoBanco;
import br.net.agroinvestapp.model.Insumo;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class AtualizaBancoHttoRequest extends AsyncTask<Void,Void,List<Insumo>> {
    private String URL_SERVER = "http://54.207.91.56:8080/ws-agroinvest/insumos/";
    private List<Insumo> insumos;
    private Activity activity;
    private Jpdroid bancoDados;
    private int indexPeriodo;


    public AtualizaBancoHttoRequest(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected  List<Insumo> doInBackground(Void... params) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Insumo[] insumo = restTemplate.getForObject(URL_SERVER, Insumo[].class);
            insumos = Arrays.asList(insumo);
            return insumos;
        } catch (Exception e) {
//           doInBackground();
             Log.e("NovoOrcamentoActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Insumo> insumosList) {

        bancoDados = ConfiguracaoBanco.getBancodeDados(activity);
        Insumo local = new Insumo();
        Cursor cursor;
        try {
            cursor = bancoDados.createQuery(Insumo.class, "idOrcamento is Null");
        }catch (Exception e ){
            cursor= bancoDados.createQuery(Insumo.class);
        }
        cursor.moveToFirst();
        if (cursor.getCount() < 5) {
            for (Insumo insumo : insumosList) {

                try {
                    insumo.set_Id(0);
                    bancoDados.persist(insumo);
                } catch (JpdroidException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Erro na importação dos dados", Toast.LENGTH_SHORT).show();

                }
            }
            Toast.makeText(activity, "Atualizado com sucesso !", Toast.LENGTH_LONG).show();


        } else {
            indexPeriodo = cursor.getColumnIndex("periodo");


            local.setPeriodo(cursor.getString(indexPeriodo));

            if (!local.getPeriodo().equals(insumosList.get(0).getPeriodo())) {


                final List<Insumo> insumosl = insumosList;
                new AlertDialog.Builder(activity)
                        .setTitle("Atualizando dados")
                        .setMessage("Deseja atualizar dados mensais ?")
                        .setPositiveButton("sim",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        bancoDados.execSQL("DELETE FROM insumo WHERE idOrcamento is NULL");

                                        for (Insumo insumo : insumosl) {

                                            try {
                                                insumo.set_Id(0);
                                                bancoDados.persist(insumo);
                                            } catch (JpdroidException e) {
                                                e.printStackTrace();
                                                Toast.makeText(activity, "Erro na importação dos dados", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                        Toast.makeText(activity,"Sucesso !", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Não Agora.",null)
                        .show();


            }

        }
    }






}
