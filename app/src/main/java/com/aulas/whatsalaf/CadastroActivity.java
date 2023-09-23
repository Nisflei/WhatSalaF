package com.aulas.whatsalaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aulas.whatsalaf.config.ConfigFirebase;
import com.aulas.whatsalaf.model.Usuario;
import com.aulas.whatsalaf.tool.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button bt=findViewById(R.id.btCadastro);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNome = ((EditText) findViewById(R.id.editNome)).getText().toString();
                String txtEmail = ((EditText) findViewById(R.id.editEmail)).getText().toString();
                String txtSenha = ((EditText) findViewById(R.id.editSenha)).getText().toString();

                // Verificar validação

                Usuario usuario = new Usuario(txtNome,txtEmail,txtSenha);
                salvarUsuario(usuario);
            }
        });
    }

    private void salvarUsuario(Usuario usuario) {
        FirebaseAuth autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CadastroActivity.this, "Informações cadastradas", Toast.LENGTH_SHORT).show();

                            // Salvar usuario na database
                            String id = Base64Custom.codificar(usuario.getEmail());
                            usuario.setId(id);

                            usuario.salvarDB();

                        } else {
                            Toast.makeText(CadastroActivity.this, "Erro ao registrar...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        finish();
    }
}