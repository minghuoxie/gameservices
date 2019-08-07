package huibian;

public class ZhiLingShuoMing {
    /**
     * 会变的db,dw和dd的区别
     * db   定义字节类型的变量，一个字节数据占1个字节单元，读完一个，偏移量加1
     * dw   定义字类型变量，一个字类型占2个字节单元，读完一个，偏移量加2
     * dd   定义双字类型变量，一个双字类型占4个字节单元，读完一个，偏移量加4
     *
     *
     * 汇编proto，proc和invoke伪指令与函数声明，函数定义，函数调用
     * proto伪指令-函数声明    在代码的最前面写函数声明，在后面写函数的定义
     * 函数名 proto[距离][语言][参数1]:数据类型,[参数2]:数据类型,......
     * FunctionName proto stdcall arg1:dword,arg2:dword
     *
     * proc伪指令-函数定义
     * FunctionName proc stdcall  arg1:dword,arg2:dword
     *      函数体代码块..
     * FunctionName endp
     *
     *
     * xor al,al    异或运算，相当于清零mov al,0   异或运算的规则：两个运算位的结果相同为0，不同为1
     *
     *
     *
     * push ax 吧ax的值压入栈中，即当前esp-4的值变为ax的值，ax本身的值不变
     * pop dx   把当前的esp指向的栈中的值赋给dx，并且esp+4
     * */
}
