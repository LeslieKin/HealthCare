package project.healthcare.phone.constants;

import java.util.GregorianCalendar;

import project.healthcare.phone.config.QueryColumns;

import com.wilimx.db.Entity;
import com.wilimx.db.annotation.table.ColumnName;
import com.wilimx.db.annotation.table.PrimaryKey;

/**
 * 成员信息
 * 
 */
public final class UserInfo extends Entity {

  /**
   * 获取姓名
   * 
   * @return 姓名
   */
  public final String getName() {
    return mName;
  }

  /**
   * 获取照片
   * 
   * @return 照片
   */
  public final byte[] getPhoto() {
    return mPhoto;
  }

  /**
   * 获取照片地址
   * 
   * @return 照片地址
   */
  public final String getPhotoAddress() {
    return mPhotoAddress;
  }

  /**
   * 获取身份证号码
   * 
   * @return 身份证号码
   */
  public final String getIdentity() {
    return mIdentity;
  }

  /**
   * 获取出生日期
   * 
   * @return 出生日期
   */
  public final long getBirth() {
    return mBirth;
  }

  /**
   * 获取可用检测类型
   * 
   * @return 可用检测类型
   */
  public final int[] getUnlockTypes() {
    return mUnlockTypes;
  }

  /**
   * 获取数据检测类型
   * 
   * @return 数据检测类型
   */
  public final int[] getDataTypes() {
    return mDataTypes;
  }

  /**
   * 设置姓名
   * 
   * @param  name 姓名
   */
  public final void setName(String name) {
    mName = name;
  }

  /**
   * 设置照片
   * 
   * @param  photo 照片
   */
  public final void setPhoto(byte[] photo) {
    mPhoto = photo;
  }

  /**
   * 设置照片地址
   * 
   * @param  photoAddress 照片地址
   */
  public final void setPhotoAddress(String photoAddress) {
    mPhotoAddress = photoAddress;
  }

  /**
   * 设置身份证号码
   * 
   * @param  identity 身份证号码
   */
  public final void setIdentity(String identity) {
    mIdentity = identity;
    if (identity != null && identity.length() == 18) {
      int i = 6;
      mBirth = new GregorianCalendar(
        Integer.parseInt(identity.substring(i, i += 4), 10)    ,
        Integer.parseInt(identity.substring(i, i += 2), 10) - 1,
        Integer.parseInt(identity.substring(i, i += 2), 10)).getTimeInMillis();
    }
  }

  /**
   * 设置出生日期
   * 
   * @param  birth 出生日期
   */
  public final void setBirth(long birth) {
    mBirth = birth;
  }

  /**
   * 设置可用检测类型
   * 
   * @param  unlockTypes 可用检测类型
   */
  public final void setUnlockTypes(int[] unlockTypes) {
    mUnlockTypes = unlockTypes;
  }

  /**
   * 设置数据检测类型
   * 
   * @param  dataTypes 数据检测类型
   */
  public final void setDataTypes(int[] dataTypes) {
    mDataTypes = dataTypes;
  }

  // 姓名
  private String mName;

  // 照片
  private byte[] mPhoto;

  //照片地址
  private String mPhotoAddress;

  // 身份证号码
  @PrimaryKey
  @ColumnName(QueryColumns.IDENTITY)
  private String mIdentity;

  // 出生日期
  @ColumnName(QueryColumns.BIRTH)
  private long mBirth;

  // 可用检测类型
  private int[] mUnlockTypes;

  // 数据检测类型
  private int[] mDataTypes;

}
