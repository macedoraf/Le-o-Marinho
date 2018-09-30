package apprender.fiap.com.br.apprender.repositorio.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import apprender.fiap.com.br.apprender.model.Nota;

@Dao
public interface NotaDao {
    @Query("SELECT * FROM nota WHERE titulo  LIKE '%' || :query || '%'")
    List<Nota> selecionaTodos(String query);

    @Query("SELECT * FROM nota")
    List<Nota> selecionaTodos();

    @Insert
    void insere(Nota nota);

    @Update
    void atualiza(Nota nota);

    @Delete
    void deleta(Nota nota);

}
