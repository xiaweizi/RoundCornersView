上周接到个小需求，就是让一张图片的右下角为圆角，然后百度了一下，发现 `Glide` 直接支持对图片进行操作，只要继承 `BitmapTransformation `,或者实现 `Transformation`的接口，就可以获得原图的 `Bitmap`对象，通过 `Canvas`对图片重新绘制，最后 `Glide.with(this).load(R.drawable.test).asBitmap().transform(transformation).into(mImageView);`传入新建的`transformation`对象即可。

这里推荐`GitHub`上的一个开源库，完全满足日常需求:[glide-transformations](https://github.com/wasabeef/glide-transformations),里面有各种各样的对图片的处理，很是强大。

不过实际使用的过程中，我倒是发现一个小问题...假如只有右下角是圆角的情况下，当服务器返回的是一张半透明的图片时，结果就成了这样:

![最终结果](http://upload-images.jianshu.io/upload_images/4043475-36eb01d918e79ca6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![](http://upload-images.jianshu.io/upload_images/4043475-0335ab7c9965900c.gif?imageMogr2/auto-orient/strip)


原图其实是一张半透明矩形图，这就很尴尬，其实从图中不难发现作者的绘制思路，首先看一下作者的源码部分：

    canvas.drawRoundRect(new RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,mRadius, paint);
    canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
    canvas.drawRect(new RectF(right - mRadius, mMargin, right, bottom - mRadius), paint);

并附上本人的草图：

![作者的.png](http://upload-images.jianshu.io/upload_images/4043475-7baad3b9b576f3e5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 第一步，绘制一个圆1
- 第二步绘制剩下的两个矩形2和3.

这样给人的感觉，右下角就有了圆角效果，举一反三，其他的四个角也是这样绘制。不过这样有个缺点，作者这样虽然可以实现，但是会有重叠的部分，假如跟我一样是半透明的图片，那么出来的效果就很差强人意。

于是，我换个方法，有点大同小异，只是稍微做了点优化，如下图:

![本人的.png](http://upload-images.jianshu.io/upload_images/4043475-9422183b9fae1da5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

一目了然，其实我就是将作者的圆换成了弧，这样，半透明的图片就可以完美的拥有了圆角。既然这样，那么就重新写一个实现圆角的lib吧，其实很简单，这是运行过后的效果：

![result.png](http://upload-images.jianshu.io/upload_images/4043475-8c2be5bd03d7a674.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

很可爱吧！

附上 `GitHub`的项目地址：[使用Glide实现圆角效果](https://github.com/xiaweizi/RoundCornersView)

#### 使用起来就很简单了：

1. 在你的项目根 `build.gradle` 文件下添加 `maven`地址

         allprojects {
		  repositories {
			...
			maven { url 'https://jitpack.io' }
		  }
        }
2. 在`module`对应的`build.gradle`下添加依赖

	    dependencies {
	        compile 'com.github.xiaweizi:RoundCornersView:v1.0'
	    }
3. 在代码中使用(需要结合`Glide`搭配使用)

            RoundCornersTransformation transformation =
                    new RoundCornersTransformation(MainActivity.this,
                                                   dip2px(25),
                                                   mList.get(position).type);
            Glide.with(MainActivity.this)
                 .load(mList.get(position).resId)
                 .bitmapTransform(transformation)
                 .into(holder.mImageView);

创建`transformation `对象需传入圆角半径和指定圆角的位置,有以下几种可供选择，当然如果你有别的需要，相信您同样可以举一反三实现的。

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