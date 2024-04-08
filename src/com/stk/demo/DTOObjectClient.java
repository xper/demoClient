package com.stk.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.AutoCloseable;

public class DTOObjectClient implements AutoCloseable {
  private Socket socket;
  private String ip;
  private int port;

  public DTOObjectClient(String ip, int port) {
    try {
      this.ip = ip;
      this.port = port;      
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  public void sendDTO() {
    OutputStream os = null;
    // socket으로 byte[] 전송    
    try {

      HeaderDTO headerDTO = new HeaderDTO((char) 0x01, (char) 0x02);

      byte[] bytes = objectToByteArray(headerDTO);
      System.out.println("* HeaderDTO: " + headerDTO.toString());

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
  private byte[] objectToByteArray(Object obj) {
    byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
            System.out.println("headerDTO를 byte[]로 변환" + bytes.toString());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bytes;
  }


  @Override
  public void close() throws Exception {
    try {
      if (socket != null) {
        socket.close();
      }
      System.out.println("DTO Object Client 객체 소멸됨");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
  
}