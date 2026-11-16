package com.upiiz.practica_02.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upiiz.practica_02.R;
import com.upiiz.practica_02.models.Libro;

import java.util.ArrayList;
import java.util.List;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {
    private List<Libro> listaLibros = new ArrayList<>();

    private final OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(Libro libro);
    }

    public LibroAdapter(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setLibros(List<Libro> libros){
        this.listaLibros = libros !=null ? libros : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro,parent,false);
        return  new LibroViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        Libro libro = listaLibros.get(position);
        holder.bind(libro,listener);

    }

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }


    static class LibroViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tvTitulo, tvAutor, tvEditorial;

        public LibroViewHolder(@NonNull View itemView){
            super(itemView);
            tvId = itemView.findViewById(R.id.tvItemId);
            tvTitulo = itemView.findViewById(R.id.tvItemTitulo);
            tvAutor = itemView.findViewById(R.id.tvItemAutor);
            tvEditorial = itemView.findViewById(R.id.tvItemEditorial);
        }

        public void bind(final Libro libro, final OnItemClickListener listener){
            tvId.setText("ID: #"+(libro.getId() != null ? libro.getId() : "N/A"));
            tvTitulo.setText(libro.getTitulo());
            tvAutor.setText("Autor: "+libro.getAutor());
            tvEditorial.setText("Editorial: "+libro.getEditorial());

            itemView.setOnClickListener(v-> listener.onItemClick(libro));

        }

    }


}
