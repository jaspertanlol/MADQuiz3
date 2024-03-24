package com.example.finaltestpractise

// Model for Comment
data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)