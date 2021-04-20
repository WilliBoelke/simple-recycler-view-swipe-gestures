package willi.boelke.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import willi.boelke.demo.R;


/**
 * A simple recycler view adapter with a OnClick interface
 */
public class RecyclerViewAdapterWithOnClick extends RecyclerView.Adapter<RecyclerViewAdapterWithOnClick.ViewHolder>
{



    //------------Instance Variables------------



    /**
     * The displayed ArrayList (here set in the MainActivity)
     */
    private ArrayList<String> items;
    /**
     * An implementation of the Adapters onClickListener Interface
     * to make clicks on the items possible
     */
    private OnItemClickListener onItemClickListener;




    //------------Constructors------------



    public RecyclerViewAdapterWithOnClick(ArrayList<String> list)
    {
        this.items = list;
    }



    //------------Recycler View Adapter------------


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_recycler_item, parent, false);
        ViewHolder articleViewHolder = new ViewHolder(view, this.onItemClickListener);
        return articleViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder articleViewHolder, int position)
    {
        String currentString = this.items.get(position);

        articleViewHolder.textView.setText(currentString);
    }



    @Override
    public int getItemCount()
    {
        return items.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.demo_text_view);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }



    //------------On Click------------


    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }


}
