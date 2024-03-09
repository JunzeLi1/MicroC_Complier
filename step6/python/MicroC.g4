grammar MicroC;

@header {

from typing import List

from MicroCCompiler.compiler.SymbolTable import SymbolTable
from MicroCCompiler.compiler.Scope import Scope
from MicroCCompiler.ast.IntLitNode import IntLitNode
from MicroCCompiler.ast.FloatLitNode import FloatLitNode
from MicroCCompiler.ast.AssignNode import AssignNode
from MicroCCompiler.ast.VarNode import VarNode
from MicroCCompiler.ast.WriteNode import WriteNode
from MicroCCompiler.ast.ReadNode import ReadNode
from MicroCCompiler.ast.ReturnNode import ReturnNode
from MicroCCompiler.ast.CondNode import CondNode
from MicroCCompiler.ast.CallNode import CallNode
from MicroCCompiler.ast.IfStatementNode import IfStatementNode
from MicroCCompiler.ast.WhileNode import WhileNode
from MicroCCompiler.ast.StatementListNode import StatementListNode
from MicroCCompiler.ast.ASTNode import ASTNode
from MicroCCompiler.ast.BinaryOpNode import BinaryOpNode
from MicroCCompiler.ast.UnaryOpNode import UnaryOpNode
from MicroCCompiler.ast.FunctionListNode import FunctionListNode
from MicroCCompiler.ast.FunctionNode import FunctionNode
from MicroCCompiler.ast.PtrDerefNode import PtrDerefNode
from MicroCCompiler.ast.AddrOfNode import AddrOfNode
from MicroCCompiler.ast.MallocNode import MallocNode
from MicroCCompiler.ast.FreeNode import FreeNode
}

@members {
def setSymbolTable(self, st: SymbolTable):
  self.st = st

def getSymbolTable(self) -> SymbolTable:
  return self.st

def setAST(self, node: ASTNode):
  self.ast = node

def getAST(self) -> ASTNode:
  return self.ast

def addParams(self, types: List[Scope.Type], names: List[str]):
  for i in reversed(range(len(types))):
    self.st.addArgument(types[i], names[i])

}
program : decls functions {self.setAST($functions.node);};

/* Declarations */
decls : var_decl decls
      | str_decl decls
      | func_decl decls
	 | /* empty */ ;

var_decls : var_decl var_decls 
          | /* empty */ ;

/* Identifiers and types */		  
ident : IDENTIFIER ;
		  
var_decl : type ident ';' {self.st.addVariable($type.t, $ident.text);};

str_decl : 'string' ident '=' val= STR_LITERAL ';' {self.st.addVariable(Scope.Type(Scope.InnerType.STRING), $ident.text, $val.text);};

type returns [Scope.Type t] : base_type {$t = $base_type.t;}
    | t1=type '*' {$t = Scope.Type.pointerToType($t1.t);} ;

base_type returns [Scope.Type t] : 'int' {$t = Scope.Type(Scope.InnerType.INT);}| 'float' {$t = Scope.Type(Scope.InnerType.FLOAT);};

func_type returns [Scope.Type t] : type {$t = $type.t;}
    | 'void' {$t = Scope.Type(Scope.InnerType.VOID);} ;

/* Functions */

func_decl : func_type ident '(' params ')' ';' {self.st.addFunction($func_type.t, $ident.text, $params.types);};

functions returns [FunctionListNode node] : function functions {$node = FunctionListNode($function.node, $functions.node);}
            | /* empty */ {$node = FunctionListNode();};

function returns [FunctionNode node] : func_type ident '(' params ')' 
{
# Add FunctionSymbolTable entry to global scope
ste = self.st.getSymbolTableEntry($ident.text);
if ste is None or not ste.isDefined():
  self.st.addFunction($func_type.t, $ident.text, $params.types);          
  ste = self.st.getSymbolTableEntry($ident.text);
  ste.setDefined(True);
else:
  raise Exception("Function already defined");
self.st.pushScope($ident.text);
self.addParams($params.types, $params.names);
}
'{' var_decls statements '}' 
{
# Create FunctionNode
funcScope = self.st.currentScope();
$node = FunctionNode($statements.node, $ident.text, funcScope);

# Done with this scope, so pop the scope
self.st.popScope();
};
		 		 
params returns [list names, list types]: param params_rest 
{
$names = [];
$types = [];
$names.append($param.name);
$names.extend($params_rest.names);
$types.append($param.param_type);
$types.extend($params_rest.types);
}
| /* empty */ {
$names = [];
$types = [];
};
		   
params_rest returns [list names, list types] : ',' param params_rest
{
$names = [];
$types = [];
$names.append($param.name);
$names.extend($params_rest.names);
$types.append($param.param_type);
$types.extend($params_rest.types);
}
| /* empty */ {
$names = [];
$types = [];
};
			
param returns [str name, Scope.Type param_type] : type ident 
{
$name = $ident.text;
$param_type = $type.t;
};                   
		 		 
/* Statements */
		 
statements returns [StatementListNode node] : statement s=statements {$node = StatementListNode($statement.node, $s.node.getStatements());}
            | /* empty */ {$node = StatementListNode();};
			
statement returns [StatementNode node] : base_stmt ';' {$node = $base_stmt.node;}
		  /* | if_stmt {} FILL IN FROM STEP 1 */ /* FILL IN FROM STEP 3 */
		  | while_stmt {} ; /* FILL IN FROM STEP 3 */
		  
base_stmt returns [StatementNode node] : assign_stmt {$node = $assign_stmt.node;}
          | read_stmt {$node = $read_stmt.node;}
		| print_stmt {$node = $print_stmt.node;}
		| return_stmt {$node = $return_stmt.node;}
    | call_expr {$node = $call_expr.node;};
		 
read_stmt returns [ReadNode node] : 'read' '(' ident ')' {$node = ReadNode(VarNode($ident.text));} ;

print_stmt returns [WriteNode node] : 'print' '(' expr ')' {$node = WriteNode($expr.node);};

return_stmt returns [ReturnNode node] : 'return' expr {$node = ReturnNode($expr.node, self.st.getFunctionSymbol(self.st.currentScope().getName()));}
    | 'return' {$node = ReturnNode(None, self.st.getFunctionSymbol(self.st.currentScope().getName()));} ;

assign_stmt returns [AssignNode node] : lhs '=' expr {$node = AssignNode($lhs.node, $expr.node);};

lhs returns [ExpressionNode node] : lval {$node = $lval.node;} 
    | array_expr {$node = $array_expr.node;} ;
/* if_stmt rules go here */

/*  
if_stmt : FILL IN FROM STEP 1

FILL IN ACTIONS FROM STEP 3
*/

while_stmt returns [WhileNode node] : 'while' '(' cond ')' '{' statements '}' {}  ; /* FILL IN FROM STEP 3 */
	 
/* Expressions */

lval returns [ExpressionNode node] : ident {$node = VarNode($ident.text);}
    | ptr_expr {$node = $ptr_expr.node;} ;

primary returns [ExpressionNode node] : lval {$node = $lval.node;}
        | addr_of_expr {$node = $addr_of_expr.node;}
        | '(' expr ')' {$node = $expr.node;}
        | unaryminus_expr {$node = $unaryminus_expr.node;}
        | call_expr {$node = $call_expr.node;}
        | array_expr {$node = $array_expr.node;}
        | il = INT_LITERAL {$node = IntLitNode($il.text);}
        | fl = FLOAT_LITERAL {$node = FloatLitNode($fl.text);};

unaryminus_expr returns [ExpressionNode node] : '-' expr {}; /* FILL IN FOR STEP 2 */

ptr_expr returns [PtrDerefNode node] : '*' primary {}; /* FILL IN FOR STEP 6 */

addr_of_expr returns [AddrOfNode node] : '&' lval {} /* FILL IN FOR STEP 6 */
        | '&' array_expr {}; /* FILL IN FOR STEP 6 */

array_expr returns [PtrDerefNode node] : lval '[' expr ']' {} /* FILL IN FOR STEP 6 */
        | ae=array_expr '[' expr ']' {} ; /* FILL IN FOR STEP 6 */
		 
/* Call expressions */
call_expr returns [CallNode node] : 'malloc' '(' expr ')' {$node = MallocNode($expr.node);}
        | 'free' '(' expr ')' {$node = FreeNode($expr.node);}
        | ident '(' arg_list ')' {$node = CallNode($ident.text, $arg_list.args);};

arg_list returns [list args] : expr args_rest
{
$args = [];
$args.append($expr.node);
$args.extend($args_rest.args);
}
| /* empty */ {$args = [];};
		 
args_rest returns [list args] : ',' expr args_rest 
{
$args = [];
$args.append($expr.node);
$args.extend($args_rest.args);
}
| /* empty */ {$args = [];};

/* This is left recursive, but ANTLR will clean this up */ 
expr returns [ExpressionNode node] : term {$node = $term.node;}
     | e1 = expr /* FILL IN addop or mulop from STEP 1*/ term {}; /* FILL IN FOR STEP 2 */
	 
/* This is left recursive, but ANTLR will clean this up */
term returns [ExpressionNode node] : primary {$node = $primary.node;}
     | t1 = term /*FILL IN addop or mulop from STEP 1*/ primary {}; /* FILL IN FOR STEP 2 */
	   	   
cond returns [CondNode node] : e1=expr cmpop e2=expr {} ; /* FILL IN FROM STEP 3 */

cmpop : '<' | '<=' | '>=' | '==' | '!=' | '>' ;

mulop : '*' | '/' ;

addop : '+' | '-' ;

/* Tokens */

IDENTIFIER : (LETTER | '_') (LETTER | DIGIT | '_')* ;

INT_LITERAL : DIGIT+;

FLOAT_LITERAL : DIGIT* '.' DIGIT+;

STR_LITERAL : '"' (~('"'))* '"' ;

COMMENT : '/*' .*? '*/' -> skip;

WS : [ \t\n\r]+ -> skip;

fragment LETTER : ('a'..'z' | 'A'..'Z') ;

fragment DIGIT : ('0'..'9') ;
