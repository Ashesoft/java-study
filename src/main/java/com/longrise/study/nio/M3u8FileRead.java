package com.longrise.study.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class M3u8FileRead {
    private static String src = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
            + File.separator + "resources";

    public static void main(String[] args) {
        File file = new File(src, "hlm.m3u8");
        try (FileInputStream fInputStream = new FileInputStream(file);
                FileChannel channel = fInputStream.getChannel();) { // 为文件输入流创建通道
            ByteBuffer buffer = ByteBuffer.allocate(105); // 创建缓冲区并分配大小
            int bread = -1; // 标识符
            while ((bread = channel.read(buffer)) != -1) { // 将通道中的数据读取到缓冲区并判断读取的数据的大小
                /**
                 * 反转缓冲区, 此前是写操作, 现在是读操作
                 * 
                 * public Buffer flip() {
                 *     limit = position;
                 *     position = 0;
                 *     mark = -1;
                 *     return this;
                 * }
                 * 
                 * 从源码可以得到就是把limit(极限/限制)移到position的位置后, 在把position移到开头, 来为接下来的操作做准备;
                */
                buffer.flip(); 
                if (buffer.hasRemaining()) { // 判断position(当前位置)与limit(极限)之间是否存在任何元素
                    System.out.print(new String(buffer.array(), 0, bread)); // 这里需要读多少数据就写多少数据, 跟clear方法有关
                }

                /**
                 * 清除此缓存区, 位置设置为0, 限制设置为容量, 标记被丢弃;
                 * 
                 * public Buffer clear() {
                 *    position = 0;
                 *    limit = capacity;
                 *    mark = -1;
                 *    return this;
                 * }
                 * 
                 * 从源码可以得出此方法并不会清除缓冲区中的数据, 但它的命名方式就好像是这样做的, 因为这种方法最常用于可能是这种情况的情况;
                 * 所以输出内容的时候, 需注意会把limit与capacity之间的数据输出出来;
                 */
                buffer.clear(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}