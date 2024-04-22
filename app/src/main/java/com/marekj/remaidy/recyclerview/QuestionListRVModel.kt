package com.marekj.restaurantreview.recyclerview

/*
 * Data model class to store logos and team names from F1
 */
class QuestionListRVModel {
    private var modelName: String? = null
    private var modelDescription: String? = null
    private var modelImage: String? = null
    private var id: String? = null

    fun getNames(): String {
        return modelName!!
    }

    fun setNames(name: String) {
        this.modelName = name
    }

    fun getImages(): String {
        return modelImage!!
    }

    fun setImages(imageDrawable: String) {
        this.modelImage = imageDrawable
    }

    fun setDescription(description: String) {
        this.modelDescription = description
    }

    fun getDescription(): String {
        return modelDescription!!
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getId(): String {
        return id!!
    }
}