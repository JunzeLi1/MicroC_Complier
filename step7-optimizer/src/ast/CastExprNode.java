package ast;
import compiler.Scope;
import ast.visitor.ASTVisitor;

/* Node for cast expressions
It has two nodes:
1) The expression to be type cast
2) Type to which expression should be cast
Updates the type of expression
*/

public class CastExprNode extends ExpressionNode {

	private ExpressionNode expr;
	public CastExprNode(ExpressionNode e, Scope.Type t)
	{
		this.setExpr(e);
		this.setType(t);
	}
	
	private void setExpr(ExpressionNode e)
	{
		this.expr = e;
	}

	public ExpressionNode getExpr()
	{
		return this.expr;
	}

	@Override
	public <R> R accept(ASTVisitor<R> visitor) {
		return visitor.visit(this);
	}

}

