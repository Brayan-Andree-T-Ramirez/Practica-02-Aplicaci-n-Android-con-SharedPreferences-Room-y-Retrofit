package com.upiiz.practica_02.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.upiiz.practica_02.data.dao.LibroDao;
import com.upiiz.practica_02.data.entities.LibroEntity;

@Database(entities = {LibroEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{
    public abstract LibroDao libroDao();
}
