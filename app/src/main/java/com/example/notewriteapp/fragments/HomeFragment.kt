package com.example.notewriteapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notewriteapp.MainActivity
import com.example.notewriteapp.R
import com.example.notewriteapp.adapter.RecordAdapter
import com.example.notewriteapp.databinding.FragmentHomeBinding
import com.example.notewriteapp.model.Record
import com.example.notewriteapp.viewmodel.RecordViewModel


class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener,MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private  val binding get() = homeBinding!!

    //
    private lateinit var recordsViewModel : RecordViewModel
    private lateinit var recordAdapter: RecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        recordsViewModel = (activity as MainActivity).recordViewModel
        setupHomeRecyclerView()


        binding.addRecordFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment2_to_addRecordFragment3)
        }
    }

    private fun updateUI(record:List<Record>?){
        if (record != null){
            if (record.isNotEmpty()){
                binding.emptyNoteImg.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            }else{
                binding.emptyNoteImg.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView(){
        recordAdapter = RecordAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = recordAdapter
        }

        activity?.let {
            recordsViewModel.getAllRecords().observe(viewLifecycleOwner){record ->
                recordAdapter.differ.submitList(record)
                updateUI(record)
            }
        }
    }

    private fun searchRecord(query: String?){
        val searchQuery = "%$query"

        recordsViewModel.searchRecord(searchQuery).observe(this){list ->
            recordAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchRecord(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}




