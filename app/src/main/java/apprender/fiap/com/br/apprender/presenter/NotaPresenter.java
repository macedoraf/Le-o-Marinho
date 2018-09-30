package apprender.fiap.com.br.apprender.presenter;

import java.util.List;

import apprender.fiap.com.br.apprender.model.Nota;

public interface NotaPresenter {

    void inserir(Nota nota);

    List<Nota> selecionarTodos(String query);

    void atualizar(Nota nota);

    void deletar(Nota nota);
}
