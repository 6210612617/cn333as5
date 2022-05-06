package com.example.phonebook.database


import com.example.phonebook.domain.model.ContactModel
import com.example.phonebook.domain.model.NEW_CONTACT_ID

import com.example.phonebook.domain.model.TypeModel

class DbMapper {
    // Create list of ContactModels by pairing each note with a tag
    fun mapNotes(
        contactDbModels: List<ContactDbModel>,
        typeDbModels: Map<Long, TypeDbModel>
    ): List<ContactModel> = contactDbModels.map {
        val typeDbModel = typeDbModels[it.typeId]
            ?: throw RuntimeException("Color for colorId: ${it.typeId} was not found. Make sure that all colors are passed to this method")

        mapContact(it, typeDbModel)
    }

    // convert ContactDbModel to ContactModel
    fun mapContact(contactDbModel: ContactDbModel, typeDbModel: TypeDbModel): ContactModel {
        val type = mapTag(typeDbModel)
        val isCheckedOff = with(contactDbModel) { if (canBeCheckedOff) isCheckedOff else null }
        return with(contactDbModel) { ContactModel(id, name, content, isCheckedOff,  isImportantPersons, type,) }
    }

    // convert list of TagDdModels to list of TagModels
    fun mapTags(typeDbModels: List<TypeDbModel>): List<TypeModel> =
        typeDbModels.map { mapTag(it) }

    // convert TagDbModel to TagModel
    fun mapTag(typeDbModel: TypeDbModel): TypeModel =
        with(typeDbModel) { TypeModel(id, name, hex) }

    // convert NoteModel back to NoteDbModel
    fun mapDbContact(contact: ContactModel): ContactDbModel =
        with(contact) {
            val canBeCheckedOff = isCheckedOff != null
            val isCheckedOff = isCheckedOff ?: false
            val isImportantPersons = isImportantPersons
            if (id == NEW_CONTACT_ID)
                ContactDbModel(
                    name = name,
                    content = content,
                    canBeCheckedOff = canBeCheckedOff,
                    isCheckedOff = isCheckedOff,
                    typeId = type.id,
                    isInTrash = false,
                    isImportantPersons = isImportantPersons
                )
            else
                ContactDbModel(id, name, content, canBeCheckedOff, isCheckedOff, type.id, false, isImportantPersons)
        }
}