package assembly;

import java.io.StringWriter;
import java.util.Collection;
import assembly.instructions.Instruction;
import compiler.Scope;
import compiler.Scope.SymbolTableEntry;

/**
 * Code object class. 
 * 
 * Stores the list of {@link Instruction}s representing the code for a particular
 * subtree, as well as the location of the register where the result of that code
 * is stored (if any). Also tracks with <code>lval</code> whether the temporary
 * holds an lval (an address) or an rval (data).
 */
public class CodeObject {
	InstructionList code;
	String temp; //temporary where result of current code is stored
	Scope.Type type; //type of value stored in temp if rval, type of value in address if lval
	boolean lval; //true if lvalue, false if rvalue
	SymbolTableEntry ste; //null if there is no variable, non-null if there is a variable
	ast.CondNode.OpType optype;
	
	String leftTemp;
	String rightTemp;	

	public CodeObject() {
		this(null);
	}

	public enum OpType {
		EQ,
		NE,
		LT,
		LE,
		GT,
		GE,
	}

	public String getleftString(){
		return leftTemp;
	}

	public String getrightString(){
		return rightTemp;
	}

	public ast.CondNode.OpType getOp() {
		return optype;
	}

	public CodeObject(SymbolTableEntry ste) {
		this.ste = ste;
		if (ste != null) 
			this.type = ste.getType();
		else
			this.type = null;
		code = new InstructionList();
	}
	
	public String toString() {
		StringWriter sw = new StringWriter();
		
		sw.write(";Current temp: " + temp + "\n");
		sw.write(";IR Code: \n");
		
		sw.write(code.toString());
		
		return sw.toString();
	}
	
	public Collection<Instruction> getCode() {
		return code;
	}

	public boolean isVar() {
		return (ste != null);
	}

	public SymbolTableEntry getSTE() {
		return ste;
	}

	public Scope.Type getType() {
		return type;
	}
}
