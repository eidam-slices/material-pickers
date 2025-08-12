# 📦 Material Pickers

A library providing Material 3–friendly item pickers for Jetpack Compose.

Currently available via **JitPack**:

[![](https://jitpack.io/v/eidam-slices/material-pickers.svg)](https://jitpack.io/#eidam-slices/material-pickers)

---

## 🔽 Vertical Picker

A basic, Material-style vertical item picker.

**Parameters:**

- `items: List<T>` – list of items (typically strings or integers, but any type with a proper `toString()` is supported)
- `value: T` – the currently selected value; the picker will automatically scroll to this value if it's in the list
- `onValueChange: (T) -> Unit` – callback triggered when the selected value changes
- `itemWidth: Dp = 56.dp` – width of each picker item (defines the overall picker width)
- `itemHeight: Dp = 40.dp` – height of each picker item (the final picker height is 3× this value)
- `shape: Shape = MaterialTheme.shapes.large` – shape of the picker's background; customize it to build double/triple pickers
- `(TODO) modifier: Modifier = Modifier` – not implemented yet; will allow controlling padding and other layout aspects
- `(TODO) textOffset: Dp = 0.dp` – not implemented yet; will allow shifting item text on the X axis (useful for multi-pickers)
<div align="center">
  <img src="https://github.com/user-attachments/assets/e07b4709-fabe-4a98-8a52-93b573b672a9" width="100" height="271" alt="Vertical Picker Preview" />
</div>

---

## ▶️ Horizontal Picker

A basic, Material-style horizontal item picker.

**Parameters:**

- `items: List<T>` – list of items (typically strings or integers, but any type with a proper `toString()` is supported)
- `value: T` – the currently selected value; the picker will automatically scroll to this value if it's in the list
- `onValueChange: (T) -> Unit` – callback triggered when the selected value changes
- `itemWidth: Dp = 56.dp` – width of each picker item (the final picker width is 3× this value)
- `itemHeight: Dp = 40.dp` – height of each picker item (defines the overall picker height)
- `shape: Shape = MaterialTheme.shapes.large` – shape of the picker's background; customize it to build double/triple pickers
- `(TODO) modifier: Modifier = Modifier` – not implemented yet; will allow controlling padding and other layout aspects
- `(TODO) textOffset: Dp = 0.dp` – not implemented yet; will allow shifting item text on the Y axis (useful for multi-pickers)
<div align="center">
  <img src="https://github.com/user-attachments/assets/2e59d71f-7f69-4b48-ab47-22a826ae4c02" width="368" height="173" alt="Horizontal Picker Preview" />
</div>

---

## 🛠 How to Use

### 1. Add JitPack repository

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}
```
### 2. Add the dependency
```kotlin
dependencies {
    implementation("com.github.eidam-slices:material-pickers:0.1.0")
}
```
