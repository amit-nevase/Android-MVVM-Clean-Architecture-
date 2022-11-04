package com.amit.assignment.presentation.list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.* // ktlint-disable no-wildcard-imports
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amit.assignment.R
import com.amit.assignment.databinding.FragmentListBinding
import com.amit.assignment.domain.model.Character
import com.amit.assignment.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), OnCharacterAdapterListener {

    private var adapter: CharactersAdapter? = null
    private val listViewModel: ListViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentListBinding
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        listViewModel.loadCharacters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentListBinding.inflate(inflater, container, false)
        initializeComponents()

        // progressbar handling
        listViewModel.isLoad.observe(
            viewLifecycleOwner,
            Observer {
                it?.let { visibility ->
                    viewDataBinding.progressBar.visibility =
                        if (visibility) View.GONE else View.VISIBLE
                    viewDataBinding.constraintErrorView.visibility =
                        if (visibility) View.GONE else View.VISIBLE
                }
            }
        )

        // consuming data from remote api and integrating with lost
        listViewModel.characterResponseLiveData.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    adapter?.addData(it)
                    adapter?.notifyDataSetChanged()
                    viewDataBinding.swipeRefreshLayout.isRefreshing = false
                    isLoading = false
                    viewDataBinding.progressBar.visibility = View.GONE
                    viewDataBinding.llProgressBarPaging.visibility = View.GONE
                }
            }
        )

        // Handling error response
        listViewModel.errorResponseLiveData.observe(
            viewLifecycleOwner,
            Observer {
                viewDataBinding.constraintErrorView.visibility = View.VISIBLE
                viewDataBinding.progressBar.visibility = View.GONE
                viewDataBinding.swipeRefreshLayout.isRefreshing = false
                viewDataBinding.llProgressBarPaging.visibility = View.GONE
            }
        )
        viewDataBinding.swipeRefreshLayout.setOnRefreshListener(refreshListener)

        // paging for continuous offset
        viewDataBinding.fragmentCharactersListRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading) {
                        val layoutManager =
                            viewDataBinding.fragmentCharactersListRecyclerView.layoutManager as GridLayoutManager
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter?.characters?.size!! - 1) {
                            viewDataBinding.llProgressBarPaging.visibility = View.VISIBLE
                            listViewModel.loadCharacters()
                            isLoading = true
                        }
                    }
                }
            })

        viewDataBinding.bookMarksFab.setOnClickListener {
            val action =
                ListFragmentDirections.actionCharactersListFragmentToBookmarksFragment()
            findNavController().navigate(action)
        }

        return viewDataBinding.root
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewDataBinding.swipeRefreshLayout.isRefreshing = true
        adapter?.clearData()
        listViewModel.setNextPosition(0)
        listViewModel.loadCharacters()
    }

    private fun initializeComponents() {
        adapter = CharactersAdapter(this)
        viewDataBinding.fragmentCharactersListRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.options_menu, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        searchView.isIconifiedByDefault = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return true
            }
        })

        // handle expansion and collapse effect
        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                searchView.requestFocus()
                Handler(Looper.getMainLooper()).post {
                    searchView.requestFocus()
                    val imm =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.showSoftInput(searchView.findFocus(), 0)
                }
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                searchView.clearFocus()
                return true
            }
        })
    }

    override fun onCharacterListener(character: Character) {
        openCharacterDetails(character)
    }

    // passing character instance to detail page to show detailed info about character
    private fun openCharacterDetails(character: Character?) {
        val action =
            ListFragmentDirections.actionCharactersListFragmentToCharacterDetailsFragment(
                character!!
            )
        findNavController().navigate(action)
    }

    // reset toolbar title
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolBarTitle(getString(R.string.app_name))
    }
}
