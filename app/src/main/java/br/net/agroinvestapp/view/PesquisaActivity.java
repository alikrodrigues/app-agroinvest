package br.net.agroinvestapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import br.net.agroinvestapp.R;

public class PesquisaActivity extends AppCompatActivity {

    private Switch lages;
    private Switch canoinhas;
    private Switch chapeco;
    private Switch jaragua;
    private Switch joacaba;
    private Switch riodoSul;
    private Switch sulCatarinense;
    private Switch saoMiguelO;
    private EditText edtPesquisa;
    private ImageView imageView;
    private Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);


        lages =(Switch) findViewById(R.id.lagesSwitch);
        canoinhas = (Switch) findViewById(R.id.canoinhasSwitch);
        chapeco = (Switch) findViewById(R.id.chapecoSwitch);
        jaragua =  (Switch) findViewById(R.id.jaraguaSwitch);
        joacaba = (Switch) findViewById(R.id.joacabaSwitch);
        riodoSul = (Switch) findViewById(R.id.rioSwitch);
        sulCatarinense = (Switch) findViewById(R.id.sulCSwitch);
        saoMiguelO = (Switch) findViewById(R.id.smoSwitch);
        edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);
        imageView = (ImageView) findViewById(R.id.imageButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarDados();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);

        toolbar.setTitle("AgroInvest");
        setSupportActionBar(toolbar);

        ativaTodos();
    }


    private void ativaTodos(){
        lages.setChecked(true);
//        canoinhas.setChecked(true);
//        chapeco.setChecked(true);
//        jaragua.setChecked(true);
//        joacaba.setChecked(true);
//        riodoSul.setChecked(true);
//        sulCatarinense.setChecked(true);
//        saoMiguelO.setChecked(true);
    }

    private void buscarDados(){
        String parametro = "";

        if(lages.isChecked()) parametro = parametro + " lages ";
        if(canoinhas.isChecked())parametro = parametro + " canoinhas ";
        if(chapeco.isChecked())parametro = parametro + " chapeco ";
        if(jaragua.isChecked())parametro = parametro + " jaragua ";
        if(joacaba.isChecked())parametro = parametro + " joacaba ";
        if(riodoSul.isChecked())parametro = parametro + " riodoSul ";
        if(sulCatarinense.isChecked())parametro = parametro + " sulCatarinense ";
        if(saoMiguelO.isChecked())parametro = parametro + " saoMiguelO ";

        Intent intent = new Intent(this, InsumosActivity.class);
        intent.putExtra("parametro",parametro);
        intent.putExtra("filtro",edtPesquisa.getText().toString());
        startActivity(intent);


    }

}
