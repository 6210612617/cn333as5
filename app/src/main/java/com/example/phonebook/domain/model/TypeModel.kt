package com.example.phonebook.domain.model

import com.example.phonebook.database.TypeDbModel

data class TypeModel( val id: Long,
                      val name: String,
                      val hex: String
) {
    companion object {
        val DEFAULT = with(TypeDbModel.DEFAULT_TAG) { TypeModel(id, name, hex) }
    }
}
