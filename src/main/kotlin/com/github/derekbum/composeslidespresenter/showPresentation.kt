package com.github.derekbum.composeslidespresenter

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.vfs.VirtualFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fife.ui.rsyntaxtextarea.FileLocation
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import org.fife.ui.rsyntaxtextarea.TextEditorPane
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Toolkit
import java.io.File
import java.io.IOException
import javax.swing.*

private val window = JFrame()
private val frame = JFrame()

var vf: VirtualFile? = null

class TextEditorDemo : JFrame() {
    init {
        val path = "/home/tulchin/IdeaProjects/prop/src/main/kotlin/Prime.kt"

        val file = vf?.findFileByRelativePath("/src/main/kotlin/main.cpp") //TODO()

        print(file)

        val kek = file?.fileType as LanguageFileType
        val lol = kek.language

        print(lol.toString())

        val textArea = TextEditorPane()
        textArea.load(FileLocation.create(path), "UTF-8")
        val cp = JPanel()
        textArea.syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_KOTLIN;
        cp.preferredSize = Dimension(300, 300)
        cp.layout = BorderLayout()
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

@Composable
@Preview
fun App(localFile: VirtualFile?) {

    MaterialTheme {

        Column(Modifier.fillMaxSize()) {

            val imageModifier = Modifier
                .fillMaxSize()

            val imagePath = localFile.toString().removePrefix("file://")

            AsyncImage(
                load = { loadImageBitmap(File(imagePath)) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Sample",
                modifier = imageModifier
            )
        }

    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

fun loadImageBitmap(file: File): ImageBitmap =
    file.inputStream().buffered().use(::loadImageBitmap)

class ShowSlide: DumbAwareAction() {

    //override fun actionPerformed(event: AnActionEvent) = BrowserUtil.browse("https://www.10bis.co.il/")
    override fun actionPerformed(e: AnActionEvent) {

        vf = e.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY)

        //val file = vf?.findFileByRelativePath("/slid/slide1.jpg")

        val file = vf?.findFileByRelativePath("/slid/slide1.jpg")

        //val psiFile = PsiManager.getInstance(e.project!!).findFile(file!!)

        //FileEditorManager.getInstance(e.project!!).openFile(file, true)

        //print(lol)

        //print(psiFile)

        //print(file.toString().removePrefix("file://"))

        if (true) {
            SwingUtilities.invokeLater { TextEditorDemo().isVisible = true }
            window.dispose()
        } else {

            //DemoDialog(e.project, file).show()
            //val window = JFrame()

            val composePanel = ComposePanel().apply {
                setContent {
                    preferredSize = Dimension(Toolkit.getDefaultToolkit().screenSize)
                    App(file)
                }
            }

            window.contentPane.add(composePanel, BorderLayout.CENTER)

            window.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            window.isLocationByPlatform = true
            window.isVisible = true

            frame.dispose()

            window.extendedState = JFrame.MAXIMIZED_BOTH
            window.isUndecorated = true
        }
    }
}