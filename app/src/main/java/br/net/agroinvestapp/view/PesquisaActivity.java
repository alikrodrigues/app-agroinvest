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
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PesquisaActivity extends AppCompatActivity {

    @BindView(R.id.lagesSwitch)
    Switch lages;
    @BindView(R.id.canoinhasSwitch)
    Switch canoinhas;
    @BindView(R.id.chapecoSwitch)
    Switch chapeco;
    @BindView(R.id.jaraguaSwitch)
    Switch jaragua;
    @BindView(R.id.joacabaSwitch)
    Switch joacaba;
    @BindView(R.id.rioSwitch)
    Switch riodoSul;
    @BindView(R.id.sulCSwitch)
    Switch sulCatarinense;
    @BindView(R.id.smoSwitch)
    Switch saoMiguelO;
    @BindView(R.id.edtPesquisa)
    EditText edtPesquisa;
    @BindView(R.id.imageButton)
    ImageView imageView;
    private Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        ButterKnife.bind(this);

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
