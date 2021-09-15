package charset;

import org.junit.Test;

import java.beans.Encoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class CharsetDemo {
    public static void main(String[] args) throws CharacterCodingException {
        // 获取charset对象
        Charset charset = Charset.forName("utf-8");
        // 获取encoder
        CharsetEncoder charsetEncoder = charset.newEncoder();
        // 创建换冲区
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("你以为听得到？");
        charBuffer.flip();
        // 编码
        ByteBuffer encodeBuffer = charsetEncoder.encode(charBuffer);
        System.out.println("编码之后的结果");
        for (int i = 0; i < encodeBuffer.limit(); i++) {
            System.out.println(encodeBuffer.get());
        }
        encodeBuffer.flip();
        // 获取解码器
        CharsetDecoder decoder = charset.newDecoder();
        // 解码
        CharBuffer decodeBuffer = decoder.decode(encodeBuffer);
        System.out.println("解码之后的结果");
        System.out.println(decodeBuffer.toString());

        // 使用GBK解码
        Charset charset1 = Charset.forName("GBK");
        encodeBuffer.flip();
//        CharsetDecoder decoder1 = charset1.newDecoder();
        // 不知道为什么这里解码直接用charset，详见下面test
        CharBuffer decodeBuffer1 = charset1.decode(encodeBuffer);
        System.out.println("使用gbk解码");
        System.out.println(decodeBuffer1.toString());
        // 拿到charset所有支持的
        Map<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = map.entrySet();
        entries.forEach(e-> System.out.println(e.getKey()+"="+e.getValue().toString()));
    }
    @Test
    public void testCharset(){
        Charset charset = Charset.forName("utf-8");
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("你以为听得到？");
        charBuffer.flip();
        ByteBuffer encodeBuffer = charset.encode(charBuffer);
        System.out.println("编码之后的结果");
        for (int i = 0; i < encodeBuffer.limit(); i++) {
            System.out.println(encodeBuffer.get());
        }
        encodeBuffer.flip();
        // 解码直接用charset来解码竟然可以，没有用encoder
        CharBuffer decodeBuffer = charset.decode(encodeBuffer);
        System.out.println("解码之后的结果");
        System.out.println(decodeBuffer.toString());

        // 这里也直接用使用GBK charset解码，没有用decoder
        Charset charset1 = Charset.forName("GBK");
        encodeBuffer.flip();
        CharBuffer decodeBuffer1 = charset1.decode(encodeBuffer);
        System.out.println("使用gbk解码");
        System.out.println(decodeBuffer1.toString());

        //
    }
}
