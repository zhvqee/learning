package com.qee;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ProjectName: learning
 * @Package: com.qee
 * @ClassName: TransferHello
 * @Description:
 * @Date: 2021/9/14 7:54 下午
 * @Version: 1.0
 */
public class TransferHelloTest {

    public static void transferClass(String name) throws IOException {
        InputStream resourceAsStream = TransferHelloTest.class.getResourceAsStream(name);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);
        name = name.substring(0, name.indexOf('.'));
        File target = new File(name + ".xlass");
        FileOutputStream fileOutputStream = new FileOutputStream(target);
        byte[] readbuf = new byte[1024];
        int slen = 0;
        while ((slen = bufferedInputStream.read(readbuf, 0, readbuf.length)) != -1) {
            for (int i = 0; i < slen; i++) {
                readbuf[i]= (byte) (255-readbuf[i]);
            }
            fileOutputStream.write(readbuf, 0, slen);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        bufferedInputStream.close();
        resourceAsStream.close();
    }

    public static void main(String[] args) throws IOException {
        TransferHelloTest.transferClass("Hello.class");
    }
}
