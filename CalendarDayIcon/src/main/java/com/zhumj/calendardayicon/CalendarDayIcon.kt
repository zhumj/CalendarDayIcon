package com.zhumj.calendardayicon

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.abs

/**
 * @Author Created by zhumj
 * @Date 2022/6/29 11:51
 * @Description 文件描述
 */
class CalendarDayIcon(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var iconSize = 0// 整个大小
    private var lineWidth = 0f// 外框线宽度
    private var vLineWidth = 0f// 竖线宽度
    private var vLineHeight = 0f// 竖线高度
    private var vLinePadding = 0f// 竖线左右两侧间隔
    private var topLineWidth = 0f// 顶部横线宽度
    private var radius = 0f// 倒角半径
    @ColorInt
    private var lineColor = 0// 外框线颜色
    private var textStr = ""// 文字
    private var textSize = 0f// 文字大小
    @ColorInt
    private var textColor = 0// 文字颜色

    private val paint = Paint()// 外框画笔
    private val textPaint = Paint()// 文字画笔

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CalendarDayIcon)
        iconSize = a.getDimension(R.styleable.CalendarDayIcon_size, dp2px(40f)).toInt()
        lineWidth = a.getDimension(R.styleable.CalendarDayIcon_lineWidth, dp2px(2f))
        vLineWidth = a.getDimension(R.styleable.CalendarDayIcon_vLineWidth, dp2px(2f))
        vLineHeight = a.getDimension(R.styleable.CalendarDayIcon_vLineHeight, dp2px(12f))
        vLinePadding = a.getDimension(R.styleable.CalendarDayIcon_vLinePadding, dp2px(2f))
        radius = a.getDimension(R.styleable.CalendarDayIcon_radius, dp2px(1f))
        lineColor = a.getColor(R.styleable.CalendarDayIcon_lineColor, Color.BLACK)
        textStr = a.getString(R.styleable.CalendarDayIcon_text) ?: ""
        textSize = a.getDimension(R.styleable.CalendarDayIcon_textSize, TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            14f, context.resources.displayMetrics
        ))
        textColor = a.getColor(R.styleable.CalendarDayIcon_textColor, Color.BLACK)
        a.recycle()

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = lineWidth
        paint.color = lineColor

        textPaint.style = Paint.Style.FILL
        textPaint.textSize = textSize
        textPaint.color = textColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        topLineWidth = (iconSize - vLineWidth*2 - vLinePadding*4)/3
        setMeasuredDimension(iconSize, iconSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(getPath(), paint)

        val contentHeight = iconSize - vLineHeight - lineWidth
        val y = vLineHeight + (contentHeight - getTextHeight(textPaint))/2 + getTextBaseLine(textPaint)
        canvas?.drawText(textStr, iconSize/2f - getTextWidth(textPaint, textStr)/2, y, textPaint)
    }

    private fun getPath(): Path {
        val path = Path()
        // 画上边左线条
        path.moveTo(topLineWidth, vLineHeight/2f)
        path.lineTo(lineWidth/2f + radius, vLineHeight/2f)
        // 画左上角倒角
        path.quadTo(lineWidth/2f, vLineHeight/2f, lineWidth/2f, vLineHeight/2f + radius)
        // 画左边线条
        path.lineTo(lineWidth/2f, iconSize - lineWidth/2f - radius)
        // 画左下倒角
        path.quadTo(lineWidth/2f, iconSize - lineWidth/2f, lineWidth/2f + radius, iconSize - lineWidth/2f)
        // 画下边线条
        path.lineTo(iconSize - lineWidth/2f - radius, iconSize - lineWidth/2f)
        // 画右下倒角
        path.quadTo(iconSize - lineWidth/2f, iconSize - lineWidth/2f, iconSize - lineWidth/2f, iconSize - lineWidth/2f - radius)
        // 画右边线条
        path.lineTo(iconSize - lineWidth/2f, vLineHeight/2f + radius)
        // 画右上倒角
        path.quadTo(iconSize - lineWidth/2f, vLineHeight/2f, iconSize - lineWidth/2f - radius, vLineHeight/2f)
        // 画上边右线条
        path.lineTo(iconSize - topLineWidth, vLineHeight/2f)
        // 画上边右竖线
        path.moveTo(iconSize - topLineWidth - vLinePadding - vLineWidth/2, 0f)
        path.lineTo(iconSize - topLineWidth - vLinePadding - vLineWidth/2, vLineHeight)
        // 画上边中间横线
        path.moveTo(iconSize - topLineWidth - vLinePadding*2 - vLineWidth, vLineHeight/2f)
        path.lineTo(iconSize - topLineWidth*2 - vLinePadding*2 - vLineWidth, vLineHeight/2f)
        // 画上边左竖线
        path.moveTo(topLineWidth + vLinePadding + vLineWidth/2, 0f)
        path.lineTo(topLineWidth + vLinePadding + vLineWidth/2, vLineHeight)
        path.close()
        return path
    }

    /**
     * dp转px
     */
    private fun dp2px(dpValue: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return dpValue * scale
    }

    /**
     * 获取字体宽度
     */
    private fun getTextWidth(textPaint: Paint, text: String): Float {
        return textPaint.measureText(text)
    }

    /**
     * 获取字体高度
     */
    private fun getTextHeight(textPaint: Paint): Float {
        val fontMetrics: Paint.FontMetrics = textPaint.fontMetrics
        return fontMetrics.bottom - fontMetrics.top
    }

    /**
     * 获取字体基线
     */
    private fun getTextBaseLine(textPaint: Paint): Float {
        val fontMetrics: Paint.FontMetrics = textPaint.fontMetrics
        return abs(fontMetrics.top)
    }

    fun setSize(size: Int) {
        this.iconSize = size
        requestLayout()
    }

    fun setParam(
        lineWidth: Float = this.lineWidth,
        vLineWidth: Float = this.vLineWidth,
        vLineHeight: Float = this.vLineHeight,
        vLinePadding: Float = this.vLinePadding,
        radius: Float = this.radius,
        text: String = this.textStr,
        textSize: Float = this.textSize,
        @ColorInt lineColor: Int = this.lineColor,
        @ColorInt textColor: Int = this.textColor
    ) {
        this.lineWidth = lineWidth
        this.vLineWidth = vLineWidth
        this.vLineHeight = vLineHeight
        this.vLinePadding = vLinePadding
        this.radius = radius
        this.textStr = text
        this.textSize = textSize
        this.lineColor = lineColor
        this.textColor = textColor

        paint.strokeWidth = lineWidth
        paint.color = lineColor

        textPaint.textSize = textSize
        textPaint.color = textColor

        invalidate()
    }
}