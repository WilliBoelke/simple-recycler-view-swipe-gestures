# Android-RecyclerView-Swipe-Gestures

## 1. Introduction 

An easy to use implemenation of swipe gestures for an android RecyclerView. 

* Support for left and right swipes for any RecyclerView
* Set Colours as background for each swipe direction
* Set Icons for each swipe direction 

## 2. Setup 
 
### 1. Copy the 'SwipeGestures' package into your project structure 
### 2. Initialize 'RecyclerAdapterSwipeGestures'in your activity : 
``` 
 RecyclerAdapterSwipeGestures recyclerAdapterSwipeGestures = new RecyclerAdapterSwipeGestures(rightCallback, leftCallback);
```
If you just need one swipe gesture the just implement one of the interfaces and pass it:
```  
RecyclerAdapterSwipeGestures recyclerAdapterSwipeGestures = new RecyclerAdapterSwipeGestures(rightCallback);
```
 
### 3. Implement the 'SwipeCallbackLeft' and/or 'SwipeCallbackLeft':
In your activity implement the interfaces which ou bassed in the 2 
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
    
### 3. Implement the 'SwipeCallbackLeft' and/or 'SwipeCallbackLeft':
