package net.fezzed.mviplayground.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.fezzed.mviplayground.databinding.HomeFragmentBinding
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleOneTimeEvent

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.searchPeopleStore.oneTimeEvent.observe(this,
            { toast ->
                if (SearchPeopleOneTimeEvent.ErrorWhenTryingToSearch == toast) {
                    showToast()
                }
            })
    }

    private fun showToast() {
        Toast.makeText(context, "Query search error.", Toast.LENGTH_SHORT).show()
    }

}