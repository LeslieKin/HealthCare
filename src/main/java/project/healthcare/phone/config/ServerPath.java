package project.healthcare.phone.config;

public interface ServerPath {

//  /**
//   * 服务器基础路径
//   */
//  String BASE_PATH = "http://192.168.3.172:8089/zkw/";
  /**
   * 服务器基础路径
   */
  String BASE_PATH = "http://manage.vkang.org/";
	
	/*刘海宁私人服务器路径*/
// String BASE_PATH = "http://172.31.71.219:7080/";
	/*曹金水私人服务器路径*/
//String BASE_PATH = "http://172.31.71.7:8077/";
   /*苏建彬私人服务器路径*/
//   String BASE_PATH="http://172.31.71.108:8070";
 
  /**
   * 本地测试的基本图片路径
   */
  String BASE_IMAGE_ADDRESS = BASE_PATH;

  /**
   * 获取可用检测类型
   * 
   * <pre>Request Sample:
   * {
   *   identity : "" // [string] 身份证号码
   * }
   * </pre>
   * 
   * <pre>Response Sample:
   * [
   *   "", .. // [int] 检测类型
   * ]
   * </pre>
   */
  String GET_AV_TYPES = BASE_PATH + "phone/phonegetMemItem";

  /**
   * 登录
   */
  String LOGIN = BASE_PATH + "person/personloginByIdCard";
  
  /**
   * 修改密码
   */
  String CHANGE_PASSWORD = BASE_PATH + "phone/phonechangePassword";

  
  /**
   * 注册
   */
  String REGISTER = BASE_PATH + "person/personregister?identity=";
  
  /**
   * 完全注册
   */
  String FULL_REGISTER = BASE_PATH + "phone/phonefullRegister";

  /**
   * 发送验证码
   */
  String SEND_VERICODE = BASE_PATH + "phone/phonegetVericode";
  
  /**
   * 分析检测结果
   */
  String ANALYSE = BASE_PATH + "phone/phoneanalysis";


  /**
   * 保存分析结果
   */
  String SAVE_ANALYSIS = BASE_PATH + "phone/phonesaveAnalysis";

  /**
   * 获取检测数据
   * 
   * <pre>Request Sample:
   * {
   *   identity : "" // [string] 身份证号码
   * }
   * </pre>
   * 
   * <pre>Response Sample:
   * {
   *   composite : [ // [Array] 综合数据
   *     {
   *       place   : "", // [string] 检测地点
   *       time    : "", // [long] 检测时间
   *       score   : "", // [float] 得分
   *       stateId : "", // [int] 健康状态ID
   *       comment : "", // [string] 评语
   *       suggest : ""  // [string] 建议
   *     }, ..
   *   ],
   *   values    : [ // [Array] 检测数据
   *     {
   *       id      : "", // [long] 记录ID
   *       type    : "", // [int] 检测类型
   *       place   : "", // [string] 检测地点
   *       time    : "", // [long] 检测时间
   *       score   : "", // [float] 得分
   *       stateId : "", // [int] 健康状态
   *       comment : "", // [string] 评语
   *       suggest : "", // [string] 建议
   *       values  : [
   *         "", ..      // [double] 测量值
   *       ]
   *     }, ..
   *   ]
   * }
   * </pre>
   */
  String GET_DETECT_DATA = BASE_PATH + "phone/phonegetDetectData";

  /**
   * 获取成员信息列表
   */
  String GET_MEMBERINFO_LIST = BASE_PATH + "phone/phonegetMemeberInfos";

  /**
   * 绑定推送的Pid
   */
  String BIND_PID = BASE_PATH + "phone/phonebindingPid";

  /**
   * 获取身份证号码
   */
  String GET_IDENTITY = BASE_PATH + "person/persongetIdentity?phone=";

  /**
   * 发起紧急救助
   */
  String REQUEST_AID = BASE_PATH + "phone/phonesponsorFirstAid";

  /**
   * 获取急救列表记录
   */
  String GET_AID_RECORD = BASE_PATH + "phone/phonegetFirstAidList";

  /**
   * 获取紧急求救联系人
   */
  String GET_AID_CONTACT = BASE_PATH + "phone/phonegetExitAidMem";

  /**
   * 设置紧急求救联系人
   */
  String SET_AID_CONTACT = BASE_PATH + "phone/phonesetCallMember";

  /**
   * 取消紧急救助
   */
  String CANCEL_AID = BASE_PATH + "phone/phonecancelAid";
}

