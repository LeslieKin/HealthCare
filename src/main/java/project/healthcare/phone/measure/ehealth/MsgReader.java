package project.healthcare.phone.measure.ehealth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MsgReader {

  public MsgReader(InputStream is) {
    mInputStream = is;
    mBuffer = new byte[BUFFER_SIZE];
    mBufferOutput = new ByteArrayOutputStream();
    mFisrtPackageFound = false;
  }

  public byte[] read() throws IOException {
    if (mFisrtPackageFound) {
      return readSimple();
    }
    return readFirstPackage();
  }

  private byte[] readFirstPackage() throws IOException {
    InputStream is = mInputStream;
    ByteArrayOutputStream baos = mBufferOutput;
    byte[] buffer = mBuffer;

    int readCount;
    int bufferSize = 0;

    byte[] header;
    boolean headerFound;

    int packageSize;
    int leftSize;
    int packageType;

    int dropCount = 0;
    byte[] msg;

    while(true) {
      readCount = is.read(buffer, 0, BUFFER_SIZE);
      bufferSize += readCount;
      baos.write(buffer, 0, readCount);

      if (bufferSize < HEADER_SIZE) {
        continue;
      }
      header = baos.toByteArray();
      packageSize = EHReader.readInt(header, 0);
      leftSize = packageSize - bufferSize + 2;
      packageType = header[3] & 0xFF;

      if (packageType == Package.MONITOR_DATA) {
        int monitorType;

        if (header.length == HEADER_SIZE) {
          monitorType = is.read();
          baos.write(monitorType);
          --leftSize;
        } else {
          monitorType = header[4] & 0xFF;
        }
        headerFound = isMonitorHeader(monitorType, packageSize);
      } else {
        headerFound = isSimpleHeader(packageType, packageSize);
      }
      if (headerFound) {
        dropCount = 0;

        if (leftSize >= 0) {
          mFisrtPackageFound = true;
        }
        if (leftSize > 0) {
          while (leftSize > BUFFER_SIZE) {
            readCount = is.read(buffer, 0, BUFFER_SIZE);
            baos.write(buffer, 0, readCount);
            leftSize -= readCount;
          }
          while (leftSize > 0) {
            readCount = is.read(buffer, 0, leftSize);
            baos.write(buffer, 0, readCount);
            leftSize -= readCount;
          }
        }
        msg = baos.toByteArray();
        baos.reset();
        return msg;
      } else {
        baos.reset();
        bufferSize = 0;
        ++dropCount;

        if (dropCount >= MAX_DROP_COUNT) {
          return null;
        }
      }
    }
  }

  private byte[] readSimple() throws IOException {
    InputStream is = mInputStream;
    ByteArrayOutputStream baos = mBufferOutput;
    byte[] buffer = mBuffer;
    int readCount;

    while (baos.size() < HEADER_SIZE) {
      readCount = is.read(buffer, 0, BUFFER_SIZE);
      baos.write(buffer, 0, readCount);
    }
    byte[] header = baos.toByteArray();
    int totalSize = EHReader.readInt(header, 0) + 2;

    while (baos.size() < totalSize) {
      readCount = is.read(buffer, 0, BUFFER_SIZE);
      baos.write(buffer, 0, readCount);
    }
    byte[] msg = baos.toByteArray();
    int leftDataSize = baos.size() - totalSize;
    baos.reset();
    baos.write(msg, totalSize, leftDataSize);
    return msg;
  }

  public void close() {
    try {
      mBufferOutput.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean isMonitorHeader(int monitorType, int packageSize) {
    switch (monitorType) {
    case Package.ECG:
      return packageSize == PackageSize.ECG;

    case Package.NIBP:
      return packageSize == PackageSize.NIBP;

    case Package.SPO2:
      return packageSize == PackageSize.SPO2;

    case Package.TEMP:
      return packageSize == PackageSize.TEMP;
    }
    return false;
  }

  private static boolean isSimpleHeader(int packageType, int packageSize) {
    switch (packageType) {
    case Package.PATIENT_INFO:
      return packageSize == PackageSize.PATIENT_INFO;

    case Package.ALARM_SWITCH:
      return packageSize == PackageSize.ALARM_SWITCH;

    case Package.TECH_ALARM:
      return packageSize == PackageSize.TECH_ALARM;

    case Package.CONTROL:
    case Package.USER_ID:
      return true;
    }
    return false;
  }

  private InputStream mInputStream;

  private byte[] mBuffer;
  private ByteArrayOutputStream mBufferOutput;

  private boolean mFisrtPackageFound;

  private static final int BUFFER_SIZE = 1024;
  private static final int HEADER_SIZE = 4;
  private static final int MAX_DROP_COUNT = 60;

  private static interface Package {
    int PATIENT_INFO = 0x11;
    int MONITOR_DATA = 0x12;
    int ALARM_SWITCH = 0x20;
    int TECH_ALARM   = 0x21;
    int CONTROL      = 0x50;
    int USER_ID      = 0x23;

    int ECG  = 0x0A;
    int NIBP = 0x0C;
    int SPO2 = 0x0D;
    int TEMP = 0x0E;
  }

  private static interface PackageSize {
    int PATIENT_INFO = 242;
    int ALARM_SWITCH = 11;
    int TECH_ALARM   = 72;

    int ECG  = 684;
    int NIBP = 36;
    int SPO2 = 276;
    int TEMP = 34;
  }

}
