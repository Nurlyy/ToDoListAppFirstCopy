package com.example.todolistappfirstcopy.tasks.all_tasks

import android.os.Bundle
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todolistappfirstcopy.MainActivity
import com.example.todolistappfirstcopy.R
import com.example.todolistappfirstcopy.databinding.FragmentAllTasksBinding
import com.example.todolistappfirstcopy.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AllTasksFragment(private val act: MainActivity) : Fragment(), AllTasksRecyclerViewAdapter.onItemClickListener {

    private lateinit var binding: FragmentAllTasksBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference : DatabaseReference
    private lateinit var activeTasksArrayList: ArrayList<Task>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activeTasksArrayList = arrayListOf()
        auth = Firebase.auth
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks/${auth.currentUser?.uid}")
        binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        getActiveTasksFromDatabase()
        return binding.root
    }

    private fun getActiveTasksFromDatabase():ArrayList<Task>{
        databaseReference.addValueEventListener(object: ValueEventListener{
            val currentDateTimeInMillis = System.currentTimeMillis()
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    activeTasksArrayList.clear()
                    for(taskSnapshot in snapshot.children){
                        val tempTask = taskSnapshot.getValue(Task::class.java)
                        if(tempTask?.isDeleted == false && !tempTask.isFinished){
                            if(currentDateTimeInMillis > tempTask.dateTimeInMillis){
                                activeTasksArrayList.add(tempTask)}
                            else{
                                tempTask?.isFinished = true
                                databaseReference.child(tempTask?.taskid!!).setValue(tempTask).addOnCompleteListener{
                                    if(it.isSuccessful){
                                        Toast.makeText(act, "One task is timed out", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                    binding.recyclerViewActiveTasksFragment.adapter = AllTasksRecyclerViewAdapter(activeTasksArrayList, this@AllTasksFragment)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return activeTasksArrayList
    }

    override fun onBtnDeleteClicked(position: Int) {
        val task = activeTasksArrayList[position]
        task.isDeleted = true
        updateTask(task, position)
    }

    private fun updateTask(task: Task, position: Int) {
        activeTasksArrayList.removeAt(position)
        databaseReference.child(task.taskid!!).setValue(task).addOnSuccessListener {
            Toast.makeText(act, "Successfully removed", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewActiveTasksFragment.adapter?.notifyDataSetChanged()
    }

    override fun onBtnFinishClicked(position: Int) {
        val task = activeTasksArrayList[position]
        task.isFinished = true
        updateTask(task, position)
    }

    override fun onEditClicked(position: Int) {
        val task = activeTasksArrayList[position]
    }


}