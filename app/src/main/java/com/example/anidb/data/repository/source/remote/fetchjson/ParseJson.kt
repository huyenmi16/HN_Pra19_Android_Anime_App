package com.example.anidb.data.repository.source.remote.fetchjson

import com.example.anidb.data.model.Anime
import com.example.anidb.data.model.AnimeRelations
import com.example.anidb.data.model.Entry
import com.example.anidb.data.model.Genre
import com.example.anidb.data.model.Studio
import com.example.anidb.utils.entry.AIRED
import com.example.anidb.utils.entry.AIRED_FROM
import com.example.anidb.utils.entry.AIRED_TO
import com.example.anidb.utils.entry.DESCRIPTION
import com.example.anidb.utils.entry.DURATION
import com.example.anidb.utils.entry.EPISODES
import com.example.anidb.utils.entry.FAVORITES
import com.example.anidb.utils.entry.GENRES
import com.example.anidb.utils.entry.ID
import com.example.anidb.utils.entry.IMAGE
import com.example.anidb.utils.entry.IMAGE_SIZE
import com.example.anidb.utils.entry.IMAGE_TYPE
import com.example.anidb.utils.entry.MEMBERS
import com.example.anidb.utils.entry.POPULARITY
import com.example.anidb.utils.entry.RANK
import com.example.anidb.utils.entry.SCORE
import com.example.anidb.utils.entry.SEASON
import com.example.anidb.utils.entry.STATUS
import com.example.anidb.utils.entry.STUDIOS
import com.example.anidb.utils.entry.TITLE
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONArray
import org.json.JSONObject

class ParseJson {
    fun animeParseJson(jsonObject: JSONObject) =
        Anime().apply {
            jsonObject.let {
                id = it.getInt(ID)
                title = it.getString(TITLE)
                image =
                    it.getJSONObject(IMAGE)
                        .getJSONObject(IMAGE_TYPE)
                        .getString(IMAGE_SIZE)
                description = it.getString(DESCRIPTION)
                status = it.getString(STATUS)
                airedTo = it.getJSONObject(AIRED).getString(AIRED_TO)
                airedFrom = it.getJSONObject(AIRED).getString(AIRED_FROM)
                members = it.optInt(MEMBERS, 0)
                score = it.optDouble(SCORE, 0.0)
                rank = it.optInt(RANK, 0)
                popularity = it.optInt(POPULARITY, 0)
                favorites = it.optInt(FAVORITES, 0)
                duration = it.getString(DURATION)
                season = it.getString(SEASON)
                episodes = it.optInt(EPISODES, 0)
                genres = getGenresFromJsonArray(it.getJSONArray(GENRES))
                studios = getStudiosFromJsonArray(it.getJSONArray(STUDIOS))
            }
        }

    fun animeRelationsParseJson(jsonObject: JSONObject) =
        AnimeRelations().apply {
            jsonObject.let {
                relation = it.getString("relation")
                entry = getEntryFromJsonArray(it.getJSONArray("entry"))
            }
        }

    private fun getEntryFromJsonArray(jsonArray: JSONArray): List<Entry> {
        return try {
            Gson().fromJson(jsonArray.toString(), Array<Entry>::class.java).toList()
        } catch (e: JsonSyntaxException) {
            emptyList()
        }
    }

    private fun getStudiosFromJsonArray(jsonArray: JSONArray): List<Studio> {
        return try {
            Gson().fromJson(jsonArray.toString(), Array<Studio>::class.java).toList()
        } catch (e: JsonSyntaxException) {
            emptyList()
        }
    }

    private fun getGenresFromJsonArray(jsonArray: JSONArray): List<Genre> {
        return try {
            Gson().fromJson(jsonArray.toString(), Array<Genre>::class.java).toList()
        } catch (e: JsonSyntaxException) {
            emptyList()
        }
    }
}
