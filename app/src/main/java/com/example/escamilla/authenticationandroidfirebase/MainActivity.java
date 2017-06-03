package com.example.escamilla.authenticationandroidfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;

        Button BtnLogin, BtnSignIn;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mAuth = FirebaseAuth.getInstance();

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        Log.d("kiovo", "onAuthStateChanged:signed_in:" + user.getUid());
                        Intent intento = new Intent(getApplicationContext(), activity_blog.class);
                        startActivity(intento);
                        finish();
                    } else {
                        // User is signed out
                        Log.d("s men", "onAuthStateChanged:signed_out");
                    }
                    // ...
                }
            };

            BtnLogin = (Button)findViewById(R.id.BtnInicio)
            BtnSignIn = (Button)findViewById(R.id.BtnRegistro)

            BtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialog = new DialogFragment();
                    dialog.show(getSupportFragmentManager(), "OtroDialogoManager");
                }
            });
            BtnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentito = new Intent(getApplicationContext(),activity_Registro.class);
                    startActivity(intentito);
                    finish();

                    Toast.makeText(MainActivity.this, "Registrate", Toast.LENGTH_SHORT);
                }

            });
        }
        @Override
        public void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
        }
        @Override
        public void onStop() {
            super.onStop();
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }
        public void check(String correo, String contra)
        {
            // Toast.makeText(this,""+ correo, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,""+ contra, Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(correo, contra)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("orale", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("camara", "signInWithEmail:failed", task.getException());
                                Toast.makeText(MainActivity.this,"Aun no te registras", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }

       }
}
