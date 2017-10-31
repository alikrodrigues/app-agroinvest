package br.net.agroinvestapp.configure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.model.Insumo;

import java.util.List;

public class OrcamentoAdapter extends ArrayAdapter<Insumo> {

    private List<Insumo> insumos;
    private Context context;


    public OrcamentoAdapter(Context c, List<Insumo> objects) {
        super(c, 0, objects);
        this.context=c;
        this.insumos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(insumos!=null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_orcamento,parent,false);


            TextView descricaoUn = (TextView) view.findViewById(R.id.tv_desceun_orc);
            TextView valorCanoinhas = (TextView) view.findViewById(R.id.tv_valorCanoinhas_orc);
            TextView valorLages = (TextView) view.findViewById(R.id.tv_valorLages_orc);
            TextView valorChapeco = (TextView) view.findViewById(R.id.tv_valorChapeco_orc);
            TextView valorJaragua = (TextView) view.findViewById(R.id.tv_valorJaragua_orc);
            TextView valorJoacaba = (TextView) view.findViewById(R.id.tv_valorJoacaba_orc);
            TextView valorRioSul = (TextView) view.findViewById(R.id.tv_valorRioSul_orc);
            TextView valorSaoMiguelOeste = (TextView) view.findViewById(R.id.tv_valorSaoMiguelOeste_orc);
            TextView valorSulCatarinense = (TextView) view.findViewById(R.id.tv_valorSulCatarinense_orc);

            Insumo insumo = insumos.get(position);
            descricaoUn.setText(insumo.getDescricao()+ " "+ insumo.getUnidadeMedida() );
            valorCanoinhas.setText(insumo.getValorCanoinhas());
            valorLages.setText(insumo.getValorLages());
            valorChapeco.setText(insumo.getValorChapeco());
            valorJaragua.setText(insumo.getValorJaragua());
            valorJoacaba.setText(insumo.getValorJoacaba());
            valorRioSul.setText(insumo.getValorRioSul());
            valorSaoMiguelOeste.setText(insumo.getValorSaoMiguelO());
            valorSulCatarinense.setText(insumo.getValorSulCatarinense());

        }

        return view;
    }
}
