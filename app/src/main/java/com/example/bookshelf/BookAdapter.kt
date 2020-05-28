package com.example.bookshelf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.book_list.view.*

class BookAdapter(private var items: List<Book>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_list,parent,false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BookViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    class BookViewHolder (
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        private val mImage=itemView.ivBook
        private val mTitle=itemView.tvTitle
        private val mAuthor=itemView.tvAuthor

        fun bind(obBook: Book){
            mTitle.text=obBook.title
            mAuthor.text="By : "+obBook.author

            val requestOptions=RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(obBook.imageURL)
                .apply(RequestOptions().override(600, 150))
                .into(mImage)
        }
    }
}