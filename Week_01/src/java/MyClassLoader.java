import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * created on 10/21/2020
 */
public class MyClassLoader extends ClassLoader {


    public static void main(String[] args) throws Exception {
        Class<?> clazz = new MyClassLoader().findClass("Hello");

        Object object = clazz.newInstance();
//        System.out.println("start call hello");
        clazz.getMethod("hello").invoke(object);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        Class clazz = null;
        String path = this.getClass().getResource("/Hello.xlass").getPath();
        byte[] fileBytes = new byte[0];

        try {
            fileBytes = readFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        decode(fileBytes);

        return defineClass(name, fileBytes, 0, fileBytes.length);
    }

    private byte[] readFile(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] bytes = new byte[1024 * 4];
        int n;
        while ((n = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, n);
        }
        return outputStream.toByteArray();
    }

    private void decode(byte[] fileBytes) {
        for (int i = 0; i < fileBytes.length; i++) {
            fileBytes[i] = (byte) (255 - fileBytes[i]);
        }
    }
}
