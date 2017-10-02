
fib:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$20, %esp
	cmpl	$1, 8(%ebp)
	jg	.L2
	movl	$1, %eax
	jmp	.L3
.L2:
	movl	8(%ebp), %eax
	subl	$1, %eax
	movl	%eax, (%esp)
	call	fib
	movl	%eax, %ebx
	movl	8(%ebp), %eax
	subl	$2, %eax
	movl	%eax, (%esp)
	call	fib
	addl	%ebx, %eax
.L3:
	addl	$20, %esp
	popl	%ebx
	popl	%ebp
	ret
	
main:

	movl	%esp, %ebp
	andl	$-16, %esp
	subl	$32, %esp
	movl	$5, 28(%esp)
	movl	28(%esp), %eax
	movl	%eax, (%esp)
	call	fib
	movl	%eax, 8(%esp)
	movl	28(%esp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC0, (%esp)
	call	printf
	leave
