package br.net.agroinvestapp.view.dialog;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.com.rafael.jpdroid.exceptions.JpdroidException;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.JpdroidSQL.ConfiguracaoBanco;
import br.net.agroinvestapp.model.Orcamento;
import br.net.agroinvestapp.view.OrcamentoActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Dialog_cidadesActivity extends Activity {

    private long id;
    private String parametro;
    @BindView(R.id.lagesSw) Switch lages;
    @BindView(R.id.canoinhassw) Switch canoinhas;
    @BindView(R.id.chapecosw) Switch chapeco;
    @BindView(R.id.jaraguasw) Switch jaragua;
    @BindView(R.id.joacabasw) Switch joacaba;
    @BindView(R.id.riosw) Switch riodoSul;
    @BindView(R.id.scsw) Switch sulCatarinense;
    @BindView(R.id.smosw) Switch saoMiguelO;
    private Orcamento orcamento;
    private Jpdroid bancoDados;
    @BindView(R.id.btnOk) Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_cidades);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);


    }

    private void switches(){
        if (parametro.contains("lages")) {
            lages.setChecked(true);
        }
        if (parametro.contains("canoinhas")) {
            canoinhas.setChecked(true);
        }

        if (parametro.contains("chapeco")) {
            chapeco.setChecked(true);
        }

        if (parametro.contains("jaragua")){
            jaragua.setChecked(true);
        }
        if(parametro.contains("joacaba")){
            joacaba.setChecked(true);
        }

        if(parametro.contains("riodoSul")){
            riodoSul.setChecked(true);
        }

        if(parametro.contains("sulCatarinense")){
            sulCatarinense.setChecked(true);
        }

        if(parametro.contains("saoMiguelO")){
            saoMiguelO.setChecked(true);
        }
    }

    @Override
    protected void onResume() {
        bancoDados = ConfiguracaoBanco.getBancodeDados(Dialog_cidadesActivity.this);
        orcamento = (Orcamento) bancoDados.retrieve(Orcamento.class,"_id = "+id,true).get(0);
        parametro = orcamento.getParametro();
        switches();
        acaoOk();
        super.onResume();
    }

    public void acaoOk(){
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orcamento.setParametro(montaParametro());
                try {
                    bancoDados.persist(orcamento);
                } catch (JpdroidException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Dialog_cidadesActivity.this, OrcamentoActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        });
    }

    public String montaParametro(){
        String retornoParametro="";
        if(lages.isChecked()) retornoParametro = retornoParametro + " lages ";
        if(canoinhas.isChecked())retornoParametro = retornoParametro + " canoinhas ";
        if(chapeco.isChecked())retornoParametro = retornoParametro + " chapeco ";
        if(jaragua.isChecked())retornoParametro = retornoParametro + " jaragua ";
        if(joacaba.isChecked())retornoParametro = retornoParametro + " joacaba ";
        if(riodoSul.isChecked())retornoParametro = retornoParametro + " riodoSul ";
        if(sulCatarinense.isChecked())retornoParametro = retornoParametro + " sulCatarinense ";
        if(saoMiguelO.isChecked())retornoParametro = retornoParametro + " saoMiguelO ";


            return retornoParametro;
    }

}
