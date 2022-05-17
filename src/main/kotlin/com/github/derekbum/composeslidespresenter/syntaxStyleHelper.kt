package com.github.derekbum.composeslidespresenter

import org.fife.ui.rsyntaxtextarea.SyntaxConstants

fun getSyntaxStyle(extension : String?): String {
    return when (extension) {
        "as" -> SyntaxConstants.SYNTAX_STYLE_ACTIONSCRIPT
        "asm" -> SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86
        "c" -> SyntaxConstants.SYNTAX_STYLE_C
        "cljs", "cljc" -> SyntaxConstants.SYNTAX_STYLE_CLOJURE
        "cpp", "h" -> SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS
        "cs" -> SyntaxConstants.SYNTAX_STYLE_CSHARP
        "css" -> SyntaxConstants.SYNTAX_STYLE_CSS
        "csv" -> SyntaxConstants.SYNTAX_STYLE_CSV
        "d" -> SyntaxConstants.SYNTAX_STYLE_D
        "dart" -> SyntaxConstants.SYNTAX_STYLE_DART
        "dpr" -> SyntaxConstants.SYNTAX_STYLE_DELPHI
        "dtd" -> SyntaxConstants.SYNTAX_STYLE_DTD
        "f90", "f95", "f03" -> SyntaxConstants.SYNTAX_STYLE_FORTRAN
        "go" -> SyntaxConstants.SYNTAX_STYLE_GO
        "groovy", "gvy", "gy", "gsh" -> SyntaxConstants.SYNTAX_STYLE_GROOVY
        "html", "htm" -> SyntaxConstants.SYNTAX_STYLE_HTML
        "java" -> SyntaxConstants.SYNTAX_STYLE_JAVA
        "js" -> SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT
        "json" -> SyntaxConstants.SYNTAX_STYLE_JSON
        "kt" -> SyntaxConstants.SYNTAX_STYLE_KOTLIN
        "tex" -> SyntaxConstants.SYNTAX_STYLE_LATEX
        "lua" -> SyntaxConstants.SYNTAX_STYLE_LUA
        "md" -> SyntaxConstants.SYNTAX_STYLE_MARKDOWN
        "pl", "PL" -> SyntaxConstants.SYNTAX_STYLE_PERL
        "php" -> SyntaxConstants.SYNTAX_STYLE_PHP
        "py" -> SyntaxConstants.SYNTAX_STYLE_PYTHON
        "rb" -> SyntaxConstants.SYNTAX_STYLE_RUBY
        "sc" -> SyntaxConstants.SYNTAX_STYLE_SCALA
        "ts" -> SyntaxConstants.SYNTAX_STYLE_TYPESCRIPT
        "vb" -> SyntaxConstants.SYNTAX_STYLE_VISUAL_BASIC
        "bat" -> SyntaxConstants.SYNTAX_STYLE_WINDOWS_BATCH
        "xml" -> SyntaxConstants.SYNTAX_STYLE_XML
        "yaml" -> SyntaxConstants.SYNTAX_STYLE_YAML
        else -> SyntaxConstants.SYNTAX_STYLE_NONE
    }
}