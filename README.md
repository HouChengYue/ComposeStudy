# [Jetpack Compose 教程](https://developer.android.google.cn/jetpack/compose/tutorial) #
共 4 课
开始设置 >

Jetpack Compose 是用于构建原生 Android 界面的新工具包。它使用更少的代码、强大的工具和直观的 Kotlin API，可以帮助您简化并加快 Android 界面开发。

在本教程中，您将使用声明性的函数构建一个简单的界面组件。您无需修改任何 XML 布局，也不需要使用布局编辑器。只需要调用 Jetpack Compose 函数来声明您想要的元素，Compose 编译器即会完成后面的所有工作。

注意：**Jetpack Compose 1.0** 现在为稳定版本。如需了解最新更新，请参阅 Jetpack 版本说明。
完整预览

## 第 1 课：可组合函数
Jetpack Compose 是围绕可组合函数构建的。这些函数可让您以程序化方式定义应用的界面，只需描述应用界面的外观并提供数据依赖项，而不必关注界面的构建过程（初始化元素，将其附加到父项等）。
如需创建可组合函数，只需将 @Composable 注解添加到函数名称中即可。


##### 添加文本元素
开始前，请下载最新版本的  **Android Studio Arctic Fox** ，然后使用 **Empty Compose Activity** 模板创建应用。默认模板已包含一些 Compose 元素，但我们下面要逐步进行构建。

首先，通过在 onCreate 方法内添加文本元素，让系统显示“Hello world!”文本。可以通过定义内容块并调用 Text() 函数来实现此目的。setContent 块定义了 activity 的布局，我们会在其中调用可组合函数。可组合函数只能从其他可组合函数调用。

Jetpack Compose 使用 Kotlin 编译器插件将这些可组合函数转换为应用的界面元素。例如，由 Compose 界面库定义的 Text() 函数会在屏幕上显示一个文本标签。

~~~kotlin
 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("Hello world!")
        }
    }
}
~~~



##### 定义可组合函数

如需使函数成为可组合函数，请添加 **@Composable** 注解。如需尝试此操作，请定义一个 MessageCard() 函数并向其传递一个名称，然后该函数就会使用该名称配置文本元素。

~~~kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard("Android")
        }
    }
}

@Composable
fun MessageCard(name: String) {
    Text(text = "Hello $name!")
}



~~~



##### 在 Android Studio 中预览函数

Android Studio 允许您在 IDE 中预览可组合函数，无需将应用安装到 Android 设备或模拟器中。可组合函数必须为任何参数提供默认值。因此，您无法直接预览 MessageCard() 函数，而是需要创建另一个名为 PreviewMessageCard() 的函数，由该函数使用适当的参数调用 MessageCard()。请在 @Composable 上方添加 @Preview 注解。

~~~kotlin
@Composable
fun MessageCard(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard("Android")
}
~~~


重新构建您的项目。由于新的 PreviewMessageCard() 函数未在任何位置受到调用，因此应用本身不会更改，但 Android Studio 会添加一个预览窗口。此窗口会显示由标有 @Preview 注解的可组合函数创建的界面元素的预览。任何时候，如需更新预览，请点击预览窗口顶部的刷新按钮。



## 第 2 课：布局

界面元素采用多层次结构，元素中又包含其他元素。在 Compose 中，您可以通过从可组合函数中调用其他可组合函数来构建界面层次结构。

##### 添加多个文本

目前为止，我们已经构建了第一个可组合函数和预览！为探索更多 Jetpack Compose 功能，我们将构建一个简单的消息屏幕，屏幕上显示可以展开且具有动画效果的消息列表。
首先，通过显示发件人和消息内容，使消息可组合项更丰富。我们需要先更改可组合参数，以接受 Message 对象（而不是 String），并在 MessageCard 可组合项内添加另一个 Text 可组合项。请务必同时更新预览：

~~~kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(Message("Android", "Jetpack Compose"))
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Text(text = msg.author)
    Text(text = msg.body)
}

@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(
        msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
    )
}
~~~


这段代码会在内容视图中创建两个文本元素。但是，由于我们未提供有关如何排列这两个文本元素的信息，因此它们会相互重叠，使文本无法阅读。

##### 使用 Column

Column 函数可让您垂直排列元素。向 MessageCard() 函数中添加一个 Column。
您可以使用 Row 水平排列项，以及使用 Box 堆叠元素。

~~~kotlin
@Composable
fun MessageCard(msg: Message) {
    Column {
        Text(text = msg.author)
        Text(text = msg.body)
    }
}
~~~



##### 添加图片元素

下面我们来添加发件人的个人资料照片，以丰富消息卡片。使用资源管理器从照片库中导入图片，或使用这张图片。添加一个 Row 可组合项，以实现良好的设计结构，并向该可组合项中添加一个 Image 可组合项：

   

```kotlin
 
 @Composable
fun MessageCard(msg: Message) {
    Row {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
        )
        Column {
        Text(text = msg.author)
        Text(text = msg.body)
    }
  }
}
```



##### 配置布局

我们的消息布局拥有良好的结构，但其元素的间距不合理，并且图片过大！为了装饰或配置可组合项，Compose 使用了修饰符。通过修饰符，您可以更改可组合项的大小、布局、外观，还可以添加高级互动，例如使元素可点击。您可以将这些修饰符链接起来，以创建更丰富的可组合项。下面我们使用其中一些修饰符来改进布局：




```kotlin
   
   @Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )
        
   // Add a horizontal space between the image and the column
    Spacer(modifier = Modifier.width(8.dp))

    Column {
        Text(text = msg.author)
        // Add a vertical space between the author and message texts
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = msg.body)
    }
  }
}
```
## 第 3 课：Material Design

Compose 旨在支持 Material Design 原则。它的许多界面元素都原生支持 Material Design。在本课中，您将使用 Material 微件来设置应用的样式。

##### 使用 Material Design

我们的消息设计现在已有布局，但看上去还不是特别理想。

Jetpack Compose 原生提供 Material Design 及其界面元素的实现。我们将使用 Material Design 样式改进 MessageCard 可组合项的外观。

首先，我们使用在您的项目中创建的 Material 主题（在本例中为 ComposeTutorialTheme）封装 MessageCard 函数。要同时在 @Preview 和 setContent 函数中执行此操作。

Material Design 是围绕三大要素构建的：颜色、排版、形状。我们来逐一添加这些要素

注意：Empty Compose Activity 会为您的项目生成默认主题，支持您自定义 MaterialTheme。如果您为项目指定的主题名称不是 ComposeTutorial，可以在 ui.theme 软件包中找到您的自定义主题。

~~~kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                MessageCard(Message("Android", "Jetpack Compose"))
            }
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        MessageCard(
            msg = Message("Colleague", "Take a look at Jetpack Compose, it's great!")
        )
    }
}

~~~

##### 颜色

使用已封装主题中的颜色设置样式很简单，您可以在需要颜色的任意位置使用主题中的值。

下面我们来设置标题样式，并为图片添加边框：




```kotlin
 @Composable
fun MessageCard(msg: Message) {
   Row(modifier = Modifier.padding(all = 8.dp)) {
       Image(
           painter = painterResource(R.drawable.profile_picture),
           contentDescription = null,
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
       )
 Spacer(modifier = Modifier.width(8.dp))

   Column {
       Text(
           text = msg.author,
           color = MaterialTheme.colors.secondaryVariant
       )

       Spacer(modifier = Modifier.height(4.dp))
       Text(text = msg.body)
   }
      }
}
```
##### 排版

MaterialTheme 中提供了 Material 排版样式，只需将其添加到 Text 可组合项中即可。

~~~kotlin
@Composable
fun MessageCard(msg: Message) {
   Row(modifier = Modifier.padding(all = 8.dp)) {
       Image(
           painter = painterResource(R.drawable.profile_picture),
           contentDescription = null,
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
       )
       Spacer(modifier = Modifier.width(8.dp))

       Column {
           Text(
               text = msg.author,
               color = MaterialTheme.colors.secondaryVariant,
               style = MaterialTheme.typography.subtitle2
           )
    
           Spacer(modifier = Modifier.height(4.dp))
    
           Text(
               text = msg.body,
               style = MaterialTheme.typography.body2
           )
       }

   }
}
~~~

##### 形状

通过形状，我们可以添加最后的“点睛之笔”。此外，我们还为消息添加内边距，以改进布局。

~~~
@Composable
fun MessageCard(msg: Message) {
   Row(modifier = Modifier.padding(all = 8.dp)) {
       Image(
           painter = painterResource(R.drawable.profile_picture),
           contentDescription = null,
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
       )
       Spacer(modifier = Modifier.width(8.dp))

       Column {
           Text(
               text = msg.author,
               color = MaterialTheme.colors.secondaryVariant,
               style = MaterialTheme.typography.subtitle2
           )
    
           Spacer(modifier = Modifier.height(4.dp))
    
           Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
               Text(
                   text = msg.body,
                   modifier = Modifier.padding(all = 4.dp),
                   style = MaterialTheme.typography.body2
               )
           }
       }

   }
}
~~~



##### 启用深色主题

您可以启用深色主题（或夜间模式），以避免显示屏过亮（尤其是在夜间），或者只是节省设备电量。由于支持 Material Design，Jetpack Compose 默认能够处理深色主题。使用 Material 颜色、文本和背景时，系统会自动适应深色背景。

您可以在文件中以单独函数的形式创建多个预览，也可以向同一个函数中添加多个注解。

下面我们来添加新的预览注解并启用夜间模式。

~~~kotlin
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
   ComposeTutorialTheme {
       MessageCard(
           msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
       )
   }
}
~~~

浅色和深色主题的颜色选项是在由 IDE 生成的 Theme.kt 文件中定义的。

目前为止，我们已创建了一个消息界面元素，它会以不同样式显示一张图片和两项文本，并且在浅色和深色主题下都有良好的视觉效果！

~~~
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
   ComposeTutorialTheme {
       MessageCard(
           msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
       )
   }
}
~~~





## 第 4 课：列表和动画

列表和动画在应用内随处可见。在本课中，您将学习如何利用 Compose 轻松创建列表并添加有趣的动画效果。

##### 创建消息列表

只包含一条消息的聊天会略显孤单，我们来更改对话，使其包含多条消息。我们需要创建一个可显示多条消息的 Conversation 函数。对于此用例，我们可以使用 Compose 的 LazyColumn 和 LazyRow.。这些可组合项只会呈现屏幕上显示的元素，因此，对于较长的列表，使用它们会非常高效。同时，这些可组合项可避免采用 XML 布局的 RecyclerView 的复杂性。

在此代码段中，您可以看到 LazyColumn 包含一个 items 子项。它接受 List 作为参数，并且其 lambda 会收到我们命名为 message 的参数（可以随意为其命名），它是 Message 的实例。 简而言之，系统会针对提供的 List 的每个项调用此 lambda：将此示例数据集导入您的项目，以便快速引导对话：

~~~kotlin
import androidx.compose.foundation.lazy.items

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}
~~~


在展开消息时显示动画效果
我们的对话变得更加有趣了。是时候添加动画效果了！我们将添加展开消息以显示更多内容的功能，同时为内容大小和背景颜色添加动画效果。为了存储此本地界面状态，我们需要跟踪消息是否已扩展。为了跟踪这种状态变化，我们必须使用 remember 和 mutableStateOf 函数。

可组合函数可以使用 remember 将本地状态存储在内存中，并跟踪传递给 mutableStateOf 的值的变化。该值更新时，系统会自动重新绘制使用此状态的可组合项（及其子项）。我们将这一功能称为重组。

通过使用 Compose 的状态 API（如 remember 和 mutableStateOf），系统会在状态发生任何变化时自动更新界面：

注意：您需要添加以下导入内容才能正确使用“by”。按 Alt+Enter 键即可添加这些内容。

~~~

~~~






显示预览
现在，我们可以根据 isExpanded 更改点击消息时的消息内容的背景。我们将使用 clickable 修饰符来处理可组合项上的点击事件。我们会为背景颜色添加动画效果，使其值逐步从 MaterialTheme.colors.surface 更改为 MaterialTheme.colors.primary（反之亦然），而不只是切换 Surface 的背景颜色。为此，我们将使用 animateColorAsState 函数。最后，我们将使用 animateContentSize 修饰符顺畅地为消息容器大小添加动画效果：

~~~kotlin
@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

​        // We keep track if the message is expanded or not in this
​        // variable
​        var isExpanded by remember { mutableStateOf(false) }
​        // surfaceColor will be updated gradually from one color to the other
​        val surfaceColor: Color by animateColorAsState(
​            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
​        )

​        // We toggle the isExpanded variable when we click on this Column
​        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
​            Text(
​                text = msg.author,
​                color = MaterialTheme.colors.secondaryVariant,
​                style = MaterialTheme.typography.subtitle2
​            )

​            Spacer(modifier = Modifier.height(4.dp))

​            Surface(
​                shape = MaterialTheme.shapes.medium,
​                elevation = 1.dp,
​                // surfaceColor color will be changing gradually from primary to surface
​                color = surfaceColor,
​                // animateContentSize will change the Surface size gradually
​                modifier = Modifier.animateContentSize().padding(1.dp)
​            ) {
​                Text(
​                    text = msg.body,
​                    modifier = Modifier.padding(all = 4.dp),
​                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                   maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
      }
   }
}
~~~






显示预览
后续步骤
恭喜，您已完成 Compose 教程！您已高效地构建了一个简单的聊天屏幕，该屏幕显示包含图片和文字的可展开的动画消息列表，使用 Material Design 原则设计，添加了深色主题，具有预览功能，所有内容只需不到 100 行代码！

以下是您目前为止所学的内容：

定义可组合函数
在可组合项中添加不同的元素
使用布局可组合项构建界面组件
使用修饰符扩展可组合项
创建高效列表
跟踪状态以及修改状态
在可组合项上添加用户互动
在展开消息时显示动画效果
如果您想深入了解其中的一些步骤，请浏览以下资源。
