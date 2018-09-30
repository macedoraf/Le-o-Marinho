package apprender.fiap.com.br.apprender.ui;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import apprender.fiap.com.br.apprender.repositorio.MeuBancoDeDados;
import kotlin.jvm.Throws;

public class ApprenderAplication extends Application {

    public static final String NOME_DO_BANCO = "BancoDeNotas";
    public static final String PREFERENCIAS = "PREFERENCIAS";
    public static final String CHAVE_DE_UPDATE = "force_update";
    public static ApprenderAplication instancia;

    private MeuBancoDeDados meuBancoDeDados;

    @Override
    public void onCreate() {
        super.onCreate();
        instancia = this;

        meuBancoDeDados = Room.databaseBuilder(getApplicationContext(),MeuBancoDeDados.class,NOME_DO_BANCO)
                .fallbackToDestructiveMigration()
                .build();




    }

    public MeuBancoDeDados getMeuBancoDeDados(){
        return meuBancoDeDados;
    }

    public static ApprenderAplication getInstancia(){
        if(instancia == null){
            throw new RuntimeException("Ocorreu um erro na classe ApprenderAplication");
        }

        return instancia;
    }


}
