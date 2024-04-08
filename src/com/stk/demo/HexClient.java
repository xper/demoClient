package com.stk.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.AutoCloseable;

public class HexClient implements AutoCloseable {
  private Socket socket;
  private String ip;
  private int port;

  public HexClient(String ip, int port) {
    try {
      this.ip = ip;
      this.port = port;      
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  public void sendHexString() {
    OutputStream os = null;
    // socket으로 byte[] 전송    
    try {

      String hexString = String.format("%16X%02X%02X%02X%02X", 0x3812121281282828L, 0x00, 0xf0, 0x01, 0x02);
      System.out.println("* hexString: " + hexString);
      byte[] bytes = hexStringToByteArray(hexString);
      long lLMagicNumber = 0x3812121281282828L;
      String hx1 = Long.toHexString(lLMagicNumber);
      String hx2 = String.format("%16X", 0x3812121281282828L);
      System.out.println("* hx1: " + hx1);
      System.out.println("* hx2: " + hx2);
      System.out.println("* hx1 == hx2: " + (hx1.equals(hx2)));

      socket = new Socket(ip, port);
      os = socket.getOutputStream();
      os.write(bytes);
      os.flush();
      System.out.println("socket으로 byte[] 전송");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } finally {
        try {
          if (os != null) {
            os.close();
          }
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
    }
  }
  private byte[] hexStringToByteArray(String hexString) {
    byte[] bytes = new byte[hexString.length() / 2];
    for (int i = 0; i < hexString.length(); i += 2) {
      bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
          + Character.digit(hexString.charAt(i + 1), 16));
    }
    return bytes;
  }


  @Override
  public void close() throws Exception {
    try {
      if (socket != null) {
        socket.close();
      }
      System.out.println("HexString Client 객체 소멸됨");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
  
}