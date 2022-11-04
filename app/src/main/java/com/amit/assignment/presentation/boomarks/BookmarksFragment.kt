package com.amit.assignment.presentation.boomarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amit.assignment.R
import com.amit.assignment.databinding.FragmentBookmarksBinding
import com.amit.assignment.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
/** * To list bookmarked character from DB
* * */
@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private var adapter: BookmarksAdapter? = null
    private lateinit var viewDataBinding: FragmentBookmarksBinding

    private val viewModel: BookMarksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
            FragmentBookmarksBinding.inflate(inflater, container, false)
        (activity as MainActivity).setToolBarTitle(getString(R.string.bookmarks))
        initializeComponents()
        return viewDataBinding.root
    }

    private fun initializeComponents() {
        adapter = BookmarksAdapter()
        viewDataBinding.recyclerViewBookmarks.adapter = adapter
        val characters = viewModel.loadBookMarkedCharacters()
        adapter?.addData(characters)

        viewDataBinding.errorViewBookmarks.visibility =
            if (characters.isEmpty()) View.VISIBLE else View.GONE
    }
}
