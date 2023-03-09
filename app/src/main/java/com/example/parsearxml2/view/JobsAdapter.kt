package com.example.parsearxml2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parsearxml2.R
import com.example.parsearxml2.databinding.ItemJobBinding
import com.example.parsearxml2.model.Job

class JobsAdapter(
    private val jobs:List<Job>,
    private val onClickJob:(Job)->Unit
):RecyclerView.Adapter<JobsAdapter.JobsViewHolder>() {


    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val item = jobs[position]
        holder.bind(item,onClickJob)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return JobsViewHolder(layoutInflater.inflate(R.layout.item_job,parent,false))
    }



    override fun getItemCount()= jobs.size



    class JobsViewHolder(view: View):RecyclerView.ViewHolder(view) {

        private val binding = ItemJobBinding.bind(view)

        fun bind(item:Job,onClickJob: (Job) -> Unit){
            binding.tvTitle.text = item.title
            binding.tvCompany.text = item.company
            binding.btnDetails.setOnClickListener { onClickJob(item) }

        }


    }




}