// import com.stk.demo.DTOObjectClient;
import com.stk.demo.HexClient;

public class App {

  public static void main(String[] args) throws Exception {
    String ip = "localhost";
    int port = 8000;

    // try (DTOObjectClient client = new DTOObjectClient(ip, port)) {
    //   client.sendDTO();
    //   System.out.println("DTO Object Client 객체 사용중");
    // } catch (Exception e) {
    //   System.out.println(e.getMessage());
    // }finally {
    //   System.out.println("DTO Object Client 객체 자동 소멸됨");
    // }

    try (HexClient hexClient = new HexClient(ip, port)) {
      hexClient.sendHexString();
      System.out.println("HexString Client 객체 사용중");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }finally {
      System.out.println("HexString Client 객체 자동 소멸됨");
    }

  }
}