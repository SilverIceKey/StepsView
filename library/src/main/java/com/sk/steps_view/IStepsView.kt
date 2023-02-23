package com.sk.steps_view

/**
 * 获取界面参数接口
 */
interface IStepsView {
    /**
     * 获取步骤界面参数
     */
    fun getStepViewParams(stepStatus: StepStatus):StepViewParams
}