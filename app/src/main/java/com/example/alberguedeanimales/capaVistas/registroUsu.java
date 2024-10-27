package com.example.alberguedeanimales.capaVistas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alberguedeanimales.R;
import com.example.alberguedeanimales.capaDatos.daoUsuarios;
import com.example.alberguedeanimales.capaEntidades.dtoUsuarios;
import com.example.alberguedeanimales.globalClass.MyAppGlobal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registroUsu extends AppCompatActivity {


    Button btnIrIniciosesion;
    EditText etxPassword;
    EditText etxPasswordConf;
    Button btnConfirmar;
    private EditText etxNombre;
    private EditText etxDireccion;

    private EditText etxEmail;

    daoUsuarios daoUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_usu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        daoUsu = new daoUsuarios(this);

        btnIrIniciosesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar al hacer clic en el botón
                Intent intent = new Intent(registroUsu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(camposVacios())
                {
                    Toast.makeText(registroUsu.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                } else if (emailRepe())
                {
                    Toast.makeText(registroUsu.this, "Email ya registrado, pruebe con otro.", Toast.LENGTH_SHORT).show();
                }
                else if (validarFormatoEmail())
                {
                    if(validarContraseñas())
                    {
                        if(!nuevoUsu())
                        {
                            Toast.makeText(registroUsu.this, "Error al registrar el nuevo Usuario", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // Todo correcto
                            String email = etxEmail.getText().toString();

                            //Almacenar email en app global
                            MyAppGlobal app = (MyAppGlobal) getApplication();
                            app.setUserData(email);

                            Intent intent = new Intent(registroUsu.this, principalAnimal.class);
                            startActivity(intent);
                        }
                    }
                }
                else
                {
                    Toast.makeText(registroUsu.this, "El formato del email es incorrecto, intentelo de nuevo.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Comprueba que el email del usuario no coincida con uno ya existente
    public boolean emailRepe() {
        List<String> correos;
        correos = daoUsu.obtenerTodosLosCorreos();

        String email = etxEmail.getText().toString();

        for (String elemento : correos)
        {
            if (elemento.equals(email))
            {
                return true;
            }
        }

        return false;
    }

    public boolean nuevoUsu()
    {
        String email = etxEmail.getText().toString();
        String contrasena = etxPassword.getText().toString();
        String nombre = etxNombre.getText().toString();
        String direccion = etxDireccion.getText().toString();
        String idAnimal = "";

       dtoUsuarios usu = new dtoUsuarios(email, contrasena, nombre, direccion, idAnimal);

       try
       {
           daoUsu.Altas(usu);

           return true;
       }
       catch(Exception e)
       {
           return false;
       }

    }


    public boolean validarContraseñas()
    {
        String password = etxPassword.getText().toString();
        String confirmPassword = etxPasswordConf.getText().toString();

        if (password.length() < 8) {
            // La contraseña no cumple con la longitud mínima
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            // Las contraseñas no coinciden
            Toast.makeText(this, "Las contraseñas no coinciden. Por favor, inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private boolean validarFormatoEmail()
    {
        String email = etxEmail.getText().toString();

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean camposVacios()
    {
        return etxEmail.getText().toString().isEmpty() ||
                etxPassword.getText().toString().isEmpty() ||
                etxPasswordConf.getText().toString().isEmpty() ||
                etxNombre.getText().toString().isEmpty() ||
                etxDireccion.getText().toString().isEmpty();
    }

    public void findViews()
    {
        btnIrIniciosesion = findViewById(R.id.btnIrIniciosesion);
        etxPassword = findViewById(R.id.edtPassword);
        etxPasswordConf = findViewById(R.id.edtPasswordConf);
        btnConfirmar = findViewById(R.id.btnConfirmarReg);
        etxNombre = findViewById(R.id.edtNombre);
        etxDireccion = findViewById(R.id.edtDireccion);
        etxEmail = findViewById(R.id.edtEmail);
    }
}