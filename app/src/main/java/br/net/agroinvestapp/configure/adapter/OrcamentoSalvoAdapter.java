package br.net.agroinvestapp.configure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.model.Orcamento;

import java.util.Collection;
import java.util.List;

public class OrcamentoSalvoAdapter extends ArrayAdapter<Orcamento> {

    private Context context;
    private List<Orcamento> orcamentos;



    public OrcamentoSalvoAdapter(Context c, List<Orcamento> objects) {
        super(c, 0, objects);
        this.context=c;
        this.orcamentos = objects;
    }


    @Override
    public void addAll(Collection<? extends Orcamento> collection) {
//        super.addAll(collection);
        this.orcamentos = (List<Orcamento>) collection;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View view = null;

            if(orcamentos!=null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.modelo_lista_orcamento_salvo, parent, false);

                TextView orcamentoSalvo = (TextView) view.findViewById(R.id.tv_orca_salvo);

                orcamentoSalvo.setText(orcamentos.get(position).getDescricao()+"   "+orcamentos.get(position).getData());
            }


        return view;
    }

    @Override
    public int getCount() {
        return this.orcamentos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() { return this.orcamentos.size(); }

}
