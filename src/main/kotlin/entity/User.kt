package entity

data class User(
    val id: String,
    val name: String? = null,
    val email: String? = null,
    val mobile: String? = null,
)