package project.healthcare.phone.config;

import com.wilimx.constants.BaseAction;

public interface PageAction extends BaseAction{

  /**
   * 登录
   */
  int LOGIN = 0x01;

  /**
   * 注册
   */
  int REGISTER = 0x02;

  /**
   * 手机号登录
   */
  int LOGIN_BY_PHONE = 0x03;

  /**
   * 删除密码
   */
  int DELETE_IDENTITY_LIST_COUNT = 0x04;
  
  /**
   * 身份证登录
   */
  int LOG_IN_BY_IDENTITY = 0x64;

  /**
   * 切换身份证
   */
  int SHIFT_IDENTITY_LIST_COUNT = 0x05;

  /**
   * 返回
   */
  int BACK = 0x06;

  /**
   * 下一步
   */
  int NEXT_STEP = 0x07;

  /**
   * 取消
   */
  int DISMISS = 0x08;

  int CANCEL = 0x09;

  /**
   * 确定
   */
  int COMFIRM = 0x10;

  /**
   * 发送验证码
   */
  int SEND_VERICODE = 0x11;

  /**
   * 填写详细信息
   */
  int FILL_DETAILS = 0x12;

  /**
   * 结束
   */
  int FINISH = 0x13;

  /**
   * 更新详情信息
   */
  int UPDATA_DETAILS = 0x14;

  /**
   * 急救
   */
  int AID = 0x15;

  /**
   * 急救定位
   */
  int AID_PLACE = 0x16;

  /**
   * 添加紧急联系人
   */
  int ADD_AID_CONTACT = 0x17;
  
  /**
   * 急救记录
   */
  int AID_RECORD = 0x18;
  
  /**
   * 取消紧急救助
   */
  int CANCEL_AID = 0x19;

  /**
   * 注销
   */
  int LOGOUT = 0x20;
  
  
  
  /**
   * 保存测量结果
   * 
   * Request_data
   * :null
   * 
   * Response_data
   * :boolean
   */
  int SAVE_MEASURE_RESULT = 0x33;

  /**
   * 开始检测
   * 
   * Request_data
   * :null
   */
  int MEASURE = 0x34;
  
  /**
   * 重新开始检测
   * 
   * Request_data
   * :null
   */
  int RESTART_MEASURE = 0x32;

  /**
   * 保存测量结果
   * 
   * Request_data
   * :null
   * 
   * Response_data
   * :boolean


  /**
   * 退出
   */
  int EXIT = 0x21;
  
  /**
   * 阅读协议
   */
  int READ_AGREEMENT = 0x22;
  
  /**
   * 忘记密码
   */
  int FORGOT_PSW = 0x23;
  
  /**
   * 修改密码
   */
  int CHANGE_PSW = 0x24;
}
