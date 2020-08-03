package com.longrise.study.loader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Objects;

/**
 * 自定 class 文件加载器, 用于加载我们指定的文件
 * 常用于服务更新, 而不想更新客户端的操作;
 */
public class MyLoader extends ClassLoader {
    private String path; // 指定 class 文件的位置

    public MyLoader(String path) {
        this.path = path;
    }

    /**
     * 获取指定的 class 文件
     * 
     * @param name 类的全限定名
     * @return 指定的 class 文件
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        File file = new File(this.path);
        if (file.exists()) {
            try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
                // 将此频道文件的区域直接映射到内存中
                MappedByteBuffer mBuffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
                byte[] bytes;
                if (mBuffer.hasArray()) { // 确保此缓冲区具有可访问的子节数组
                    bytes = mBuffer.array();
                } else {
                    bytes = new byte[mBuffer.limit()];
                    mBuffer.get(bytes);
                }
                if (Objects.isNull(bytes)) {
                    throw new UnsupportedOperationException("没有可操作的缓冲区子节数组");
                }
                clazz = defineClass(name, bytes, 0, bytes.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (Objects.isNull(clazz)) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }
}