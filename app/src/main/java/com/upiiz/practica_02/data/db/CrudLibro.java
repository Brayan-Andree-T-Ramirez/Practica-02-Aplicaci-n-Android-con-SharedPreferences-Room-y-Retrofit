package com.upiiz.practica_02.data.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.upiiz.practica_02.data.entities.LibroEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrudLibro {
    AppDataBase db;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CrudLibro(Context context){
        db = Room.databaseBuilder(context, AppDataBase.class, "librosBD").build();
    }

    public void agregar(LibroEntity libro){
        executorService.execute(()->{
            db.libroDao().crear(libro);
        });
    }

    public void eliminar(Long id){
        executorService.execute(()->{
            db.libroDao().eliminarPorId(id);
        });
    }

    public void obtenerPorId(Long id, OnLibroLoadedListener onLibroLoadedListener){
        executorService.execute(()->{
            LibroEntity libro = db.libroDao().obtenerPorId(id);
            new Handler(Looper.getMainLooper()).post(()->{
               onLibroLoadedListener.onComplete(libro);
            });
        });
    }

    public void obtenerTodos(OnLibrosLoadedListener listener){

        executorService.execute(()->{
            List<LibroEntity> libros = db.libroDao().obtenerTodos();
            new Handler(Looper.getMainLooper()).post(()->{
                listener.onComplete(libros);
            });
        });

    }

    public interface OnLibroLoadedListener{
        void onComplete(LibroEntity libro);
    }

    public interface  OnLibrosLoadedListener{
        void onComplete(List<LibroEntity> libro);
    }
}
