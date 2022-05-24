## Slides presenter plugin

![Test CI](https://github.com/DerekBum/composeSlidesPresenter/actions/workflows/gradle.yml/badge.svg)


This is a plugin to show slides and code examples directly from IntelliJ IDEs, using [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/) UI.

## Why?

It can be useful if large part of your presentation is based on code examples, so instead of copy-pasting code into Keynote or PowerPoint slides you can show both slides and code in IDE. 
 
 - Code examples stay up-to-date. 
 - You can run and modify code examples live to show how they work.
 - Smooth transition between slides and code examples.

## How to use?

1. Create `slides.txt` in IDE project with code examples.
2. Save presentation slides as images to some folder in IDE project folder (e.g. in Keynote in the main menu `File -> Export To -> Images...`).
3. Edit `slides.txt` so that it contains paths to slides image files and code examples (see format description below).
4. Use `Next Slide` and `Previous Slide` actions. It's recommended to change default shortcuts to something more convenient (e.g. I use `F12`, `F11`; the only reason for defaults shortcuts `alt-shift-F12`, `alt-shift-F11` is that they don't conflict with other actions).
5. Always check your presentation in advance on the actual setup.
6. To enter presentation simply press `Tools -> Show Presentation`.
 
 
## Format of slides.txt

- Empty lines and lines starting with `--` or `#` are ignored.
- Each line contains a path to a file with unix-style path separator `/` which can be an absolute path or path relative to the project root.  
- Paths can include special syntax `{{next N}}` where `N` is the amount of times the line will be repeated with incremented counter. E.g. `slides/slides.{{next 2}}.png` will be expanded into two lines `slides/slides.000.png` and `slides/slides.001.png`.

For example:
```
# Some comment
/absolute/path/intro.png

-- More slides
slides/slides.{{next 2}}.png
src/code.js
src/more-code.js
slides/slides.{{next 2}}.png
```

## Words of gratitude

I would like to thank [Dmitry Kandalov](https://github.com/dkandalov/slides-presenter) for his work on non-Compose version of this plugin.
