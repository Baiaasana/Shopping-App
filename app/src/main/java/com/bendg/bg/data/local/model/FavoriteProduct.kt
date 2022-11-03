package com.bendg.bg.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "image")
    val image: String?,
    @ColumnInfo(name = "price")
    val price: Int?,
)
