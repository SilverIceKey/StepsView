package com.sk.steps_view

import androidx.annotation.ColorInt

/**
 * 步骤界面参数
 */
class StepViewParams {
    /**
     * 步骤数文字大小
     */
    var stepTextSize: Float = 0f

    /**
     * 步骤数文字颜色
     */
    @ColorInt
    var stepTextColor: Int = 0

    /**
     * 步骤背景
     */
    var stepBackground: Int = 0

    /**
     * 步骤数内边距
     */
    var stepPadding: Float = 0f

    /**
     * 步骤名称文字大小
     */
    var stepNameTextSize: Float = 0f

    /**
     * 步骤名称文字颜色
     */
    @ColorInt
    var stepNameTextColor: Int = 0

    /**
     * 步骤数和名称之间的边距
     */
    var stepAndNamePadding: Float = 0f

    /**
     * 步骤名称相对步骤数位置
     */
    var stepNameToNoGravity: Int = 0

    /**
     * 连接线宽度
     */
    var stepConnectLineWidth: Float = 0f

    /**
     * 连接线高度
     */
    var stepConnectLineHeight: Float = 0f

    /**
     * 步骤和连接线间距
     */
    var stepAndConnectLinePadding: Float = 0f

    /**
     * 连接线颜色
     */
    @ColorInt
    var stepConnectLineColor: Int = 0

    companion object {
        /**
         * 左边
         */
        const val LEFT: Int = 0

        /**
         * 上边
         */
        const val TOP: Int = 1

        /**
         * 右边
         */
        const val RIGHT: Int = 2

        /**
         * 下边
         */
        const val BOTTOM: Int = 3
    }
}