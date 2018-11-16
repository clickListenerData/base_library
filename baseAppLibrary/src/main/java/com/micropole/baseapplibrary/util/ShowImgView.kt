package com.micropole.baseapplibrary.util

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.micropole.baseapplibrary.R
import com.micropole.baseapplibrary.widght.ImageChooseDialog
import java.io.File

/**
 * @ClassName       ShowImgView
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/16 15:55
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class ShowImgView(context: Context,attributeSet: AttributeSet) : LinearLayout(context,attributeSet) {

    var maxCount = 3  //最大图片数
    var maxLine = 1   //最大行数

    var isAdd = true
    var addDrawable : Int = 0
    var deleteDrawable : Int = 0
    var mImgList = arrayListOf<String>()
    private set
    get() {
        for (i in mImgList.iterator()){
            Log.i("img_list",i)
        }
        return field
    }

    var imageChooseHelper : ImageChooseHelper? = null
    var addAction : (view : View) -> Unit = {}
    set(value) {  //设置了回调后，不再调用默认对话框
        imageChooseHelper = null
        field = value
    }

    init {
        createAddView()
    }

    override fun addView(child: View?) {
        if (childCount > maxCount){
            return
        }
        if (childCount == maxCount){  //不可再 add image
            isAdd = false
        }
        mImgList.add("")
        removeViewAt(childCount - 1)
        val i = maxCount / maxLine  //每行显示的图片数
        val i1 = this.layoutParams.width / i  //图片 width
        val i2 = this.layoutParams.height / maxLine //图片 height
        val params = LinearLayout.LayoutParams(i1,i2)
        if (childCount + 1 < maxCount){
            params.rightMargin = 10
        }
        addView(child,params)
        if (isAdd){
            createAddView()
        }
    }

    override fun removeViewAt(index: Int) {
        mImgList.removeAt(index)
        super.removeViewAt(index)
        if (!isAdd){
            createAddView()
        }
    }

    override fun removeView(view: View?) {
        val i = indexOfChild(view)
        mImgList.removeAt(i)
        super.removeView(view)
        if (!isAdd){
            createAddView()
        }
    }

    fun createAddView(){
        isAdd = true
        val imageView = ImageView(this.context)
        imageView.setBackgroundDrawable(this.context.resources.getDrawable(R.drawable.shape_gray_r3))
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        if (addDrawable != 0) imageView.setImageResource(addDrawable)
        imageView.setOnClickListener {
            if (imageChooseHelper == null){
                addAction.invoke(it)
            }else{
                ImageChooseDialog(this.context){
                    if (it == 0){  //拍照
                        imageChooseHelper?.takePhoto()
                    }else if (it == 1){
                        imageChooseHelper?.selectPicker()
                    }
                }
            }
        }
        addView(imageView)
    }

    fun addImgView(path : String){
//        EncodeUtils.base64Encode2String(File(path).readBytes())
        mImgList.add(path)
        createImgView(this.context,path)
    }

    fun createImgView(context: Context,path : String){
        val frameLayout = FrameLayout(context)

        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageURI(Uri.fromFile(File(path)))
        frameLayout.addView(imageView,FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT))

        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.RIGHT or Gravity.TOP
        params.topMargin = -5
        params.rightMargin = -5
        frameLayout.addView(createDeleteView(context,frameLayout),params)

        this.addView(frameLayout)
    }

    fun  createDeleteView(context: Context,tag : View) : View{
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        imageView.tag = tag
        if (deleteDrawable != 0) imageView.setImageResource(deleteDrawable)
        else imageView.setImageResource(android.R.drawable.ic_delete)
        imageView.setOnClickListener {
            this@ShowImgView.removeView(it.tag as View)
        }
        return imageView
    }

}