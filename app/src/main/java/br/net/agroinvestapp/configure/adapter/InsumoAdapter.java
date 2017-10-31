package br.net.agroinvestapp.configure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.model.Insumo;

import java.util.ArrayList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(insumos!=null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_insumo,parent,false);


            final CheckedTextView descricao = (CheckedTextView) view.findViewById(R.id.tv_descricao);




          Insumo insumo = insumos.get(position);
            descricao.setText(insumo.getDescricao());


//            descricao.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(descricao.isChecked())descricao.setChecked(false);
//                    else {
//                        descricao.setChecked(true);
//                        Toast.makeText(getContext(),insumo.getDescricao()+" vim do adapter",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

        }

        return view;
    }
}
