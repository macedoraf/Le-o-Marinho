package apprender.fiap.com.br.apprender.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import apprender.fiap.com.br.apprender.R;
import apprender.fiap.com.br.apprender.model.Nota;
import apprender.fiap.com.br.apprender.ui.adapter.holder.NotaHolder;

public class NotaAdapter extends RecyclerView.Adapter<NotaHolder> {

    private final List<Nota> listaDeNotas;
    private OnClickListenerNota listener;

    public NotaAdapter(List<Nota> listaDeNotas) {
        this.listaDeNotas = listaDeNotas;
    }


    /**
     *
     * @param parent
     * @param viewType
     * @return Retorna a o holder que vai preencher as informações na celular da nota
     */
    @NonNull
    @Override
    public NotaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotaHolder holder, int position) {
        holder.preencherView(listaDeNotas.get(position));
        holder.setOnClick(listener);


    }

    public void setOnClickListener(OnClickListenerNota listener){
        this.listener = listener;

    }

    @Override
    public int getItemCount() {
        return listaDeNotas.size();
    }

    public interface OnClickListenerNota{
        void onClickNota(Nota nota);
        void onSwipeNota(Nota nota);
    }


}
