package com.example.appberita.activities.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appberita.R;
import com.example.appberita.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // inisialisasi
    private EditText editEmail, editPassword;
    private TextView btnLogin, btnRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // panggil
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        // progress dialog
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading!!");
        progressDialog.setMessage("Silakan tunggu!!");
        progressDialog.setCancelable(false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // membuka activity register
//        btnRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
        btnRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

        btnLogin.setOnClickListener(v ->{
            if (editEmail.getText().length()>0 && editPassword.getText().length()>0){
                login(editEmail.getText().toString(), editPassword.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Isi dulu semua data!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login (String email, String password){
        // code login
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() !=null){

                if (task.getResult().getUser()!=null){
                    reload();
                }else {
                    Toast.makeText(getApplicationContext(), "Login gagal!!", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getApplicationContext(), "Login gagal!!", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        });
    }

    private void reload(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    // ketika user pernah login, maka akan reload
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}