import java.math.BigDecimal;

public class TestClass implements Foo, Zoo {
    @Override
    public String method(String str) {
        System.out.println(new BigDecimal(0.2));



        return str;
    }

    @Override
    public void test() {
        Zoo.super.test();
        Foo.super.test();

        System.out.println(Zoo.super.hashCode());
        System.out.println(Foo.super.hashCode());
        System.out.println(hashCode());
    }

}
