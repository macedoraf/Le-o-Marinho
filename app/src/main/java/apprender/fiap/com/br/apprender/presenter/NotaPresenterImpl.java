package apprender.fiap.com.br.apprender.presenter;

import java.util.List;

import apprender.fiap.com.br.apprender.model.Nota;
import apprender.fiap.com.br.apprender.ui.ApprenderAplication;

public class NotaPresenterImpl implements NotaPresenter {

    @Override
    public void inserir(Nota nota) {
        ApprenderAplication.getInstancia().getMeuBancoDeDados().notaDao().insere(nota);
    }

    @Override
    public List<Nota> selecionarTodos(String query) {
        if(query.equals("")){
            return ApprenderAplication.getInstancia().getMeuBancoDeDados().notaDao().selecionaTodos();
        }else{
            return ApprenderAplication.getInstancia().getMeuBancoDeDados().notaDao().selecionaTodos(query);
        }

    }

    @Override
    public void atualizar(Nota nota) {
        ApprenderAplication.getInstancia().getMeuBancoDeDados().notaDao().atualiza(nota);
    }

    @Override
    public void deletar(final Nota nota) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApprenderAplication.instancia.getMeuBancoDeDados().notaDao().deleta(nota);
            }
        }).start();

    }
}
