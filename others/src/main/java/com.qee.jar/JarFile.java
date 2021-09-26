package com.qee.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;

public class JarFile {

    private static final String BOOT_INF = "BOOT-INF";

    private static final String META_INF = "META-INF";

    private static final String LIBS = "libs";

    private static final String CLASSES = "classes";

    private static final String MANIFEST_MF = "MANIFEST.MF";

    private static final String SEPARATE = "/";

    /**
     * 就是spring boot jar 类型
     * jar 结构为
     * BOOT-INF :
     * libs:
     * classes:
     * META-INF:
     * MANIFIEST.MF
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static File createJarArchive(String path) throws IOException, URISyntaxException {
        File archive = new File(path);
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(archive));
        jarOutputStream.putNextEntry(new JarEntry(BOOT_INF + SEPARATE));
        jarOutputStream.putNextEntry(new JarEntry(BOOT_INF + SEPARATE + LIBS + SEPARATE));
        jarOutputStream.putNextEntry(new JarEntry(BOOT_INF + SEPARATE + CLASSES + SEPARATE));

        byte[] byteCode = getSelfByteCode();
        JarEntry selfEntry = createJarEntry(BOOT_INF + SEPARATE + CLASSES + SEPARATE + getPackagePath() + ".class", byteCode, "self");
        jarOutputStream.putNextEntry(selfEntry);
        jarOutputStream.write(byteCode, 0, byteCode.length);


        jarOutputStream.putNextEntry(new JarEntry(META_INF + SEPARATE));


        byte[] manifestFile = getManifestFile("com.qee.boot.Main");
        JarEntry manifestEntry = createJarEntry(META_INF + SEPARATE + MANIFEST_MF, manifestFile, "test");
        jarOutputStream.putNextEntry(manifestEntry);
        jarOutputStream.write(manifestFile, 0, manifestFile.length);

        jarOutputStream.flush();
        jarOutputStream.close();
        return archive;
    }

    private static JarEntry createJarEntry(String name, byte[] data, String comment) {
        JarEntry jarEntry = new JarEntry(name);
        jarEntry.setSize(data.length);
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        jarEntry.setCrc(crc32.getValue());
        jarEntry.setComment(comment);
        return jarEntry;
    }

    private static String getPackagePath() {
        Class<JarFile> jarFileClass = JarFile.class;
        return jarFileClass.getName().replace(".", "/");
    }

    public static byte[] getSelfByteCode() throws URISyntaxException, IOException {
        Class<JarFile> jarFileClass = JarFile.class;
        CodeSource codeSource = jarFileClass.getProtectionDomain().getCodeSource();
        String schemeSpecificPart = codeSource.getLocation().toURI().getSchemeSpecificPart();
        String replaceName = jarFileClass.getName().replace(".", "/");
        File file = new File(schemeSpecificPart + replaceName + ".class");
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] buf = new byte[2000];
        byte[] buf2 = null;
        int cnt = 0;
        while ((cnt = fileInputStream.read(buf)) != -1) {
            if (buf2 == null) {
                buf2 = new byte[cnt];
                System.arraycopy(buf, 0, buf2, 0, cnt);
            } else {
                byte[] buf3 = new byte[buf2.length + cnt];
                System.arraycopy(buf2, 0, buf3, 0, buf2.length);
                System.arraycopy(buf, 0, buf3, buf2.length, cnt);
                buf2 = buf3;
            }
        }
        fileInputStream.close();
        return buf2;
    }


    private static byte[] getManifestFile(String startClass) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5000);
        String s1 = "Manifest-Version: 1.0\n";
        String s2 = "Implementation-Title: JarFile-TEST\n";
        String s3 = "Implementation-Version: 1-SNAPSHOT\n";
        String s4 = "Built-By: system\n";
        String s5 = "Implementation-Vendor-Id: system\n";
        String s6 = "Main-Class:com.qee.boot.loader.JarLauncher\n";
        String s7 = "Start-Class: " + startClass + "\n";
        String s8 = "Boot-Classes: " + BOOT_INF + SEPARATE + CLASSES + "\n";
        String s9 = "Boot-libs: " + BOOT_INF + SEPARATE + LIBS + "\n";

        int len = s1.getBytes().length
                + s2.getBytes().length
                + s3.getBytes().length
                + s4.getBytes().length
                + s5.getBytes().length
                + s6.getBytes().length
                + s7.getBytes().length
                + s8.getBytes().length
                + s9.getBytes().length;

        byteBuffer.put(s1.getBytes());
        byteBuffer.put(s2.getBytes());
        byteBuffer.put(s3.getBytes());
        byteBuffer.put(s4.getBytes());
        byteBuffer.put(s5.getBytes());
        byteBuffer.put(s6.getBytes());
        byteBuffer.put(s7.getBytes());
        byteBuffer.put(s8.getBytes());
        byteBuffer.put(s9.getBytes());
        return Arrays.copyOf(byteBuffer.array(), len);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        JarFile.createJarArchive("/Users/zhuqi/Documents/workspace/learning/others/src/main/resources/test.jar");
    }

}

