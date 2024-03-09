package assembly;

import java.util.List;
import ast.visitor.AbstractASTVisitor;
import compiler.Compiler;
import ast.*;
import compiler.Scope;

/**
 * A visitor class for AST nodes that performs type checking.
 * It extends AbstractASTVisitor and returns a CodeObject with type information for each node.
 */
public class TypeErrorChecking extends AbstractASTVisitor<CodeObject> {
    
    /**
     * Processes a variable node and assigns its type to a new code object.
     */
    @Override
    protected CodeObject postprocess(VarNode node) {
        CodeObject co = new CodeObject();
        co.type = node.getType();
        return co;
    }

    /**
     * Processes an integer literal node and assigns its type to a new code object.
     */
    @Override
    protected CodeObject postprocess(IntLitNode node) {
        CodeObject co = new CodeObject();
        co.type = node.getType();
        return co;
    }

    /**
     * Processes a float literal node and assigns its type to a new code object.
     */
    @Override
    protected CodeObject postprocess(FloatLitNode node) {
        CodeObject co = new CodeObject();
        co.type = node.getType();
        return co;
    }

    /**
     * Processes a binary operation node, checks for type compatibility between operands,
     * and exits the program with an error if they are not compatible.
     */
    @Override
    protected CodeObject postprocess(BinaryOpNode node, CodeObject left, CodeObject right) {
        CodeObject co = new CodeObject();
        if (left.type != right.type) {
            System.err.println("TYPE ERROR");
            System.exit(7);
        }
        co.type = node.getType();
        return co;
    }

    /**
     * Processes a unary operation node and assigns its type to a new code object.
     */
    @Override
    protected CodeObject postprocess(UnaryOpNode node, CodeObject expr) {
        CodeObject co = new CodeObject();
        co.type = node.getType();
        return co;
    }

    /**
     * Processes an assignment node, checks for type compatibility between the variable and the expression,
     * and exits the program with an error if they are not compatible.
     */
    @Override
    protected CodeObject postprocess(AssignNode node, CodeObject left, CodeObject right) {
        CodeObject co = new CodeObject();
        if (left.type != right.type) {
            System.err.println("TYPE ERROR");
            System.exit(7);
        }
        co.type = node.getType();
        return co;
    }

    /**
     * Processes a conditional node, checks for type compatibility between the left and right expressions,
     * and exits the program with an error if they are not compatible.
     */
    @Override
    protected CodeObject postprocess(CondNode node, CodeObject left, CodeObject right) {
        CodeObject co = new CodeObject();
        if (left.getType() != right.getType()) {
            System.err.println("TYPE ERROR");
            System.exit(7);
        }
        co.type = left.getType();
        return co;
    }

    /**
     * Processes a return node, checks that the expression type matches the function return type,
     * and exits the program with an error if they do not match.
     */
    @Override
    protected CodeObject postprocess(ReturnNode node, CodeObject retExpr) {
        CodeObject co = new CodeObject();
        if (retExpr.getType() != node.getFuncSymbol().getReturnType()) {
            System.err.println("TYPE ERROR");
            System.exit(7);
        }
        co.type = node.getFuncSymbol().getReturnType();
        return co;
    }

    /**
     * Processes a function call node, checks the number and types of arguments against the function's signature,
     * and exits the program with an error if they do not match.
     */
    @Override
    protected CodeObject postprocess(CallNode node, List<CodeObject> args) {
        CodeObject co = new CodeObject();
        String funcName = node.getFuncName();
        Scope.FunctionSymbolTableEntry ste = (Scope.FunctionSymbolTableEntry) Compiler.symbolTable.getFunctionSymbol(funcName);
        List<Scope.Type> argList = ste.getArgTypes();
    
        if (argList.size() != args.size()) {
            System.err.println("TYPE ERROR");
            System.exit(7);
        }
    
        int i = 0; // Initialize the counter
        while (i < argList.size()) { // Condition to continue the loop
            if (argList.get(i) != args.get(i).type) {
                System.err.println("TYPE ERROR");
                System.exit(7);
            }
            i++; // Increment the counter
        }
        co.type = node.getType();
        return co;
    }
    
}
