package com.amit.assignment.presentation.boomarks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amit.assignment.data.source.local.entity.CharacterEntity
import com.amit.assignment.databinding.ItemBookmarkBinding
import com.amit.assignment.util.loadImage
import java.util.*

class BookmarksAdapter() :
    PagingDataAdapter<CharacterEntity, BookmarksAdapter.CharacterEntityViewHolder>(
        CharacterEntityDiffCallback
    ) {

    val characterEntities: MutableList<CharacterEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterEntityViewHolder {
        val binding =
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterEntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterEntityViewHolder, position: Int) {
        holder.bind(characterEntities[position])
    }

    override fun getItemCount(): Int {
        return characterEntities.size
    }

    fun addData(list: List<CharacterEntity>) {
        this.characterEntities.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.characterEntities.clear()
        notifyDataSetChanged()
    }

    inner class CharacterEntityViewHolder(private val itemListBinding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(itemListBinding.root) {
        fun bind(characterEntity: CharacterEntity) {
            itemListBinding.imageViewCharacter.loadImage(
                characterEntity.imageURL,
                itemListBinding.progressBar
            )
            itemListBinding.cardCharacterNameTextView.text = characterEntity.name
            itemListBinding.cardCharacterDescriptionTextView.text = characterEntity.description
        }
    }
}

object CharacterEntityDiffCallback : DiffUtil.ItemCallback<CharacterEntity>() {

    override fun areContentsTheSame(
        oldCharacterEntity: CharacterEntity,
        newCharacterEntity: CharacterEntity
    ): Boolean {
        return oldCharacterEntity.name == newCharacterEntity.name
    }

    override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
        return oldItem == newItem
    }
}
