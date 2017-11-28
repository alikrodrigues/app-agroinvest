package br.net.agroinvestapp.configure.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    private String parametro;




    public OrcamentoAdapter(Context c, List<Insumo> objects,String parametro) {
        super(c, 0, objects);
        this.context=c;
        this.insumos = objects;
        this.parametro = parametro;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        boolean lages=false;
        boolean canoinhas=false;
        boolean chapeco=false;
        boolean jaragua=false;
        boolean joacaba=false;
        boolean rioSul=false;
        boolean sulCatarinense=false;
        boolean saoMiguelOeste=false;
        if(insumos!=null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.modelo_nova_lista_orcamento,parent,false);


            TextView descricaoUn = (TextView) view.findViewById(R.id.tv_desceun_orc);
            TextView valorCanoinhas = (TextView) view.findViewById(R.id.tv_valorCanoinhas_orc);
            TextView valorLages = (TextView) view.findViewById(R.id.tv_valorLages_orc);
            TextView valorChapeco = (TextView) view.findViewById(R.id.tv_valorChapeco_orc);
            TextView valorJaragua = (TextView) view.findViewById(R.id.tv_valorJaragua_orc);
            TextView valorJoacaba = (TextView) view.findViewById(R.id.tv_valorJoacaba_orc);
            TextView valorRioSul = (TextView) view.findViewById(R.id.tv_valorRioSul_orc);
            TextView valorSaoMiguelOeste = (TextView) view.findViewById(R.id.tv_valorSaoMiguelOeste_orc);
            TextView valorSulCatarinense = (TextView) view.findViewById(R.id.tv_valorSulCatarinense_orc);
            TextView vermelhos = (TextView) view.findViewById(R.id.vermelhos);

            Insumo insumo = insumos.get(position);
            descricaoUn.setText(insumo.getDescricao()+ " "+ insumo.getUnidadeMedida() );
            valorCanoinhas.setText("Canoinhas: R$" +insumo.getValorCanoinhas().replace(".",","));
            valorLages.setText("Lages: R$"+insumo.getValorLages().replace(".",","));
            valorChapeco.setText("Chapecó: R$"+insumo.getValorChapeco().replace(".",","));
            valorJaragua.setText("Jaraguá: R$"+insumo.getValorJaragua().replace(".",","));
            valorJoacaba.setText("Joaçaba: R$"+insumo.getValorJoacaba().replace(".",","));
            valorRioSul.setText("Rio do Sul: R$"+insumo.getValorRioSul().replace(".",","));
            valorSaoMiguelOeste.setText("São Miguel O: R$"+insumo.getValorSaoMiguelO().replace(".",","));
            valorSulCatarinense.setText("Sul Catarinense: R$"+insumo.getValorSulCatarinense().replace(".",","));


            if (!parametro.contains("lages")) {
                valorLages.setVisibility(View.GONE);
            }else{
                if(insumo.getValorLages().equals("...")){
                    valorLages.setVisibility(View.GONE);
                    valorLages.setTextColor(Color.RED);
                    lages=true;
                }
            }

            if (!parametro.contains("canoinhas")) {
                valorCanoinhas.setVisibility(View.GONE);
            }else{
                if(insumo.getValorCanoinhas().equals("...")){
                    valorCanoinhas.setVisibility(View.GONE);
                    valorCanoinhas.setTextColor(Color.RED);
                    canoinhas=true;
                }
            }

            if (!parametro.contains("chapeco")) {
                valorChapeco.setVisibility(View.GONE);
            }else{
                if(insumo.getValorChapeco().equals("...")){
                    valorChapeco.setTextColor(Color.RED);
                    valorChapeco.setVisibility(View.GONE);
                    chapeco=true;
                }
            }

            if (!parametro.contains("jaragua")){
                valorJaragua.setVisibility(View.GONE);
            }else{
                if(insumo.getValorJaragua().equals("...")){
                    valorJaragua.setTextColor(Color.RED);
                    valorJaragua.setVisibility(View.GONE);
                    jaragua=true;
                }
            }
            if(!parametro.contains("joacaba")){
                valorJoacaba.setVisibility(View.GONE);
            }else{
                if(insumo.getValorJoacaba().equals("...")){
                    valorJoacaba.setTextColor(Color.RED);
                    valorJoacaba.setVisibility(View.GONE);
                    joacaba=true;
                }
            }

            if(!parametro.contains("riodoSul")){
                valorRioSul.setVisibility(View.GONE);
            }else{
                if(insumo.getValorRioSul().equals("...")){
                    valorRioSul.setVisibility(View.GONE);
                    valorRioSul.setTextColor(Color.RED);
                    rioSul=true;
                }
            }

            if(!parametro.contains("sulCatarinense")){
                valorSulCatarinense.setVisibility(View.GONE);
            }else{
                if(insumo.getValorSulCatarinense().equals("...")){
                    valorSulCatarinense.setTextColor(Color.RED);
                    valorSulCatarinense.setVisibility(View.GONE);
                    sulCatarinense=true;
                }
            }

            if(!parametro.contains("saoMiguelO")){
                valorSaoMiguelOeste.setVisibility(View.GONE);
            }else{
                if(insumo.getValorSaoMiguelO().equals("...")){
                    valorSaoMiguelOeste.setTextColor(Color.RED);
                    valorSaoMiguelOeste.setVisibility(View.GONE);
                    saoMiguelOeste=true;
                }
            }


            if(!lages && !canoinhas && !jaragua && !joacaba &&
                    !rioSul && !saoMiguelOeste && !chapeco && !sulCatarinense)vermelhos.setVisibility(View.GONE);
            else {
                vermelhos.setTextColor(Color.RED);
                String texto = "";

                if(lages)texto="Lages, ";

                if(canoinhas)texto=texto+"Canoinhas, ";


                if(chapeco)texto=texto+"Chapecó, ";


                if(jaragua)texto=texto+"Jaraguá, ";


                if(joacaba)texto=texto+"Joaçaba, ";


                if(rioSul)texto=texto+"Rio do Sul, ";


                if(sulCatarinense)texto=texto+"Sul Catarinense, ";

                if(saoMiguelOeste)texto=texto+"São Miguel";

                vermelhos.setText(texto);
            }



        }

        return view;
    }

}
