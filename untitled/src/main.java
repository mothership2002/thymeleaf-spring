import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) {
//        Temp2 temp1 = new Temp2() {
//            @Override
//            public void temp2() {
//                System.out.println("temp222222");;
//            }
//        };
//        temp1.temp2();
//
//        Temp2 temp2 = () -> System.out.println("AAAA");
//        temp2.temp2();
        temp();
    }

    public static void temp() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println(list.stream().filter(e -> Integer.parseInt(e) == 3)
                .findFirst()
                .orElse(null));

    }
}
