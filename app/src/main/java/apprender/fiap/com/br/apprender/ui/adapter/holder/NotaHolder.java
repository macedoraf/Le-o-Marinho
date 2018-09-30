package apprender.fiap.com.br.apprender.ui.adapter.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import apprender.fiap.com.br.apprender.R;
import apprender.fiap.com.br.apprender.model.Nota;
import apprender.fiap.com.br.apprender.ui.adapter.NotaAdapter;

public class NotaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Nota nota;
    private TextView lblTitulo;
    private TextView lblSubTitulo;
    private CardView cardView;
    private NotaAdapter.OnClickListenerNota listenerNota;

    public NotaHolder(View itemView) {
        super(itemView);
        lblTitulo = itemView.findViewById(R.id.lblTitulo);
        cardView = itemView.findViewById(R.id.cardView);
        lblSubTitulo = itemView.findViewById(R.id.lblSubTitulo);
        cardView.setOnClickListener(this);

    }

    public void setOnClick(NotaAdapter.OnClickListenerNota listenerNota ){
            this.listenerNota = listenerNota;
    }

    public void preencherView(Nota nota){
        lblTitulo.setText(nota.getTitulo());
        this.nota = nota;
        lblSubTitulo.setText(nota.getTexto());
//        lblDataCriacao.setText(nota.getDataCriacao());
    }


    @Override
    public void onClick(View v) {
        if(v == cardView){
            listenerNota.onClickNota(nota);
        }
    }
}
