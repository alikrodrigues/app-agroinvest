package br.net.agroinvestapp.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.adapter.InsumoAdapter;
import br.net.agroinvestapp.configure.adapter.OrcamentoAdapter;
import br.net.agroinvestapp.model.Insumo;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrcamentoFragment extends Fragment {
    private ArrayAdapter<Insumo> adapter;
    private ListView listView;
    private String colunasPrecos ="";
    private EditText edtColunasPrecos;

    public OrcamentoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_orcamento, container, false);
       edtColunasPrecos = (EditText) view.findViewById(R.id.edtColunasPrecos);
       edtColunasPrecos.setEnabled(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            String parametro = bundle.getString("parametro");
            String filtro = bundle.getString("filtro");
            montaResumo(parametro);


        }
        List<Insumo> insumosList = new ArrayList<>();
        Insumo insumo = new Insumo();
        insumo.setDescricao("Camarao (Cultivado)");
        insumo.setUnidadeMedida("unidade");
        insumo.setValorCanoinhas("...");
        insumo.setValorChapeco("2.3");
        insumo.setValorJaragua("2.3");
        insumo.setValorJoacaba("2.3");
        insumo.setValorLages("2.3");
        insumo.setValorRioSul("2.3");
        insumo.setValorSaoMiguelO("2.3");
        insumo.setValorSulCatarinense("2.3");

        insumosList.add(insumo);

        listView = (ListView) getActivity().findViewById(R.id.listViewOrcam);
        adapter = new OrcamentoAdapter(getActivity(),insumosList);
        listView.setAdapter(adapter);
    }

    private void montaResumo(String parametro){
        if(parametro.contains("lages"))
        colunasPrecos = "Lages";
        if(parametro.contains("canoinhas"))
            colunasPrecos =colunasPrecos + "    Canoinhas";

        if(parametro.contains("chapeco"))
            colunasPrecos =colunasPrecos + "    Chapeco";

        if(parametro.contains("jaragua"))
            colunasPrecos =colunasPrecos + "    Jaragua";

        if(parametro.contains("joacaba"))
            colunasPrecos =colunasPrecos + "    Joacaba";

        if(parametro.contains("riodoSul"))
            colunasPrecos =colunasPrecos + "    Rio do Sul";

        if(parametro.contains("sulCatarinense"))
            colunasPrecos =colunasPrecos + "    Sul Catarinense";

        if(parametro.contains("saoMiguelO"))
            colunasPrecos =colunasPrecos + "    Sao Miguel O";


        edtColunasPrecos.setText(colunasPrecos);
        if(colunasPrecos.length()>40&&colunasPrecos.length()<55)
            edtColunasPrecos.setTextSize(15);
        if(colunasPrecos.length()>54&&colunasPrecos.length()<68)
            edtColunasPrecos.setTextSize(12);
        if(colunasPrecos.length()>67&&colunasPrecos.length()<88)
            edtColunasPrecos.setTextSize(10);
        if(colunasPrecos.length()>88)edtColunasPrecos.setTextSize(8);


    }

    public void change(String dados){

}

}
