package com.example.parsearxml2.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parsearxml2.viewmodel.JobsViewModel
import com.example.parsearxml2.databinding.FragmentJobsBinding
import com.example.parsearxml2.model.Job
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope


@AndroidEntryPoint
class JobsFragment : Fragment() {

    private var _binding : FragmentJobsBinding? = null
    private val binding get() = _binding!!

    private val jobsViewModel: JobsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            Log.i("Accion", "onViewCreated JobsFragments")
            jobsViewModel.onViewCreated()
            jobsViewModel.jobsLiveData.observe(viewLifecycleOwner, { jobs ->
                with(binding.recycleViewJobs) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = JobsAdapter(jobs) {
                        val intentDetail = Intent(context, DetailsActivity::class.java)
                        intentDetail.putExtra("extra", it)
                        startActivity(intentDetail)
                    }
                }

            })

            jobsViewModel.progressLiveData.observe(viewLifecycleOwner, { isLoading ->
                binding.pbLoading.isVisible = isLoading
                binding.recycleViewJobs.isVisible = !isLoading


            })


    }

    override fun onPause() {
        super.onPause()
        Log.i("Accion","onPause JobsFragments")

    }



}