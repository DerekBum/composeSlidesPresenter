package com.github.derekbum.composeslidespresenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.Button
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import org.fife.ui.rsyntaxtextarea.FileLocation
import org.fife.ui.rsyntaxtextarea.TextEditorPane
import org.fife.ui.rtextarea.RTextScrollPane
import java.awt.*
import java.io.File
import javax.swing.*

var frame = JFrame()

private var file: File = File("")

private var prevType = ""
val textArea = TextEditorPane()

class TextEditorDemo : JFrame() {
    init {
        val path = file.toString()
        val currInd = presentation.frameIndex

        presentation.frameIndex = currInd

        textArea.load(FileLocation.create(path), "UTF-8")
        val cp = JPanel()
        textArea.syntaxEditingStyle = getSyntaxStyle(file.extension)
        textArea.tabSize = 4
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

                    Button(onClick = { TODO() }) {
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

@Composable
fun App() {
    MaterialTheme {

        Column(Modifier.fillMaxSize()) {

            val imageModifier = Modifier
                .fillMaxSize()

            val imageBitmap: ImageBitmap = remember(presentationSlidePath.value) {
                loadImageBitmap(presentationSlidePath.value.inputStream())
            }

            Image(
                painter = BitmapPainter(image = imageBitmap),
                contentDescription = "Slide",
                modifier = imageModifier
            )
        }

    }
}

class ShowSlide: DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {

        file = File(presentation.slides[presentation.index])

        if (presentation.type == "Editor") {

            frame.contentPane.removeAll()
            frame.repaint()

            prevType = "Editor"
            SwingUtilities.invokeLater { TextEditorDemo() }
        } else {

            presentationSlidePath.value = File(presentation.slides[presentation.index])

            if (prevType == "Editor" || presentation.index == 0) {
                frame.contentPane.removeAll()
                frame.repaint()

                prevType = "Image"

                val composePanel = ComposePanel().apply {
                    setContent {
                        preferredSize = Dimension(Toolkit.getDefaultToolkit().screenSize)
                        App()
                    }
                }

                frame.contentPane.add(composePanel, BorderLayout.CENTER)
                frame.pack()
            }
        }
    }
}