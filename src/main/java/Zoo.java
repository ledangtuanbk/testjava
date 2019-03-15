public interface Zoo {
    String method(String str);
    default void test(){
        System.out.println("zoo");
    }
    static void staticMethod(){
        System.out.println("static method");
    }
}
