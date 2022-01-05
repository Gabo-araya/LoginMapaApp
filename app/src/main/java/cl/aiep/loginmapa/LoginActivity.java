package cl.aiep.loginmapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    // Definir variables
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonEntrar;

    // variables de dependencias para firebase
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    //Toolbar
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Invocamos el toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Indicar el titulo
        getSupportActionBar().setTitle("Entrar");
        // Activar boton de regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Definimos inputs
        mTextInputEmail = findViewById(R.id.txtInputEmail);
        mTextInputPassword = findViewById(R.id.txtInputPassword);
        mButtonEntrar = findViewById(R.id.btnEntrar);

        //Instanciar autenticacion de firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Creamos listener de click para Login
        mButtonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    //Creamos la funcion para acceder
    private void login(){
        //Definimos variables
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6){
                //Esto es para saber que hay que hacer cuando termine la autenticacion
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Acceso concedido.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText( LoginActivity.this, "La contraseña o el password son incorrectos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Debe escribir su correo electronico o contraseña.", Toast.LENGTH_SHORT).show();
        }


        //Intent intent = new Intent(LoginActivity.this, LoginActivity.class);

        //Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);

        //startActivity(intent);
    }
}