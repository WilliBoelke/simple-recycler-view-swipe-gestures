package swipe.gestures;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Here the animation of the moving RecyclerItem adn the colour behind it is implemented
 * Also the methods of the implemented interfaces will be called.
 * <p>
 * The plan is to make it possible that only one f the two (left and right swipe) interfaces can be passed
 * and then only the one swipe gesture will be possible.
 * That doesn't completely works at the moment
 */
public class GestureManager extends ItemTouchHelper.SimpleCallback
{
    //------------Standard Values------------

    private final int STANDARD_MARGIN = 40;
    private final int STANDARD_COLOUR = Color.BLUE;
    private final int STANDARD_ICON_SIZE_MULTIPLIER = 1;
    private final int STANDARD_TEXT_SIZE = 60;
    private final int STANDARD_TEXT_STROKE = 3;

    //------------Instance Variables------------

    /**
     * Colour to be shown on left swipe
     */
    private ColorDrawable leftBackgroundColor;
    /**
     * Colour to be shown on right swipe
     */
    private ColorDrawable rightBackgroundColor;
    /**
     * Icon for the left swipe action
     */
    private Drawable drawableLeft;
    /**
     * Icon for the right swipe action
     */
    private Drawable drawableRight;
    /**
     * The interface which implements the code which will be executed on the left swipe
     */
    private SwipeCallbackLeft swipeCallbackLeft;
    /**
     * The interface which implements the code which will be executed on the right swipe
     */
    private SwipeCallbackRight swipeCallbackRight;
    /**
     * Used to Multiply the intrinsic width and height of the
     * drawable icon to change its site
     */
    private int iconSizeMultiplier;
    /**
     * Text which will be displayed on right swipe
     */
    private String textRight;
    /**
     * text which ill be displayed on left swipe
     */
    private String textLeft;
    /**
     * text paint for the left swipe text
     */
    private Paint textPaintLeft;
    /**
     * text paint for the right swipe text
     */
    private Paint textPaintRight;
    /**
     * The margin between canvas-border, icon and text
     */
    private int margin = 40;


    //------------Constructors------------

    /**
     * Public Constructor to just implement the LeftSwipe
     *
     * @param onLeftSwipe Implementation of the left swipe action
     */
    public GestureManager(SwipeCallbackLeft onLeftSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        initWithStandardValues();
        this.swipeCallbackLeft = onLeftSwipe;
        this.swipeCallbackRight = null;
    }

    /**
     * Public Constructor to just implement the RightSwipe
     *
     * @param onRightSwipe Implementation of the right swipe action
     */
    public GestureManager(SwipeCallbackRight onRightSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        initWithStandardValues();
        this.swipeCallbackRight = onRightSwipe;
        this.swipeCallbackLeft = null;
    }


    /**
     * Public Constructor to implement both swipe directions
     *
     * @param onLeftSwipe  Implementation of the left swipe action
     * @param onRightSwipe Implementation of the right swipe action
     */
    public GestureManager(SwipeCallbackRight onRightSwipe, SwipeCallbackLeft onLeftSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        initWithStandardValues();
        this.swipeCallbackRight = onRightSwipe;
        this.swipeCallbackLeft = onLeftSwipe;
    }

    private void initWithStandardValues()
    {
        this.iconSizeMultiplier = STANDARD_ICON_SIZE_MULTIPLIER;
        this.margin = STANDARD_MARGIN;
        this.leftBackgroundColor = new ColorDrawable(STANDARD_COLOUR);
        this.rightBackgroundColor = new ColorDrawable(STANDARD_COLOUR);

        this.textPaintLeft = new Paint();
        this.textPaintLeft.setColor(Color.BLACK);
        this.textPaintLeft.setStyle(Paint.Style.FILL_AND_STROKE);
        this.textPaintLeft.setStrokeWidth(STANDARD_TEXT_STROKE);
        this.textPaintLeft.setTextSize(STANDARD_TEXT_SIZE);
        this.textPaintLeft.setTextAlign(Paint.Align.RIGHT);

        this.textPaintRight = new Paint();
        this.textPaintRight.setColor(Color.BLACK);
        this.textPaintRight.setStyle(Paint.Style.FILL_AND_STROKE);
        this.textPaintRight.setStrokeWidth(STANDARD_TEXT_STROKE);
        this.textPaintRight.setTextSize(STANDARD_TEXT_SIZE);
        this.textPaintRight.setTextAlign(Paint.Align.LEFT);
    }

    //------------ItemTouchHelper Methods------------

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1)
    {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
    {
        int position = viewHolder.getAdapterPosition();

        if (swipeCallbackLeft != null && direction == ItemTouchHelper.LEFT)
        {
            this.swipeCallbackLeft.onLeftSwipe(position);
        }
        if (swipeCallbackRight != null && direction == ItemTouchHelper.RIGHT)
        {
            this.swipeCallbackRight.onRightSwipe(position);
        }
    }


    @Override
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {

        View itemView = viewHolder.itemView;


        // Swiping to the right
        if (dX > 0)
        {
            this.drawRightLayout(canvas, itemView, dX);

        }
        // Swiping to the left
        else if (dX < 0)
        {
            this.drawLeftLayout(canvas, itemView, dX);
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }


    //------------Drawing Canvas------------


    private void drawLeftLayout(Canvas canvas, View itemView, float dX)
    {
        int backgroundCornerOffset = 0;
        if (swipeCallbackLeft != null)
        {

            //Setting background colours bounds  and draw to he canvas
            rightBackgroundColor.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            rightBackgroundColor.draw(canvas);

            //Draw Icons and Text
            if (drawableLeft != null)
            {
                //Calculate values
                int drawableHeight = drawableLeft.getIntrinsicHeight() * iconSizeMultiplier;
                int drawableWidth = drawableLeft.getIntrinsicWidth() * iconSizeMultiplier;
                int boundRight = itemView.getRight() - margin;
                int boundLeft = boundRight - drawableWidth;
                int boundTop = itemView.getTop() + (itemView.getHeight() - drawableHeight) / 2;
                int boundBottom = itemView.getBottom() - (itemView.getHeight() - drawableHeight) / 2;

                drawableLeft.setBounds(boundLeft, boundTop, boundRight, boundBottom);
                drawableLeft.draw(canvas);

                if (textLeft != null)
                {
                    // if an icon is present we need to consider its size for the left margin
                    drawTextLeft(canvas, itemView, drawableWidth + 2 * margin);
                }
            }
            else if (textLeft != null)
            {
                drawTextLeft(canvas, itemView, margin);
            }
        }
        else
        {
            rightBackgroundColor.setBounds(0, 0, 0, 0);
            leftBackgroundColor.setBounds(0, 0, 0, 0);
        }
    }

    private void drawTextLeft(Canvas canvas, View itemView, double marginRight)
    {
        //Getting the text Bounds
        Rect textBounds = new Rect();
        textPaintLeft.getTextBounds(textLeft, 0, textLeft.length(), textBounds);

        //calculating values
        float textMiddle = textBounds.top - textBounds.top / 2;
        float x = (float) (itemView.getRight() - marginRight);
        float y = itemView.getBottom() - itemView.getHeight() / 2 - textMiddle;

        //Drawing text at coordinates
        canvas.drawText(textLeft, x, y, textPaintLeft);
    }

    private void drawRightLayout(Canvas canvas, View itemView, float dX)
    {
        int backgroundCornerOffset = 0;
        if (swipeCallbackRight != null)
        {
            leftBackgroundColor.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
            leftBackgroundColor.draw(canvas);

            if (drawableRight != null)
            {
                //Calculate values
                int drawableHeight = drawableLeft.getIntrinsicHeight() * iconSizeMultiplier;
                int drawableWidth = drawableLeft.getIntrinsicWidth() * iconSizeMultiplier;
                int boundLeft = itemView.getLeft() + margin;
                int boundRight = boundLeft + drawableWidth;
                int boundTop = itemView.getTop() + (itemView.getHeight() - drawableHeight) / 2;
                int boundBottom = itemView.getBottom() - (itemView.getHeight() - drawableHeight) / 2;

                drawableRight.setBounds(boundLeft, boundTop, boundRight, boundBottom);
                drawableRight.draw(canvas);

                if (textRight != null)
                {
                    drawTextRight(canvas, itemView, drawableWidth + 2 * margin);
                }
            }
            else if (textRight != null)
            {
                drawTextRight(canvas, itemView, margin);
            }
        }
    }


    private void drawTextRight(Canvas canvas, View itemView, double marginLeft)
    {
        //Getting the text Bounds
        Rect textBounds = new Rect();
        textPaintRight.getTextBounds(textRight, 0, textRight.length(), textBounds);

        //calculating values
        float textMiddle = textBounds.top - textBounds.top / 2;
        float x = (float) (itemView.getLeft() + marginLeft);
        float y = itemView.getBottom() - itemView.getHeight() / 2 - textMiddle;

        //Drawing text at coordinates
        canvas.drawText(textRight, x, y, textPaintRight);
    }


    //------------Setter------------

    /**
     * Setter fort the  background colour of the
     * left swipe action
     *
     * @param color the colour to be used
     */
    public void setBackgroundColorLeft(ColorDrawable color)
    {
        leftBackgroundColor = color;
    }

    /**
     * Setter for the colour of the right swipe action
     *
     * @param color the colour to be used
     */
    public void setBackgroundColorRight(ColorDrawable color)
    {
        rightBackgroundColor = color;
    }

    /**
     * Setter for the left swipe action icon
     *
     * @param icon the icon to be used
     */
    public void setIconLeft(Drawable icon)
    {
        drawableLeft = icon;
    }

    /**
     * Setter for the right swipe action icon
     *
     * @param icon the icon to be used
     */
    public void setIconRight(Drawable icon)
    {
        drawableRight = icon;
    }

    /**
     * Sets the colour for both icons
     *
     * @param color the colour to be applied
     */
    public void setIconColor(int color)
    {
        if(drawableLeft != null)
        {
            drawableLeft.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
        if (drawableRight != null)
        {
            drawableRight.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * Sets the colours for both icons individually
     *
     * @param colorRight colour for the right swipe icon
     * @param colorLeft  colour for the left swipe icon
     */
    public void setIconColor(int colorRight, int colorLeft)
    {
        if(drawableLeft != null)
        {
            drawableLeft.setColorFilter(colorLeft, PorterDuff.Mode.SRC_ATOP);
        }
        if (drawableRight != null)
        {
            drawableRight.setColorFilter(colorRight, PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * The the multiply factor for the icon size
     *
     * @param factor the factor
     */
    public void setIconSizeMultiplier(int factor)
    {
        this.iconSizeMultiplier = factor;
    }

    /**
     * Setter for the right swipe action text
     *
     * @param textRight the text which will appear at the side of the icon
     */
    public void setTextRight(String textRight)
    {
        this.textRight = textRight;
    }

    /**
     * Setter for the left swipe action text
     *
     * @param textLeft the text which will appear at the side of the icon
     */
    public void setTextLeft(String textLeft)
    {
        this.textLeft = textLeft;
    }

    /**
     * Setter for the text size (both swipe directions)
     *
     * @param size
     */
    public void setTextSize(int size)
    {
        textPaintLeft.setTextSize(size);
        textPaintRight.setTextSize(size);
    }

    /**
     * Setter for two different sizes for both swipe actions
     *
     * @param leftSize  size for the text at left swipe
     * @param rightSize size for the text on right swipe
     */
    public void setTextSize(int leftSize, int rightSize)
    {
        textPaintLeft.setTextSize(leftSize);
        textPaintRight.setTextSize(rightSize);
    }

    /**
     * Sets one colour for both texts
     *
     * @param color the colour to be applied
     */
    public void setTextColor(int color)
    {
        textPaintRight.setColor(color);
        textPaintLeft.setColor(color);
    }

    /**
     * Sets two different colours for the texts individually
     *
     * @param colorLeft  the colour for the left swipe text
     * @param colorRight the colour for the right swipe text
     */
    public void setTextColor(int colorLeft, int colorRight)
    {
        textPaintRight.setColor(colorRight);
        textPaintLeft.setColor(colorLeft);
    }

    /**
     * Sets the Margin between border - icon - text
     *
     * @param margin the margin
     */
    public void setMargin(int margin)
    {
        this.margin = margin;
    }


    /**
     * Sets the text weight/stroke for both texts
     * @param weight
     * An integer which changes the stroke of the text paint
     */
    public void setTextWeight(float weight)
    {
        textPaintRight.setStrokeWidth(weight);
        textPaintLeft.setStrokeWidth(weight);
    }

    /**
     *  Sets the text weight/stroke for both texts individually
     * @param weightLeft
     * An integer which changes the stroke of the text paint
     * @param weightRight
     * An integer which changes the stroke of the text paint
     */
    public void setTextWeight(float weightLeft, float weightRight)
    {
        textPaintRight.setStrokeWidth(weightRight);
        textPaintLeft.setStrokeWidth(weightLeft);
    }


    //------------OnSwipe------------


    /**
     * Setter for the left right callback
     * to set it after initialization
     *
     * @param swipeCallbackRight
     */
    public void setSwipeCallbackRight(SwipeCallbackRight swipeCallbackRight)
    {
        this.swipeCallbackRight = swipeCallbackRight;
    }

    /**
     * Used to implement different  Swipe actions in {@link GestureManager}
     */
    public interface SwipeCallbackLeft
    {
        void onLeftSwipe(int position);
    }

    /**
     * Used to implement different  Swipe actions in {@link GestureManager}
     */
    public interface SwipeCallbackRight
    {
        void onRightSwipe(int position);
    }

    /**
     * Setter for the left swipe callback
     * to set it after initialization
     *
     * @param swipeCallbackLeft
     */
    public void setSwipeCallbackLeft(SwipeCallbackLeft swipeCallbackLeft)
    {
        this.swipeCallbackLeft = swipeCallbackLeft;
    }

}