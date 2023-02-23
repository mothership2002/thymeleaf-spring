public class main {

    public static void main(String[] args) {
        Temp2 temp1 = new Temp2() {
            @Override
            public void temp2() {
                System.out.println("temp222222");;
            }
        };
        temp1.temp2();

        Temp2 temp2 = () -> System.out.println("AAAA");
        temp2.temp2();
    }
}
