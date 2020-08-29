package nl.deschepers.laraveltinker.toolwindow

import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.JBColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.JEditorPane
import javax.swing.JPanel

class TinkerOutputToolwindow(private val toolWindow: ToolWindow) {
    companion object {
        private const val COLOR1 = 0xFFFFFF
        private const val COLOR2 = 0x999999
    }

    private var tinkerOutputToolWindowContent: JPanel? = null
    private var tinkerOutput: JEditorPane? = null
    private var outputText: String = ""
    private var outputTime: String = ""

    fun resetOutput() {
        this.tinkerOutput?.foreground =
            JBColor.namedColor("Editor.foreground", JBColor(COLOR1, COLOR2))
        outputTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        outputText = ""
        toolWindow.show()
        this.updateView()
    }

    fun addOutput(tinkerOutput: String) {
        outputText += tinkerOutput
        outputText = outputText.replace(
            Regex(
                "<aside(.*?)</aside>",
                setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL)
            ),
            ""
        )
        this.updateView()
    }

    fun updateView() {
        this.tinkerOutput!!.text = "<html>" +
            "<head>" +
            "<style>.output{padding:5px;} .header{font-weight:bold;}</style>" +
            "</head>" +
            "<body>" +
            "<div class=\"header\">" +
            "// Laravel Tinker started at " +
            outputTime +
            "</div>" +
            "<div class=\"output\">" +
            "<pre><code>" +
            outputText +
            "</code></pre>" +
            "</div>" +
            "</body>" +
            "</html>"
    }

    fun getContent(): JPanel? {
        return tinkerOutputToolWindowContent
    }
}
