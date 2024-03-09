package ast;

import ast.visitor.ASTVisitor;
import compiler.Scope;

/**
 * A node for unary expressions (negation)
 * 
 * This has one child: the {@link ExpressionNode} being operated on
 */
public class CastNode extends ExpressionNode {
	
	private ExpressionNode expr;
    private Scope.Type type;
	
	public CastNode(ExpressionNode expr, Scope.Type type) {
        this.setExpr(expr);
        this.setTypeCast(type);
    }

	@Override
	public <R> R accept(ASTVisitor<R> visitor) {
		return visitor.visit(this);
	}

	public ASTNode getExpr() {
		return expr;
	}

	private void setExpr(ExpressionNode right) {
		this.expr = right;
	}

    public Scope.Type getTypeCast() {
        return type;
    }

    private void setTypeCast(Scope.Type type) {
        this.type = type;
    }
}
