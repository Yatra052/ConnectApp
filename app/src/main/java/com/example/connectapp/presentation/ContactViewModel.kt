package com.example.connectapp.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectapp.Data.database.Contact
import com.example.connectapp.Data.database.ContactDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(var database: ContactDatabase): ViewModel(){

    private var isSortByName = MutableStateFlow(true)

    private val contact = isSortByName.flatMapLatest {
        if (it){
            database.dao.getContactsSortName()
        }
        else{
            database.dao.getContactSortDate()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


     val _state = MutableStateFlow(ContactState())

     val state = combine(_state, contact, isSortByName){
         _state, contact, isSortByName->

         _state.copy(contact = contact)
     }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


    fun changeSorting()
    {
        isSortByName.value = !isSortByName.value
    }



    @SuppressLint("SuspiciousIndentation")
    fun saveContact(){
        val contact = Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = System.currentTimeMillis().toLong(),
            isActive = true,
            image = state.value.image.value
            )
          viewModelScope.launch {
              database.dao.upsertContact(contact)
          }


        state.value.id.value = 0
        state.value.name.value = ""
        state.value.number.value = ""
        state.value.email.value = ""
        state.value.dateOfCreation.value = 0
        state.value.image.value = ByteArray(0)
    }


    fun deleteContact()
    {
        val contact = Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = state.value.dateOfCreation.value,
            isActive = true
        )


        viewModelScope.launch {
            database.dao.deleteContact(contact)
        }
         state.value.id.value = 0
         state.value.name.value = ""
         state.value.number.value = ""
         state.value.email.value = ""
         state.value.dateOfCreation.value = 0

    }



}