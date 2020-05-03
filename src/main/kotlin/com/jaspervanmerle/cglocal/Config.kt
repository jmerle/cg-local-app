package com.jaspervanmerle.cglocal

import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.koin
import mu.KLogging
import java.nio.file.Files
import java.nio.file.Paths
import java.util.prefs.Preferences

class Config {
    companion object : KLogging()

    private val server: Server by koin.inject()

    private val prefs = Preferences.userNodeForPackage(CGLocal::class.java)

    var oneFileForAllPuzzles: Boolean
        get() = prefs.getBoolean("oneFileForAllPuzzles", false)
        set(value) = putBoolean("oneFileForAllPuzzles", value)

    var autoPlay: Boolean
        get() = prefs.getBoolean("autoPlay", false)
        set(value) = putBoolean("autoPlay", value)

    var twoWayDataBinding: Boolean
        get() = prefs.getBoolean("twoWayDataBinding", false)
        set(value) {
            putBoolean("twoWayDataBinding", value)
            server.setReadOnly(!value)
        }

    var defaultAction: String
        get() = prefs.get("defaultAction", "ask")
        set(value) = put("defaultAction", value)

    fun getPreviouslySelectedFile(questionId: Int): String {
        val path = if (oneFileForAllPuzzles) {
            prefs.get("fileHistory", "")
        } else {
            prefs.get("fileHistory.$questionId", "")
        }

        return if (Files.exists(Paths.get(path))) path else ""
    }

    fun setSelectedFile(questionId: Int, path: String) {
        put("fileHistory", path)
        put("fileHistory.$questionId", path)
    }

    fun isPreviouslySelected(questionId: Int): Boolean {
        return getPreviouslySelectedFile(questionId) != ""
    }

    private fun put(key: String, value: String) {
        logger.info("Changing '$key' to '$value'")
        prefs.put(key, value)
    }

    private fun putBoolean(key: String, value: Boolean) {
        logger.info("Changing '$key' to '$value'")
        prefs.putBoolean(key, value)
    }
}
