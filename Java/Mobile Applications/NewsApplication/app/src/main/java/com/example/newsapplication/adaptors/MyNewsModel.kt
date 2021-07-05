package com.example.newsapplication.adaptors

/**
 * model for storing data obtained from the API
 */
class MyNewsModel {
    private var source: String? = null
    private var author: String? = null
    private var title: String? = null
    private var description: String? = null
    private var image: String? = null
    private var publishedAt: String? = null
    private var url: String? = null
    private var content: String? = null

    constructor(
        source: String?, author: String?, title: String?, description: String?, image: String?,
        publishedAt: String?, url: String?, content: String?
    ) {
        this.source = source
        this.author = author
        this.title = title
        this.description = description
        this.image = image
        this.publishedAt = publishedAt
        this.url = url
        this.content = content
    }

    fun getSource(): String {
        return source.toString()
    }

    fun setSource(source: String) {
        this.source = source
    }

    fun getAuthor(): String {
        return author.toString()
    }

    fun setAuthor(author: String) {
        this.author = author
    }

    fun getTitle(): String {
        return title.toString()
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getDescription(): String {
        return description.toString()
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getImage(): String {
        return image.toString()
    }

    fun setImage(image: String) {
        this.image = image
    }

    fun getContent(): String {
        return content.toString()
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun getPublishedAt(): String {
        return publishedAt.toString()
    }

    fun setPublishedAt(publishedAt: String) {
        this.publishedAt = publishedAt
    }

    fun getUrl(): String {
        return url.toString()
    }

    fun setUrl(publishedAt: String) {
        this.url = url
    }
}