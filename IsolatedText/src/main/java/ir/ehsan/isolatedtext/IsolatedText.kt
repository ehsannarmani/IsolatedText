package ir.ehsan.isolatedtext

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.max
import androidx.core.text.isDigitsOnly

@Composable
@JvmName("IsolatedTextPairStyle")
fun IsolatedText(
    text: String,
    vararg styles: Pair<WordType, SpanStyle>,
    miscWords: List<String> = defaultMiscWords,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        isolatedText(
            text = text,
            miscWords = miscWords,
            styles = styles
        ),
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        style = style,
        onTextLayout = onTextLayout,
        maxLines = maxLines,
        softWrap = softWrap
    )
}

@Composable
@JvmName("IsolatedTextListPairStyle")
fun IsolatedText(
    modifier: Modifier = Modifier,
    text: String,
    styles: List<Pair<WordType, SpanStyle>>,
    miscWords: List<String> = defaultMiscWords,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    IsolatedText(
        text = text,
        styles = styles.toTypedArray(),
        miscWords = miscWords,
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        style = style,
        onTextLayout = onTextLayout,
        maxLines = maxLines,
        softWrap = softWrap
    )
}

@Composable
@JvmName("IsolatedTextListFont")
fun IsolatedText(
    modifier: Modifier = Modifier,
    text: String,
    fonts: List<Pair<WordType, FontFamily>> = emptyList(),
    miscWords: List<String> = defaultMiscWords,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    IsolatedText(
        text = text,
        styles = fonts.map { it.first to WordStyle(fontFamily = it.second) }.toTypedArray(),
        miscWords = miscWords,
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        style = style,
        onTextLayout = onTextLayout,
        maxLines = maxLines,
        softWrap = softWrap
    )
}

fun isolatedText(
    text: String,
    vararg styles: Pair<WordType, SpanStyle>,
    miscWords: List<String> = defaultMiscWords,
): AnnotatedString {
    val result = splitPerLanguages(text = text, miscWords = miscWords)
    return buildAnnotatedString {
        result.forEach {
            val style =
                styles.firstOrNull { style -> it.type == style.first }?.second ?: SpanStyle()
            withStyle(style) {
                append(it.word)
            }
        }
    }
}
val defaultMiscWords = listOf(
    "!",
    "@",
    "#",
    "$",
    "%",
    "^",
    "&",
    "*",
    "(",
    ")",
    "[",
    "]",
    "_",
    "-",
    "=",
    "+",
    "<",
    ">",
    "/",
    "?",
    ";",
    ":",
    ",",
    "|",
    "\\"
)

fun splitPerLanguages(
    text: String,
    miscWords: List<String> = defaultMiscWords
): List<IsolatedWord> {
    val result = mutableListOf<IsolatedWord>()
    val justEnglishRegex = Regex("^[a-zA-Z]*\$")
    var englishWord = ""
    var nonEnglishWord = ""
    var numberWord = ""
    var miscWord = ""

    val split = text.split("")
    split.forEachIndexed { index, it ->
        val lastIndex = index == split.lastIndex
        if (justEnglishRegex.matches(it)) {
            englishWord += it
            if (lastIndex){
                result.add(IsolatedWord(word = englishWord, type = WordType.English))
            }
            if (nonEnglishWord.isNotEmpty()) {
                result.add(IsolatedWord(word = nonEnglishWord, type = WordType.NonEnglish))
            }
            if (numberWord.isNotEmpty()) {
                result.add(IsolatedWord(word = numberWord, type = WordType.Number))
            }
            if (miscWord.isNotEmpty()) {
                result.add(IsolatedWord(word = miscWord, type = WordType.Misc))
            }
            nonEnglishWord = ""
            numberWord = ""
            miscWord = ""
        } else if (it.isDigitsOnly()) {
            numberWord += it
            if (lastIndex){
                result.add(IsolatedWord(word = numberWord, type = WordType.Number))
            }
            if (nonEnglishWord.isNotEmpty()) {
                result.add(IsolatedWord(word = nonEnglishWord, type = WordType.NonEnglish))
            }
            if (englishWord.isNotEmpty()) {
                result.add(IsolatedWord(word = englishWord, type = WordType.English))
            }
            if (miscWord.isNotEmpty()) {
                result.add(IsolatedWord(word = miscWord, type = WordType.Misc))
            }
            englishWord = ""
            nonEnglishWord = ""
            miscWord = ""
        } else if (it in miscWords) {
            miscWord += it
            if (lastIndex){
                result.add(IsolatedWord(word = miscWord, type = WordType.Misc))
            }
            if (nonEnglishWord.isNotEmpty()) {
                result.add(IsolatedWord(word = nonEnglishWord, type = WordType.NonEnglish))
            }
            if (englishWord.isNotEmpty()) {
                result.add(IsolatedWord(word = englishWord, type = WordType.English))
            }
            if (numberWord.isNotEmpty()) {
                result.add(IsolatedWord(word = numberWord, type = WordType.Number))
            }
            englishWord = ""
            numberWord = ""
            nonEnglishWord = ""
        } else {
            if (englishWord.isNotEmpty()) {
                result.add(IsolatedWord(word = englishWord, type = WordType.English))
            }
            if (numberWord.isNotEmpty()) {
                result.add(IsolatedWord(word = numberWord, type = WordType.Number))
            }
            if (miscWord.isNotEmpty()) {
                result.add(IsolatedWord(word = miscWord, type = WordType.Misc))
            }
            englishWord = ""
            numberWord = ""
            miscWord = ""
            nonEnglishWord += it
            if (lastIndex){
                result.add(IsolatedWord(word = nonEnglishWord, type = WordType.NonEnglish))
            }
        }
    }
    return result
}

data class IsolatedWord(val word: String, val type: WordType)
enum class WordType {
    English, NonEnglish, Number, Misc
}