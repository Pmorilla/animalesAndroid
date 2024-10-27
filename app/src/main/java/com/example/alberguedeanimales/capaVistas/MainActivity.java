package com.example.alberguedeanimales.capaVistas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alberguedeanimales.R;
import com.example.alberguedeanimales.capaDatos.AlbergueDBHelper;
import com.example.alberguedeanimales.capaDatos.AnimalDataSource;
import com.example.alberguedeanimales.capaDatos.daoUsuarios;
import com.example.alberguedeanimales.globalClass.MyAppGlobal;

import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db = null;

    Button btnIrRegistro;
    Button btnConfirmar;
    EditText etxEmail;
    EditText etxPassword;
    daoUsuarios daoUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Instanciamos un objeto SQLOpenHelper de la BD Votantes.
        AlbergueDBHelper AlbergueHelper = new AlbergueDBHelper(this, "AlbergueDB", null, 1);

        //Abrimos la base de datos
        db = AlbergueHelper.getWritableDatabase();

        findViews();
        daoUsu = new daoUsuarios(this);

        btnIrRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar al hacer clic en el botón
                Intent intent = new Intent(MainActivity.this, registroUsu.class);
                startActivity(intent);
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (camposVacios())
                {
                    Toast.makeText(MainActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();

                }else if(!existeEmail())
                {
                    Toast.makeText(MainActivity.this, "El usuario no existe, si no tiene cuenta cree una nueva.", Toast.LENGTH_SHORT).show();
                } else if (contraCoincice())
                {
                    // Todo correcto
                    String email = etxEmail.getText().toString();

                    //Almacenar email en app global
                    MyAppGlobal app = (MyAppGlobal) getApplication();
                    app.setUserData(email);

                    Intent intent = new Intent(MainActivity.this, principalAnimal.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Contraseña incorrecta, inténtelo de nuevo.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public boolean existeEmail()
    {
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

    //Comprueba que la contraseña coincide con la del email
    public boolean contraCoincice()
    {
        String email = etxEmail.getText().toString();
        String password = etxPassword.getText().toString();

        //Si la contraseña es correcta devuelve true
        return daoUsu.validarContrasena(email,password);
    }

    public boolean camposVacios()
    {
        String email = etxEmail.getText().toString();
        String password = etxPassword.getText().toString();

        return email.isEmpty() || password.isEmpty();
    }

    public void findViews()
    {
        btnIrRegistro = findViewById(R.id.btnIrRegistro);
        btnConfirmar = findViewById(R.id.btnConfirmarIS);
        etxEmail = findViewById(R.id.etxEmail);
        etxPassword = findViewById(R.id.etxPassword);
    }


}