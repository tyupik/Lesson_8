import org.junit.Test
import org.junit.Assert.*


class Test {
    private val noteService = NoteService()
    @Test
    fun add() {
        val result = noteService.add("title", "text")
        assertEquals(1, result)

    }

    @Test
    fun createComment() {
        val result = noteService.createComment(1, 1, null, "message", 1)
        assertEquals(1, result)
    }

    @Test
    fun delete() {
        noteService.add("title", "text")
        val result = noteService.delete(1)
        assertTrue(result)
    }

    @Test
    fun deleteComment() {
        noteService.createComment(1, 1, null, "message", 1)
        val result = noteService.deleteComment(1, 1)
        assertTrue(result)
    }

    @Test
    fun edit() {
        noteService.add("title", "text")
        val result = noteService.edit(1, "new title", "new text")
        assertTrue(result)
    }

    @Test
    fun editComment() {
        noteService.createComment(1, 1, null, "message", 1)
        println()
        val result = noteService.editComment(1, 1, "new message")
        assertTrue(result)
    }

    @Test
    fun get() {
        val note1 = noteService.add("title1", "text1")
        val note2 = noteService.add("title2", "text2")
        val note3 = noteService.add("title3", "text3")
        val note4 = noteService.add("title4", "text4")
        val get1 = noteService.getById(1, null)
        val get2 = noteService.getById(2, null)
        val get3 = noteService.getById(3, null)
        val get4 = noteService.getById(4, null)
        val result = noteService.get(mutableListOf(1, 2, 3), null, 3, true)
        assertEquals(mutableListOf(get1, get2, get3), result)
    }

    @Test
    fun getReversed() {
        val note1 = noteService.add("title1", "text1")
        val note2 = noteService.add("title2", "text2")
        val note3 = noteService.add("title3", "text3")
        val note4 = noteService.add("title4", "text4")
        val get1 = noteService.getById(1, null)
        val get2 = noteService.getById(2, null)
        val get3 = noteService.getById(3, null)
        val get4 = noteService.getById(4, null)
        val result = noteService.get(mutableListOf(1, 2, 3, 4), null, 3, false)
        assertEquals(mutableListOf(get4, get3, get2), result)
    }

    @Test
    fun getById() {
        val note1 = noteService.add("title1", "text1")
        val note2 = noteService.add("title2", "text2")
        val note3 = noteService.add("title3", "text3")
        val note4 = noteService.add("title4", "text4")
        val result = noteService.getById(3, null)
        assertEquals(Note("title3", "text3", 3), result)
    }

    @Test
    fun getByIdNull() {
        val result = noteService.getById(3, null)
        assertEquals(null, result)
    }


    // В NoteService пришлось создавать createCommentTest(), который в отличие от оригинального createComment()
    // возвращает Comment, а не Comment.id. По-другому не пройти тест на getComments()
    @Test
    fun getComments() {
        val com1 = noteService.createCommentTest(1, 1, null, "message1", 1)
        val com2 = noteService.createCommentTest(1, 2, null, "message2", 1)
        val com3 = noteService.createCommentTest(2, 1, null, "message3", 1)
        val com4 = noteService.createCommentTest(1, 1, null, "message4", 1)
        val com5 = noteService.createCommentTest(1, 1, null, "message5", 1)
        val result = noteService.getComments(1, 1, true, null, 10)
        assertEquals(mutableListOf(com1, com4, com5), result)
    }

    @Test
    fun getCommentsReversed() {
        val com1 = noteService.createCommentTest(1, 1, null, "message1", 1)
        val com2 = noteService.createCommentTest(1, 2, null, "message2", 1)
        val com3 = noteService.createCommentTest(2, 1, null, "message3", 1)
        val com4 = noteService.createCommentTest(1, 1, null, "message4", 1)
        val com5 = noteService.createCommentTest(1, 1, null, "message5", 1)
        val result = noteService.getComments(1, 1, false, null, 10)
        assertEquals(mutableListOf(com5, com4, com1), result)
    }

    @Test
    fun restoreComment() {
        val com1 = noteService.createComment(1, 1, null, "message1", 1)
        val com2 = noteService.createComment(1, 2, null, "message2", 1)
        val com3 = noteService.createComment(2, 1, null, "message3", 1)
        val com4 = noteService.createComment(1, 1, null, "message4", 1)
        val com5 = noteService.createComment(1, 1, null, "message5", 1)
        val result = noteService.deleteComment(1, 1)
        assertTrue(result)
    }

    @Test
    fun restoreCommentFalse() {
        val com1 = noteService.createComment(1, 1, null, "message1", 1)
        val com2 = noteService.createComment(1, 2, null, "message2", 1)
        val com3 = noteService.createComment(2, 1, null, "message3", 1)
        val com4 = noteService.createComment(1, 1, null, "message4", 1)
        val com5 = noteService.createComment(1, 1, null, "message5", 1)
        val result = noteService.deleteComment(1, 3)
        assertTrue(!result)
    }

}