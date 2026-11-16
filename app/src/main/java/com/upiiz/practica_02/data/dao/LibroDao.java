package com.upiiz.practica_02.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.upiiz.practica_02.data.entities.LibroEntity;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface LibroDao {

    @Query("SELECT * FROM libros")
    List<LibroEntity> obtenerTodos();

    @Query("SELECT * FROM libros WHERE id=:id")
    LibroEntity obtenerPorId(Long id);

    @Insert
    void crear(LibroEntity libro);

    @Query("DELETE FROM libros WHERE id=:id")
    void eliminarPorId(Long id);


}
