package apprender.fiap.com.br.apprender.repositorio;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import apprender.fiap.com.br.apprender.model.Nota;
import apprender.fiap.com.br.apprender.repositorio.dao.NotaDao;

@Database(entities = {Nota.class}, version = 2)
@TypeConverters({ConversorDeDados.class})
public abstract class MeuBancoDeDados extends RoomDatabase {

    public abstract NotaDao notaDao();

}
