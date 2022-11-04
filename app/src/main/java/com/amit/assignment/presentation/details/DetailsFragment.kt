package com.amit.assignment.presentation.details

import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.amit.assignment.R
import com.amit.assignment.databinding.FragmentDetailsBinding
import com.amit.assignment.domain.model.Comics
import com.amit.assignment.presentation.main.MainActivity
import com.amit.assignment.util.loadImageFull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var adapter: ComicsAdapter? = null
    private lateinit var viewDataBinding: FragmentDetailsBinding
    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
            FragmentDetailsBinding.inflate(inflater, container, false)
        var character = args.let {
            it.character
        }
        // Recyclerview Adapter initialisation
        initializeComponents(character.comics)

        // rendering data and toolbar setup
        with(character) {
            viewDataBinding.detailToolbarImageView.loadImageFull(thumbnail?.getUrl())
            (activity as MainActivity).setToolBarTitle(name)
            if (description.isEmpty()) {
                viewDataBinding.textDescription.visibility = View.GONE
            } else {
                viewDataBinding.textDescription.visibility = View.VISIBLE
            }
            viewDataBinding.textDescription.text = description
        }

        with(viewModel) {
            // Check if current selected character bookmarked or not
            checkFavoriteStatus(character.id)

            // bookmarked UI handling
            isFavorite.observe(
                requireActivity(),
                Observer {
                    it?.let {
                        viewDataBinding.detailFab.setImageResource(
                            if (it) R.drawable.ic_baseline_bookmark_24 else R.drawable.ic_baseline_bookmark_border_24
                        )
                    }
                }
            )
            // bookmark action
            viewDataBinding.detailFab.setOnClickListener {
                updateFavoriteStatus(character)
            }
        }
        return viewDataBinding.root
    }

    private fun initializeComponents(comics: Comics?) {
        adapter = ComicsAdapter()
        viewDataBinding.recyclerViewComics.adapter = adapter
        comics?.items?.let { adapter?.addData(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }
}
