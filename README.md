# Android-RecyclerView-Swipe-Gestures

## 1. Introduction 

An easy to use implemenation of swipe gestures for an android RecyclerView. 

* Support for left and right swipes for any RecyclerView
* Set Colours as background for each swipe direction
* Set Icons for each swipe direction 

## 2. Planned 

* The Actions will be executed onl when clicking on the coloured button, which will be schown when swiped, not directly after swiping. 
* More the Acion for each swipe direction (several buttons will be displayed)

## 3. Setup 

### 3.1. Add JitPack to your Project

#### Gradle: 

* Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

* Add the dependencie 
```
	dependencies {
	        implementation 'com.github.WilliBoelke:simple-recycler-view-swipe-gestures:v1.0'
	}
```

#### Maven:

* Add the JitPack Repository to your pom.xml 
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

* Add the dependencie to your pom.xml

```
<dependency>
	    <groupId>com.github.WilliBoelke</groupId>
	    <artifactId>simple-recycler-view-swipe-gestures</artifactId>
	    <version>v1.0</version>
	</dependency>
```

### 3.2 Initialize 'RecyclerAdapterSwipeGestures'in your activity : 
 <br />
``` 
 RecyclerAdapterSwipeGestures recyclerAdapterSwipeGestures = new RecyclerAdapterSwipeGestures(rightCallback, leftCallback);
```
If you just need one swipe gesture the just implement one of the interfaces and pass it:
 <br />
```  
RecyclerAdapterSwipeGestures recyclerAdapterSwipeGestures = new RecyclerAdapterSwipeGestures(rightCallback);
```

### 3.3 Implement the `SwipeCallbackLeft` and/or `SwipeCallbackLeft`:
In your activity implement the interfaces which you passed in the 2. 
```  
  private SwipeCallbackLeft leftCallback = new SwipeCallbackLeft()
    {
        @Override
        public void onLeftSwipe(int position)
        {
            // your code here 
        }
    }; 
```

### 3.4 Set a colour:
Use the setter to set a colour: 
```     
recyclerAdapterSwipeGestures.setBackgroundColorLeft(new ColorDrawable(Color.RED));
```
You can set a different colour for the two directions.
The standard colours are RED and GREEN.

### Colours 
Blue                          |  Yellow
:-----------------------------------:|:---------------------------------------:
![BLUE](img/ColourBlue.png )       | ![YELLOW](img/ColourYellow.png)

### 3.5 Set icons:
Optionally you can use icons for the swipe acions which will be displayed when the swpie is performed. 
```
recyclerAdapterSwipeGestures.setIconRight(ContextCompat.getDrawable(this, R.drawable.your_icon));
```
That again works for both actions. 
you also can change the size of the icons by using 
```
recyclerAdapterSwipeGestures.setIconSizeMultiplier(2);
```

Icon                                 |  Icon
:-----------------------------------:|:---------------------------------------:
![RIGHT](img/IconLeft.png )          | ![LEFT](img/IconRight.png)
Small                                |  Small
![SMALLLEFT](img/SmallIconLeft.png ) | ![SMAllRIGHT](img/SmallIconRight.png) 
Big                                  |  Big
![BIGLEFT](img/BigIconLeft.png )     | ![BIGRIGHT](img/BigIconRight.png) 

### 3.6 Attach to the `RechylerViewAdapter`
You need to attach the swipe gestures to the RecyyclerView Adapter using a ItemTouchHelper
```
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerAdapterSwipeGestures);
itemTouchHelper.attachToRecyclerView(recyclerView);
```

And thats it for now.
You can find an example implementation in the `MainActivity`
