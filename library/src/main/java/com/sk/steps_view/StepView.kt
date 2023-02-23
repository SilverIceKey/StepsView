package com.sk.steps_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setPadding

/**
 * 单步骤
 */
class StepView(context: Context, private val iStepsView: IStepsView) : FrameLayout(context) {
    /**
     * 步骤序号
     */
    private var stepNo: TextView

    /**
     * 步骤序号容器
     */
    private var stepNoLayout: FrameLayout

    /**
     * 步骤名称
     */
    private var stepName: TextView

    /**
     * 步骤连接线
     */
    private var stepConnectLine: View

    /**
     * 步骤连接线
     */
    private var stepConnectLineLayout: FrameLayout

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.step_item, this)
        stepNo = view.findViewById(R.id.step_no)
        stepNoLayout = view.findViewById(R.id.step_no_layout)
        stepName = view.findViewById(R.id.step_name)
        stepConnectLine = view.findViewById(R.id.step_connect_line)
        stepConnectLineLayout = view.findViewById(R.id.step_connect_line_layout)

    }

    /**
     * 设置数据和间距
     */
    fun setData(stepItem: StepItem, showConnectLine: Boolean) {
        if (!showConnectLine) {
            stepConnectLine.visibility = View.GONE
        } else {
            stepConnectLine.visibility = View.VISIBLE
        }
        stepNo.text = stepItem.stepNo
        stepName.text = stepItem.stepName
        val stepViewParams = iStepsView.getStepViewParams(stepItem.stepStatus)
        setViewParams(stepViewParams)
        setViewRule(stepViewParams)

    }

    /**
     * 设置控件参数
     */
    private fun setViewParams(stepViewParams: StepViewParams) {
        setStepNoViewParams(stepViewParams)
        setStepNameViewParams(stepViewParams)
        setStepConnectLineViewParams(stepViewParams)
    }

    /**
     * 设置步骤连接线界面参数
     */
    private fun setStepConnectLineViewParams(stepViewParams: StepViewParams) {
        stepConnectLine.setBackgroundColor(stepViewParams.stepConnectLineColor)
        val stepConnectLineLayoutParams =
            stepConnectLine.layoutParams as LayoutParams
        stepConnectLineLayoutParams.width = stepViewParams.stepConnectLineWidth.toInt()
        stepConnectLineLayoutParams.height = stepViewParams.stepConnectLineHeight.toInt()
        stepConnectLine.layoutParams = stepConnectLineLayoutParams
        //连接线容器
        val stepConnectLineLayoutLayoutParams =
            stepConnectLineLayout.layoutParams as RelativeLayout.LayoutParams
        stepConnectLineLayoutLayoutParams.leftMargin =
            stepViewParams.stepAndConnectLinePadding.toInt()
        stepConnectLineLayoutLayoutParams.rightMargin =
            stepViewParams.stepAndConnectLinePadding.toInt()
        stepConnectLineLayout.layoutParams = stepConnectLineLayoutLayoutParams
    }

    /**
     * 设置步骤数界面参数
     */
    private fun setStepNoViewParams(stepViewParams: StepViewParams) {
        stepNo.textSize = stepViewParams.stepTextSize
        stepNo.setBackgroundResource(stepViewParams.stepBackground)
        stepNo.setTextColor(stepViewParams.stepTextColor)
        stepNo.setPadding(stepViewParams.stepPadding.toInt())
        stepNo.measure(measuredWidth, measuredHeight)
        //步骤数容器
        val stepNoLayoutLayoutParams = stepNoLayout.layoutParams as RelativeLayout.LayoutParams
        val stepNoLayoutParams = stepNo.layoutParams as LayoutParams
        if (stepNoLayoutParams.width > stepNoLayoutParams.height) {
            stepNoLayoutParams.height =
                stepNoLayoutParams.width + stepViewParams.stepPadding.toInt()
            stepNoLayoutParams.width = stepNoLayoutParams.width + stepViewParams.stepPadding.toInt()
        } else {
            stepNoLayoutParams.width =
                stepNoLayoutParams.height + stepViewParams.stepPadding.toInt()
            stepNoLayoutParams.height =
                stepNoLayoutParams.height + stepViewParams.stepPadding.toInt()
        }
        stepNoLayout.layoutParams = stepNoLayoutLayoutParams
        stepNoLayout.measure(measuredWidth, measuredHeight)
    }

    /**
     * 设置步骤名称界面参数
     */
    private fun setStepNameViewParams(stepViewParams: StepViewParams) {
        stepName.setTextColor(stepViewParams.stepNameTextColor)
        stepName.textSize = stepViewParams.stepNameTextSize
        val stepNameLayoutParams = stepName.layoutParams as RelativeLayout.LayoutParams
        stepNameLayoutParams.topMargin = stepViewParams.stepAndNamePadding.toInt()
        stepName.layoutParams = stepNameLayoutParams
        stepName.measure(measuredWidth, measuredHeight)
    }

    /**
     * 设置界面规则
     */
    private fun setViewRule(stepViewParams: StepViewParams) {
        val stepNameLayoutParams = stepName.layoutParams as RelativeLayout.LayoutParams
        val stepNoLayoutLayoutParams = stepNoLayout.layoutParams as RelativeLayout.LayoutParams
        val stepConnectLineLayoutLayoutParams =
            stepConnectLineLayout.layoutParams as RelativeLayout.LayoutParams
        when (stepViewParams.stepNameToNoGravity) {
            StepViewParams.LEFT -> {
                stepNoLayoutLayoutParams.addRule(RelativeLayout.RIGHT_OF, stepName.id)
                stepNameLayoutParams.rightMargin = stepViewParams.stepAndNamePadding.toInt()
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.RIGHT_OF, stepNoLayout.id)
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.ALIGN_TOP, stepNoLayout.id)
                stepConnectLineLayoutLayoutParams.addRule(
                    RelativeLayout.ALIGN_BOTTOM,
                    stepNoLayout.id
                )
                if (stepNoLayout.measuredHeight + (stepViewParams.stepPadding.toInt() * 2) > stepName.measuredHeight) {
                    stepNameLayoutParams.addRule(RelativeLayout.ALIGN_TOP, stepNoLayout.id)
                    stepNameLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, stepNoLayout.id)
                } else {
                    stepNoLayoutLayoutParams.addRule(RelativeLayout.ALIGN_TOP, stepName.id)
                    stepNoLayoutLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, stepName.id)
                }
            }
            StepViewParams.TOP -> {
                stepNoLayoutLayoutParams.addRule(RelativeLayout.BELOW, stepName.id)
                stepNameLayoutParams.bottomMargin = stepViewParams.stepAndNamePadding.toInt()
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.RIGHT_OF, stepNoLayout.id)
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.ALIGN_TOP, stepNoLayout.id)
                stepConnectLineLayoutLayoutParams.addRule(
                    RelativeLayout.ALIGN_BOTTOM,
                    stepNoLayout.id
                )
                if (stepNoLayout.measuredWidth + (stepViewParams.stepPadding.toInt() * 2) > stepName.measuredWidth) {
                    stepNameLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, stepNoLayout.id)
                    stepNameLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, stepNoLayout.id)
                } else {
                    stepNoLayoutLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, stepName.id)
                    stepNoLayoutLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, stepName.id)
                }
            }
            StepViewParams.RIGHT -> {
                stepNameLayoutParams.addRule(RelativeLayout.RIGHT_OF, stepNoLayout.id)
                stepNameLayoutParams.leftMargin = stepViewParams.stepAndNamePadding.toInt()
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.RIGHT_OF, stepName.id)
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.ALIGN_TOP, stepName.id)
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, stepName.id)
                if (stepNoLayout.measuredHeight + (stepViewParams.stepPadding.toInt() * 2) > stepName.measuredHeight) {
                    stepNameLayoutParams.addRule(RelativeLayout.ALIGN_TOP, stepNoLayout.id)
                    stepNameLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, stepNoLayout.id)
                } else {
                    stepNoLayoutLayoutParams.addRule(RelativeLayout.ALIGN_TOP, stepName.id)
                    stepNoLayoutLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, stepName.id)
                }
            }
            StepViewParams.BOTTOM -> {
                stepNameLayoutParams.addRule(RelativeLayout.BELOW, stepNoLayout.id)
                stepNameLayoutParams.topMargin = stepViewParams.stepAndNamePadding.toInt()
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.RIGHT_OF, stepNoLayout.id)
                stepConnectLineLayoutLayoutParams.addRule(RelativeLayout.ALIGN_TOP, stepNoLayout.id)
                stepConnectLineLayoutLayoutParams.addRule(
                    RelativeLayout.ALIGN_BOTTOM,
                    stepNoLayout.id
                )
                if (stepNoLayout.measuredWidth + (stepViewParams.stepPadding.toInt() * 2) > stepName.measuredWidth) {
                    stepNameLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, stepNoLayout.id)
                    stepNameLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, stepNoLayout.id)
                } else {
                    stepNoLayoutLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, stepName.id)
                    stepNoLayoutLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, stepName.id)
                }
            }
        }
        stepName.layoutParams = stepNameLayoutParams
        stepNoLayout.layoutParams = stepNoLayoutLayoutParams
        stepConnectLineLayout.layoutParams = stepConnectLineLayoutLayoutParams
    }
}