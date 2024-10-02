package com.blogspot.agusticar.miscuentasv2.main.data.database.repository

import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.NoteDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    // 1. Insertar una nota
    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    // 2. Eliminar una nota
    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    // 3. Eliminar todas las notas
    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    // 4. Listar todas las notas
    suspend fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }

    // 5. Obtener una nota específica por ID
    suspend fun getNoteById(noteId: Int): Note? {
        return noteDao.getNoteById(noteId)
    }

    // 6. Obtener notas por categoría
    suspend fun getNotesByCategory(category: Category): List<Note> {
        return noteDao.getNotesByCategory(category)
    }

    // 7. Consultar notas con un monto específico
    suspend fun getNotesByAmount(amount: Double): List<Note> {
        return noteDao.getNotesByAmount(amount)
    }

    // 8. Actualizar una nota existente
    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    // 9. Obtener notas filtradas por un rango de monto
    suspend fun getNotesByAmountRange(minAmount: Double, maxAmount: Double): List<Note> {
        return noteDao.getNotesByAmountRange(minAmount, maxAmount)
    }

    // 10. Contar el número total de notas
    suspend fun countNotes(): Int {
        return noteDao.countNotes()
    }
}