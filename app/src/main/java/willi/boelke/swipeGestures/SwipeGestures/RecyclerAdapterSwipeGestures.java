package willi.boelke.swipeGestures.SwipeGestures;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Here the animation of the moving RecyclerItem adn the colour behind it is implemented
 * Also the methods of the implemented interfaces will be called.
 *
 * The plan is to make it possible that only one f the two (left and right swipe) interfaces can be passed
 * and then only the one swipe gesture will be possible.
 * That doesn't completely works at the moment
 */
public class RecyclerAdapterSwipeGestures extends ItemTouchHelper.SimpleCallback
{

    //------------Instance Variables------------

    /**
     * Colour to be shown on left swipe
     */
    private final ColorDrawable redBackground;
    /**
     * Colour to be shown on right swipe
     */
    private final ColorDrawable greenBackground;
    /**
     * The interface which implements the code which will be executed on the left swipe
     */
    private SwipeCallbackLeft swipeCallbackLeft;
    /**
     * The interface which implements the code which will be executed on the right swipe
     */
    private SwipeCallbackRight swipeCallbackRight;
    /**
     * The Colour which will be drawn
     * (its one of the above)
     */
    private ColorDrawable actualIColor;


    //------------Constructors------------

    /**
     * Public Constructor to just implement the LeftSwipe
     * @param onLeftSwipe
     */
    public RecyclerAdapterSwipeGestures(SwipeCallbackLeft onLeftSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        actualIColor = new ColorDrawable(Color.GREEN);
        redBackground = new ColorDrawable(Color.RED);
        greenBackground = new ColorDrawable(Color.GREEN);
        this.swipeCallbackLeft = onLeftSwipe;
        this.swipeCallbackRight = null;
    }

    /**
     * Public Constructor to just implement the RightSwipe
     * @param onRightSwipe
     */
    public RecyclerAdapterSwipeGestures(SwipeCallbackRight onRightSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        actualIColor = new ColorDrawable(Color.GREEN);
        redBackground = new ColorDrawable(Color.RED);
        greenBackground = new ColorDrawable(Color.GREEN);
        this.swipeCallbackRight = onRightSwipe;
        this.swipeCallbackLeft = null;
    }


    /**
     * Public Constructor to implement both swipe directions
     *
     * @param onLeftSwipe
     */
    public RecyclerAdapterSwipeGestures(SwipeCallbackRight onRightSwipe, SwipeCallbackLeft onLeftSwipe)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        actualIColor = new ColorDrawable(Color.GREEN);
        redBackground = new ColorDrawable(Color.RED);
        greenBackground = new ColorDrawable(Color.GREEN);
        this.swipeCallbackRight = onRightSwipe;
        this.swipeCallbackLeft = onLeftSwipe;
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

        if (swipeCallbackLeft != null) {
            if (direction == ItemTouchHelper.LEFT) {
                this.swipeCallbackLeft.onLeftSwipe(position);
            }
        }
        if (swipeCallbackRight != null) {
            if (direction == ItemTouchHelper.RIGHT) {
                this.swipeCallbackRight.onRightSwipe(position);
            }
        }
    }


    @Override
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 0;


        // Swiping to the right
        if (dX > 0) {
            if (swipeCallbackRight != null) {
                actualIColor = redBackground;
                actualIColor.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());

                actualIColor.draw(canvas);
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        // Swiping to the left
        else if (dX < 0) {
            if (swipeCallbackLeft != null) {
                actualIColor = greenBackground;
                actualIColor.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());

                actualIColor.draw(canvas);
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        } else {
            actualIColor.setBounds(0, 0, 0, 0);
        }

    }
}
