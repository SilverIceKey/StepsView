package com.sk.steps_view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

/**
 * 步骤控件
 */
class StepsView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), IStepsView {
    /**
     * 步骤数字和背景间距
     */
    private var mStepPadding: Float = 0f

    /**
     * 未完成的步骤，步骤数背景
     */
    private var mStepBackgroundUndo: Int = 0

    /**
     * 未完成的步骤，步骤数文字颜色
     */
    @ColorInt
    private var mStepTextColorUndo: Int = 0

    /**
     * 未完成的步骤，步骤数文字大小
     */
    private var mStepTextSizeUndo: Float = 0f

    /**
     * 进行中的步骤，步骤数背景
     */
    private var mStepBackgroundDoing: Int = 0

    /**
     * 进行中的步骤，步骤数文字颜色
     */
    @ColorInt
    private var mStepTextColorDoing: Int = 0

    /**
     * 进行中的步骤，步骤数文字大小
     */
    private var mStepTextSizeDoing: Float = 0f

    /**
     * 已完成的步骤，步骤数背景
     */
    private var mStepBackgroundDone: Int = 0

    /**
     * 已完成的步骤，步骤数文字颜色
     */
    @ColorInt
    private var mStepTextColorDone: Int = 0

    /**
     * 已完成的步骤，步骤数文字大小
     */
    private var mStepTextSizeDone: Float = 0f

    /**
     * 步骤的连接线颜色
     */
    @ColorInt
    private var mStepConnectLineColor: Int = 0

    /**
     * 连接线宽度
     */
    private var mStepConnectLineWidth: Float = 0f

    /**
     * 连接线高度
     */
    private var mStepConnectLineHeight: Float = 0f

    /**
     * 步骤名称文字大小
     */
    private var mStepNameTextSize: Float = 0f

    /**
     * 步骤名称文字颜色
     */
    @ColorInt
    private var mStepNameTextColor: Int = Color.BLACK

    /**
     * 步骤和连接线之间的距离
     */
    private var mStepAndConnectLinePadding: Float = 0f

    /**
     * 步骤和名称之间的距离
     */
    private var mStepAndNamePadding: Float = 0f

    /**
     * 步骤名称相对于序号的位置
     * left top bottom right
     */
    private var mStepNameToNoGravity: Int = 0

    /**
     * 步骤数据
     */
    private var mStepItems: MutableList<StepItem> = mutableListOf()

    /**
     * 当前步骤
     */
    private var currentStep: Int = -1

    /**
     * 是否是内部跳转
     */
    private var isStepTo: Boolean = false

    init {
        setAttrs(attrs)
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    /**
     * 设置参数
     */
    private fun setAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepsView)
        mStepPadding =
            typedArray.getDimension(R.styleable.StepsView_step_padding, 0f)
        mStepBackgroundUndo = typedArray.getInteger(
            R.styleable.StepsView_step_background_undo,
            R.drawable.step_background_undo
        )
        mStepTextColorUndo =
            typedArray.getColor(R.styleable.StepsView_step_text_color_undo, Color.WHITE)
        mStepTextSizeUndo = typedArray.getDimension(R.styleable.StepsView_step_text_size_undo, 12f)
        mStepBackgroundDoing = typedArray.getInteger(
            R.styleable.StepsView_step_background_undo,
            R.drawable.step_background_doing
        )
        mStepTextColorDoing =
            typedArray.getColor(R.styleable.StepsView_step_text_color_doing, Color.WHITE)
        mStepTextSizeDoing =
            typedArray.getDimension(R.styleable.StepsView_step_text_size_doing, 12f)
        mStepBackgroundDone = typedArray.getInteger(
            R.styleable.StepsView_step_background_undo,
            R.drawable.step_background_done
        )
        mStepTextColorDone =
            typedArray.getColor(R.styleable.StepsView_step_text_color_done, Color.WHITE)
        mStepTextSizeDone = typedArray.getDimension(R.styleable.StepsView_step_text_size_done, 12f)
        mStepConnectLineColor =
            typedArray.getColor(
                R.styleable.StepsView_step_connect_line_color,
                ContextCompat.getColor(context, R.color.step_default_undo)
            )
        mStepConnectLineWidth =
            typedArray.getDimension(R.styleable.StepsView_step_connect_line_width, 100f)
        mStepConnectLineHeight =
            typedArray.getDimension(R.styleable.StepsView_step_connect_line_height, 4f)
        mStepNameTextColor =
            typedArray.getColor(
                R.styleable.StepsView_step_name_text_color,
                ContextCompat.getColor(context, R.color.step_default_name_text_color)
            )
        mStepNameTextSize = typedArray.getDimension(R.styleable.StepsView_step_name_text_size, 12f)
        mStepAndConnectLinePadding =
            typedArray.getDimension(R.styleable.StepsView_step_and_connect_line_padding, 10f)
        mStepAndNamePadding =
            typedArray.getDimension(R.styleable.StepsView_step_and_name_padding, 10f)
        mStepNameToNoGravity =
            typedArray.getInt(
                R.styleable.StepsView_step_name_to_no_gravity,
                StepViewParams.BOTTOM
            )
        typedArray.recycle()
    }

    /**
     * 设置步骤数据
     */
    fun setSteps(stepItems: MutableList<StepItem>) {
        this.mStepItems = stepItems
        if (!isStepTo) {
            currentStep = this.mStepItems.indexOfLast { it.stepStatus == StepStatus.DOING }
            for (stepIndex in 0 until currentStep) {
                mStepItems[stepIndex].stepStatus = StepStatus.DONE
            }
            isStepTo = false
        }
        updateViews()
    }

    /**
     * 更新视图
     */
    private fun updateViews() {
        removeAllViews()
        for (stepIndex in mStepItems.indices) {
            if (mStepItems[stepIndex].stepStatus == StepStatus.DOING) {
                currentStep = stepIndex
            }
            val stepView = StepView(context, this)
            stepView.setData(mStepItems[stepIndex], stepIndex < mStepItems.size - 1)
            addView(stepView)
        }
    }

    /**
     * 步骤到哪一步
     * 当输入小于1时默认为1
     * 当大于步骤数时默认为全部完成
     */
    fun stepTo(currentStep: Int) {
        if (currentStep > mStepItems.size - 1) {
            this.currentStep = mStepItems.size
            for (stepItem in mStepItems) {
                stepItem.stepStatus = StepStatus.DONE
            }
        } else {
            if (currentStep < 0) {
                this.currentStep = -1
                for (stepItem in mStepItems) {
                    stepItem.stepStatus = StepStatus.UNDO
                }
            } else {
                for (stepIndex in mStepItems.indices) {
                    if (stepIndex < currentStep) {
                        mStepItems[stepIndex].stepStatus = StepStatus.DONE
                    } else if (stepIndex == currentStep) {
                        mStepItems[stepIndex].stepStatus = StepStatus.DOING
                    } else {
                        mStepItems[stepIndex].stepStatus = StepStatus.UNDO
                    }
                }
            }
        }
        isStepTo = true
        setSteps(mStepItems)
    }

    /**
     * 步骤到下一步
     * 当输入小于1时默认为1
     * 当大于步骤数时默认为全部完成
     */
    fun stepToNext() {
        stepTo(currentStep + 1)
    }

    /**
     * 步骤到上一步
     */
    fun stepToPrevious() {
        stepTo(currentStep - 1)
    }

    override fun getStepViewParams(stepStatus: StepStatus): StepViewParams {
        val stepViewParams = StepViewParams()
        stepViewParams.stepPadding = mStepPadding
        stepViewParams.stepNameTextSize = mStepNameTextSize
        stepViewParams.stepNameTextColor = mStepNameTextColor
        stepViewParams.stepAndNamePadding = mStepAndNamePadding
        stepViewParams.stepNameToNoGravity = mStepNameToNoGravity
        stepViewParams.stepConnectLineWidth = mStepConnectLineWidth
        stepViewParams.stepConnectLineHeight = mStepConnectLineHeight
        stepViewParams.stepAndConnectLinePadding = mStepAndConnectLinePadding
        stepViewParams.stepConnectLineColor = mStepConnectLineColor
        when (stepStatus) {
            StepStatus.UNDO -> {
                stepViewParams.stepTextSize = mStepTextSizeUndo
                stepViewParams.stepTextColor = mStepTextColorUndo
                stepViewParams.stepBackground = mStepBackgroundUndo
            }
            StepStatus.DOING -> {
                stepViewParams.stepTextSize = mStepTextSizeDoing
                stepViewParams.stepTextColor = mStepTextColorDoing
                stepViewParams.stepBackground = mStepBackgroundDoing
            }
            StepStatus.DONE -> {
                stepViewParams.stepTextSize = mStepTextSizeDone
                stepViewParams.stepTextColor = mStepTextColorDone
                stepViewParams.stepBackground = mStepBackgroundDone
            }
        }
        return stepViewParams
    }
}