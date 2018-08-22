package project.healthcare.phone.presenter;

/**
 * 基础presnter
 * K：view接口 V:Model接口
 *
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IBasePresenter<K,V> {

    /**
     * 初始化
     */
    void init();

    /**
     * 绑定视图
     * @param view  视图
     */
    void bindView(K view);

    /**
     * 绑定 Model
     * @param model model
     */
    void bindModel(V model);

    /**
     * 关闭监听、回调
     */
    void destory();
}
