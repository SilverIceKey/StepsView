package com.sk.sample_steps_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sk.sample_steps_view.databinding.ActivityMainBinding
import com.sk.steps_view.StepItem
import com.sk.steps_view.StepStatus

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mSteps: MutableList<StepItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        createStep()
        binding.stepsView.setSteps(mSteps)
        binding.previous.setOnClickListener {
            binding.stepsView.stepToPrevious()
        }
        binding.next.setOnClickListener {
            binding.stepsView.stepToNext()
        }
    }

    private fun createStep() {
        mSteps.add(StepItem().apply {
            this.stepNo = "1"
            this.stepName = "第一步"
            this.stepStatus = StepStatus.UNDO
        })
        mSteps.add(StepItem().apply {
            this.stepNo = "2"
            this.stepName = "第二步"
            this.stepStatus = StepStatus.DOING
        })
        mSteps.add(StepItem().apply {
            this.stepNo = "3"
            this.stepName = "第三步"
            this.stepStatus = StepStatus.UNDO
        })
    }
}