package com.aplicacion.proyectofinalpm1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.MotionEvent;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogin extends AppCompatActivity {

    EditText txtLoginCorreo;
    EditText txtLoginContra;
    Button btnLoginIng;
    Button btnLoginReg;
    //Button btnLoginMostrar;

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLoginCorreo = (EditText) findViewById(R.id.txtLoginCorreo);
        txtLoginContra = (EditText) findViewById(R.id.txtLoginContra);

        firebaseAuth = FirebaseAuth.getInstance();

        //Persistencia de Datos
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            menuPrincipal();
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txtRegisCorreo, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.txtRegisContra, ".{6,}", R.string.invalid_password);

        //Loguear usuario
        btnLoginIng = (Button) findViewById(R.id.btnLoginIng);
        btnLoginIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) { //Verifica el tipo de datos ingresados
                    String mail = txtLoginCorreo.getText().toString();
                    String pass = txtLoginContra.getText().toString();

                    if (!mail.isEmpty() && !pass.isEmpty()){ //Verifica si los campos estan llenos
                        firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    menuPrincipal();
                                 } else {
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    Toasterror(errorCode);
                                }
                            }
                        });
                    } else { //En caso de que los campos correo y contraseña esten vacios
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
                        builder.setMessage("Verifique los Datos Ingresados")
                                .setTitle("Atención");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });

        //Registrar Usuarios Nuevos
        btnLoginReg = (Button) findViewById(R.id.btnLoginReg);
        btnLoginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityLogin.this, ActivityRegisUsu.class);
                startActivity(i);
            }
        });
    }

    private void menuPrincipal() {
        Intent i = new Intent(ActivityLogin.this, ActivityMenu.class);
        i.putExtra("email", txtLoginCorreo.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void Toasterror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(ActivityLogin.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(ActivityLogin.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(ActivityLogin.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(ActivityLogin.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                txtLoginCorreo.setError("La dirección de correo electrónico está mal formateada.");
                txtLoginCorreo.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(ActivityLogin.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                txtLoginContra.setError("la contraseña es incorrecta ");
                txtLoginContra.requestFocus();
                txtLoginContra.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(ActivityLogin.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(ActivityLogin.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(ActivityLogin.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(ActivityLogin.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                txtLoginCorreo.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                txtLoginCorreo.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(ActivityLogin.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(ActivityLogin.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(ActivityLogin.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(ActivityLogin.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(ActivityLogin.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(ActivityLogin.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(ActivityLogin.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                txtLoginContra.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                txtLoginContra.requestFocus();
                break;

        }

    }
}