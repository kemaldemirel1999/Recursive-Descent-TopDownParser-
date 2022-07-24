java RecursiveDescentTopDownParser.java sample1.ETU output1.txt
*This program takes two arguments.
*Input filename is given in the first argument and output will be printed to file given in the second argument.

*Firstly I wrote isItVar,isItOp,isItSignOp,isItLogicOp functions to check whether given string is var or related operation.
*Secondly I used isItVar,isItOp,isItSignOp,isItLogicOp functions to check if given string is statement,expression or logic expression.
*Thirdly I used isItStatement,isItExpression,isItLogicExpr functions in isItIfStatement.I parsed the given string and I send these parsed string to related functions.
*Lastly, In isItStart function,I used isItFunction and isItStatement functions.If given string is not suitable for being if_statement,string will send to isItStatement and if not ,it causes error. 

*You can see boolean primitive type in the functions as a parameter.It provides me that if there is an else statement ,I can load these data to related global variables. 
