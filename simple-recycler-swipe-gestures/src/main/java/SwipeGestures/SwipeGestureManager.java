package SwipeGestures;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
public class SwipeGestureManager extends ItemTouchHelper.SimpleCallback
{

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

    private String textRight;
    private String textLeft;

    private Paint textPaintLeft;
    private Paint textPaintRight;


    //------------Constructors------------

    /**
     * Public Constructor to just implement the LeftSwipe
     *
     * @param onLeftSwipe Implementation of the left swipe action
     */
    public SwipeGestureManager(SwipeCallbackLeft onLeftSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        leftBackgroundColor = new ColorDrawable(Color.BLUE);
        rightBackgroundColor = new ColorDrawable(Color.BLUE);
        this.iconSizeMultiplier = 1;
        this.swipeCallbackLeft = onLeftSwipe;
        this.swipeCallbackRight = null;
        this.textPaintLeft = new Paint();
        textPaintLeft.setColor(Color.BLACK);
        textPaintLeft.setStyle(Paint.Style.FILL);
        textPaintLeft.setStrokeWidth(5);
        textPaintLeft.setTextSize(100);
        textPaintLeft.setTextAlign(Paint.Align.RIGHT);
        this.textPaintRight = new Paint();
        textPaintRight.setColor(Color.BLACK);
        textPaintRight.setStyle(Paint.Style.STROKE);
        textPaintRight.setStrokeWidth(5);
        textPaintRight.setTextSize(100);
        textPaintRight.setTextAlign(Paint.Align.LEFT);
    }

    /**
     * Public Constructor to just implement the RightSwipe
     *
     * @param onRightSwipe Implementation of the right swipe action
     */
    public SwipeGestureManager(SwipeCallbackRight onRightSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        leftBackgroundColor = new ColorDrawable(Color.BLUE);
        rightBackgroundColor = new ColorDrawable(Color.BLUE);
        this.iconSizeMultiplier = 1;
        this.swipeCallbackRight = onRightSwipe;
        this.swipeCallbackLeft = null;
        this.textPaintLeft = new Paint();
        textPaintLeft.setColor(Color.BLACK);
        textPaintLeft.setStyle(Paint.Style.FILL);
        textPaintLeft.setStrokeWidth(5);
        textPaintLeft.setTextSize(100);
        textPaintLeft.setTextAlign(Paint.Align.RIGHT);
        this.textPaintRight = new Paint();
        textPaintRight.setColor(Color.BLACK);
        textPaintRight.setStyle(Paint.Style.STROKE);
        textPaintRight.setStrokeWidth(5);
        textPaintRight.setTextSize(100);
        textPaintRight.setTextAlign(Paint.Align.LEFT);
    }


    /**
     * Public Constructor to implement both swipe directions
     *
     * @param onLeftSwipe  Implementation of the left swipe action
     * @param onRightSwipe Implementation of the right swipe action
     */
    public SwipeGestureManager(SwipeCallbackRight onRightSwipe, SwipeCallbackLeft onLeftSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        leftBackgroundColor = new ColorDrawable(Color.BLUE);
        rightBackgroundColor = new ColorDrawable(Color.BLUE);
        this.iconSizeMultiplier = 1;
        this.swipeCallbackRight = onRightSwipe;
        this.swipeCallbackLeft = onLeftSwipe;
        this.textPaintLeft = new Paint();
        this.textPaintLeft = new Paint();
        textPaintLeft.setColor(Color.BLACK);
        textPaintLeft.setStyle(Paint.Style.FILL);
        textPaintLeft.setStrokeWidth(5);
        textPaintLeft.setTextSize(100);
        textPaintLeft.setTextAlign(Paint.Align.RIGHT);
        this.textPaintRight = new Paint();
        textPaintRight.setColor(Color.BLACK);
        textPaintRight.setStyle(Paint.Style.FILL);
        textPaintRight.setStrokeWidth(5);
        textPaintRight.setTextSize(100);
        textPaintRight.setTextAlign(Paint.Align.LEFT);
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

        if (swipeCallbackLeft != null)
        {
            if (direction == ItemTouchHelper.LEFT)
            {
                this.swipeCallbackLeft.onLeftSwipe(position);
            }
        }
        if (swipeCallbackRight != null)
        {
            if (direction == ItemTouchHelper.RIGHT)
            {
                this.swipeCallbackRight.onRightSwipe(position);
            }
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
                int boundRight = itemView.getRight() - 40;
                int boundLeft = boundRight - drawableWidth;
                int boundTop = itemView.getTop() + (itemView.getHeight() - drawableHeight) / 2;
                int boundBottom = itemView.getBottom() - (itemView.getHeight() - drawableHeight) / 2;

                drawableLeft.setBounds(boundLeft, boundTop, boundRight, boundBottom);
                drawableLeft.draw(canvas);

                if (textLeft != null)
                {
                    // if an icon is present we need to consider its size for the left margin
                    drawTextLeft(canvas, itemView, 1.5 * drawableWidth);
                }
            }
            else if (textLeft != null)
            {
                drawTextLeft(canvas, itemView, 40);
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
                int boundLeft = itemView.getLeft() + 40;
                int boundRight = boundLeft + drawableWidth;
                int boundTop = itemView.getTop() + (itemView.getHeight() - drawableHeight) / 2;
                int boundBottom = itemView.getBottom() - (itemView.getHeight() - drawableHeight) / 2;

                drawableRight.setBounds(boundLeft, boundTop, boundRight, boundBottom);
                drawableRight.draw(canvas);

                if (textRight != null)
                {
                    drawTextRight(canvas, itemView, 1.5 * drawableWidth);
                }
            }
            else if (textRight != null)
            {
                drawTextRight(canvas, itemView, 40);
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
     * Used to implement different  Swipe actions in {@link SwipeGestureManager}
     */
    public interface SwipeCallbackLeft
    {
        void onLeftSwipe(int position);
    }

    /**
     * Used to implement different  Swipe actions in {@link SwipeGestureManager}
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