package apprender.fiap.com.br.apprender.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import apprender.fiap.com.br.apprender.R;
import apprender.fiap.com.br.apprender.model.Nota;
import apprender.fiap.com.br.apprender.presenter.NotaPresenter;
import apprender.fiap.com.br.apprender.presenter.NotaPresenterImpl;
import apprender.fiap.com.br.apprender.ui.activity.MainActivity;

public class AdicionarNotaFragment extends Fragment {

    private Nota nota;
    private EditText edtTexto;
    private EditText edtTitulo;
    private MainActivity mActivity;
    private NotaPresenter notaPresenter;


    /**
     * Aqui eu estou verificando se eu passei uma nota para essa tela antes dela ser chamada
     * caso eu tenha passado a nota os campos vão ser preenchidos de acordo com os valores do objeto
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        notaPresenter = new NotaPresenterImpl();

        if(getArguments() != null){
            final Bundle arguments = getArguments();
             nota = (Nota) arguments.getSerializable("nota");
        }else{
            nota = new Nota();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_formulario_nota, container, false);
        inicializaComponetes(view);
        return  view;
    }

    /**
     * Preenche as variaveis de componentes
     * @param view
     */
    private void inicializaComponetes(View view) {
        edtTexto = view.findViewById(R.id.edtTexto);
        edtTitulo = view.findViewById(R.id.edtTitulo);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configuraBarraDeFerramentas();
        preencheValores();
    }

    private void preencheValores() {
        if(nota != null){
            edtTitulo.setText(nota.getTitulo());
            edtTexto.setText(nota.getTexto());
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_salvar_nota){
            salvaNota();
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvaNota() {



        new AsyncTask<Void,Void,Boolean>(){

            private String menssagem;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected Boolean doInBackground(Void... voids) {

                try{
                    nota.setTexto(edtTexto.getText().toString());
                    nota.setTitulo(edtTitulo.getText().toString());

                    if(nota.getId() != 0)
                        notaPresenter.atualizar(nota);
                    else
                        notaPresenter.inserir(nota);

                    return true;

                }catch (Exception err){
                    menssagem = err.getMessage();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if(aBoolean){
                mActivity.onBackPressed();
                }else{
                    Toast.makeText(mActivity, menssagem, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_nota,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Exibe os botões na barra de ferramentas
     * EX: Salvar
     */
    private void configuraBarraDeFerramentas() {
        setHasOptionsMenu(true);
    }




}
