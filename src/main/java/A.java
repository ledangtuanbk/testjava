public class A {
    {
        System.out.println("non-static block code A");
    }
    static {
        System.out.println("static block code A");
    }
    A(String abc) {
        System.out.println("contructor A " + abc);
    }

//    A() {
//        System.out.println("contructor A ");
//    }
}