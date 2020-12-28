class NoteService {
    private val notes = mutableListOf<Note>()
    private val comments = mutableListOf<Comment>()

    fun add(title: String, text: String): Int {
        val nextNid: Int = if (notes.isEmpty()) 1 else notes[notes.size - 1].nid + 1
        val note = Note(title, text, nextNid)
        notes.add(note)
        return nextNid
    }

    fun createComment(noteId: Int, ownerId: Int, replyTo: Int?, message: String, uid: Int): Int {
        val nextId: Int = if (comments.isEmpty()) 1 else comments[comments.size - 1].id + 1
        val date = System.currentTimeMillis() / 1000L
        val comment = Comment(nextId, uid, noteId, ownerId, date, message)
        comments.add(comment)
        return nextId
    }

    fun createCommentTest(noteId: Int, ownerId: Int, replyTo: Int?, message: String, uid: Int): Comment {
        val nextId: Int = if (comments.isEmpty()) 1 else comments[comments.size - 1].id + 1
        val date = System.currentTimeMillis() / 1000L
        val comment = Comment(nextId, uid, noteId, ownerId, date, message)
        comments.add(comment)
        return comment
    }

    fun delete(noteId: Int): Boolean {
        for (note in notes) {
            if (note.nid == noteId) {
                notes.remove(note)
                println("Успешно удалено")

                // Удаляем все комментарии этой заметки
                for (comment in comments) {
                    if (comment.nid == noteId) comment.isDeleted = true
                }

                return true
            }
        }
        println("Заметки с id $noteId не найдено")
        return false
    }

    fun deleteComment(commentId: Int, ownerId: Int): Boolean {
        for (comment in comments) {
            if (comment.id == commentId && comment.oid == ownerId) {
                comment.isDeleted = true
                return true
            }
        }
        println("Комментария с commentId $commentId и ownerId $ownerId не найдено")
        return false
    }

    fun edit(noteId: Int, title: String, text: String): Boolean {
        notes.forEachIndexed { index, note ->
            if (note.nid == noteId) {
                val updatedNote = Note(title = title, nid = note.nid, text = text)
                notes[index] = updatedNote
                return true
            }
        }
        println("Заметка с noteId $noteId не найдена")
        return false
    }

    fun editComment(commentId: Int, ownerId: Int, message: String): Boolean {
        comments.forEachIndexed { index, comment ->
            if (comment.id == commentId && comment.oid == ownerId && !comment.isDeleted) {
                val updatedComment = Comment(id = commentId, oid = ownerId, message = message)
                comments[index] = updatedComment
                return true
            }
        }
        println("Комментарий с commentId $commentId не найден")
        return false
    }

    fun get(noteIds: List<Int>, userId: Int?, count: Int, sort: Boolean): MutableList<Note> {
        var getNotes = mutableListOf<Note>()
        for (noteId in noteIds) {
            for (gNote in notes) {
                if (gNote.nid == noteId) getNotes.add(gNote)
            }
        }
        if (!sort) getNotes = getNotes.asReversed()
        return if (getNotes.size <= count) getNotes else getNotes.take(count).toMutableList()
    }

    fun getById(noteId: Int, ownerId: Int?): Note? {
        for (note in notes) {
            if (note.nid == noteId)
                return note
        }
        return null
    }

    fun getComments(noteId: Int, ownerId: Int, sort: Boolean, offset: Int?, count: Int): MutableList<Comment> {
        var getComments = mutableListOf<Comment>()
        for (comment in comments) {
            if (comment.nid == noteId && comment.oid == ownerId && !comment.isDeleted) {
                getComments.add(comment)
            }
        }
        if (!sort) getComments = getComments.asReversed()
        return if (getComments.size <= count) getComments else getComments.take(count).toMutableList()
    }

    fun restoreComment(commentId: Int, ownerId: Int): Boolean {
        for (comment in comments) {
            if (comment.id == commentId && !comment.isDeleted) comment.isDeleted = true
            return true
        }
        println("Комментарий не удален или такого комментарий не существует")
        return false
    }
}