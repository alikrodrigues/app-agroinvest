package br.net.agroinvestapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import br.net.agroinvestapp.R;

public class InfoActivity extends AppCompatActivity {

    private TextView textoInfo;
    private TextView linkEpagri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        textoInfo = (TextView) findViewById(R.id.textoInfoId);
        linkEpagri = (TextView) findViewById(R.id.txtLinkEpagri);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textoInfo.setText(" Com o objetivo de auxiliar os produtores Agricolas em uma activity_pesquisa " +
                "de preços tabelados disponibilizados pela EPAGRI, Preços mensal de produtos agrícolas, segundo as principais praças" +
                " de Santa Catarina.");
        linkEpagri.setText("http://www.epagri.sc.gov.br/?page_id=2711");
    }
}
