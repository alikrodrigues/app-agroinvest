package br.net.agroinvestapp.configure.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.model.Insumo;

import java.util.Collection;
import java.util.List;

public class InsumoAdapter extends ArrayAdapter<Insumo> {

    private List<Insumo> insumos;
    private Context context;


    public InsumoAdapter(Context c, List<Insumo> objects) {
        super(c, 0, objects);
        this.context=c;
        this.insumos = objects;
    }


    @Override
    public void addAll(Collection<? extends Insumo> collection) {
//       this.activity_insumos.clear();
       this.insumos = (List<Insumo>) collection;
//       super.addAll(this.activity_insumos);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(insumos!=null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.modelo_lista_insumo,parent,false);


            final CheckedTextView descricao = (CheckedTextView) view.findViewById(R.id.tv_descricao);




          Insumo insumo = insumos.get(position);
            descricao.setText(insumo.getDescricao());
            if(insumo.getChecado()==1)descricao.setChecked(true);




        }

        return view;
    }


//    @Override
//    public int getCount() {
//        return activity_insumos.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return this.activity_insumos.size();
//    }




}
