package com.example.alberguedeanimales.capaVistas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alberguedeanimales.R;
import com.example.alberguedeanimales.capaDatos.daoAnimales;
import com.example.alberguedeanimales.capaDatos.daoUsuarios;
import com.example.alberguedeanimales.capaEntidades.dtoAnimales;
import com.example.alberguedeanimales.capaEntidades.dtoUsuarios;
import com.example.alberguedeanimales.globalClass.MyAppGlobal;

public class perfil extends AppCompatActivity {

    Button btnPrincipalAnimal;
    Button btnDEP;
    TextView tvPerfilTitle;
    TextView tvTuAnimal;
    TextView tvDatosAnimal;
    TextView tvNombreAnimal;
    TextView tvEspecieAnimal;
    TextView tvRazaAnimal;
    TextView tvNombreUsuario;
    TextView tvEmailUsuario;
    TextView tvDireccionUsuario;
    ImageView ivFotoAnimal;
    private daoUsuarios daoUsua ;
    private daoAnimales daoAnimalP ;

    private dtoAnimales dtoAni;

    public dtoUsuarios dtoUsuP;

    public String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar las vistas
        initViews();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializar los objetos de los dao
        daoUsua = new daoUsuarios(this);
        daoAnimalP = new daoAnimales(this);

        btnDEP.setEnabled(false);

        btnDEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAdoptConfirmationDialog( perfil.this, dtoAni);
            }
        });

        // Configurar el botón de perfil
        btnPrincipalAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(perfil.this, principalAnimal.class);
                startActivity(intent);
                overridePendingTransition(0, 0); // Sin animación
            }
        });

        this.inicializaPerfil();
    }

    private void showAdoptConfirmationDialog(Context context, dtoAnimales animal) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar Muerte")
                .setMessage("¿Está seguro de que su mascota " + animal.getNombre() + " ha fallecido?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        try
                        {

                            daoAnimalP.eliminarAnimal(animal.getId());

                            dtoUsuP.setIdAnimal("");

                            daoUsua.Modificaciones(dtoUsuP);

                            Toast.makeText(perfil.this, "Sentimos tu perdida, el animal se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(perfil.this, "Ha habido un error a la hora de eliminal el animal.", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemCerrarSesion) {
            Intent intent = new Intent(perfil.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void initViews()
    {
        btnPrincipalAnimal = findViewById(R.id.btnListaAnimalesP);
        btnDEP = findViewById(R.id.btnMuerto);
        tvPerfilTitle = findViewById(R.id.tvPerfilTitle);
        tvTuAnimal = findViewById(R.id.tvTuAnimal);
        tvDatosAnimal = findViewById(R.id.tvDatosAnimal);
        tvNombreAnimal = findViewById(R.id.tvNombreAnimal);
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        tvEmailUsuario = findViewById(R.id.tvEmailUsuario);
        tvDireccionUsuario = findViewById(R.id.tvDireccionUsuario);
        tvRazaAnimal = findViewById(R.id.tvRaza);
        tvEspecieAnimal = findViewById(R.id.tvEspecieAnimal);
        ivFotoAnimal = findViewById(R.id.ivFotoAnimal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);

        MenuItem switchItem = menu.findItem(R.id.switchModoOscuro);
        SwitchCompat switchCompat = (SwitchCompat) switchItem.getActionView();

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(perfil.this, "Modo oscuro activado", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    Toast.makeText(perfil .this, "Modo claro activado", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        return true;
    }

    //Método para añadir sdatos al pefil
    private void inicializaPerfil()
    {
        try
        {
            dtoUsuP = new dtoUsuarios();
            dtoAni = new dtoAnimales();

            // Obtenemos la instancia de MyAppGlobal
            MyAppGlobal app = (MyAppGlobal) this.getApplicationContext();

            email = app.getUserData();

            dtoUsuP = daoUsua.consultarUsuario(email);


            iniciaDatosUsu(dtoUsuP);

            if(!dtoUsuP.getIdAnimal().equals(""))
            {
                btnDEP.setEnabled(true);

                dtoAni = daoAnimalP.consultarAnimal(dtoUsuP.getIdAnimal());

                iniciaDatosAnimal(dtoAni);
            }

        }
        catch (Exception e)
        {}
    }

    private void iniciaDatosAnimal(dtoAnimales dataAnimal)
    {
        tvNombreAnimal.setText(getResources().getString(R.string.inicioNombreAnimal) + dataAnimal.getNombre());
        tvRazaAnimal.setText(getResources().getString(R.string.inicioRaza) + dataAnimal.getRaza());
        tvEspecieAnimal.setText(getResources().getString(R.string.inicioEspecie) + dataAnimal.getEspecie());

        ivFotoAnimal.setImageResource(dataAnimal.getIdImagen());
    }
    private void iniciaDatosUsu(dtoUsuarios dataPerfilU)
    {
        if (dataPerfilU != null)
        {
            tvPerfilTitle.setText(getResources().getString(R.string.inicioTituloP) + dataPerfilU.getNombre());
            tvNombreUsuario.setText(getResources().getString(R.string.inicioNombre) + dataPerfilU.getNombre());
            tvEmailUsuario.setText(getResources().getString(R.string.inicioEmail) + dataPerfilU.getEmail());
            tvDireccionUsuario.setText(getResources().getString(R.string.inicioDireccion) + dataPerfilU.getDireccion());
        }
        else
        {
            // Si el perfil de usuario no existe, puedes mostrar mensajes de error o valores por defecto
            tvNombreUsuario.setText("Nombre no disponible");
            tvEmailUsuario.setText("Email no disponible");
            tvDireccionUsuario.setText("Dirección no disponible");
            tvNombreAnimal.setText("Sin animal asociado");
        }
    }

}
