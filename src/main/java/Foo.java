public interface Foo {
    String method(String str);
    default void test(){
        System.out.println("foo");
    }
    static void staticMethod(){
        System.out.println("static method");
    }
    static void staticMethod2(){
        System.out.println("static method");
    }
}
