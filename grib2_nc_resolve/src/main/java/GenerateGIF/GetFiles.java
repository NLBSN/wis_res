package GenerateGIF;

import java.io.File;

/**
 * @author guangxush    用于读取某目录下的多张图片（按照帧的顺序排序）
 */
public class GetFiles {
    public static File[] getFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        return files;
    }
}
