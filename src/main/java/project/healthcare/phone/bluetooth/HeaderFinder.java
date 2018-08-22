package project.healthcare.phone.bluetooth;

import java.io.IOException;

import com.wilimx.assist.IOAssist;

public class HeaderFinder {

  public byte[] find(IOAssist assist) {
    try {
      byte[] header = assist.read(7);
      int firstPos = findFirstPos(header);

      if (firstPos != -1) {
        
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private int findFirstPos(byte header[]) {
    if (header != null) {
      for (int i = 0, size = header.length; i < size; ++i) {
        if (header[i] == 0xAA) {
          return i;
        }
      }
    }
    return -1;
  }

}
