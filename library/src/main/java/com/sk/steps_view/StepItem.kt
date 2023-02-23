package com.sk.steps_view

/**
 * 步骤数据
 */
class StepItem {
    /**
     * 步骤序号
     */
    var stepNo: String = ""

    /**
     * 步骤名称
     */
    var stepName: String = ""

    /**
     * 步骤状态
     */
    var stepStatus: StepStatus = StepStatus.UNDO
}
