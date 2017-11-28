package br.net.agroinvestapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import br.net.agroinvestapp.configure.preferences.PreferenciaClass;
import br.net.agroinvestapp.configure.restClient.EnviaEmailHttpRequest;
import br.net.agroinvestapp.model.Insumo;
import br.net.agroinvestapp.model.Orcamento;
import br.net.agroinvestapp.model.Preferencias;
import br.net.agroinvestapp.view.dialog.Dialog_cidadesActivity;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrcamentoActivity extends AppCompatActivity {

    private List<Insumo> insumos;

    @BindView(R.id.legendaLaranj)
    TextView legendaLaranj;
    @BindView(R.id.legenda)
    TextView legenda;

    @BindView(R.id.btnFinalizar)
    Button btnFinalizar;
    @BindView(R.id.btnSaveSend)
    Button btnSaveSend;
    @BindView(R.id.listOrca)
    ListView listView;
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
    @BindViews({R.id.c1,R.id.c2,R.id.c3,R.id.c4,R.id.c5,R.id.c6,R.id.c7,R.id.c8})
    List<EditText> coluna;
    //valores
    @BindViews({R.id.v1,R.id.v2,R.id.v3,R.id.v4,R.id.v5,R.id.v6,R.id.v7,R.id.v8})
    List<EditText> valores;
    //colunaLaranja
    private boolean lagesLaranja = false;
    private boolean canoinhasLaranja = false;
    private boolean chapecoLaranja = false;
    private boolean joacabaLaranja = false;
    private boolean jaraguaLaranja = false;
    private boolean rioSulLaranja = false;
    private boolean sulCatarinenseLaranja = false;
    private boolean saoMiguelOLaranja = false;


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

        ButterKnife.bind(this);

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
            PreferenciaClass preferenciaClass = new PreferenciaClass(this);
            Preferencias preferencias = preferenciaClass.getPreferencias();
            if(preferencias!=null&&preferencias.getEmailPreferido()!=null){
                m_Text=preferencias.getEmailPreferido();
            }
            if(parametro==null){
                Jpdroid bancoJpdroid = ConfiguracaoBanco.getBancodeDados(OrcamentoActivity.this);
                Orcamento orcamento =(Orcamento) bancoJpdroid.retrieve(Orcamento.class,"_id = "+id,true).get(0);
                parametro = orcamento.getParametro();
                Log.i("DEBUG",orcamento.getInsumos().get(0).getDescricao());
                if(insumos.size()<1){
                    Log.i("DEBUG","teste");
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
        legendaLaranj.setTextColor(Color.parseColor("#F1A629"));
        if(!validaCheckedTV)legenda.setVisibility(View.GONE);
        if((!lagesLaranja&&!joacabaLaranja&&!jaraguaLaranja&&!saoMiguelOLaranja&&
                !sulCatarinenseLaranja&&!rioSulLaranja&&!chapecoLaranja&&!canoinhasLaranja)
                )legendaLaranj.setVisibility(View.GONE);
    }

    /*Metodo responsavel por desativar os edit text para não interação do usuario*/
    private void desativaEdtText(){

        ButterKnife.apply(coluna,DISABLE);
        ButterKnife.apply(valores,DISABLE);

    }

    /*Metodo Responsavel por receber o parametro e montar um resumo com os valores e as colunas das regiões selecionadas*/
    private void montaResumo(String parametro) {
        if (parametro.contains("lages")) {
            colunasPrecos = "Lages,";
            for (Insumo insumo : insumos) {
                if (insumo.getValorLages().contains("..")) {
                    validaCheckedTV = true;
                    lagesLaranja = true;
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
                    canoinhasLaranja=true;
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
                    chapecoLaranja=true;
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
                    jaraguaLaranja=true;
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
                    joacabaLaranja=true;
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
                    rioSulLaranja=true;
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
                    sulCatarinenseLaranja=true;
                    somaSulcata+= new Double(0);
                }
                else somaSulcata =somaSulcata + Double.parseDouble(insumo.getValorSulCatarinense().replace(",",".").replace(" ",""));
            }
            valoresString = valoresString+"      "+ nf.format(somaSulcata);
        }

        if(parametro.contains("saoMiguelO")){
            colunasPrecos =colunasPrecos + "São Miguel,";
            for(Insumo insumo : insumos){
                if(insumo.getValorSaoMiguelO().contains("..")) {
                    validaCheckedTV=true;
                    saoMiguelOLaranja = true;
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
                coluna.get(0).setText(vetorColuna[0]);
                valores.get(0).setText(nf.format(somaLages));
                if(lagesLaranja)coluna.get(0).setTextColor(Color.parseColor("#F1A629"));
                if(somaLages<=0)coluna.get(0).setTextColor(Color.RED);
                break;

            case 2:
                coluna.get(0).setText(vetorColuna[0]);
                coluna.get(1).setText(vetorColuna[1]);

                valores.get(0).setText(nf.format(somaLages));
                valores.get(1).setText(nf.format(somaCanoinhas));
                if(lagesLaranja)coluna.get(0).setTextColor(Color.parseColor("#F1A629"));
                if(canoinhasLaranja)coluna.get(1).setTextColor(Color.parseColor("#F1A629"));

                if(somaLages<=0)coluna.get(0).setTextColor(Color.RED);
                if(somaCanoinhas<=0)coluna.get(1).setTextColor(Color.RED);
                break;

            case 3:
                coluna.get(0).setText(vetorColuna[0]);
                coluna.get(1).setText(vetorColuna[1]);
                coluna.get(2).setText(vetorColuna[2]);

                valores.get(0).setText(nf.format(somaLages));
                valores.get(1).setText(nf.format(somaCanoinhas));
                valores.get(2).setText(nf.format(somaChapeco));
                if(lagesLaranja)coluna.get(0).setTextColor(Color.parseColor("#F1A629"));
                if(canoinhasLaranja)coluna.get(1).setTextColor(Color.parseColor("#F1A629"));
                if(chapecoLaranja)coluna.get(2).setTextColor(Color.parseColor("#F1A629"));

                if(somaLages<=0)coluna.get(0).setTextColor(Color.RED);
                if(somaCanoinhas<=0)coluna.get(1).setTextColor(Color.RED);
                if(somaChapeco<=0)coluna.get(2).setTextColor(Color.RED);

                break;

            case 4:
                coluna.get(0).setText(vetorColuna[0]);
                coluna.get(1).setText(vetorColuna[1]);
                coluna.get(2).setText(vetorColuna[2]);
                coluna.get(3).setText(vetorColuna[3]);

                valores.get(0).setText(nf.format(somaLages));
                valores.get(1).setText(nf.format(somaCanoinhas));
                valores.get(2).setText(nf.format(somaChapeco));
                valores.get(3).setText(nf.format(somaJaragua));
                if(lagesLaranja)coluna.get(0).setTextColor(Color.parseColor("#F1A629"));
                if(canoinhasLaranja)coluna.get(1).setTextColor(Color.parseColor("#F1A629"));
                if(chapecoLaranja)coluna.get(2).setTextColor(Color.parseColor("#F1A629"));
                if(jaraguaLaranja)coluna.get(3).setTextColor(Color.parseColor("#F1A629"));

                if(somaLages<=0)coluna.get(0).setTextColor(Color.RED);
                if(somaCanoinhas<=0)coluna.get(1).setTextColor(Color.RED);
                if(somaChapeco<=0)coluna.get(2).setTextColor(Color.RED);
                if(somaJaragua<=0)coluna.get(3).setTextColor(Color.RED);
                break;

            case 5:
                coluna.get(0).setText(vetorColuna[0]);
                coluna.get(1).setText(vetorColuna[1]);
                coluna.get(2).setText(vetorColuna[2]);
                coluna.get(3).setText(vetorColuna[3]);
                coluna.get(4).setText(vetorColuna[4]);


                valores.get(0).setText(nf.format(somaLages));
                valores.get(1).setText(nf.format(somaCanoinhas));
                valores.get(2).setText(nf.format(somaChapeco));
                valores.get(3).setText(nf.format(somaJaragua));
                valores.get(4).setText(nf.format(somaJoacaba));
                if(lagesLaranja)coluna.get(0).setTextColor(Color.parseColor("#F1A629"));
                if(canoinhasLaranja)coluna.get(1).setTextColor(Color.parseColor("#F1A629"));
                if(chapecoLaranja)coluna.get(2).setTextColor(Color.parseColor("#F1A629"));
                if(jaraguaLaranja)coluna.get(3).setTextColor(Color.parseColor("#F1A629"));
                if(joacabaLaranja)coluna.get(4).setTextColor(Color.parseColor("#F1A629"));

                if(somaLages<=0)coluna.get(0).setTextColor(Color.RED);
                if(somaCanoinhas<=0)coluna.get(1).setTextColor(Color.RED);
                if(somaChapeco<=0)coluna.get(2).setTextColor(Color.RED);
                if(somaJaragua<=0)coluna.get(3).setTextColor(Color.RED);
                if(somaJoacaba<=0)coluna.get(4).setTextColor(Color.RED);
                break;

            case 6:
                coluna.get(0).setText(vetorColuna[0]);
                coluna.get(1).setText(vetorColuna[1]);
                coluna.get(2).setText(vetorColuna[2]);
                coluna.get(3).setText(vetorColuna[3]);
                coluna.get(4).setText(vetorColuna[4]);
                coluna.get(5).setText(vetorColuna[5]);

                valores.get(0).setText(nf.format(somaLages));
                valores.get(1).setText(nf.format(somaCanoinhas));
                valores.get(2).setText(nf.format(somaChapeco));
                valores.get(3).setText(nf.format(somaJaragua));
                valores.get(4).setText(nf.format(somaJoacaba));
                valores.get(5).setText(nf.format(somaRiosul));
                if(lagesLaranja)coluna.get(0).setTextColor(Color.parseColor("#F1A629"));
                if(canoinhasLaranja)coluna.get(1).setTextColor(Color.parseColor("#F1A629"));
                if(chapecoLaranja)coluna.get(2).setTextColor(Color.parseColor("#F1A629"));
                if(jaraguaLaranja)coluna.get(3).setTextColor(Color.parseColor("#F1A629"));
                if(joacabaLaranja)coluna.get(4).setTextColor(Color.parseColor("#F1A629"));
                if(rioSulLaranja)coluna.get(5).setTextColor(Color.parseColor("#F1A629"));

                if(somaLages<=0)coluna.get(0).setTextColor(Color.RED);
                if(somaCanoinhas<=0)coluna.get(1).setTextColor(Color.RED);
                if(somaChapeco<=0)coluna.get(2).setTextColor(Color.RED);
                if(somaJaragua<=0)coluna.get(3).setTextColor(Color.RED);
                if(somaJoacaba<=0)coluna.get(4).setTextColor(Color.RED);
                if(somaRiosul<=0)coluna.get(5).setTextColor(Color.RED);
                break;

            case 7:
                coluna.get(0).setText(vetorColuna[0]);
                coluna.get(1).setText(vetorColuna[1]);
                coluna.get(2).setText(vetorColuna[2]);
                coluna.get(3).setText(vetorColuna[3]);
                coluna.get(4).setText(vetorColuna[4]);
                coluna.get(5).setText(vetorColuna[5]);
                coluna.get(6).setText(vetorColuna[6]);

                valores.get(0).setText(nf.format(somaLages));
                valores.get(1).setText(nf.format(somaCanoinhas));
                valores.get(2).setText(nf.format(somaChapeco));
                valores.get(3).setText(nf.format(somaJaragua));
                valores.get(4).setText(nf.format(somaJoacaba));
                valores.get(5).setText(nf.format(somaRiosul));
                valores.get(6).setText(nf.format(somaSulcata));
                if(lagesLaranja)coluna.get(0).setTextColor(Color.parseColor("#F1A629"));
                if(canoinhasLaranja)coluna.get(1).setTextColor(Color.parseColor("#F1A629"));
                if(chapecoLaranja)coluna.get(2).setTextColor(Color.parseColor("#F1A629"));
                if(jaraguaLaranja)coluna.get(3).setTextColor(Color.parseColor("#F1A629"));
                if(joacabaLaranja)coluna.get(4).setTextColor(Color.parseColor("#F1A629"));
                if(rioSulLaranja)coluna.get(5).setTextColor(Color.parseColor("#F1A629"));
                if(sulCatarinenseLaranja)coluna.get(6).setTextColor(Color.parseColor("#F1A629"));


                if(somaLages<=0)coluna.get(0).setTextColor(Color.RED);
                if(somaCanoinhas<=0)coluna.get(1).setTextColor(Color.RED);
                if(somaChapeco<=0)coluna.get(2).setTextColor(Color.RED);
                if(somaJaragua<=0)coluna.get(3).setTextColor(Color.RED);
                if(somaJoacaba<=0)coluna.get(4).setTextColor(Color.RED);
                if(somaRiosul<=0)coluna.get(5).setTextColor(Color.RED);
                if(somaSulcata<=0)coluna.get(6).setTextColor(Color.RED);
                break;

            case 8:
                coluna.get(0).setText(vetorColuna[0]);
                coluna.get(1).setText(vetorColuna[1]);
                coluna.get(2).setText(vetorColuna[2]);
                coluna.get(3).setText(vetorColuna[3]);
                coluna.get(4).setText(vetorColuna[4]);
                coluna.get(5).setText(vetorColuna[5]);
                coluna.get(6).setText(vetorColuna[6]);
                coluna.get(7).setText(vetorColuna[7]);

                valores.get(0).setText(nf.format(somaLages));
                valores.get(1).setText(nf.format(somaCanoinhas));
                valores.get(2).setText(nf.format(somaChapeco));
                valores.get(3).setText(nf.format(somaJaragua));
                valores.get(4).setText(nf.format(somaJoacaba));
                valores.get(5).setText(nf.format(somaRiosul));
                valores.get(6).setText(nf.format(somaSulcata));
                valores.get(7).setText(nf.format(somaSaoMiguel));
                if(lagesLaranja)coluna.get(0).setTextColor(Color.parseColor("#F1A629"));
                if(canoinhasLaranja)coluna.get(1).setTextColor(Color.parseColor("#F1A629"));
                if(chapecoLaranja)coluna.get(2).setTextColor(Color.parseColor("#F1A629"));
                if(jaraguaLaranja)coluna.get(3).setTextColor(Color.parseColor("#F1A629"));
                if(joacabaLaranja)coluna.get(4).setTextColor(Color.parseColor("#F1A629"));
                if(rioSulLaranja)coluna.get(5).setTextColor(Color.parseColor("#F1A629"));
                if(sulCatarinenseLaranja)coluna.get(6).setTextColor(Color.parseColor("#F1A629"));
                if(saoMiguelOLaranja)coluna.get(7).setTextColor(Color.parseColor("#F1A629"));


                if(somaLages<=0)coluna.get(0).setTextColor(Color.RED);
                if(somaCanoinhas<=0)coluna.get(1).setTextColor(Color.RED);
                if(somaChapeco<=0)coluna.get(2).setTextColor(Color.RED);
                if(somaJaragua<=0)coluna.get(3).setTextColor(Color.RED);
                if(somaJoacaba<=0)coluna.get(4).setTextColor(Color.RED);
                if(somaRiosul<=0)coluna.get(5).setTextColor(Color.RED);
                if(somaSulcata<=0)coluna.get(6).setTextColor(Color.RED);
                if(somaSaoMiguel<=0)coluna.get(7).setTextColor(Color.RED);
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
        input.setText(m_Text);
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


    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };
}
