package br.cericatto.leo.model

data class Search(
    val total_count: Int = 0,
    val incomplete_results: Boolean = false,
    val items: List<Repo>
)