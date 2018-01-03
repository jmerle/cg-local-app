package com.jaspervanmerle.cglocal

import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.server.setReadOnly
import java.nio.file.Files
import java.nio.file.Paths
import java.util.prefs.Preferences

object Config {
    /**
     * On Windows machines with a Java version lower than 9 a Java warning
     * might show up about the registry. This warning can be ignored.
     */
    val prefs = Preferences.userNodeForPackage(CGLocal::class.java)

    var oneFileForAllPuzzles: Boolean
        get() = prefs.getBoolean("oneFileForAllPuzzles", false)
        set(value) = prefs.putBoolean("oneFileForAllPuzzles", value)

    var autoPlay: Boolean
        get() = prefs.getBoolean("autoPlay", false)
        set(value) = prefs.putBoolean("autoPlay", value)

    var twoWayDataBinding: Boolean
        get() = prefs.getBoolean("twoWayDataBinding", false)
        set(value) {
            prefs.putBoolean("twoWayDataBinding", value)
            Server.connectedSocket?.setReadOnly(!value)
        }

    var defaultAction: String
        get() = prefs.get("defaultAction", "ask")
        set(value) = prefs.put("defaultAction", value)

    fun getPreviouslySelectedFile(questionId: Int): String {
        val path = if (oneFileForAllPuzzles) {
            prefs.get("fileHistory", "")
        } else {
            prefs.get("fileHistory.$questionId", "")
        }

        return if (Files.exists(Paths.get(path))) path else ""
    }

    fun setSelectedFile(questionId: Int, path: String) {
        prefs.put("fileHistory", path)
        prefs.put("fileHistory.$questionId", path)
    }

    fun isPreviouslySelected(questionId: Int): Boolean {
        return getPreviouslySelectedFile(questionId) != ""
    }
}
