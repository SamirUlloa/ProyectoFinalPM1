package com.aplicacion.proyectofinalpm1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegisUsu extends AppCompatActivity {

    EditText txtRegisCorreo;
    EditText txtRegisContra;
    EditText txtRegisNombre;
    EditText txtRegisApellido;
    EditText txtRegisTelefono;
    EditText txtRegisDirec;

    //EditText

    Button btnRegisCrear;
    Button btnRegisCancelar;

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    //Codigo para BD
    String nombre = "";
    String apellido = "";
    String telefono = "";
    String direccion = "";
    String correo = "";
    String contra = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_usu);

        //Codigo para base de datos
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txtRegisCorreo, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.txtRegisContra, ".{6,}", R.string.invalid_password);

        txtRegisCorreo = (EditText) findViewById(R.id.txtRegisCorreo);
        txtRegisNombre = (EditText) findViewById(R.id.txtRegisNombre);
        txtRegisApellido = (EditText) findViewById(R.id.txtRegisApellido);
        txtRegisTelefono = (EditText) findViewById(R.id.txtRegisTelefono);
        txtRegisDirec = (EditText) findViewById(R.id.txtRegisDirec);
        txtRegisContra = (EditText) findViewById(R.id.txtRegisContra);

        //Cancela la creación del usuario
        btnRegisCancelar = (Button) findViewById(R.id.btnRegisCancelar);
        btnRegisCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityRegisUsu.this, ActivityLogin.class);
                startActivity(i);
            }
        });

        btnRegisCrear = (Button) findViewById(R.id.btnRegisCrear);
        btnRegisCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String mail = txtRegisCorreo.getText().toString();
                String pass = txtRegisContra.getText().toString();

                if (awesomeValidation.validate()){
                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ActivityRegisUsu.this, "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                Toasterror(errorCode);
                            }
                        }
                    });
                } else {
                    Toast.makeText(ActivityRegisUsu.this, "Revisa los datos", Toast.LENGTH_SHORT).show();
                }*/

                //Codigo base de datos
                correo = txtRegisCorreo.getText().toString();
                nombre = txtRegisNombre.getText().toString();
                apellido = txtRegisApellido.getText().toString();
                telefono = txtRegisTelefono.getText().toString();
                direccion = txtRegisDirec.getText().toString();
                contra = txtRegisContra.getText().toString();

                if (!correo.isEmpty() && !nombre.isEmpty() && !contra.isEmpty()){
                    if (contra.length() >= 6){
                        registrarUsuario();
                    } else {
                        Toast.makeText(ActivityRegisUsu.this, "La contraseña debe contener al menos 6 digitos", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ActivityRegisUsu.this, "COmplete todos los campos", Toast.LENGTH_LONG).show();
                }

                //
            }
        });
    }

    private void registrarUsuario() {
        //Codigo para base de datos
        mAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("apellido", apellido);
                    map.put("telefono", telefono);
                    map.put("direccion", direccion);
                    map.put("correo", correo);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                //Nos envia al Login despues de crear la sesión
                                startActivity(new Intent(ActivityRegisUsu.this, ActivityLogin.class));
                                //Cierra la sesión para que la persistencia de datos no abra el Menu de la aplicación
                                FirebaseAuth.getInstance().signOut();
                                finish();//cierra la pantalla de registro de usuario.
                            } else {
                                Toast.makeText(ActivityRegisUsu.this, "No se pudieron crear los datos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ActivityRegisUsu.this, "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                }
                //Lleva al login despues de crear el usuario
                Intent i = new Intent(ActivityRegisUsu.this, ActivityLogin.class);
                startActivity(i);
            }
        });

        //
    }

    private void Toasterror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(ActivityRegisUsu.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(ActivityRegisUsu.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(ActivityRegisUsu.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(ActivityRegisUsu.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                txtRegisCorreo.setError("La dirección de correo electrónico está mal formateada.");
                txtRegisCorreo.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(ActivityRegisUsu.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                txtRegisContra.setError("la contraseña es incorrecta ");
                txtRegisContra.requestFocus();
                txtRegisContra.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(ActivityRegisUsu.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(ActivityRegisUsu.this, "Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(ActivityRegisUsu.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(ActivityRegisUsu.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                txtRegisCorreo.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                txtRegisCorreo.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(ActivityRegisUsu.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(ActivityRegisUsu.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(ActivityRegisUsu.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(ActivityRegisUsu.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(ActivityRegisUsu.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(ActivityRegisUsu.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(ActivityRegisUsu.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                txtRegisContra.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                txtRegisContra.requestFocus();
                break;

        }

    }
}