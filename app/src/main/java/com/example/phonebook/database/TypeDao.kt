package com.example.phonebook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TypeDao {
    @Query("SELECT * FROM TypeDbModel")
    fun getAll(): LiveData<List<TypeDbModel>>

    @Query("SELECT * FROM TypeDbModel")
    fun getAllSync(): List<TypeDbModel>

    @Insert
    fun insertAll(vararg colorDbModels: TypeDbModel)
}