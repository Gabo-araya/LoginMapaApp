package cl.aiep.loginmapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
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

import models.User;

public class RegisterActivity extends AppCompatActivity {

    // Definir variables
    TextInputEditText mTextInputRegNombre;
    TextInputEditText mTextInputRegEmail;
    TextInputEditText mTextInputRegTelefono;
    TextInputEditText mTextInputRegPassword;
    Button mButtonRegistrar;

    // variables de dependencias para firebase
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    //Toolbar
    Toolbar mToolbar;

    //Preferencia (Conductor o cliente)
    SharedPreferences mPreferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Definimos preferencia de conductor o cliente
        mPreferencia = getApplicationContext().getSharedPreferences( "typeUser", MODE_PRIVATE);
        String SelectedUser = mPreferencia.getString("user", "");
        Toast.makeText(this, "Bienvenido " + SelectedUser, Toast.LENGTH_SHORT).show();

        //Invocamos el toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Indicar el titulo
        getSupportActionBar().setTitle("Registrar usuario");
        // Activar boton de regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Definimos inputs
        mTextInputRegNombre = findViewById(R.id.txtInputRegNombre);
        mTextInputRegEmail = findViewById(R.id.txtInputRegEmail);
        mTextInputRegTelefono = findViewById(R.id.txtInputRegTelefono);
        mTextInputRegPassword = findViewById(R.id.txtInputRegPassword);
        mButtonRegistrar = findViewById(R.id.btnRegistrar);

        //Instanciar autenticacion de firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Creamos listener de click para Registrar
        mButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrar();
            }
        });
    }

    //Creamos la funcion para registrar
    private void registrar(){
        //Definimos variables, obtenemos texto y lo pasamos a string
        final String nombre = mTextInputRegNombre.getText().toString();
        final String email = mTextInputRegEmail.getText().toString();
        final String telefono = mTextInputRegTelefono.getText().toString();
        final String password = mTextInputRegPassword.getText().toString();

        if (!nombre.isEmpty() && !email.isEmpty() && !telefono.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6){
              //Esto es para saber que hay que hacer cuando termine el registro
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mAuth.getCurrentUser().getUid();
                            saveUser(id, nombre, email, telefono);
                            Toast.makeText(RegisterActivity.this,"Registro exitoso.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText( RegisterActivity.this, "No se pudo registrar el usuario.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
        }


        //Intent intent = new Intent(LoginActivity.this, LoginActivity.class);

        //Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);

        //startActivity(intent);
    }
    void saveUser(String id, String nombre, String email, String telefono){
        String selectedUser = mPreferencia.getString("user", "");

        User user = new User("", nombre, email, telefono);
        user.setEmail(email);
        user.setNombre(nombre);
        user.setTelefono(telefono);


        if (selectedUser.equals("conductor")){
            mDatabase.child("Users").child("Conductores").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else if (selectedUser.equals("cliente")){
            mDatabase.child("Users").child("Clientes").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}