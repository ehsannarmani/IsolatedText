package ir.ehsan.languagesplitertext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import ir.ehsan.isolatedtext.IsolatedText
import ir.ehsan.isolatedtext.WordStyle
import ir.ehsan.isolatedtext.WordType
import ir.ehsan.isolatedtext.defaultMiscWords
import ir.ehsan.languagesplitertext.ui.theme.LanguageSpliterTextTheme
import ir.ehsan.languagesplitertext.ui.theme.blomFont
import ir.ehsan.languagesplitertext.ui.theme.knickFont
import ir.ehsan.languagesplitertext.ui.theme.ubuntuFont
import ir.ehsan.languagesplitertext.ui.theme.vazirFont


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanguageSpliterTextTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val text =
                        "Hi, with this isolated text you can define separate style for each type of word: english,non english, numbers and misc word. Example:\n\nHi, my name is [احسان] my number is (78992837) and i bought (macbook) for 1000$\n\nWith this library, as you can see, you can define separate fontSize, fontFamily, color, backgroundColor and all of text styles for each type of words."

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp), contentAlignment = Alignment.Center
                    ) {
                        IsolatedText(
                            text = text,
                            WordType.English to WordStyle(
                                fontFamily = ubuntuFont,
                                fontSize = 16.sp,
                            ),
                            WordType.NonEnglish to WordStyle(
                                fontFamily = vazirFont,
                                fontSize = 20.sp,
                                shadow = Shadow(
                                    color = Color(0xff313131),
                                    Offset(4f,4f),
                                    blurRadius = 7f
                                ),
                                color = Color(0xFF212121),
                                baselineShift = BaselineShift(0.1f),
                            ),
                            WordType.Number to WordStyle(
                                fontFamily = knickFont,
                                fontSize = 18.sp,
                                color = Color(0xFF0960CA),
                                letterSpacing = 6.sp,
                                textDecoration = TextDecoration.Underline,
                                background = Color(0xA8FFEB2F)
                            ),
                            WordType.Misc to WordStyle(
                                fontFamily = blomFont,
                                fontSize = 20.sp,
                                color = Color.Red,
                            ),
                            miscWords = defaultMiscWords - ",",
                            textAlign = TextAlign.Justify
                        )

                    }
                }
            }
        }
    }

}



