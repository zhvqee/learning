package question6.httpClient;

import java.util.HashMap;
import java.util.Map;

public class NettyServerTest {

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            stringBuilder.append(i);
        }



    /*    for (int i = 0; i < 100000; i++) {
            String s = HttpOkClient.get("http://127.0.0.1:8888/" + i);
            System.out.println(s);
        }


        for (int i = 0; i < 10000; i++) {
            String s = HttpOkClient.get("http://127.0.0.1:8888/" + stringBuilder.toString() + "-" + i);
            System.out.println(s);
        }

*/



        for (int i = 0; i < 10000; i++) {
            Map<String, String> extraMap = new HashMap<>();
            extraMap.put(i + "", stringBuilder.toString() + "-" + i);
            String s = HttpOkClient.post("http://127.0.0.1:8888/", extraMap);
            System.out.println(s);
        }
    }
}
