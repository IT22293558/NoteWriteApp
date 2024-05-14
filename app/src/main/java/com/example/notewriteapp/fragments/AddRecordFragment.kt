package com.example.notewriteapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.notewriteapp.MainActivity
import com.example.notewriteapp.R
import com.example.notewriteapp.databinding.FragmentAddRecordBinding
import com.example.notewriteapp.model.Record
import com.example.notewriteapp.viewmodel.RecordViewModel


class AddRecordFragment : Fragment(R.layout.fragment_add_record),MenuProvider {

    private var addRecordBinding:FragmentAddRecordBinding? = null
    private val binding get() = addRecordBinding!!

    private lateinit var recordsViewModel: RecordViewModel
    private lateinit var addRecordView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addRecordBinding = FragmentAddRecordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        recordsViewModel = (activity as MainActivity).recordViewModel
        addRecordView = view
    }
    private fun saveRecord(view: View){
        val recordTitle = binding.addRecordTitle.text.toString().trim()
        val recordDesc = binding.addRecordDesc.text.toString().trim()

        if (recordTitle.isNotEmpty()){
            val record = Record(0,recordTitle,recordDesc)
            recordsViewModel.addRecord(record)

            Toast.makeText(addRecordView.context, "Note Saved",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFrag,false)
        }else{
            Toast.makeText(addRecordView.context, "Please enter note title",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_record_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.save_menu ->{
                saveRecord(addRecordView)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addRecordBinding = null
    }
}