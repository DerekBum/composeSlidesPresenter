package com.github.derekbum.composeslidespresenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import org.fife.ui.rsyntaxtextarea.FileLocation
import org.fife.ui.rsyntaxtextarea.TextEditorPane
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.*


var window = JFrame()
var frame = JFrame()

private var file: File = File("")

private var prevType = ""
val textArea = TextEditorPane()


class TextEditorDemo : JFrame() {
    init {
        val path = file.toString()

        //val file = vf?.findFileByRelativePath("/src/main/kotlin/Prime.kt") //TODO()

        //print(file?.extension)

        val currInd = presentation.frameIndex

        presentation.frameIndex = currInd

        //val textArea = TextEditorPane()
        textArea.load(FileLocation.create(path), "UTF-8")
        val cp = JPanel()
        textArea.syntaxEditingStyle = getSyntaxStyle(file.extension)
        textArea.tabSize = 4
        textArea.tabsEmulated = true
        cp.preferredSize = Dimension(300, 300)
        cp.layout = BorderLayout()

        val incButton = JButton("+")
        incButton.addActionListener(FontIncAction())
        val decButton = JButton("-")
        decButton.addActionListener(FontDecAction())

        val buttonPanel = JPanel()
        buttonPanel.layout = FlowLayout(FlowLayout.RIGHT)
        buttonPanel.add(incButton)
        buttonPanel.add(decButton)
        cp.add(buttonPanel, BorderLayout.SOUTH)

        cp.add(JScrollPane(textArea))

        frame.contentPane = cp
        frame.pack()
        frame.defaultCloseOperation = DISPOSE_ON_CLOSE
        frame.isLocationByPlatform = true
        frame.isVisible = true

        frame.extendedState = MAXIMIZED_BOTH
        frame.isUndecorated = true

    }

}

private class FontIncAction : ActionListener {
    override fun actionPerformed(e: ActionEvent) {
        val font: Font = textArea.font
        val size: Float = font.size + 1.0f
        textArea.font = font.deriveFont(size)
    }
}

private class FontDecAction : ActionListener {
    override fun actionPerformed(e: ActionEvent) {
        val font: Font = textArea.font
        val size: Float = font.size - 1.0f
        textArea.font = font.deriveFont(size)
    }
}

@Composable
fun App() {

    //val imagePath by remember { presentationSlidePath }

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

        //vf = e.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY)

        //val file = vf?.findFileByRelativePath("/slid/slide1.jpg")

        //val path = vf?.findFileByRelativePath("/slid/slide1.jpg")

        //file = File(path.toString().removePrefix("file://"))
        file = File(presentation.slides[presentation.index])

        //val psiFile = PsiManager.getInstance(e.project!!).findFile(file!!)

        //FileEditorManager.getInstance(e.project!!).openFile(file, true)

        //print(lol)

        //print(psiFile)

        //print(file.toString().removePrefix("file://"))

        /*window[presentation.windowIndex].addWindowListener(object : WindowAdapter() {
            override fun windowClosed(windowEvent: WindowEvent) {
                presentationOpened = false
            }
        })

        frame[presentation.frameIndex].addWindowListener(object : WindowAdapter() {
            override fun windowClosed(windowEvent: WindowEvent) {
                presentationOpened = false
            }
        })*/

        if (presentation.type == "Editor") {
            if (prevType == "Image" || presentation.index == 0)
                window.dispose()
            // else
            //     frame[presentation.frameIndex].dispose()
            prevType = "Editor"
            SwingUtilities.invokeLater { TextEditorDemo().isVisible = true }
        } else {

            //DemoDialog(e.project, file).show()
            //val window = JFrame()

            presentationSlidePath.value = File(presentation.slides[presentation.index])

            println(presentation.windowIndex)

            //window.dispose()
            if (prevType == "Editor" || presentation.index == 0) {
                prevType = "Image"

                frame.dispose()

                val composePanel = ComposePanel().apply {
                    setContent {
                        preferredSize = Dimension(Toolkit.getDefaultToolkit().screenSize)
                        App()
                    }
                }

                window.contentPane.add(composePanel, BorderLayout.CENTER)

                window.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
                window.isLocationByPlatform = true
                window.isVisible = true

                window.extendedState = JFrame.MAXIMIZED_BOTH
                window.isUndecorated = true
            }

            //val currInd = (presentation.windowIndex + 1) % 2

            //presentation.windowIndex = currInd

        }
        //println(presentationOpened)
    }
}