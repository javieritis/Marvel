package com.marvel.characters.screens.home

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.paging.LoadState
import com.marvel.characters.R
import com.marvel.characters.databinding.ActivityHomeBinding
import com.marvel.characters.library.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import androidx.core.view.ViewCompat

import androidx.core.app.ActivityOptionsCompat

import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import com.marvel.characters.data.character.Character

import com.marvel.characters.library.extensions.showToast
import kotlinx.coroutines.flow.collectLatest

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvel.characters.screens.home.order_dialog.DialogOrderList


@ExperimentalSerializationApi
@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override val binding by viewBinding(ActivityHomeBinding::inflate)

    private val viewModel: HomeViewModel by viewModels()

    private val characterAdapter by lazy {
        CharacterAdapter { character, imageView ->
            openDetails(character, imageView)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { binding.rvCharacters.scrollToPosition(0) }
        binding.rvCharacters.adapter = characterAdapter

        binding.swipeRefresh.setColorSchemeResources(R.color.red)
        binding.swipeRefresh.setOnRefreshListener {
            characterAdapter.refresh()
        }

        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            /**
             * The best solution is use app:layout_behavior="@string/hide_bottom_view_on_scroll_behavior" in the FAB of the XML.
             * But the FAB is not hidden at the top of the recyclerview, and it does not need to be shown at the top.
             */
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                val firstItemIsVisible =
                    (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition() == 0
                if (dy > 0 || firstItemIsVisible) {
                    if (binding.fab.isShown) {
                        binding.fab.hide()
                    }
                } else if (dy < 0) {
                    if (!binding.fab.isShown) {
                        binding.fab.show()
                    }
                }
            }
        })

        binding.layoutError.buttonRetry.setOnClickListener { retry() }

        //Fetch de characters and we pass them to the adapter.
        uiScope.launch {
            viewModel.fetchCharacters().collectLatest {
                characterAdapter.submitData(lifecycle, it)
            }
        }

        //When the order type is changed, i refresh the call with param 'distinctUntilChanged' to invalidate cache.
        viewModel.orderTypeList.observe(this) {
            uiScope.launch {
                viewModel.fetchCharacters(update = true).collectLatest {
                    characterAdapter.submitData(lifecycle, it)
                }
            }
        }

        initAdapter()
    }

    private fun retry() {
        characterAdapter.retry()
    }

    private fun initAdapter() {
        characterAdapter.addLoadStateListener { loadState ->
            val haveItemsInAdapter = characterAdapter.itemCount > 0
            val noHaveItemsInAdapter = characterAdapter.itemCount == 0
            val isInitialLoad = loadState.source.refresh is LoadState.Loading
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && noHaveItemsInAdapter
            val isListError = loadState.source.refresh is LoadState.Error

            //Enable swipe refresh animation when the load initial is present.
            binding.swipeRefresh.isRefreshing =
                loadState.refresh is LoadState.Loading && !isInitialLoad

            //Disable swipe refresh when error is founded. Only use "Try again" button for function "try" instead "refresh".
            binding.swipeRefresh.isEnabled = !isListError

            //If the list is not available or filled, the FloatingActionButton is hidden.
            binding.fab.isVisible = !isListEmpty && !isListError && haveItemsInAdapter

            //The progressbar only shows when detect "Loading more".
            binding.progress.isVisible = loadState.append is LoadState.Loading

            binding.layoutLoading.root.isVisible = isInitialLoad && noHaveItemsInAdapter
            binding.layoutError.root.isVisible = isListError
//            binding.layoutEmpty.root.isVisible = !isInitialLoad && isListEmpty

            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                showToast(getString(R.string.error_generic))
            }
        }
    }

    private fun openDetails(character: Character, imageView: ImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            ViewCompat.getTransitionName(imageView)!!
        )

        navigator.openCharacterDetails(character, ViewCompat.getTransitionName(imageView), options.toBundle())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
                showOrderDialogList()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showOrderDialogList() {
        val dialog = DialogOrderList(viewModel.orderTypeList.value!!) {
            it?.let {
                viewModel.orderTypeList.value = it
            }
        }
        dialog.show(supportFragmentManager, DialogOrderList.TAG)
    }
}