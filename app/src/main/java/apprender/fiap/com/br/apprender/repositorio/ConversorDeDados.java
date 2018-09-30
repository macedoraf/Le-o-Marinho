package apprender.fiap.com.br.apprender.repositorio;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class ConversorDeDados {

    @TypeConverter
    public static Date toDate(Long valor){
        return valor == null ? null:new Date(valor);
    }

    public static Long toLong(Date valor){
        return valor == null ? null:valor.getTime();
    }
}
