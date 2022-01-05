package cl.aiep.loginmapa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOptionAuthActivity extends AppCompatActivity {

    // Definir variables
    Button mButtonRedirectLogin;
    Button mButtonRedirectRegistrar;

    //Toolbar
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        //Invocamos el toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Indicar el titulo
        getSupportActionBar().setTitle("Seleccionar opcion");
        // Activar boton de regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Definimos botones
        mButtonRedirectLogin = findViewById(R.id.btnRedirectLogin);
        mButtonRedirectRegistrar = findViewById(R.id.btnRedirectRegistrar);

        //Creamos listener de click para Login
            mButtonRedirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        //Creamos listener de click para Registrar
            mButtonRedirectRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
    }

    //Creamos la funcion para acceder a Login
    private void goToLogin(){
        Intent intent = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //Creamos la funcion para acceder a Registro
    private void goToRegister(){
        Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}