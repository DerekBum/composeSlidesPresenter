package com.github.derekbum.composeslidespresenter

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import org.fife.ui.rsyntaxtextarea.FileLocation
import org.fife.ui.rsyntaxtextarea.TextEditorPane
import org.fife.ui.rtextarea.RTextScrollPane
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.JPanel

val textArea = TextEditorPane()
const val baseFontSize = 20.0f

class TextEditorSwing : JFrame() {
    init {
        val path = file.toString()
        val currInd = presentation.frameIndex

        presentation.frameIndex = currInd

        textArea.load(FileLocation.create(path), "UTF-8")
        val cp = JPanel()
        textArea.syntaxEditingStyle = getSyntaxStyle(file.extension)
        textArea.tabSize = 4
        textArea.font = textArea.font.deriveFont(baseFontSize)
        textArea.tabsEmulated = true
        cp.layout = BorderLayout()

        val sp = RTextScrollPane(textArea)

        sp.lineNumbersEnabled = true
        sp.isFoldIndicatorEnabled = true

        val composePanel = ComposePanel().apply {
            setSize(maximumSize.width, 200)
            setContent {
                Row(modifier = Modifier.fillMaxWidth()) {

                    Spacer(modifier = Modifier.weight(0.001f))

                    Button(onClick = { saveEditorAction() }) {
                        Text(text = "save", style = TextStyle(fontSize = 15.sp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = { fontResetAction() }) {
                        Text(text = "size reset", style = TextStyle(fontSize = 15.sp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = { fontIncAction() }) {
                        Text(text = "+", style = TextStyle(fontSize = 15.sp))
                    }

                    Spacer(modifier = Modifier.weight(0.001f))

                    Button(onClick = { fontDecAction() }) {
                        Text(text = "-", style = TextStyle(fontSize = 15.sp))
                    }
                    Spacer(modifier = Modifier.weight(0.001f))
                }
            }
        }

        cp.add(sp, BorderLayout.CENTER)
        cp.add(composePanel, BorderLayout.SOUTH)

        frame.contentPane = cp
        frame.pack()
    }
}