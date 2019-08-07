package huibian;

public class HuiBianTtudy {
    /**
     * call指令是如何传递参数的
     *
     * 题目要求：
     * 有一个数组，内容为34h,78h,0afh,8ah,要求主程序安排并且存放其结果，并由子程序来完成求数组元素的校验和
     * 校验和：补给进位的累加
     *
     * 1.寄存器传参实现：
     * data segment
     *      ARRAY db 34h,78h,0afh,8ah   ;数据准备，数组
     *      COUNT equ $-ARRAY           ;保存数组的数据个数
     *      RESULT db ?                 ;定义一个RESULT的结果变量，用于存储校验和结果
     * data ends
     *code segment
     *      assume cs:code,ds:data      ;指明代码段code，栈data
     *      start:
     *          mov ax,data             ;将data移到ax寄存器中
     *          mov dx,ax
     *          mov bx,offset ARRAY     ;数组的起始地址赋给bx
     *          mov cx,COUNT            ;将数组的数字个数赋值给cx，确定循环的次数
     *          call CHECKSUM           ;段内调用子程序，实现计算校验和
     *          mov RESULT,al           ;将结果保存到RESULT中
     *          mov ax,4c00h
     *          int 21h
     *
     *      CHECKSUM proc
     *          xor al,al               ;将al清0
     *          SUM:                    ;循环  cx的值为循环的次数
     *              add al,[bx]
     *              inc bx
     *              loop SUM
     *              ret
     *      CHECKSUM endp
     * code ends
     * end start
     *
     *
     *
     * -----------------------------------------------------------分割线---------------------------------------------------------------------
     * 变量传参的实现：
     *data segment
     *      ARRAY db 34h,78h,0afh,8ah   ;数据准备，数组
     *      COUNT equ $-ARRAY           ;保存数组的数据个数
     *      RESULT db ?                 ;定义一个RESULT的结果变量，用于存储校验和结果
     * data ends
     * code segment
     *      assume cs:code,ds:data      ;指明代码段code，栈data
     *      start:
     *          mov ax,data
     *          mov ds,ax
     *          call CHECKSUM
     *          mov ax,4c00h
     *          int 21h
     *
     *      CHECKSUM proc
     *          push ax                 ;寄存器的保护
     *          push bx
     *          push cx
     *          xor al,al
     *          mov bx,offset ARRAY
     *          mov cx,COUNT
     *
     *          SUM:
     *              add al,[bx]
     *              inc bx
     *              loop SUM
     *              mov RESULT,al
     *              pop cx          ;寄存器恢复
     *              pop bx
     *              pop ax
     *              ret
     *     CHECKSUM endp
     * code ends
     * end start
     *
     * -----------------------------------------------------------分割线---------------------------------------------------------------------
     *
     *
     *
     *
     *
     * */
}
