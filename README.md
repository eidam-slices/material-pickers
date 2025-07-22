A library for Material 3 friendly item pickers for Jetpack Compose.
Currently, you can download it on Jitpack.

[![](https://jitpack.io/v/eidam-slices/material-pickers.svg)](https://jitpack.io/#eidam-slices/material-pickers)

### Vertical Picker
- basic Material-looking item picker
- parameters:
 - items: List<T> - list of T, typically strings or integers, but it can be anything else, the object must have toString() function
 - value: T - defines its value, if you change it, it will automatically scroll to the position of it in the list (if there is a position)
 - onValueChange: (T) -> Unit - defines what will happen when the value changes, typically, you want to update its value, but you can do more
 - itemWidth: Dp = 56.dp - defines width of a single picker item (so whole VerticalPicker width)
 - itemHeight: Dp = 40.dp - defines height of a single picker item (so final VerticalPicker height is equal to 3 times this value)
 - shape: Shape = MaterialTheme.shapes.large - specifies background surface shape of the picker, you can play with it and create something like double or triple pickers
 - (TODO) modifier: Modifier = Modifier - not implemented yet, in future, it will define picker's padding, etc.
 - (TODO) textOffset: Dp = 0.dp - not implemented yet, in future it will define texts offset on X axis, for example +2.dp or -2.dp, will be useful for creating double pickers
<img width="100" height="271" alt="image" src="https://github.com/user-attachments/assets/b929b490-f30e-4f4f-90e8-998274447ff9" />


### Horizontal Picker
- basic Material-looking item picker
- parameters:
 - items: List<T> - list of T, typically strings or integers, but it can be anything else, the object must have toString() function
 - value: T - defines its value, if you change it, it will automatically scroll to the position of it in the list (if there is a position)
 - onValueChange: (T) -> Unit - defines what will happen when the value changes, typically, you want to update its value, but you can do more
 - itemWidth: Dp = 56.dp - defines width of a single picker item (so final width is equal to 3 times this value)
 - itemHeight: Dp = 40.dp - defines height of a single picker item (so whole HorizontalPicker height)
 - shape: Shape = MaterialTheme.shapes.large - specifies background surface shape of the picker, you can play with it and create something like double or triple pickers
 - (TODO) modifier: Modifier = Modifier - not implemented yet, in future, it will define picker's padding, etc.
 - (TODO) textOffset: Dp = 0.dp - not implemented yet, in future it will define texts offset on Y axis, for example +2.dp or -2.dp, will be useful for creating double pickers
<img width="368" height="173" alt="image" src="https://github.com/user-attachments/assets/576bab0e-92d9-441d-b14e-652b3bccffe5" />
