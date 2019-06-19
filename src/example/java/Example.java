import itsgjk.juba.core.JUBA;

public class Example {

    public static void main(String[] args){
        JUBA juba = new JUBA(args[0]);
        juba.modifyUserBalance("472264989793583106", "196834326963290112", 1, 0).reason("test").complete();
    }
}