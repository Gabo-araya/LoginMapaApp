package cl.aiep.loginmapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // Definir variables
    Button mButtonConductor;
    Button mButtonCliente;

    //Preferencia (Conductor o cliente)
    SharedPreferences mPreferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Definimos preferencia de conductor o cliente
        mPreferencia = getApplicationContext().getSharedPreferences( "typeUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferencia.edit();

        //Definimos botones
        mButtonConductor = findViewById(R.id.btnConductor);
        mButtonCliente = findViewById(R.id.btnCliente);



        //Creamos listener de click para Conductor
        mButtonConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //guardar la preferencia del usuario (conductor o cliente)
                editor.putString("user","conductor");
                editor.apply(); //guardar el valor

                //enviar al usuario pantalla de seleccion
                goToSelectAuth();
            }
        });

        //Creamos listener de click para Cliente
        mButtonCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //guardar la preferencia del usuario (conductor o cliente)
                editor.putString("user", "cliente");
                editor.apply();

                //enviar al usuario pantalla de seleccion
                goToSelectAuth();
            }
        });
    }

    //Creamos la funcion para acceder a Registrar
    private void goToSelectAuth(){
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }





}