package br.cericatto.leo.model

data class Repo(
    val id: String = "82128465",
    val node_id: String = "MDEwOlJlcG9zaXRvcnk4MjEyODQ2NQ==",
    val name: String = "Android",
    val full_name: String = "open-android/Android",
    val private: Boolean = false,
    val owner: Owner = Owner(),
    val stargazers_count : Long = 8679,
    val watchers_count : Long = 8679,
    val forks : Long = 2525,
    val open_issues : Long = 21,
    val score : Double = 119.72391
)