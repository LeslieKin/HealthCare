package project.healthcare.phone.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
/**
 * 自定义横向滑动条
 * @author Cai.wh
 *
 */
public class CustomHorizontalScrollView extends HorizontalScrollView{
  
  //构造函数
  public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    
    //滑动条任务
    scrollerTask = new Runnable() {
      
      @Override
      public void run() {        
        //获取横向滑动条滑动位置
        int newPostion = getScrollX();
        
        //旧的位置和新的位置一致说明滑动停止
        if (initPosition - newPostion ==0) {
          //监听器为空，结束此次任务
          if (onScrollStopListener == null ){
            return;
          }
          
          onScrollStopListener.onScrollStoped();
          Rect outRect = new Rect();
          //返回视图的可见视图边界
          getDrawingRect(outRect);
          
          //横向滑动条可见部分的左边界位置为0时，此时横向滑动条位已滑到最左端
          if (getScrollX()==0) {
            //调用滑动停止监听器，滑动到最左端
            onScrollStopListener.onScrollToLeftEdge();
          } 
          else if (childWidth + getPaddingLeft() + getPaddingRight() == outRect.right) {
            onScrollStopListener.onScrollToRightEdge();
          }
          else {
            onScrollStopListener.onScrollMiddle();
          }
        } 
        else {
          //如果滑动没有停止更新初始滑动条位置
          initPosition = getScrollX();
          //延迟发送任务处理请求
          postDelayed(scrollerTask, newCheck);
        }
      }
    };
  }
  
  /**
   * 开始滑动处理任务
   */
  public void startScrollerTask() { 
    initPosition = getScrollX();
    postDelayed(scrollerTask, newCheck);
    checkTotalWidth();
  }
  
  /**
   * 计算横向滑动条子视图的宽度
   */
  protected final void checkTotalWidth() { 
    if (childWidth > 0) {
      return;
    }
    for(int i = 0; i<getChildCount(); i++) {
      childWidth += getChildAt(i).getWidth();
    }
  }
  
  public interface OnScrollStopListener {
    
    /**
     * 滑动停止状态回调
     */
    public void onScrollStoped();
    
    /**
     * 滑动到最左状态回调
     */
    public void onScrollToLeftEdge();
    
    /**
     * 滑动到最右状态回调
     */
    public void onScrollToRightEdge();
    
    /**
     * 在最左和最右之间滑动状态回调
     */
    public void onScrollMiddle(); 
  }
  
  /**
   * 滑动停止监听器接口
   * @param listener
   */
  public void SetOnScrollStopListener (OnScrollStopListener listener) {
    onScrollStopListener = listener;
  }
  
  /**
   * 滑动停止监听器接口
   */
  private OnScrollStopListener onScrollStopListener;
  
  /**
   * 滑动处理任务
   */
  private Runnable scrollerTask;
  
  /**
   * 滑动位置
   */
  private int initPosition;
  
  /**
   * 滑动处理任务延时时间
   */
  private int newCheck = 50;
  
  /**
   * 横向滑动条子视图的宽度
   */
  private int childWidth = 0;
}
