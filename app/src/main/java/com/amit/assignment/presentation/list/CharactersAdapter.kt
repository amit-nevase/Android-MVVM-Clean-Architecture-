package com.amit.assignment.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amit.assignment.databinding.ItemListBinding
import com.amit.assignment.domain.model.Character
import com.amit.assignment.util.loadImage
import java.util.*

class CharactersAdapter(val mListener: OnCharacterAdapterListener) :
    PagingDataAdapter<Character, CharactersAdapter.CharacterViewHolder>(CharacterDiffCallback),
    Filterable {

    val characters: MutableList<Character> = ArrayList()
    var filteredCharacters: MutableList<Character> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(filteredCharacters[position])
    }

    override fun getItemCount(): Int {
        return filteredCharacters.size
    }

    fun addData(list: List<Character>) {
//        this.characters.clear()
        this.characters.addAll(list)
        this.filteredCharacters.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.characters.clear()
        this.filteredCharacters.clear()
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(private val itemBinding: ItemListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(character: Character) {
            itemBinding.imageViewIcon.loadImage(
                character.thumbnail?.getUrl(),
                itemBinding.progressBar
            )
            itemBinding.textviewName.text = character.name
            itemBinding.root.setOnClickListener {
                mListener.onCharacterListener(character)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredCharacters = if (charString.isEmpty()) characters else {
                    val filteredList = ArrayList<Character>()
                    characters
                        .filter {
                            it.name!!.lowercase().contains(constraint.toString().lowercase())
                        }
                        .forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = filteredCharacters }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCharacters = if (results?.values == null) {
                    ArrayList()
                } else {
                    results.values as ArrayList<Character>
                }
                notifyDataSetChanged()
            }
        }
    }
}

object CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }
}
