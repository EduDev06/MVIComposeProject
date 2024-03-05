package com.example.mvicomposeproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvicomposeproject.domain.model.GenderUser


@Entity(
    tableName = "genderUser"
)
data class GenderUserEntity(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "probability") val probability: Float
)

fun GenderUserEntity.asExternalModel() = GenderUser(
    name = name,
    count = count,
    gender = gender,
    probability = probability
)
