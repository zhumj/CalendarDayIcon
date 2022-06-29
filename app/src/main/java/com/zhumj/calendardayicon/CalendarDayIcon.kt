package com.zhumj.calendardayicon

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

/**
 * @Author Created by zhumj
 * @Date 2022/6/29 11:51
 * @Description 文件描述
 */
class CalendarDayIcon(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var iconSize = 0// 整个大小
    private var lineWidth = 0f// 外框线宽度
    private var verticalLineWidth = 0f// 竖线宽度
    private var verticalLineHeight = 0f// 竖线高度
    private var verticalLinePadding = 0f// 竖线左右两侧间隔
    private var topLineWidth = 0f// 顶部横线宽度
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
        iconSize = a.getDimension(
            R.styleable.CalendarDayIcon_size,
            DensityUtil.dp2px(context, 40f)
        ).toInt()
        lineWidth = a.getDimension(
            R.styleable.CalendarDayIcon_lineWidth,
            DensityUtil.dp2px(context, 2f)
        )
        verticalLineWidth = a.getDimension(
            R.styleable.CalendarDayIcon_verticalLineWidth,
            DensityUtil.dp2px(context, 2f)
        )
        verticalLineHeight = a.getDimension(
            R.styleable.CalendarDayIcon_verticalLineHeight,
            DensityUtil.dp2px(context, 12f)
        )
        verticalLinePadding = a.getDimension(
            R.styleable.CalendarDayIcon_verticalLinePadding,
            DensityUtil.dp2px(context, 2f)
        )
        lineColor = a.getColor(R.styleable.CalendarDayIcon_lineColor, Color.BLACK)
        textStr = a.getString(R.styleable.CalendarDayIcon_text) ?: ""
        textSize = a.getDimension(
            R.styleable.CalendarDayIcon_textSize,
            DensityUtil.sp2px(getContext(), 14f),
        )
        textColor = a.getColor(R.styleable.CalendarDayIcon_textColor, Color.BLACK)
        a.recycle()

        topLineWidth = (iconSize - verticalLineWidth*2 - verticalLinePadding*4)/3

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = lineWidth
        paint.color = lineColor

        textPaint.style = Paint.Style.FILL
        textPaint.textSize = textSize
        textPaint.color = textColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(iconSize, iconSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(getPath(), paint)

        val contentHeight = iconSize - verticalLineHeight - lineWidth
        val y = verticalLineHeight + (contentHeight - TextUtil.getTextHeight(textPaint))/2 + TextUtil.getTextBaseLine(textPaint)
        canvas?.drawText(textStr, iconSize/2f - TextUtil.getTextWidth(textPaint, textStr)/2, y, textPaint)
    }

    private fun getPath(): Path {
        val path = Path()
        path.moveTo(topLineWidth, verticalLineHeight/2f)
        path.lineTo(lineWidth/2f, verticalLineHeight/2f)
        path.lineTo(lineWidth/2f, iconSize - lineWidth/2f)
        path.lineTo(iconSize - lineWidth/2f, iconSize - lineWidth/2f)
        path.lineTo(iconSize - lineWidth/2f, verticalLineHeight/2f)
        path.lineTo(iconSize - topLineWidth, verticalLineHeight/2f)
        path.moveTo(iconSize - topLineWidth - verticalLinePadding - verticalLineWidth/2, 0f)
        path.lineTo(iconSize - topLineWidth - verticalLinePadding - verticalLineWidth/2, verticalLineHeight)
        path.moveTo(iconSize - topLineWidth - verticalLinePadding*2 - verticalLineWidth, verticalLineHeight/2f)
        path.lineTo(iconSize - topLineWidth*2 - verticalLinePadding*2 - verticalLineWidth, verticalLineHeight/2f)
        path.moveTo(topLineWidth + verticalLinePadding + verticalLineWidth/2, 0f)
        path.lineTo(topLineWidth + verticalLinePadding + verticalLineWidth/2, verticalLineHeight)
        path.close()
        return path
    }

    fun setText(text: String) {
        this.textStr = text
        invalidate()
    }
}