# Android-RecyclerView-Swipe-Gestures

[![](https://jitpack.io/v/WilliBoelke/simple-recycler-view-swipe-gestures.svg)](https://jitpack.io/#WilliBoelke/simple-recycler-view-swipe-gestures)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b2ac62aae75f4db8b5edbd7d7b5da606)](https://www.codacy.com/gh/WilliBoelke/simple-recycler-view-swipe-gestures/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=WilliBoelke/simple-recycler-view-swipe-gestures&amp;utm_campaign=Badge_Grade)
![GitHub last commit](https://img.shields.io/github/last-commit/WilliBoelke/simple-recycler-view-swipe-gestures?color=red&style=flat)
[![Issues](https://img.shields.io/github/issues-raw/WilliBoelke/simple-recycler-view-swipe-gestures?maxAge=25000)](https://github.com/WilliBoelke/simple-recycler-view-swipe-gestures/issues)  
[![PR's Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat)](http:/WilliBoelke/simple-recycler-view-swipe-gestures)

## Contribution 

Contributions / PRs are welcome. 

## 1. Introduction 

An easy to use and highly customizable implementation of swipe gestures for an android RecyclerView. 

*	Support for left and right swipes for any RecyclerView
*	Set Colours as background for each swipe direction
*	Set Icons for each swipe direction 
*	Set texts in addition to icons

## 2. Planned 

*	The Actions will be executed only when clicking on the coloured button, which will be schown when swiped, not directly after swiping. 
*	More then one Action for each swipe direction (several buttons will be displayed)

## 3. Setup 

### 3.1. Add JitPack to your Project

#### Gradle

*	Add it in your root build.gradle at the end of repositories: <br />
**(!!! Note:  check the JitPack link for newer version, the following ones may not be up to date !!!)**

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

*	Add the dependencie 
```	
dependencies {
	implementation 'com.github.WilliBoelke:simple-recycler-view-swipe-gestures:1.3'
}
```

#### Maven

*	Add the JitPack Repository to your pom.xml 
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```

*	Add the dependency to your pom.xml

```xml
<dependency>
    <groupId>com.github.WilliBoelke</groupId>
    <artifactId>simple-recycler-view-swipe-gestures</artifactId>
    <version>1.3</version>
</dependency>
```
#### Or find other versions here
[JitPack-SompleSwipeGestures](https://jitpack.io/#WilliBoelke/simple-recycler-view-swipe-gestures)

### 3.2 Import `GestureManager`  <br />

In your Activity add  <br />
```java 
import swipe.gestures.GestureManager;
```

## 4. Usage 

### 4.1 Implement the `SwipeCallbackLeft` and/or `SwipeCallbackLeft`
In your activity implement the interfaces. <br />
Here you put the code which will be executed when the recycler item was swiped.
```java
  private GestureManager.SwipeCallbackLeft leftCallback leftCallback = new SwipeCallbackLeft()
    {
        @Override
        public void onLeftSwipe(int position)
        {
            // your code here 
        }
    }; 
```

### 4.2 Initialize 'GestureManager'in your activity  <br />

```java
 GestureManager recyclerAdapterSwipeGestures = new GestureManager(rightCallback, leftCallback);
```
If you just need one swipe gesture the just implement one of the interfaces and pass it:
 <br />
```java
GestureManager recyclerAdapterSwipeGestures = new GestureManager(rightCallback);
```

### 4.3 Set a colour
Use the setter to set a colour: 
```java     
recyclerAdapterSwipeGestures.setBackgroundColorLeft(new ColorDrawable(Color.RED));
```
You can set a different colour for the two directions.
The standard colours are RED and GREEN.

### Colours 
Blue                          |  Yellow
:-----------------------------------:|:---------------------------------------:
![BLUE](img/ColourBlue.png )       | ![YELLOW](img/ColourYellow.png)

### 4.4 Set Icons
Optionally you can use icons for the swipe acions which will be displayed when the swpie is performed. 
```java
recyclerAdapterSwipeGestures.setIconRight(ContextCompat.getDrawable(this, R.drawable.your_icon));
```
That again works for both actions. 
you also can change the size of the icons by using 
```java
recyclerAdapterSwipeGestures.setIconSizeMultiplier(2);
```

Icon                                 |  Icon
:-----------------------------------:|:---------------------------------------:
![RIGHT](img/IconLeft.png )          | ![LEFT](img/IconRight.png)
Small                                |  Small
![SMALLLEFT](img/SmallIconLeft.png ) | ![SMAllRIGHT](img/SmallIconRight.png) 
Big                                  |  Big
![BIGLEFT](img/BigIconLeft.png )     | ![BIGRIGHT](img/BigIconRight.png) 

###  4.5 Text 

You can set a text (insead or with and icon), the text can be customized by using the 
setters. 

```java
recyclerAdapterSwipeGestures.setTextLeft("LEFT");
recyclerAdapterSwipeGestures.setTextRight("RIGHT");
```
Customize the text : 

```java
//Set text size
recyclerAdapterSwipeGestures.setTextSize(60);

//Set text colour
recyclerAdapterSwipeGestures.setTextColor(Color.BLACK);
```
Texts can also be customized seperatly by using the setters as follows: 

```java
//Set text size
recyclerAdapterSwipeGestures.setTextSize(60, 100

//Set text colour
recyclerAdapterSwipeGestures.setTextColor(Color.BLACK, Color.YELLOW);
```

Text                                 |  Text
:-----------------------------------:|:---------------------------------------:
Right Text                           |  Small
![RIGHTTEXT](img/RightText.png )     | ![TEXTWITHICON](img/TextWithIcon.png) 
Text and icon color                  |  Only Text
![COLOURTEXT](img/TextAndIconColour.png) | ![ONLYTEXT](img/TextNoIcon.png) 

### 4.6 Attach to the `RechylerViewAdapter`

You need to attach the swipe gestures to the RecyyclerView Adapter using a ItemTouchHelper
```java
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerAdapterSwipeGestures);
itemTouchHelper.attachToRecyclerView(recyclerView);
```

And thats it for now.
You can find an example implementation in the `MainActivity`
