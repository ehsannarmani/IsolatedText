# IsolatedText

#### You can define separeted style for each type of words: english, non english, numbers and misc words.
#### For Example with this library you can define ubuntu font for english words, chewy font for numbers, y font for non english words, also you can define all of text styles, for exmaple you can define blue color to english words, 20 sp size to numbers and ...

<img src="https://raw.githubusercontent.com/ehsannarmani/IsolatedText/master/assets/shot.png" width="300"/>

## Instalation:
### Add Jitpack Repository:
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
### Add Dependency:
<img src="https://jitpack.io/v/ehsannarmani/IsolatedText.svg" />

```groovy
implementation 'com.github.ehsannarmani:IsolatedText:latest_version'
```
## Basic Usage:
```kotlin
IsolatedText(
  text = "...",
  fonts = listOf(
    WordType.English to ubuntuFont,
    WordType.NonEnglish to vazirFont,
    WordType.Number to chewyFont,
    WordType.Misc to knickFont
  )
)
```

## Complementary Usage:
```kotlin
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
      Offset(4f, 4f),
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
```

### Misc Words:
#### You can define any misc words you want in miscWords parameter, example:
```kotlin
miscWords = listOf("<",">")
```
