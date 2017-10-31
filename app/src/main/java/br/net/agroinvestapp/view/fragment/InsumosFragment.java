package br.net.agroinvestapp.view.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.ConfiguracaoBanco;
import br.net.agroinvestapp.configure.adapter.InsumoAdapter;
import br.net.agroinvestapp.configure.restClient.InsumoHttpRequest;
import br.net.agroinvestapp.model.Insumo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsumosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Insumo> adapter;
    private ArrayList<Insumo> insumos;
    private String extra;
    private InsumoHttpRequest insumoHttpRequest;
    private OnComunicatePlanos comunicatePlanos;

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



    public InsumosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_insumos, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        insumos = new ArrayList<>();






        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.tv_descricao);
                if(checkedTextView.isChecked())checkedTextView.setChecked(false);
                else checkedTextView.setChecked(true);
                comunicatePlanos.evento("Passou o dado");
                Toast.makeText(getActivity(), insumos.get(position).getDescricao(), Toast.LENGTH_SHORT).show();

            }
        });



        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comunicatePlanos = (OnComunicatePlanos)context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            String parametro = bundle.getString("parametro");
            String filtro = bundle.getString("filtro");
            requisitaLocal(parametro,filtro);

        }


    }



    private void requisitaHttp(String parametro){
        insumoHttpRequest = new InsumoHttpRequest(getActivity(),"");
        insumoHttpRequest.execute();
    }

    private void requisitaLocal(String parametro,String filtro){
        bancoLocal = ConfiguracaoBanco.getBancodeDados(getActivity());
        Cursor cursor = bancoLocal.createQuery(Insumo.class,"descricao like '%"+filtro+"%' OR categoria like '%"+filtro+"%'", "descricao");
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

        adapter = new InsumoAdapter(getActivity(),insumos);
        listView.setAdapter(adapter);

    }


}
