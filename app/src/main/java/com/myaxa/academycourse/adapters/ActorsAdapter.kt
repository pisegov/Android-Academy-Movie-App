package com.myaxa.academycourse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.myaxa.academycourse.R
import com.myaxa.academycourse.model.Actor

class ActorsAdapter() :
        RecyclerView.Adapter<ActorsViewHolder>() {

    private var actors = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return ActorsViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.onBind(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun bindItems(newActors: List<Actor>?) {
        if (newActors != null) {
            actors = newActors
        }
        notifyDataSetChanged()
    }
}

class ActorsViewHolder(itemView: View) : DataViewHolder(itemView) {
    private val avatar: ImageView = itemView.findViewById<ImageView>(R.id.iv_actors_avatar)
    private val name: TextView = itemView.findViewById<TextView>(R.id.tv_actors_name)

    fun onBind(actor: Actor) {
        Glide.with(context)
                .load(actor.imageUrl)
                .apply(imageOption)
                .into(avatar)

        name.text = actor.name
    }


    companion object {
        private val imageOption = RequestOptions()
                .placeholder(R.drawable.actor1)
                .fallback(R.drawable.actor1)
                .transforms(CenterCrop(), RoundedCorners(10))

    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

