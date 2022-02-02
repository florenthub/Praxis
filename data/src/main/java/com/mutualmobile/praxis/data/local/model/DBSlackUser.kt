package com.mutualmobile.praxis.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "slackUser")
data class DBPraxisUser(
  @PrimaryKey val uuid: String,
  @ColumnInfo(name = "first_name") val firstName: String?,
  @ColumnInfo(name = "last_name") val lastName: String?,
  @ColumnInfo(name = "photo") val photo: String?
)
