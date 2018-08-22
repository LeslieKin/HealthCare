package project.healthcare.phone.db.data;

/**
 * 可解析接口
 * <p>结合toString()方法使用</p>
 * <pre>
 * toString : Type   -> String
 * parse    : String -> Type
 * </pre>
 * 
 * @author xiao.yl
 * created at 2014-04-30 10:01
 */
public interface Parsable {

  /**
   * 解析
   * 
   * @param data 字符串
   * @return 解析结果
   */
  Parsable parase(String data);

}
