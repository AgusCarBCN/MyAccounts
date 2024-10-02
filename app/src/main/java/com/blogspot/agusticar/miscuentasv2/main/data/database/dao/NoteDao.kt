package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Note

@Dao
interface NoteDao {

    // 1. Añadir una nota
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    // 2. Borrar una nota
    @Delete
    suspend fun deleteNote(note: Note)

    // 3. Borrar todas las notas
    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    // 4. Listar todas las notas
    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<Note>

    // 5. Obtener una nota específica por ID
    @Query("SELECT * FROM note_table WHERE id = :noteId LIMIT 1")
    suspend fun getNoteById(noteId: Int): Note?

    // 6. Obtener notas por categoría
    @Query("SELECT * FROM note_table WHERE category = :category LIMIT 1")
    suspend fun getNotesByCategory(category: Category): List<Note>

    // 7. Consultar notas con un monto específico
    @Query("SELECT * FROM note_table WHERE amount = :amount")
    suspend fun getNotesByAmount(amount: Double): List<Note>

    // 8. Actualizar una nota existente
    @Update
    suspend fun updateNote(note: Note)

    // 9. Obtener notas filtradas por un rango de monto
    @Query("SELECT * FROM note_table WHERE amount BETWEEN :minAmount AND :maxAmount")
    suspend fun getNotesByAmountRange(minAmount: Double, maxAmount: Double): List<Note>

    // 10. Contar el número total de notas
    @Query("SELECT COUNT(*) FROM note_table")
    suspend fun countNotes(): Int
}