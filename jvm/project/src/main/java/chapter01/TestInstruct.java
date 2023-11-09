package chapter01;

public class TestInstruct {
    //找到class输出目录，执行javap -v TestInstruct.class，可以查看到对于的字节码内容。
    /**
     *   public static void main(java.lang.String[]);
     *     descriptor: ([Ljava/lang/String;)V
     *     flags: ACC_PUBLIC, ACC_STATIC
     *     Code:
     *       stack=2, locals=4, args_size=1
     *          0: iconst_5
     *          1: istore_1
     *          2: iconst_2
     *          3: istore_2
     *          4: iload_2
     *          5: iconst_3
     *          6: iadd
     *          7: istore_3
     *          8: return
     *       LineNumberTable:
     *         line 5: 0
     *         line 6: 2
     *         line 7: 4
     *         line 8: 8
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       9     0  args   [Ljava/lang/String;
     *             2       7     1     i   I
     *             4       5     2     j   I
     *             8       1     3     k   I
     * }
     */
    public static void main(String[] args) {
        int i = 2+3;
        int j = 2;
        int k = j+3;
    }
}
