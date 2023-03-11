package com.example.kyoassistance.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kyoassistance.database.Entity.ContentEntity
import com.example.kyoassistance.database.Entity.NoteEntity
import com.example.kyoassistance.pojo.GptText
import com.example.kyoassistance.repository.DatabaseRepository
import com.example.kyoassistance.repository.NetWorkRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel:ViewModel() {

    private val databaseRepository = DatabaseRepository()
    private val netWorkRepository = NetWorkRepository()

    private var _contentList = MutableLiveData<List<ContentEntity>>()
    val contentList : LiveData<List<ContentEntity>>
        get() = _contentList

    private var _deleteMessageCheck = MutableLiveData<Boolean>(false)
    val deleteMessageCheck : LiveData<Boolean>
        get() = _deleteMessageCheck

    private var _gptInsertMessageCheck = MutableLiveData<Boolean>(false)

    val gptInsertMessageCheck : LiveData<Boolean>
        get() = _gptInsertMessageCheck

    private val _notesList = MutableLiveData<List<NoteEntity>>()
    val noteList:LiveData<List<NoteEntity>> get() = _notesList

    private var _deleteNoteCheck = MutableLiveData<Boolean>(false)
    val deleteNoteCheck : LiveData<Boolean>
        get() = _deleteNoteCheck


    fun postResponse(query : String) = viewModelScope.launch {
        val jsonObject: JsonObject = JsonObject().apply{

            addProperty("model", "text-davinci-003")
            addProperty("prompt", query)
            addProperty("temperature", 0)
            addProperty("max_tokens", 4000)
        }
        val response = netWorkRepository.postResponse(jsonObject)

        Timber.tag("response").e("${response.choices.get(0)}")

        val gson = Gson()
        val tempjson = gson.toJson(response.choices.get(0))
        val tempgson = gson.fromJson(tempjson, GptText::class.java)
        Timber.tag("responseJson").e("${tempgson.text}")
        insertContent(tempgson.text.toString(), 1)
    }

    fun getContentData() = viewModelScope.launch(Dispatchers.IO) {
        _contentList.postValue(databaseRepository.getContentData())
        _deleteMessageCheck.postValue(false)
        _gptInsertMessageCheck.postValue(false)
    }

    fun insertContent(content : String, gptOrUser : Int) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.insertContent(content, gptOrUser)
        if (gptOrUser == 1) {
            _gptInsertMessageCheck.postValue(true)
        }
    }

    fun deleteSelectedContent(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.deleteSelectedContent(id)
        _deleteMessageCheck.postValue(true)
    }


    fun getNotesData() = viewModelScope.launch(Dispatchers.IO) {
        _notesList.postValue(databaseRepository.getNotesData())
        _deleteMessageCheck.postValue(false)
        _gptInsertMessageCheck.postValue(false)
    }

    fun insertNote(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.insertNote(noteEntity)
    }

    fun deleteSelectedNote(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.deleteSelectedNote(id)
        _deleteNoteCheck.postValue(true)
    }
}