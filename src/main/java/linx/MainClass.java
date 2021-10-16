package linx;

public class MainClass {
    public static void main(String[] args) {
        String deviceId = "d02544fffef4190e";
        
        String s = deviceId.substring(deviceId.length()-6, deviceId.length());

        System.out.println(Integer.parseInt(s, 16) & Integer.parseInt("3fffff", 16));
        Integer.parseInt(s, 16);
        
    }
}
