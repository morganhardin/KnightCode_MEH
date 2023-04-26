# KnightCodeSkeleton

This project is for CS322 and contains a grammar file, lexer, parser, tokens, and methods to execute the Knight Code programming language. The main program 
is bodyListener that listens as it walks through the parse tree to save variables and all their information in a hashmap that can then be used to 
mainpulate them and perform basic operations. This compiler allows for setting variables as strings and integers, adding integers, subtracting integers, 
multiplying integers, divinding integers, comparing integers, along with reading user input and printing these values.

To use the compiler, you can compile the grammar file using:
javac compiler/kcc.java. 

In order to compile a program and create the class file, you can use:
java compiler/kcc myPrograms/printInteger.kc output/printInteger
This should have output that goes through the overridden classes in the bodyListener class and shows what is happening at each step in the compilation.

In order to run the class file previously produced, you can use:
java output/printInteger
This will execute your program.
