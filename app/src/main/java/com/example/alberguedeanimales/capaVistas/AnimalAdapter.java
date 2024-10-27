package com.example.alberguedeanimales.capaVistas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.TextView;
import com.example.alberguedeanimales.R;
import com.example.alberguedeanimales.capaDatos.daoAnimales;
import com.example.alberguedeanimales.capaDatos.daoUsuarios;
import com.example.alberguedeanimales.capaEntidades.dtoAnimales;
import com.example.alberguedeanimales.capaEntidades.dtoUsuarios;
import com.example.alberguedeanimales.globalClass.MyAppGlobal;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>
{
    private List<dtoAnimales> animalList;

    private dtoUsuarios usuario;
    private Context mContext;

    private daoUsuarios daoUsu ;
    private daoAnimales daoAnimal ;

    public AnimalAdapter(List<dtoAnimales> animalList, Context context) {
        this.animalList = animalList;
        mContext = context;

        daoUsu = new daoUsuarios(mContext);
        daoAnimal = new daoAnimales(mContext);
    }



    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_list_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        dtoAnimales animal = animalList.get(position);
        holder.textViewAnimalName.setText(animal.getNombre());
        holder.textViewAnimalSpecies.setText(animal.getEspecie());
        holder.textViewAnimalBreed.setText(animal.getRaza());
        holder.imageViewAnimal.setImageResource(animal.getIdImagen());

        holder.buttonAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showAdoptConfirmationDialog(holder.itemView.getContext(), animal);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return animalList.size();
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewAnimalName;
        TextView textViewAnimalSpecies;
        TextView textViewAnimalBreed;
        ImageView imageViewAnimal;
        Button buttonAdopt;

        AnimalViewHolder(View itemView)
        {
            super(itemView);
            textViewAnimalName = itemView.findViewById(R.id.textViewAnimalName);
            textViewAnimalSpecies = itemView.findViewById(R.id.textViewAnimalSpecies);
            textViewAnimalBreed = itemView.findViewById(R.id.textViewAnimalBreed);
            imageViewAnimal = itemView.findViewById(R.id.imageViewAnimal);
            buttonAdopt = itemView.findViewById(R.id.buttonAdopt);
        }
    }

    private void showAdoptConfirmationDialog(Context context, dtoAnimales animal) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar Adopción")
                .setMessage("¿Está seguro de que quiere adoptar a " + animal.getNombre() + "?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        boolean isAdopted = usuarioAdopta(context, animal);
                        if (isAdopted)
                        {
                            Toast.makeText(context, "Has adoptado a " + animal.getNombre(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context, "No se pudo completar la adopción.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private boolean usuarioAdopta(Context context, dtoAnimales animal)
    {

        // Obtenemos la instancia de MyAppGlobal
        MyAppGlobal app = (MyAppGlobal) context.getApplicationContext();

        String email = app.getUserData();

        String idAnimal = animal.getId();

        try
        {
            if(animal.isAdoptado())
            {
                Toast.makeText(mContext,  animal.getNombre() + " ya está adoptado, pruebe con otro animal." , Toast.LENGTH_SHORT).show();
                return false;
            }


            //se saca el dto del usuario y se le añade el id del animal
            usuario = new dtoUsuarios();

            usuario = daoUsu.consultarUsuario(email);

            if(!usuario.getIdAnimal().equals(""))
            {
                Toast.makeText(mContext, "¡YA HAS ADOPTADO!, solo puedes adoptar un animal.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                usuario.setIdAnimal(animal.getId());

                daoUsu.Modificaciones(usuario);

                daoAnimal.actualizarAdopcion(animal.getId(), true);

                return true;
            }

            return false;

        }
        catch (Exception e)
        {
            return false;
        }
    }
}

