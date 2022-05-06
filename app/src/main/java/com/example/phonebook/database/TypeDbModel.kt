package com.example.phonebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TypeDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "hex") val hex: String,
    @ColumnInfo(name = "name") val name: String
) {
    companion object {
        val DEFAULT_TAGS = listOf(
            TypeDbModel(1, "#FFFFFF","Mobile"),
            TypeDbModel(2, "#81FFC4","Home"),
            TypeDbModel(3, "#17C08A","Family"),
            TypeDbModel(4, "#003280","Business"),
            TypeDbModel(5, "#D00026","Emergency"),

            )
        val DEFAULT_TAG = DEFAULT_TAGS[0]
    }
}
