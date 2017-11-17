package br.net.agroinvestapp.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.R;

public class SplashActivity extends AppCompatActivity {

    private Jpdroid dataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        dataBase = Jpdroid.getInstance();
//        dataBase.setContext(this);
//        dataBase.setDatabaseVersion(12);
//        dataBase.setDatabaseName("agroinvest");
//
//        dataBase.addEntity(Insumo.class);
//        dataBase.open();
//
//        Insumo insumo = new Insumo();
//        insumo.set_Id(0);
//        insumo.setDescricao("aa");
//        insumo.setUnidadeMedida("aa");
//        insumo.setValorSaoMiguelO("aa");
//        insumo.setValorJoacaba("aa");
//        insumo.setValorJaragua("aa");
//        insumo.setValorChapeco("aa");
//        insumo.setValorSulCatarinense("aa");
//        insumo.setValorRioSul("aa");
//        insumo.setValorCanoinhas("aa");
//        insumo.setPeriodo("aa");
//        insumo.setCategoria("aa");
//        insumo.setValorLages("aa");
//
//        try {
//            dataBase.persist(insumo);
//        } catch (JpdroidException e) {
//            e.printStackTrace();
//        }
//
//        Cursor cursor = dataBase.createQuery(Insumo.class);
//        Log.i("cursorrr", String.valueOf(cursor.getCount()));
//        cursor.moveToFirst();
//        Log.i("cursorr22222", String.valueOf(cursor.getCount()));





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


}
