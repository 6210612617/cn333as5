package com.example.phonebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "can_be_checked_off") val canBeCheckedOff: Boolean,
    @ColumnInfo(name = "is_checked_off") val isCheckedOff: Boolean,
    @ColumnInfo(name = "type_id") val typeId: Long,
    @ColumnInfo(name = "in_trash") val isInTrash: Boolean,
    @ColumnInfo(name = "is_ImportantPersons") val isImportantPersons: Boolean,




    ) {
    companion object {
        val DEFAULT_CONTACTS = listOf(
            ContactDbModel(1, "Greek Pop", "08444475265", false, false, 1, false,true),
            ContactDbModel(2, "Peter parker", "0833659842", false, false, 3, false,false),
            ContactDbModel(3, "Pompompurin gin", "09254652658", false, false, 1, false,false),
            ContactDbModel(4, "Staphany kim", "0485556235", false, false, 4, false, false),
            ContactDbModel(5, "Peter pan", "07878565252", false, false, 1, false,false),
            ContactDbModel(6, "Serial wong", "0626559585", false, false, 1, false,false),
            ContactDbModel(7, "V-song", "09878754265", false, false, 2, false,false),
            ContactDbModel(8, "Police", "191", false, false, 5, false,false),
            ContactDbModel(9, "Kim miso", "012345678", false, false, 4, false,false),
            ContactDbModel(10, "thor ascard", "033558998", false, false, 5, false,false),
            ContactDbModel(11, "Jinger hi", "09885554475", true, false, 1, false,false),
        )
    }
}


