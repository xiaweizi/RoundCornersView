package com.xiaweizi.cornerslibrary;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.cornerslibrary.CornersProperty
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/08/21
 *     desc   :
 * </pre>
 */

public class CornersProperty {

    public enum CornerType {
        /** 所有角 */
        ALL,
        /** 左上 */
        LEFT_TOP,
        /** 左下 */
        LEFT_BOTTOM,
        /** 右上 */
        RIGHT_TOP,
        /** 右下 */
        RIGHT_BOTTOM,
        /** 左侧 */
        LEFT,
        /** 右侧 */
        RIGHT,
        /** 下侧 */
        BOTTOM,
        /** 上侧 */
        TOP,
    }

    private int mCornersRadius; // 圆角半径
    private int mCornersDiameter; // 圆角直径
    private CornerType mCornerType = CornerType.ALL; // 圆角类型

    public CornersProperty setCornersRadius(int radius) {
        this.mCornersRadius = radius;
        this.mCornersDiameter = 2 * radius;
        return this;
    }

    public CornersProperty setCornersType(CornerType type) {
        this.mCornerType = type;
        return this;
    }

    public int getCornersRadius() {
        return this.mCornersRadius;
    }

    public CornerType getCornerType() {
        return this.mCornerType;
    }

    public int getCornersDiameter() {
        return this.mCornersDiameter;
    }
}
