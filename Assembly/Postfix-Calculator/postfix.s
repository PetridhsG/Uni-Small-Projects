

#---------MIPS POSTFIX CALCULATOR---------#
#-Calculate a postfix expression given by the user.
#-Between the terms of the postfix expression, the user types
# at least one space character and at the end the character '='.
#-The numbers in the expression are unsigned ints.
#-The operations performed by the computing engine are limited to arithmetic addition,
# subtraction, multiplication and division (+, -, *, /).

	.data
#---------PROGRAM VARIABLES---------#	
pfix:		.space 80


#---------MESSAGES TO THE USER---------#
welc:		.asciiz "\nWelcome to postfix calculator!!!\n"
entr:		.asciiz "Please enter a postfix expression:"
inv:		.asciiz "Invalid postfix!"
res:		.asciiz "Postfix Evaluation: "
zdiv:		.asciiz "Error!!!Division by zero"
ex:			.asciiz "\nThe program will shut down..."

#------------------------------------------------------#
	.text
	.globl main
	
#---------MAIN PROGRAM---------#
main:
	la $a0,welc			#print welcome message
	li $v0,4	
	syscall
	
	la $a0,entr			#print enter message
	li $v0,4
	syscall
	
	la $a0,pfix			#read postfix expression
	li $a1,80
	li $v0,8
	syscall
	
	la $s0,pfix			#pfix address
	sub $s0,$s0,1	
	li $s5,0			#stack pointer
	
loop_1:
	add $s0,$s0,1		#goto to next character
	lb $s1,($s0)		#pfix characters
	beq $s1,'=',result	#if char = '=' goto exit
	li $s2,0			#number
	bne $s1,' ',to_int	#if char != ' ' goto to_int
	j loop_1
	
to_int:
	lb $s1,($s0)			#pfix characters
	beq $s1,' ',loop_2		#if character = ' '	
	blt $s1,'0',loop_2		#if character < 0 
	bgt $s1,'9',loop_2 		#if character > 9
	sub $s1,$s1,48			#char - 48 (ASCII to INT)
	mul $s2,$s2,10			#mul number by 10 in case the given number is greater than 10
	add $s2,$s2,$s1			#add to number
	add $s0,$s0,1			#goto to next character
	j to_int
	
loop_2:
	beq	$s1,'+',evl			#if char = '+' goto evl(calculation)
	beq	$s1,'-',evl			#if char = '-' goto evl(calculation)
	beq	$s1,'*',evl			#if char = '*' goto evl(calculation)
	beq	$s1,'/',evl			#if char = '/' goto evl(calculation)
	bne $s1,'=',loop_3		#if char != '=' goto loop_3
	
evl:
	jal pop					#pop number
	move $s3,$v0			#first number
	jal pop					#pop number
	move $s4,$v0			#second number
	move $a0,$s3			#calc() first argument
	move $a1,$s1			#calc() second argument
	move $a2,$s4			#calc() third argument
	jal calc				#calculate current expression
	move $a0,$v0			#push() argument
	jal push				#push result
	j loop_1
	
loop_3:
	move $a0,$s2			#push() argument
	jal push				#push result
	j loop_1
	
#---------POP---------#
pop:
	beqz $s5,inpf
	sub $s5,$s5,1
	lw $t0,($sp)		#load current number to $t0
	add $sp,$sp,4		#pop current number
	move $v0,$t0		#return current number
	jr $ra				#return to call address

#---------PUSH---------#
push:
	add $s5,$s5,1
	move $t0,$a0		#move to $s0 current argument
	sub $sp,$sp,4		
	sw $t0,($sp)		#push $s0 to stack 
	jr $ra				#return to call address

#---------CALCULATE---------#
calc:
	move $t0,$a0		 #save first argument to $s0(first number)
	move $t1,$a1		 #save second argument to $s1(operator)
	move $t2,$a2		 #save third argument to $s2(second number)
	beq $t1,'+',addition 		#if operator is + goto addition
	beq $t1,'-',substraction 	#if operator is - goto substraction
	beq $t1,'*',multiplication 	#if operator is * goto multiplication
	beq $t1,'/',division		#if operator is & goto division
	
addition:
	add $t0,$t2,$t0	
	j calc_end
	
substraction:
	sub $t0,$t2,$t0
	j calc_end
	
multiplication:
	mul $t0,$t2,$t0
	j calc_end
	
division:
	beqz $t0,diverror
	div $t0,$t2,$t0
	j calc_end
	
diverror:
	la $a0,zdiv		#division by zero message
	li $v0,4
	syscall
	
	j exit
	
calc_end:
	move $v0,$t0	#return value
	jr $ra
	
#---------RESULT---------#
result:
	bne $s5,1,inpf	#if stack is empty
	la $a0,res		#result message
	li $v0,4
	syscall
	
	jal pop			#pop result
	
	move $a0,$v0	#print result
	li $v0,1
	syscall
	j exit
#---------INVALID POSTFIX---------#
inpf:
	la $a0,inv		#invalid postfix message
	li $v0,4
	syscall
	
#---------PROGRAM EXIT---------#
exit:
	la $a0,ex		#exit message
	li $v0,4
	syscall
	
	li $v0,10		#system exit
	syscall
