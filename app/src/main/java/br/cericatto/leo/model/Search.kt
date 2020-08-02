package br.cericatto.leo.model

data class Search(
    val total_count: Long = 0L,
    val incomplete_results: Boolean = false,
    val items: List<Repo>
)