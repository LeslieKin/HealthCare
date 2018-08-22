package project.healthcare.phone.db;

import project.healthcare.phone.constants.UserInfo;

import com.wilimx.db.DatabaseConfig;
import com.wilimx.db.annotation.DatabaseName;
import com.wilimx.db.annotation.DatabaseVersion;
import com.wilimx.db.annotation.EntityTypes;
import com.wilimx.db.annotation.RegisterColumnConverterTypes;

@DatabaseName("healthcare_phone.db")
@DatabaseVersion(1)
@EntityTypes({
  CompositeData.class,
  DetectData.class,
//  PhoneCount.class,
  IdentityCount.class,
  UserInfo.class
})
@RegisterColumnConverterTypes(
  fieldTypes = {
    double[].class,
    int[].class,
  },
  converterTypes = {
    DoubleArrayColumnConverter.class,
    IntArrayColumnConverter.class,
  })
public class UserInfoDatabaseConfig extends DatabaseConfig {

}
