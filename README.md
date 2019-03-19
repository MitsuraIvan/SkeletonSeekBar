## Mitsura's seekBar lib

This is the seekbar I was creating due to unflexiblility of other solutions available. Writen on kotlin, you can use it on java as well. 

# MitsuraSeekBar

This is an :View item that controlling 'Sliders' objects inside itself.


```java
 <mitsuru.msb.view.MitsuraSeekBar
                android:id="@+id/msb"
                android:layout_width="match_parent"
                android:layout_height="70dp"
                app:colorLineActive="@color/colorAccent"
                app:colorLineBg="@color/colorPrimary"
                app:draggableDifference="3"
                app:itemH="50dp"
                app:itemW="50dp" />
```

the available params atm are:

```java
     itemH: Int// slider H, default 30dp
     itemW: Int// slider W, default 30dp

     gravityY: Float// middle line of the seekbar on Y axes, percents, from 0 to 1, default 0.5
     colorLineBg: Paint// bg color of seekbar line where sliders move. default= White
     colorLineActive: Paint
     //color between seekbars. If you have 1 seekbar-> from seek bar to right side of the seekbar, if more- color between first and last slider
     min: Float// min seekbar value, default- 0
     max: Float// max seekbar value, default- 100
     step: Float// distance between slider going onto next value, default- 1. Float value
     draggableDifference// Float, max distance between two sliders inside seekbar. default- 0
     
```

# Draggable slider

There are 3 types of dragables available from the box:
1. Bitmap, which allow you to show bitmap as slider
2. circle
3. numeric circle with number inside.
 
```java
DraggableBitmap(
              val bitmap: Bitmap, 
              viewTag: String, 
              percent: Float) : AbstractDraggable(viewTag, percent)
DraggableCircle(
              tag: String, 
              percent: Float, 
              val color: Int) : AbstractDraggable(tag, percent)
DraggableTextCircle(
              tag: String, 
              percent: Float, 
              color: Int, 
              textColor: Int, 
              val formatter: String = "%.0f") : DraggableCircle(tag, percent, color) 
```

each takes corresponding resourses to work(Bitmap, color etc)+ tag+ start position as percent of seekbars width. By those tags you will be able to undertand what slider is moving right now and how to respond

example

```java
        val seekBar = findViewById<MitsuraSeekBar>(R.id.msb)
        seekBar.addSlider(DraggableBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lego), "one", 0f))
        seekBar.addSlider(DraggableBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lego), "two", 0.5f))
        seekBar.addSlider(DraggableTextCircle("three", 1f, Color.RED, Color.BLACK))
```

Which will give you this

<p align="center">
  <img width="600" height="150" src="https://s22.postimg.cc/fx1sxay01/image.png">
</p>


#Events
also selfexpl, example in half java style

```java
        seekBar.iSeekBarChangeListener = object : ISeekBarChangeListener {
            override fun onSeekBarValueChange(tag: String, value: Float) {
                //slider with 'tag' just 'moved' to value
            }
        }
        seekBar.iSeekBarGestureListener= object : ISeekBarGestureListener{
            override fun onSeekbarItemFocused(tag: String, value: Float) {
                
            }

            override fun onSeekbarItemMoved(tag: String, value: Float) {
            }

            override fun onSeekbarItemReleased(tag: String, value: Float) {
            }
        }
```


Thats all for basics.

# I need a hardcore solution for my project and I dont want to waste time by doing it myseft again, can this lib help?
yes. Sort of.

You can create all sort of crazy stuff with this api and abstract slider class. Just extend from AbstractDraggable and you will have acess to canvas/paints/rects, where you can build stuff like this. The trick is that you need a doze of understanding the canvas+ draw flow around rectangles.

<p align="center">
  <img width="600" height="120" src="https://s22.postimg.cc/85poc3dz5/image.png">
</p>

Achieved by setting gravity to 0.3 and creating child of DraggableBitmap, that includes Bitmap+ custom path drawing+ value on it. Easy custom seekbar.

If you are not familliar with drawing on canvas, you should, it is fun and very entertaining. or you can contact me, I will superwise you for that sweet green presidents


## TODO
0. fix runtime slider adding. make them add not in the end, but in right order to not crash view logic of positioning
0. make seekbar viewgroup and add xml sliders support
0. add sliding removal
0. add support of starting pos of sliders as value, not as percent. this is important when saving/loading step. Just make a builder. fromPercent or fromValue
1. rx support
2. negative items?
3. tests?
