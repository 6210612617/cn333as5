package com.example.phonebook.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.phonebook.domain.model.ContactModel

import com.example.phonebook.domain.model.TypeModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(
    private val contactDao: ContactDao,
    private val typeDao: TypeDao,
    private val dbMapper: DbMapper
) {

    // Working Notes
    private val contactsNotInTrashLiveData: MutableLiveData<List<ContactModel>> by lazy {
        MutableLiveData<List<ContactModel>>()
    }

    fun getAllContactsNotInTrash(): LiveData<List<ContactModel>> = contactsNotInTrashLiveData

    // Deleted Contacts
    private val contactsInTrashLiveData: MutableLiveData<List<ContactModel>> by lazy {
        MutableLiveData<List<ContactModel>>()
    }

    fun getAllContactsInTrash(): LiveData<List<ContactModel>> = contactsInTrashLiveData

    init {
        initDatabase(this::updateContactsLiveData)
    }

    /**
     * Populates database with tag if it is empty.
     */
    private fun initDatabase(postInitAction: () -> Unit) {
        GlobalScope.launch {
            // Prepopulate colors
            val type = TypeDbModel.DEFAULT_TAGS.toTypedArray()
            val dbTypes = typeDao.getAllSync()
            if (dbTypes.isNullOrEmpty()) {
                typeDao.insertAll(*type)
            }

            // Prepopulate notes
            val contacts = ContactDbModel.DEFAULT_CONTACTS.toTypedArray()
            val dbNotes = contactDao.getAllSync()
            if (dbNotes.isNullOrEmpty()) {
                contactDao.insertAll(*contacts)
            }

            postInitAction.invoke()
        }
    }

    // get list of working contacts or deleted contacts
    private fun getAllContactsDependingOnTrashStateSync(inTrash: Boolean): List<ContactModel> {
        val typeDbModels: Map<Long, TypeDbModel> = typeDao.getAllSync().map { it.id to it }.toMap()
        val dbNotes: List<ContactDbModel> =
            contactDao.getAllSync().filter { it.isInTrash == inTrash }
        return dbMapper.mapNotes(dbNotes, typeDbModels)
    }

    fun insertContact(contact: ContactModel) {
        contactDao.insert(dbMapper.mapDbContact(contact))
        updateContactsLiveData()
    }

    fun deleteContacts(contactIds: List<Long>) {
        contactDao.delete(contactIds)
        updateContactsLiveData()
    }

    fun moveContactToTrash(contactId: Long) {
        val dbContact = contactDao.findByIdSync(contactId)
        val newDbContact = dbContact.copy(isInTrash = true)
        contactDao.insert(newDbContact)
        updateContactsLiveData()
    }

    fun restoreContactsFromTrash(contactIds: List<Long>) {
        val dbContactsInTrash = contactDao.getNotesByIdsSync(contactIds)
        dbContactsInTrash.forEach {
            val newDbContact = it.copy(isInTrash = false)
            contactDao.insert(newDbContact)
        }
        updateContactsLiveData()
    }

    fun getAllTypes(): LiveData<List<TypeModel>> =
        Transformations.map(typeDao.getAll()) { dbMapper.mapTags(it) }

    private fun updateContactsLiveData() {
        contactsNotInTrashLiveData.postValue(getAllContactsDependingOnTrashStateSync(false))
        contactsInTrashLiveData.postValue(getAllContactsDependingOnTrashStateSync(true))
    }
}