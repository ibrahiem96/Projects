	.file	"hwk3practice.c"
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"result %lx\n"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB1:
	.section	.text.startup,"ax",@progbits
.LHOTB1:
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB24:
	.cfi_startproc
	xorl	%eax, %eax
	movl	$2, %edx
	.p2align 4,,10
	.p2align 3
.L2:
	leaq	0(,%rax,8), %rcx
	addq	$1, %rax
	orq	%rcx, %rdx
	cmpq	$22, %rax
	jne	.L2
	movl	$.LC0, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	jmp	__printf_chk
	.cfi_endproc
.LFE24:
	.size	main, .-main
	.section	.text.unlikely
.LCOLDE1:
	.section	.text.startup
.LHOTE1:
	.ident	"GCC: (Ubuntu 4.9.2-10ubuntu13) 4.9.2"
	.section	.note.GNU-stack,"",@progbits
