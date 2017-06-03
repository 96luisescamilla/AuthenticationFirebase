package com.example.escamilla;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.escamilla.authenticationandroidfirebase.MainActivity;
import com.example.escamilla.authenticationandroidfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_Registro extends AppCompatActivity {



    EditText ETemail, ETpass, ETpassconfirm;
    String email, pass, pass_confirm;
    Button BtnRegistro;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__registro);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.d("TAG", "onAuthStateChanged:signed_in:" +user.getUid());
                    Intent intento = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intento);
                }
                else
                {
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
            }
        };


        ETemail = (EditText) findViewById(R.id.ETCorreo);
        ETpass = (EditText) findViewById(R.id.ETPass);
        ETpassconfirm = (EditText) findViewById(R.id.ETPassC);
        BtnRegistro = (Button) findViewById(R.id.BtnReg);

        BtnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=ETemail.getText().toString();
                pass=ETpass.getText().toString();
                pass_confirm=ETpassconfirm.getText().toString();
                if(pass.equals(pass_confirm))
                {
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(activity_Registro.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                    Toast.makeText(activity_Registro.this, "Creado con exito", Toast.LENGTH_LONG).show();
                                    Intent new_intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(new_intent);

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Alo Salio mal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(activity_Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
