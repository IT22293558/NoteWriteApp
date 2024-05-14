package com.example.notewriteapp.fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.example.notewriteapp.MainActivity
import com.example.notewriteapp.R
import com.example.notewriteapp.databinding.FragmentEditRecordBinding
import com.example.notewriteapp.model.Record
import com.example.notewriteapp.viewmodel.RecordViewModel


class EditRecordFragment : Fragment(R.layout.fragment_edit_record), MenuProvider {

    private var editRecordBinding: FragmentEditRecordBinding? = null
    private val binding get() = editRecordBinding!!

    private lateinit var recordsViewModel: RecordViewModel
    private lateinit var currentRecord: Record

    private val args: EditRecordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editRecordBinding = FragmentEditRecordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        recordsViewModel = (activity as MainActivity).recordViewModel
        currentRecord = args.record!!

        binding.editRecordTitle.setText(currentRecord.recordTitle)
        binding.editRecordDesc.setText(currentRecord.recordDesc)

        binding.editRecordFab.setOnClickListener {
            val recordTitle = binding.editRecordTitle.text.toString().trim()
            val recordDesc = binding.editRecordDesc.text.toString().trim()

            if(recordTitle.isNotEmpty()){
                val record = Record(currentRecord.id,recordTitle, recordDesc)
                recordsViewModel.updateRecord(record)
                view.findNavController().popBackStack(R.id.homeFrag,false)
            }else{
                Toast.makeText(context,"Please enter note title",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteRecord(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){_,_ ->
                recordsViewModel.deleteRecord(currentRecord)
                Toast.makeText(context,"Note Deleted",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFrag,false)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_note_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return  when(menuItem.itemId){
            R.id.delete_menu ->{
                deleteRecord()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editRecordBinding = null
    }
}