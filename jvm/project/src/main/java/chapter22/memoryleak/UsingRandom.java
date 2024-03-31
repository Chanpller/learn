package chapter22.memoryleak;

public class UsingRandom {
    private String msg;
    public void receiveMse(){
        readFromNet();//从网络中接受数据保存到msg中
        saveDB();//把msg保存到数据库库中
    }

    private void saveDB() {
    }

    private void readFromNet() {
    }
}
