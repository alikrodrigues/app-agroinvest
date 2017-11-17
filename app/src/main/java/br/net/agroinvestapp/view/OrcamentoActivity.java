package br.net.agroinvestapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import br.com.rafael.jpdroid.core.Jpdroid;
import br.com.rafael.jpdroid.exceptions.JpdroidException;
import br.net.agroinvestapp.R;
import br.net.agroinvestapp.configure.JpdroidSQL.ConfiguracaoBanco;
import br.net.agroinvestapp.configure.adapter.OrcamentoAdapter;
import br.net.agroinvestapp.configure.restClient.EnviaEmailHttpRequest;
import br.net.agroinvestapp.model.Insumo;
import br.net.agroinvestapp.model.Orcamento;
import br.net.agroinvestapp.view.dialog.Dialog_cidadesActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrcamentoActivity extends AppCompatActivity {

    private List<Insumo> insumos;

    private TextView legenda;
    private Button btnFinalizar;
    private Button btnSaveSend;
    private ListView listView;
    private String colunasPrecos="";
    private OrcamentoAdapter adapter;
    public String parametro;
    public String m_Text = "";
    private String email="";
    public boolean valida = false;
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

    private boolean validaCheckedTV=false;
    private long id;

    //colunas
    private EditText c1;
    private EditText c2;
    private EditText c3;
    private EditText c4;
    private EditText c5;
    private EditText c6;
    private EditText c7;
    private EditText c8;

    //valores
    private EditText v1;
    private EditText v2;
    private EditText v3;
    private EditText v4;
    private EditText v5;
    private EditText v6;
    private EditText v7;
    private EditText v8;


    // somas
    private String valoresString="";
    private double somaLages = 0;
    private double somaCanoinhas = 0;
    private double somaRiosul = 0;
    private double somaSaoMiguel = 0;
    private double somaSulcata = 0;
    private double somaChapeco = 0;
    private double somaJoacaba = 0;
    private double somaJaragua = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orcamento);

        listView = (ListView) findViewById(R.id.listOrca);
        legenda =(TextView) findViewById(R.id.legenda);
        btnFinalizar=(Button) findViewById(R.id.btnFinalizar);
        btnSaveSend = (Button) findViewById(R.id.btnSaveSend);

        c1= (EditText) findViewById(R.id.c1);
        c2= (EditText) findViewById(R.id.c2);
        c3= (EditText) findViewById(R.id.c3);
        c4= (EditText) findViewById(R.id.c4);
        c5= (EditText) findViewById(R.id.c5);
        c6= (EditText) findViewById(R.id.c6);
        c7= (EditText) findViewById(R.id.c7);
        c8= (EditText) findViewById(R.id.c8);

        v1= (EditText) findViewById(R.id.v1);
        v2= (EditText) findViewById(R.id.v2);
        v3= (EditText) findViewById(R.id.v3);
        v4= (EditText) findViewById(R.id.v4);
        v5= (EditText) findViewById(R.id.v5);
        v6= (EditText) findViewById(R.id.v6);
        v7= (EditText) findViewById(R.id.v7);
        v8= (EditText) findViewById(R.id.v8);

        insumos = new ArrayList<>();


        Intent intent = getIntent();
        parametro = intent.getStringExtra("parametro");
        if(parametro!=null)
        insumos =(List<Insumo>) intent.getBundleExtra("bundle").getSerializable("insumos");

        id = intent.getLongExtra("id",0);
        if(id==(long)0){
            btnSaveSend.setText("Salvar");
            btnFinalizar.setText("Finalizar");
        }
        else {
            btnFinalizar.setText("Editar");
            btnSaveSend.setText("Enviar email");
            if(parametro==null){
                Jpdroid bancoJpdroid = ConfiguracaoBanco.getBancodeDados(OrcamentoActivity.this);
                Orcamento orcamento =(Orcamento) bancoJpdroid.retrieve(Orcamento.class,"_id = "+id,true).get(0);
                parametro = orcamento.getParametro();
                if(insumos.size()<1){
                    insumos = orcamento.getInsumos();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnSaveSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarOrcamento();
            }
        });
        montaResumo(parametro);
        desativaEdtText();
        adapter = new OrcamentoAdapter(this,insumos,parametro);
        listView.setAdapter(adapter);
        if(!validaCheckedTV)legenda.setVisibility(View.GONE);
    }

    /*Metodo responsavel por desativar os edit text para não interação do usuario*/
    private void desativaEdtText(){
        c1.setEnabled(false);
        c2.setEnabled(false);
        c3.setEnabled(false);
        c4.setEnabled(false);
        c5.setEnabled(false);
        c6.setEnabled(false);
        c7.setEnabled(false);
        c8.setEnabled(false);

        v1.setEnabled(false);
        v2.setEnabled(false);
        v3.setEnabled(false);
        v4.setEnabled(false);
        v5.setEnabled(false);
        v6.setEnabled(false);
        v7.setEnabled(false);
        v8.setEnabled(false);
    }

    /*Metodo Responsavel por receber o parametro e montar um resumo com os valores e as colunas das regiões selecionadas*/
    private void montaResumo(String parametro) {
        if (parametro.contains("lages")) {
            colunasPrecos = "Lages,";
            for (Insumo insumo : insumos) {
                if (insumo.getValorLages().contains("..")) {
                    validaCheckedTV = true;
                    somaLages += new Double(0);
                }else
                    somaLages = somaLages + Double.parseDouble(insumo.getValorLages().replace(",", ".").replace(" ", ""));
            }
            valoresString = nf.format(somaLages);
        }
        if (parametro.contains("canoinhas")) {
            colunasPrecos = colunasPrecos + "Canoinhas,";
            for (Insumo insumo : insumos) {
                if (insumo.getValorCanoinhas().contains("..")){
                    validaCheckedTV = true;
                    somaCanoinhas += new Double(0);
            }else
                    somaCanoinhas = somaCanoinhas + Double.parseDouble(insumo.getValorCanoinhas().replace(",", ".").replace(" ", ""));
            }
            valoresString = valoresString+"    "+ nf.format(somaCanoinhas);
        }

        if (parametro.contains("chapeco")) {
            colunasPrecos = colunasPrecos + "Chapecó,";
            for (Insumo insumo : insumos) {
                if (insumo.getValorChapeco().contains("..")){
                    validaCheckedTV=true;
                    somaChapeco += new Double(0);
                }
                else
                    somaChapeco = somaChapeco + Double.parseDouble(insumo.getValorChapeco().replace(",", ".").replace(" ", ""));
            }
            valoresString = valoresString+"        "+ nf.format(somaChapeco);
        }

        if (parametro.contains("jaragua")){
            colunasPrecos = colunasPrecos + "Jaragua,";
            for(Insumo insumo : insumos){
                if(insumo.getValorJaragua().contains("..")){
                    validaCheckedTV=true;
                    somaJaragua+= new Double(0);
                }
                else somaJaragua =somaJaragua + Double.parseDouble(insumo.getValorJaragua().replace(",",".").replace(" ",""));
            }
            valoresString = valoresString+"      "+ nf.format(somaJaragua);
        }
        if(parametro.contains("joacaba")){
            colunasPrecos =colunasPrecos + "Joacaba,";
            for(Insumo insumo : insumos){
                if(insumo.getValorJoacaba().contains("..")){
                    validaCheckedTV=true;
                    somaJoacaba+= new Double(0);
                }
                else somaJoacaba =somaJoacaba + Double.parseDouble(insumo.getValorJoacaba().replace(",",".").replace(" ",""));
            }
            valoresString = valoresString+"      "+ nf.format(somaJoacaba);
        }

        if(parametro.contains("riodoSul")){
            colunasPrecos =colunasPrecos + "Rio do Sul,";
            for(Insumo insumo : insumos){
                if(insumo.getValorRioSul().contains("..")){
                    validaCheckedTV=true;
                    somaRiosul+= new Double(0);
                }
                else somaRiosul =somaRiosul + Double.parseDouble(insumo.getValorRioSul().replace(",",".").replace(" ",""));
            }
            valoresString = valoresString+"      "+ nf.format(somaRiosul);
        }

        if(parametro.contains("sulCatarinense")){
            colunasPrecos =colunasPrecos + "Sul,";
            for(Insumo insumo : insumos){
                if(insumo.getValorSulCatarinense().contains("..")) {
                    validaCheckedTV=true;
                    somaSulcata+= new Double(0);
                }
                else somaSulcata =somaSulcata + Double.parseDouble(insumo.getValorSulCatarinense().replace(",",".").replace(" ",""));
            }
            valoresString = valoresString+"      "+ nf.format(somaSulcata);
        }

        if(parametro.contains("saoMiguelO")){
            colunasPrecos =colunasPrecos + "Sao Miguel,";
            for(Insumo insumo : insumos){
                if(insumo.getValorSaoMiguelO().contains("..")) {
                    validaCheckedTV=true;
                    somaSaoMiguel+= new Double(0);
                }
                else somaSaoMiguel =somaSaoMiguel + Double.parseDouble(insumo.getValorSaoMiguelO().replace(",",".").replace(" ",""));
            }
            valoresString = valoresString+"          "+ nf.format(somaSaoMiguel);
        }

        String[] vetorColuna = colunasPrecos.split(",");

        montaColuna(vetorColuna);

    }

    /*Atraves do Array de String criado com o montaResumo, agora montamos a coluna e preços em seus devidos EditText*/
    private void montaColuna(String[] vetorColuna){


        switch (vetorColuna.length){
            case 1:
                c1.setText(vetorColuna[0]);
                v1.setText(nf.format(somaLages));
                break;

            case 2:
                c1.setText(vetorColuna[0]);
                c2.setText(vetorColuna[1]);

                v1.setText(nf.format(somaLages));
                v2.setText(nf.format(somaCanoinhas));
                break;

            case 3:
                c1.setText(vetorColuna[0]);
                c2.setText(vetorColuna[1]);
                c3.setText(vetorColuna[2]);

                v1.setText(nf.format(somaLages));
                v2.setText(nf.format(somaCanoinhas));
                v3.setText(nf.format(somaChapeco));
                break;

            case 4:
                c1.setText(vetorColuna[0]);
                c2.setText(vetorColuna[1]);
                c3.setText(vetorColuna[2]);
                c4.setText(vetorColuna[3]);

                v1.setText(nf.format(somaLages));
                v2.setText(nf.format(somaCanoinhas));
                v3.setText(nf.format(somaChapeco));
                v4.setText(nf.format(somaJaragua));
                break;

            case 5:
                c1.setText(vetorColuna[0]);
                c2.setText(vetorColuna[1]);
                c3.setText(vetorColuna[2]);
                c4.setText(vetorColuna[3]);
                c5.setText(vetorColuna[4]);


                v1.setText(nf.format(somaLages));
                v2.setText(nf.format(somaCanoinhas));
                v3.setText(nf.format(somaChapeco));
                v4.setText(nf.format(somaJaragua));
                v5.setText(nf.format(somaJoacaba));
                break;

            case 6:
                c1.setText(vetorColuna[0]);
                c2.setText(vetorColuna[1]);
                c3.setText(vetorColuna[2]);
                c4.setText(vetorColuna[3]);
                c5.setText(vetorColuna[4]);
                c6.setText(vetorColuna[5]);

                v1.setText(nf.format(somaLages));
                v2.setText(nf.format(somaCanoinhas));
                v3.setText(nf.format(somaChapeco));
                v4.setText(nf.format(somaJaragua));
                v5.setText(nf.format(somaJoacaba));
                v6.setText(nf.format(somaRiosul));
                break;

            case 7:
                c1.setText(vetorColuna[0]);
                c2.setText(vetorColuna[1]);
                c3.setText(vetorColuna[2]);
                c4.setText(vetorColuna[3]);
                c5.setText(vetorColuna[4]);
                c6.setText(vetorColuna[5]);
                c7.setText(vetorColuna[6]);

                v1.setText(nf.format(somaLages));
                v2.setText(nf.format(somaCanoinhas));
                v3.setText(nf.format(somaChapeco));
                v4.setText(nf.format(somaJaragua));
                v5.setText(nf.format(somaJoacaba));
                v6.setText(nf.format(somaRiosul));
                v7.setText(nf.format(somaSulcata));
                break;

            case 8:
                c1.setText(vetorColuna[0]);
                c2.setText(vetorColuna[1]);
                c3.setText(vetorColuna[2]);
                c4.setText(vetorColuna[3]);
                c5.setText(vetorColuna[4]);
                c6.setText(vetorColuna[5]);
                c7.setText(vetorColuna[6]);
                c8.setText(vetorColuna[7]);

                v1.setText(nf.format(somaLages));
                v2.setText(nf.format(somaCanoinhas));
                v3.setText(nf.format(somaChapeco));
                v4.setText(nf.format(somaJaragua));
                v5.setText(nf.format(somaJoacaba));
                v6.setText(nf.format(somaRiosul));
                v7.setText(nf.format(somaSulcata));
                v8.setText(nf.format(somaSaoMiguel));
                break;



        }
    }

    /*Metodo de salvar Orçamento internamente*/
    public void salvarOrcamento(){
        if(id==0){

                dialogDescri("Digite uma descrição:");


        }else{
            new AlertDialog.Builder(this)
                    .setTitle("AGROINVEST")
                    .setMessage("Deseja enviar relatorio do orçamento por email ?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialogEmail();

                        }
                    }).setNegativeButton("Não",null)
                    .show();
        }


    }

    /*Dialog de inserir descrição para salvar*/
    private void dialogDescri(String titulo){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                Jpdroid bancoLocal = ConfiguracaoBanco.getBancodeDados(OrcamentoActivity.this);
                       final Orcamento orcamento = new Orcamento();
                        orcamento.setDescricao(input.getText().toString());

                        orcamento.setData(new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())));
                        orcamento.setConcluido(0);
                        orcamento.setParametro(parametro);
                        orcamento.setInsumos(insumos);
                        try {
                            bancoLocal.persist(orcamento);
                            Toast.makeText(OrcamentoActivity.this,"Salvo com sucesso !",Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (JpdroidException e) {
                            Toast.makeText(OrcamentoActivity.this,"Erro ao salvar !",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    /*Dialog de inserir email para envio de relatorio*/
    private void dialogEmail(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insira seu e-mail");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                email = input.getText().toString();
                if(email.contains("@")&&email.contains(".")){

                    Orcamento orcamento = ConfiguracaoBanco.getBancodeDados(OrcamentoActivity.this).retrieve(Orcamento.class,"_id = "+id,true).get(0);
                    new EnviaEmailHttpRequest(OrcamentoActivity.this,email,orcamento).execute();
                    finish();
                }else{
                Toast.makeText(OrcamentoActivity.this, "Email fora dos padrões, verfique !",0).show();
                }

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    /*Ação do Finalizar/Editar */
    public void actionFinalizar(View view){
        if(id!=0) {
            Intent intenti = new Intent(OrcamentoActivity.this, Dialog_cidadesActivity.class);
            intenti.putExtra("parametro", parametro);
            intenti.putExtra("id",id);
            startActivity(intenti);
            finish();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Sair sem salvar ?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OrcamentoActivity.this.finish();
                        }
                    }).setNegativeButton("Não",null)
                    .show();

        }
        }

}
