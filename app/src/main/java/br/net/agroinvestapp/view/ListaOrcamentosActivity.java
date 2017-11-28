package br.net.agroinvestapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.JpdroidSQL.ConfiguracaoBanco;
import br.net.agroinvestapp.configure.adapter.OrcamentoSalvoAdapter;
import br.net.agroinvestapp.model.Insumo;
import br.net.agroinvestapp.model.Orcamento;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class ListaOrcamentosActivity extends AppCompatActivity {


    @BindView(R.id.edtOrcamSalvo) EditText pesquisa;
    @BindView(R.id.listaOrcamentosSalvos) ListView listaOrcamentoSalvo;
    private List<Orcamento> orcamentos;
    private String filtro = "";
    private ArrayAdapter<Orcamento> orcamentoSalvoAdapter;
    private Toolbar toolbar;
    private  Jpdroid bancoLocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_orcamentos);

        ButterKnife.bind(this);

        orcamentos = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);

        toolbar.setTitle("AgroInvest");
        setSupportActionBar(toolbar);

        bancoLocal = ConfiguracaoBanco.getBancodeDados(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        orcamentoSalvoAdapter = new OrcamentoSalvoAdapter(this,orcamentos);
        requisitaLoca(filtro);
        pesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            requisitaLoca(pesquisa.getText().toString());
            }
        });
        listaOrcamentoSalvo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(ListaOrcamentosActivity.this).setTitle("AGROINVEST")
                        .setMessage("Deseja deletar o orçamento: \n "+orcamentos.get(position).getDescricao()+" ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bancoLocal.delete(orcamentos.get(position));
                                orcamentos.remove(position);
                                Toast.makeText(ListaOrcamentosActivity.this,"Orçamento deletado !",Toast.LENGTH_SHORT).show();
                                orcamentoSalvoAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("Não",null)
                        .show();
                return true;
            }
        });

        listaOrcamentoSalvo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

             new AlertDialog.Builder(ListaOrcamentosActivity.this).setTitle("AGROINVEST")
                     .setMessage("Deseja Abrir o orçamento: \n "+orcamentos.get(position).getDescricao()+" ?")
                     .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             Bundle bundle = new Bundle();
                             Intent intent = new Intent(ListaOrcamentosActivity.this ,OrcamentoActivity.class);
                             intent.putExtra("id",orcamentos.get(position).get_id());
                             startActivity(intent);

                         }
                     }).setNegativeButton("Não",null)
                     .show();
            }
        });
        listaOrcamentoSalvo.setAdapter(orcamentoSalvoAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable("InstaciaOrcamento", (Serializable) this.orcamentos);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        orcamentos =(List<Orcamento>) savedInstanceState.getSerializable("InstanciaOrcamento");
    }

    private void requisitaLoca(String filtro){
        Jpdroid bancoLocal = ConfiguracaoBanco.getBancodeDados(this);
        orcamentos = bancoLocal.retrieve(Orcamento.class,"descricao like '%"+filtro+"%' OR data like '%"+filtro+"%'",true);
        orcamentoSalvoAdapter.addAll(this.orcamentos);
    }

}
