package br.net.agroinvestapp.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.JpdroidSQL.ConfiguracaoBanco;
import br.net.agroinvestapp.configure.adapter.InsumoAdapter;
import br.net.agroinvestapp.model.Insumo;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InsumosActivity extends AppCompatActivity {

    @BindView(R.id.listInsumos)
    ListView listView;
    @BindView(R.id.edtContador) EditText contadorSelecionados;
    @BindView(R.id.edtPesquisa) EditText edtPesquisa;
    private  ArrayAdapter<Insumo> adapter;
    private ArrayList<Insumo> insumos;
    private String filtro;
    private String parametro;
    private List<Insumo> selecionados;


    //Banco local
    private Jpdroid bancoLocal;
    private int indexDesc;
    private int indexCategoria;
    private int indexPeriodo;
    private int indexUnidade;
    private int indexVLages;
    private int indexVCanoinhas;
    private int indexVRioSul;
    private int indexVSMO;
    private int indexVSulCatarinense;
    private int indexVChapeco;
    private int indexVjoacaba;
    private int indexVjaragua;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insumos);
        ButterKnife.bind(this);
        insumos = new ArrayList<>();
        selecionados = new ArrayList<>();



        Intent intent = getIntent();
        filtro = intent.getStringExtra("filtro");
        parametro = intent.getStringExtra("parametro");

        adapter = new InsumoAdapter(this,insumos);


        contadorSelecionados.setEnabled(false);



    }


    @Override
    protected void onResume() {
        super.onResume();
        requisitaLocal(filtro);
        edtPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                requisitaLocal(edtPesquisa.getText().toString());
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.tv_descricao);
                if(insumos.get(position).getChecado()==1){
                    checkedTextView.setChecked(false);
                    insumos.get(position).setChecado(0);
                    selecionados.remove(insumos.get(position));
                }
                else{
                    insumos.get(position).setChecado(1);
                    checkedTextView.setChecked(true);
                    selecionados.add(insumos.get(position));
                }

                contadorSelecionados.setText(String.valueOf(selecionados.size()));

                Toast.makeText(InsumosActivity.this, insumos.get(position).getDescricao(), Toast.LENGTH_SHORT).show();

            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("InstaciaInsumos", (Serializable) selecionados);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selecionados =(List<Insumo>) savedInstanceState.getSerializable("InstaciaInsumos");
        adapter.notifyDataSetChanged();
    }

    /*Vai até o banco local para requisitar os dados*/
    private void requisitaLocal(String filtro){
        bancoLocal = ConfiguracaoBanco.getBancodeDados(this);
        Cursor cursor = bancoLocal.createQuery(Insumo.class,"(descricao like '%"+filtro+"%' OR categoria like '%"+filtro+"%') AND idOrcamento is Null", "descricao");
        cursor.moveToFirst();
        indexDesc = cursor.getColumnIndex("descricao");
        indexUnidade = cursor.getColumnIndex("unidadeMedida");
        indexPeriodo = cursor.getColumnIndex("periodo");
        indexCategoria = cursor.getColumnIndex("categoria");
        indexVLages = cursor.getColumnIndex("valorLages");
        indexVCanoinhas = cursor.getColumnIndex("valorCanoinhas");
        indexVRioSul = cursor.getColumnIndex("valorRioSul");
        indexVSMO = cursor.getColumnIndex("valorSaoMiguelOeste");
        indexVSulCatarinense = cursor.getColumnIndex("valorSulCatarinense");
        indexVChapeco = cursor.getColumnIndex("valorChapeco");
        indexVjoacaba = cursor.getColumnIndex("valorJoacaba");
        indexVjaragua = cursor.getColumnIndex("valorJaragua");
        insumos.clear();

        if(cursor.getCount()==0){
            cursor = null;
            cursor = bancoLocal.createQuery(Insumo.class,"idOrcamento is Null", "descricao");
            cursor.moveToFirst();
            Toast.makeText(this, "Desculpe, não encontrado!", Toast.LENGTH_SHORT).show();
        }
        for(int i =0; i< cursor.getCount();i++){
            Insumo insumo = new Insumo();
            insumo.setDescricao(cursor.getString(indexDesc));
            insumo.setUnidadeMedida(cursor.getString(indexUnidade));
            insumo.setPeriodo(cursor.getString(indexPeriodo));
            insumo.setCategoria(cursor.getString(indexCategoria));
            insumo.setValorLages(cursor.getString(indexVLages));
            insumo.setValorCanoinhas(cursor.getString(indexVCanoinhas));
            insumo.setValorRioSul(cursor.getString(indexVRioSul));
            insumo.setValorSulCatarinense(cursor.getString(indexVSulCatarinense));
            insumo.setValorChapeco(cursor.getString(indexVChapeco));
            insumo.setValorSaoMiguelO(cursor.getString(indexVSMO));
            insumo.setValorJaragua(cursor.getString(indexVjaragua));
            insumo.setValorJoacaba(cursor.getString(indexVjoacaba));
            insumos.add(insumo);
            cursor.moveToNext();

        }
        if(selecionados.size()>0)
        manipulaLista();
        else
            adapter.addAll(insumos);

    }

    /*Manipula com o resultado do banco e os selecionados*/
    private void manipulaLista(){
        Log.i("resultadoB tm", String.valueOf(insumos.size()) );
        if(selecionados.size()>0){
            for (int i=0;i<insumos.size();i++){
                boolean verifica = false;
                Log.i("for", "for resultado");
                for(Insumo insumoSelecionado : selecionados){
                    if(insumoSelecionado.getDescricao().equals(insumos.get(i).getDescricao())&&
                            insumoSelecionado.getPeriodo().equals(insumos.get(i).getPeriodo()) &&
                            insumoSelecionado.getCategoria().equals(insumos.get(i).getCategoria()))verifica=true;
                }
                if(verifica){
                insumos.remove(insumos.get(i));
                }
            }
        for (Insumo insumoSelecionado : selecionados ){
                insumoSelecionado.setChecado(1);
                insumos.add(insumoSelecionado);
        }
        adapter.addAll(insumos);
        }


    }

    /*Ação do botão do click do orçamento*/
    public void clickOrcamento(View view){
        Bundle bundle = new Bundle();
        bundle.putSerializable("insumos", (Serializable) selecionados);
        Intent intent = new Intent(this,OrcamentoActivity.class);
        intent.putExtra("parametro",parametro);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
        finish();
    }
}
