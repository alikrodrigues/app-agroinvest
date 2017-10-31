package br.net.agroinvestapp.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.view.PlanoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PesquisaFragment extends Fragment {

    private Switch mes;
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


    public PesquisaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_pesquisa, container, false);

        mes = view.findViewById(R.id.mesesSwitch);
        lages = view.findViewById(R.id.lagesSwitch);
        canoinhas = view.findViewById(R.id.canoinhasSwitch);
        chapeco = view.findViewById(R.id.chapecoSwitch);
        jaragua = view.findViewById(R.id.jaraguaSwitch);
        joacaba = view.findViewById(R.id.joacabaSwitch);
        riodoSul = view.findViewById(R.id.rioSwitch);
        sulCatarinense = view.findViewById(R.id.sulCSwitch);
        saoMiguelO = view.findViewById(R.id.smoSwitch);
        edtPesquisa = view.findViewById(R.id.edtPesquisa);
        imageView = view.findViewById(R.id.imageButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarDados();
            }
        });

        ativaTodos();
        return view;
    }

    private void ativaTodos(){
        lages.setChecked(true);
        canoinhas.setChecked(true);
        chapeco.setChecked(true);
        jaragua.setChecked(true);
        joacaba.setChecked(true);
        riodoSul.setChecked(true);
        sulCatarinense.setChecked(true);
        saoMiguelO.setChecked(true);
    }

    private void buscarDados(){
        String parametro = "";
        if (edtPesquisa.getText().toString().length()<1){
            Toast.makeText(getContext(),"Insira um texto", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mes.isChecked())
            parametro = "periodo= t,";

        if(lages.isChecked()) parametro = parametro + " lages ";
        if(canoinhas.isChecked())parametro = parametro + " canoinhas ";
        if(chapeco.isChecked())parametro = parametro + " chapeco ";
        if(jaragua.isChecked())parametro = parametro + " jaragua ";
        if(joacaba.isChecked())parametro = parametro + " joacaba ";
        if(riodoSul.isChecked())parametro = parametro + " riodoSul ";
        if(sulCatarinense.isChecked())parametro = parametro + " sulCatarinense ";
        if(saoMiguelO.isChecked())parametro = parametro + " saoMiguelO ";

        Intent intent = new Intent(getActivity(), PlanoActivity.class);
        intent.putExtra("parametro",parametro);
        intent.putExtra("filtro",edtPesquisa.getText().toString());
        startActivity(intent);


    }




}
