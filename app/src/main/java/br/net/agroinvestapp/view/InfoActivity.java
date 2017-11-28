package br.net.agroinvestapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import br.net.agroinvestapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {

    @BindView(R.id.textoInfoId)
    TextView textoInfo;
    @BindView(R.id.txtLinkEpagri)
    TextView linkEpagri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textoInfo.setText(" Com o objetivo de auxiliar os produtores Agricolas em uma activity_pesquisa " +
                "de preços tabelados disponibilizados pela EPAGRI, Preços mensal de produtos agrícolas, segundo as principais praças" +
                " de Santa Catarina.");
        linkEpagri.setText("http://www.epagri.sc.gov.br/");
        linkEpagri.setTextColor(Color.parseColor("#668CB7F7"));
        linkEpagri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this,WebActivity.class);
                intent.putExtra("epagri","epagri");
                startActivity(intent);
                finish();

            }
        });

    }
}
