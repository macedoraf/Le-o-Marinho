package apprender.fiap.com.br.apprender.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import apprender.fiap.com.br.apprender.R;
import apprender.fiap.com.br.apprender.model.Nota;
import apprender.fiap.com.br.apprender.presenter.NotaPresenter;
import apprender.fiap.com.br.apprender.presenter.NotaPresenterImpl;
import apprender.fiap.com.br.apprender.ui.activity.MainActivity;
import apprender.fiap.com.br.apprender.ui.adapter.NotaAdapter;

public class ListaDeNotasFragment extends Fragment implements NotaAdapter.OnClickListenerNota {

    private List<Nota> listaDeNotas;
    private MainActivity mActivity;
    private RecyclerView recyclerViewNotas;
    private NotaPresenter notaPresenter;
    private NotaAdapter notaAdapter;
    private SearchView searchView;
    private String query = "";
    private TextView lblNotFound;

    /**
     *
     * @param savedInstanceState
     * Inicializando a lista de notas
     * Recuperando o objeto da activity principal para fazer a transição de telas por delegate
     *
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaDeNotas = new ArrayList<>();
        notaPresenter = new NotaPresenterImpl();
        mActivity = (MainActivity) getActivity();
        configuraBarraDeFerramentas();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lista_notas, container, false);
        inicializaComponetes(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configuraRecyclerView();
        configuraAdapter();
        carregaNotasDoBanco();

    }

    /**
     * Deleta a nota selecionada pela posicao
     */
    private void deletaNota(int posicao){
        notaPresenter.deletar(listaDeNotas.get(posicao));
    }

    private void configuraAdapter() {
        notaAdapter = new NotaAdapter(listaDeNotas);
        notaAdapter.setOnClickListener(this);
        recyclerViewNotas.setAdapter(notaAdapter);
    }

    private void configuraRecyclerView() {
        recyclerViewNotas.setLayoutManager(new LinearLayoutManager(mActivity));
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int macacoes = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
                return makeMovementFlags(0, macacoes);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                deletaNota(position);
                listaDeNotas.remove(position);
                notaAdapter.notifyItemRemoved(position);
                validaNotFound();


            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotas);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lista_nota, menu);
        searchView = (SearchView) menu.findItem(R.id.action_seach).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query = newText;
                carregaNotasDoBanco();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void configuraBarraDeFerramentas() {
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add_nota){
            mActivity.carregaViewDeAdicionarNotas(null);
        }

        return super.onOptionsItemSelected(item);
    }

    private void inicializaComponetes(View view){
        recyclerViewNotas = view.findViewById(R.id.recyclerViewNotas);
        lblNotFound = view.findViewById(R.id.lblNotFound);
    }


    private void validaNotFound(){
        if(listaDeNotas.isEmpty())
            lblNotFound.setVisibility(View.VISIBLE);
        else
            lblNotFound.setVisibility(View.GONE);
    }

    /**
     * Carrega os dados do banco de forma Asyncrona e atualiza a lista em caso de sucesso.
     *
     * PS: Fiz em classe anonima por que tive preguiça, se quiser que eu arrumo me avisa.
     */
    private void carregaNotasDoBanco(){

        new AsyncTask<Void,Void,Boolean>(){
            private String menssagem;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                try{
                    listaDeNotas.clear();
                    listaDeNotas.addAll(notaPresenter.selecionarTodos(query));
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
                    notaAdapter.notifyDataSetChanged();
                    validaNotFound();
                }else{
                    Toast.makeText(mActivity, menssagem, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();


    }

    @Override
    public void onClickNota(Nota nota) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("nota",nota);
        mActivity.carregaViewDeAdicionarNotas(bundle);

    }

    @Override
    public void onSwipeNota(Nota nota) {

    }
}
