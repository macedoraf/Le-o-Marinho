package apprender.fiap.com.br.apprender.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import apprender.fiap.com.br.apprender.R;
import apprender.fiap.com.br.apprender.service.AppHeadService;
import apprender.fiap.com.br.apprender.ui.fragment.AdicionarNotaFragment;
import apprender.fiap.com.br.apprender.ui.fragment.ListaDeNotasFragment;

public class MainActivity extends AppCompatActivity {
    /**
     * Container que recebera as telas da aplicação
     */
    private FrameLayout frame;
    private Toolbar toolbar;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificaPermissoes();
        inicializaComponentes();
        configuraBarraDeFerramentas();
        carregaViewDeListagem();

    }

    private void verificaPermissoes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
                this.startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this))
                    initializeView();
            } else {
                initializeView();
            }
        }
    }

    private void initializeView() {
        startService(new Intent(MainActivity.this, AppHeadService.class));
    }

    private void configuraBarraDeFerramentas() {
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /**
     * Inicializa os componentes da tela principal que armazenará as outras telas
     */
    private void inicializaComponentes() {
        frame = this.findViewById(R.id.frame);
        toolbar = this.findViewById(R.id.toolbar);
    }


    /**
     * Carrega a tela de listagem de notas
     */
    public void carregaViewDeListagem() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frame.getId(), new ListaDeNotasFragment());
        fragmentTransaction.commit();
    }

    /**
     * Carrega a tela de listagem de notas
     */
    public void carregaViewDeAdicionarNotas(Bundle bundle) {
        final AdicionarNotaFragment adicionarNotaFragment = new AdicionarNotaFragment();
        adicionarNotaFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frame.getId(), adicionarNotaFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        carregaViewDeListagem();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
