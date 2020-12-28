data class Comment(
    val id: Int = 1, // Идентификатор комментария
    val uid: Int = 1, // Идентификатор автора комментария
    val nid: Int = 1, // Идентификатор заметки
    val oid: Int = 1, // Идентификатор владельца заметки
    val date: Long = 1, // Дата
    val message: String = "message", //Текст комментария
    var isDeleted: Boolean = false,
    val replyTo: Int? = 1
)