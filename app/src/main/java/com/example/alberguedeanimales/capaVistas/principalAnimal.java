package com.example.alberguedeanimales.capaVistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alberguedeanimales.R;
import com.example.alberguedeanimales.capaDatos.daoAnimales;
import com.example.alberguedeanimales.capaEntidades.dtoAnimales;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class principalAnimal extends AppCompatActivity {

    private SwitchCompat switchCompat;
    private RecyclerView recyclerViewAnimals;
    private AnimalAdapter animalAdapter;
    private List<dtoAnimales> ListaAnimales;
    private RecyclerView.LayoutManager layoutManager;
    private MaterialButton btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal_animal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewAnimals = findViewById(R.id.recyclerViewAnimals);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerViewAnimals.setLayoutManager(layoutManager);

        ListaAnimales = new ArrayList<>();
        ListaAnimales = this.generarListaAnimales();

        animalAdapter = new AnimalAdapter(ListaAnimales, this);
        recyclerViewAnimals.setAdapter(animalAdapter);

        // Configurar el botón de perfil
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(principalAnimal.this, perfil.class);
                startActivity(intent);
                overridePendingTransition(0, 0); // Sin animación
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);

        MenuItem switchItem = menu.findItem(R.id.switchModoOscuro);
        switchCompat = (SwitchCompat) switchItem.getActionView();

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(principalAnimal.this, "Modo oscuro activado", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    Toast.makeText(principalAnimal.this, "Modo claro activado", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemCerrarSesion) {
            Intent intent = new Intent(principalAnimal.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public List<dtoAnimales> generarListaAnimales() {
        List<dtoAnimales> listaAnimales = new ArrayList<>();
        daoAnimales daoA = new daoAnimales(this);
        listaAnimales = daoA.obtenerTodosLosAnimalesNoAdoptados();
        return listaAnimales;
    }
}

