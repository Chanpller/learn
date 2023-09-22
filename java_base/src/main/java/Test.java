import java.io.File;

public class Test {
    public static void main(String[] args) {
//       File file = new File("F:\\BaiduNetdiskDownload\\视频教程");
//       listFile(file);

        int ids[]  = {1001,1002,1003,1004};

    }
    public static  void listFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File fileChildren : files ){
                listFile(fileChildren);
            }
        }else{
            String fileName = file.getName();
            if(fileName.contains("baiduyun.downloading")){
                file.delete();
            }
        }
    }
}
