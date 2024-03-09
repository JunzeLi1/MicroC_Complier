package assembly;

import java.util.List;

import compiler.Scope.SymbolTableEntry;
import ast.visitor.AbstractASTVisitor;

import ast.*;
import assembly.instructions.*;
import compiler.Scope;

public class CodeGenerator extends AbstractASTVisitor<CodeObject> {

	int intRegCount;
	int floatRegCount;
	static final public char intTempPrefix = 't';
	static final public char floatTempPrefix = 'f';
	
	int loopLabel;
	int elseLabel;
	int outLabel;
	
	public CodeGenerator() {
		loopLabel = 0;
		elseLabel = 0;
		outLabel = 0;
		intRegCount = 0;		
		floatRegCount = 0;
	}

	public int getIntRegCount() {
		return intRegCount;
	}

	public int getFloatRegCount() {
		return floatRegCount;
	}
	
	/**
	 * Generate code for Variables
	 * 
	 * Create a code object that just holds a variable
	 * 
	 * Important: add a pointer from the code object to the symbol table entry
	 *            so we know how to generate code for it later (we'll need to find
	 *            the address)
	 * 
	 * Mark the code object as holding a variable, and also as an lval
	 */
	@Override
	protected CodeObject postprocess(VarNode node) {
		
		Scope.SymbolTableEntry sym = node.getSymbol();
		
		CodeObject co = new CodeObject(sym);
		co.lval = true;
		co.type = node.getType();

		return co;
	}

	/** Generate code for IntLiterals
	 * 
	 * Use load immediate instruction to do this.
	 */
	@Override
	protected CodeObject postprocess(IntLitNode node) {
		CodeObject co = new CodeObject();
		
		//Load an immediate into a register
		//The li and la instructions are the same, but it's helpful to distinguish
		//for readability purposes.
		//li tmp' value
		Instruction i = new Li(generateTemp(Scope.Type.INT), node.getVal());

		co.code.add(i); //add this instruction to the code object
		co.lval = false; //co holds an rval -- data
		co.temp = i.getDest(); //temp is in destination of li
		co.type = node.getType();

		return co;
	}

	/** Generate code for FloatLiteras
	 * 
	 * Use load immediate instruction to do this.
	 */
	@Override
	protected CodeObject postprocess(FloatLitNode node) {
		CodeObject co = new CodeObject();
		
		//Load an immediate into a regisster
		//The li and la instructions are the same, but it's helpful to distinguish
		//for readability purposes.
		//li tmp' value
		Instruction i = new FImm(generateTemp(Scope.Type.FLOAT), node.getVal());

		co.code.add(i); //add this instruction to the code object
		co.lval = false; //co holds an rval -- data
		co.temp = i.getDest(); //temp is in destination of li
		co.type = node.getType();

		return co;
	}

	/**
	 * Generate code for binary operations.
	 * 
	 * Step 0: create new code object
	 * Step 1: add code from left child
	 * Step 1a: if left child is an lval, add a load to get the data
	 * Step 2: add code from right child
	 * Step 2a: if right child is an lval, add a load to get the data
	 * Step 3: generate binary operation using temps from left and right
	 * 
	 * Don't forget to update the temp and lval fields of the code object!
	 * 	   Hint: where is the result stored? Is this data or an address?
	 * 
	 */
	@Override
	protected CodeObject postprocess(BinaryOpNode node, CodeObject left, CodeObject right) {
		CodeObject co = new CodeObject();
		Instruction i = null;
	
		// Convert left and right children to r-values if they are l-values
		if (left.lval) {
			left = rvalify(left);
		}
		co.code.addAll(left.code);
	
		if (right.lval) {
			right = rvalify(right);
		}
		co.code.addAll(right.code);
	
		// Generate binary operation code based on the operation type
		if (node.getType() == Scope.Type.INT) {
			switch (node.getOp()) {
				case ADD: i = new Add(left.temp, right.temp, generateTemp(Scope.Type.INT)); break;
				case SUB: i = new Sub(left.temp, right.temp, generateTemp(Scope.Type.INT)); break;
				case MUL: i = new Mul(left.temp, right.temp, generateTemp(Scope.Type.INT)); break;
				case DIV: i = new Div(left.temp, right.temp, generateTemp(Scope.Type.INT)); break;
				default: throw new Error("Unsupported operation for INT type.");
			}
		} else if (node.getType() == Scope.Type.FLOAT) {
			switch (node.getOp()) {
				case ADD: i = new FAdd(left.temp, right.temp, generateTemp(Scope.Type.FLOAT)); break;
				case SUB: i = new FSub(left.temp, right.temp, generateTemp(Scope.Type.FLOAT)); break;
				case MUL: i = new FMul(left.temp, right.temp, generateTemp(Scope.Type.FLOAT)); break;
				case DIV: i = new FDiv(left.temp, right.temp, generateTemp(Scope.Type.FLOAT)); break;
				default: throw new Error("Unsupported operation for FLOAT type.");
			}
		} else {
			throw new Error("Unsupported type for binary operation.");
		}
	
		// Add the binary operation instruction to the code object
		co.code.add(i);
		co.lval = false; // The result of a binary operation is an r-value
		co.temp = i.getDest(); // Store the result in a temporary register
	
		return co;
	}

	/**
	 * Generate code for unary operations.
	 * 
	 * Step 0: create new code object
	 * Step 1: add code from child expression
	 * Step 1a: if child is an lval, add a load to get the data
	 * Step 2: generate instruction to perform unary operation
	 * 
	 * Don't forget to update the temp and lval fields of the code object!
	 * 	   Hint: where is the result stored? Is this data or an address?
	 * 
	 */
	@Override
	protected CodeObject postprocess(UnaryOpNode node, CodeObject expr) {
		
		CodeObject co = new CodeObject();
		/* FILL IN FOR STEP 2 */
		if(expr.lval){
		expr = rvalify(expr);
		 }
		co.code.addAll(expr.code);
		InstructionList il = new InstructionList();
		String newTemp = generateTemp(expr.getType()); 
		switch(expr.getType()) {
			case INT: 
				Instruction negi = new Neg(expr.temp, newTemp);// neg tmp, expr.temp
				il.add(negi);
				break;
			case FLOAT:
				Instruction negf = new FNeg(expr.temp, newTemp);// fneg tmp, expr.temp
				il.add(negf);
				break;
			default:
				throw new Error("Negation operation not supported for type " + expr.type);
		}
		// Add the unary operation instruction to the code object
		co.code.addAll(il);
		// Update the temp and lval fields of the code object
		co.lval = false; 
		co.temp = newTemp;  
		co.type = expr.getType();
		return co;
	}

	/**
	 * Generate code for assignment statements
	 * 
	 * Step 0: create new code object
	 * Step 1: if LHS is a variable, generate a load instruction to get the address into a register
	 * Step 1a: add code from LHS of assignment (make sure it results in an lval!)
	 * Step 2: add code from RHS of assignment
	 * Step 2a: if right child is an lval, add a load to get the data
	 * Step 3: generate store
	 * 
	 * Hint: it is going to be easiest to just generate a store with a 0 immediate
	 * offset, and the complete store address in a register:
	 * 
	 * sw rhs 0(lhs)
	 */
	@Override
	protected CodeObject postprocess(AssignNode node, CodeObject left, CodeObject right) {
		
		CodeObject co = new CodeObject();
		Instruction store = null; // Renamed from 'load' to 'store' for clarity
	
		assert(left.lval == true); // Left-hand side must hold an address
	
		// Step 1a: if LHS is a variable, get the address into a register
		if (left.isVar()) {
			left = generateAddrFromVariable(left);
		}
		co.code.addAll(left.code);
	
		// Step 2: add code from RHS of assignment
		// Step 2a: if right child is an lval, load its value
		if (right.lval) {
			right = rvalify(right);
		}
		co.code.addAll(right.code);
	
		// Step 3: generate the appropriate store instruction based on the type
		switch (node.getType()) {
			case INT:
				store = new Sw(right.temp, left.temp, "0");
				break;
			case FLOAT:
				store = new Fsw(right.temp, left.temp, "0");
				break;
			default:
				throw new Error("Unsupported type in assignment");
		}
		
		co.code.add(store); // Add the store instruction to the code
		return co; // Return the completed code object
	}

	/**
	 * Add together all the lists of instructions generated by the children
	 */
	@Override
	protected CodeObject postprocess(StatementListNode node,
			List<CodeObject> statements) {
		CodeObject co = new CodeObject();
		//add the code from each individual statement
		for (CodeObject subcode : statements) {
			co.code.addAll(subcode.code);
		}
		co.type = null; //set to null to trigger errors
		return co;
	}
	
	/**
	 * Generate code for read
	 * 
	 * Step 0: create new code object
	 * Step 1: add code from VarNode (make sure it's an lval)
	 * Step 2: generate GetI instruction, storing into temp
	 * Step 3: generate store, to store temp in variable
	 */
	@Override
	protected CodeObject postprocess(ReadNode node, CodeObject var) {
		
		//Step 0
		CodeObject co = new CodeObject();

		//Generating code for read(id)
		assert(var.getSTE() != null); //var had better be a variable
		CodeObject tmp = generateAddrFromVariable(var);

		co.code.addAll(tmp.code);

		InstructionList il = new InstructionList();
		switch(node.getType()) {
			case INT: 
				//Code to generate if INT:
				//geti tmp
				//sw tmp 0(var.tmp)
				Instruction geti = new GetI(generateTemp(Scope.Type.INT));
				il.add(geti);
				Instruction sw = new Sw(geti.getDest(), tmp.temp, "0");
				il.add(sw);
				break;
			case FLOAT:
				//Code to generate if FLOAT:
				//getf tmp
				//fsw tmp 0(var.tmp)
				Instruction getf = new GetF(generateTemp(Scope.Type.FLOAT));
				il.add(getf);
				Instruction fsw = new Fsw(getf.getDest(), tmp.temp, "0");
				il.add(fsw);
				break;
			default:
				throw new Error("Shouldn't read into other variable");
		}
		
		co.code.addAll(il);

		co.lval = false; //doesn't matter
		co.temp = null; //set to null to trigger errors
		co.type = null; //set to null to trigger errors

		return co;
	}

	/**
	 * Generate code for print
	 * 
	 * Step 0: create new code object
	 * 
	 * If printing a string:
	 * Step 1: add code from expression to be printed (make sure it's an lval)
	 * Step 2: generate a PutS instruction printing the result of the expression
	 * 
	 * If printing an integer:
	 * Step 1: add code from the expression to be printed
	 * Step 1a: if it's an lval, generate a load to get the data
	 * Step 2: Generate PutI that prints the temporary holding the expression
	 */
	@Override
	protected CodeObject postprocess(WriteNode node, CodeObject expr) {
		CodeObject co = new CodeObject();

		//generating code for write(expr)

		//for strings, we expect a variable
		if (node.getWriteExpr().getType() == Scope.Type.STRING) {
			//Step 1:
			assert(expr.getSTE() != null);
			
			//Get the address of the variable
			CodeObject addrCo = generateAddrFromVariable(expr);
			co.code.addAll(addrCo.code);

			//Step 2:
			Instruction write = new PutS(addrCo.temp);
			co.code.add(write);
		} else {
			//Step 1a:
			//if expr is an lval, load from it
			if (expr.lval == true) {
				expr = rvalify(expr);
			}
			
			//Step 1:
			co.code.addAll(expr.code);

			//Step 2:
			//if type of writenode is int, use puti, if float, use putf
			Instruction write = null;
			switch(node.getWriteExpr().getType()) {
			case STRING: throw new Error("Shouldn't have a STRING here");
			case INT: write = new PutI(expr.temp); break;
			case FLOAT: write = new PutF(expr.temp); break;
			default: throw new Error("WriteNode has a weird type");
			}

			co.code.add(write);
		}

		co.lval = false; //doesn't matter
		co.temp = null; //set to null to trigger errors
		co.type = null; //set to null to trigger errors

		return co;
	}

	/**
	 * FILL IN FOR STEP 3
	 * 
	 * Generating an instruction sequence for a conditional expression
	 * 
	 * Implement this however you like. One suggestion:
	 *
	 * Create the code for the left and right side of the conditional, but defer
	 * generating the branch until you process IfStatementNode or WhileNode (since you
	 * do not know the labels yet). Modify CodeObject so you can save the necessary
	 * information to generate the branch instruction in IfStatementNode or WhileNode
	 * 
	 * Alternate idea 1:
	 * 
	 * Don't do anything as part of CodeGenerator. Create a new visitor class
	 * that you invoke *within* your processing of IfStatementNode or WhileNode
	 * 
	 * Alternate idea 2:
	 * 
	 * Create the branch instruction in this function, then tweak it as necessary in
	 * IfStatementNode or WhileNode
	 * 
	 * Hint: you may need to preserve extra information in the returned CodeObject to
	 * make sure you know the type of branch code to generate (int vs float)
	 */
	@Override
	protected CodeObject postprocess(CondNode node, CodeObject left, CodeObject right) {
		CodeObject co = new CodeObject();
		
		// Ensure both sides of the condition are r-values
		if (left.lval) {
			left = rvalify(left);
		}
		if (right.lval) {
			right = rvalify(right);
		}
		
		// Add code from left and right sides of the condition
		co.code.addAll(left.code);
		co.code.addAll(right.code);
		
		// Save the type and temporary registers for later branching instruction generation
		co.type = left.getType();
		co.optype = node.getOp();
		co.leftTemp = left.temp;
		co.rightTemp = right.temp;
	
		return co;
	}
	

	/**
	 * FILL IN FOR STEP 3
	 * 
	 * Step 0: Create code object
	 * 
	 * Step 1: generate labels
	 * 
	 * Step 2: add code from conditional expression
	 * 
	 * Step 3: create branch statement (if not created as part of step 2)
	 * 			don't forget to generate correct branch based on type
	 * 
	 * Step 4: generate code
	 * 		<cond code>
	 *		<flipped branch> elseLabel
	 *		<then code>
	 *		j outLabel
	 *		elseLabel:
	 *		<else code>
	 *		outLabel:
	 *
	 * Step 5 insert code into code object in appropriate order.
	 */
	@Override
	protected CodeObject postprocess(IfStatementNode node, CodeObject cond, CodeObject tlist, CodeObject elist) {
		//Step 0:
		
		CodeObject co = new CodeObject();
		/* FILL IN */
		// Initialize conditional branch instruction and label statements
		Instruction branchCond = null;
		Instruction jumpStmt = null;
		Instruction elseStmt = null;
		Instruction outStmt = null;
		CondNode cnode = null;
		// Extract the conditional node from the if statement for processing
		cnode = node.getCondExpr();
		// Step 1: Generate labels for the 'else' and 'end if' parts of the if-statement
		String elseLabel = generateElseLabel();
		String outLabel = generateOutLabel();
		
		 // Step 2: Add the code for evaluating the conditional expression
		co.code.addAll(cond.code);
		 // Step 3: Generate the branch instruction based on the type of conditional expression
		if(elist.code.isEmpty())
		{
			if(cond.type == Scope.Type.INT)
			{
				switch(cnode.getReversedOp())
				{
					case LE: branchCond = new Ble(cond.leftTemp, cond.rightTemp, outLabel); break;
					case LT: branchCond = new Blt(cond.leftTemp, cond.rightTemp, outLabel); break;
					case GE: branchCond = new Bge(cond.leftTemp, cond.rightTemp, outLabel); break;
					case GT: branchCond = new Bgt(cond.leftTemp, cond.rightTemp, outLabel); break;
					case EQ: branchCond = new Beq(cond.leftTemp, cond.rightTemp, outLabel); break;
					case NE: branchCond = new Bne(cond.leftTemp, cond.rightTemp, outLabel); break;
				}
			}
			if(cond.type == Scope.Type.FLOAT)
			{
				Instruction floatCmp = null;
				switch(cnode.getReversedOp())
				{
					case LE: floatCmp = new Fle(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Bne(floatCmp.getDest(), "x0", outLabel); break;

					case LT: floatCmp = new Flt(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Bne(floatCmp.getDest(), "x0", outLabel); break;

					case GE: floatCmp = new Flt(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Beq(floatCmp.getDest(), "x0", outLabel); break;

					case GT: floatCmp = new Fle(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Beq(floatCmp.getDest(), "x0", outLabel); break;

					case EQ: floatCmp = new Feq(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Bne(floatCmp.getDest(), "x0", outLabel); break;

					case NE: floatCmp = new Feq(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Beq(floatCmp.getDest(), "x0", outLabel); break;
				}
				co.code.add(floatCmp);
			}
			co.code.add(branchCond);

			// Step 4: Add the 'then' part code
			co.code.addAll(tlist.code);
			// Step 5: Add the 'end if' label to signify the end of the if-statement
			outStmt = new Label(outLabel);
			co.code.add(outStmt);
		}
	

		//Else not empty
		else
		{
			if(cond.type == Scope.Type.INT)
			{
				switch(cnode.getReversedOp())
				{
					case LE: branchCond = new Ble(cond.leftTemp, cond.rightTemp, elseLabel); break;
					case LT: branchCond = new Blt(cond.leftTemp, cond.rightTemp, elseLabel); break;
					case GE: branchCond = new Bge(cond.leftTemp, cond.rightTemp, elseLabel); break;
					case GT: branchCond = new Bgt(cond.leftTemp, cond.rightTemp, elseLabel); break;
					case EQ: branchCond = new Beq(cond.leftTemp, cond.rightTemp, elseLabel); break;
					case NE: branchCond = new Bne(cond.leftTemp, cond.rightTemp, elseLabel); break;
				}
			}
			if(cond.type == Scope.Type.FLOAT)
			{
				Instruction floatCmp = null;
				switch(cnode.getReversedOp())
				{
					case LE: floatCmp = new Fle(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Bne(floatCmp.getDest(), "x0", elseLabel); break;

					case LT: floatCmp = new Flt(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Bne(floatCmp.getDest(), "x0", elseLabel); break;

					case GE: floatCmp = new Flt(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Beq(floatCmp.getDest(), "x0", elseLabel); break;

					case GT: floatCmp = new Fle(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Beq(floatCmp.getDest(), "x0", elseLabel); break;

					case EQ: floatCmp = new Feq(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Bne(floatCmp.getDest(), "x0", elseLabel); break;

					case NE: floatCmp = new Feq(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));branchCond = new Beq(floatCmp.getDest(), "x0", elseLabel); break;
				}
				co.code.add(floatCmp);
			}
			co.code.add(branchCond);

			co.code.addAll(tlist.code);
			jumpStmt = new J(outLabel);
			co.code.add(jumpStmt);
			elseStmt = new Label(elseLabel);
			co.code.add(elseStmt);
			co.code.addAll(elist.code);
			outStmt = new Label(outLabel);
			System.out.println("found? "+branchCond.toString().indexOf(':'));
			co.code.add(outStmt);
		}
		return co;
	}

	

		/**
	 * FILL IN FOR STEP 3
	 * 
	 * Step 0: Create code object
	 * 
	 * Step 1: generate labels
	 * 
	 * Step 2: add code from conditional expression
	 * 
	 * Step 3: create branch statement (if not created as part of step 2)
	 * 			don't forget to generate correct branch based on type
	 * 
	 * Step 4: generate code
	 * 		loopLabel:
	 *		<cond code>
	 *		<flipped branch> outLabel
	 *		<body code>
	 *		j loopLabel
	 *		outLabel:
	 *
	 * Step 5 insert code into code object in appropriate order.
	 */
	@Override
	protected CodeObject postprocess(WhileNode node, CodeObject cond, CodeObject slist) {
		// Create a new CodeObject for the while loop
		CodeObject co = new CodeObject();
	
		// Declaration of variables for branch instructions and labels
		Instruction branchCond = null;
		Instruction jumpStmt = null;
		Instruction loopStmt = null;
		Instruction outStmt = null;
		CondNode cnode = node.getCond(); // Get the conditional node from the while statement
	
		// Generate unique labels for the loop start and the loop exit
		String loopLabel = generateLoopLabel();
		String outLabel = generateOutLabel();
		
		// Create a label at the start of the loop
		loopStmt = new Label(loopLabel);
		co.code.add(loopStmt); // Add the loop start label to the code object
	
		// Add the code for the condition check
		co.code.addAll(cond.code);
		
		// Depending on the type of the condition (INT or FLOAT), generate the appropriate branch instruction
		// The branch will be to the out label if the condition is false (reversed logic for while loops)
		if(cond.type == Scope.Type.INT) {
			// Handle integer type conditions with corresponding branch instructions
			switch(cnode.getReversedOp()) {
				case LE: branchCond = new Ble(cond.leftTemp, cond.rightTemp, outLabel); break;
				case LT: branchCond = new Blt(cond.leftTemp, cond.rightTemp, outLabel); break;
				case GE: branchCond = new Bge(cond.leftTemp, cond.rightTemp, outLabel); break;
				case GT: branchCond = new Bgt(cond.leftTemp, cond.rightTemp, outLabel); break;
				case EQ: branchCond = new Beq(cond.leftTemp, cond.rightTemp, outLabel); break;
				case NE: branchCond = new Bne(cond.leftTemp, cond.rightTemp, outLabel); break;
			}
		} else if(cond.type == Scope.Type.FLOAT) {
			// Handle floating-point type conditions
			// For floating-point, first perform the comparison and then use the result in a branch
			Instruction floatCmp = null;
			switch(cnode.getReversedOp()) {
				// For each case, compare and then set up a branch instruction based on the comparison result
				// If the condition is true, continue in the loop; otherwise, jump to the out label
				// The `generateTemp` method is used to create a temporary register for holding comparison results
				case LE: 
					floatCmp = new Fle(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));
					branchCond = new Bne(floatCmp.getDest(), "x0", outLabel); 
					break;
				case LT: 
					floatCmp = new Flt(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));
					branchCond = new Bne(floatCmp.getDest(), "x0", outLabel); 
					break;
				case GE: 
					floatCmp = new Flt(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));
					branchCond = new Beq(floatCmp.getDest(), "x0", outLabel); 
					break;
				case GT: 
					floatCmp = new Fle(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));
					branchCond = new Beq(floatCmp.getDest(), "x0", outLabel); 
					break;
				case EQ: 
					floatCmp = new Feq(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));
					branchCond = new Bne(floatCmp.getDest(), "x0", outLabel); 
					break;
				case NE: 
					floatCmp = new Feq(cond.leftTemp, cond.rightTemp, generateTemp(Scope.Type.INT));
					branchCond = new Beq(floatCmp.getDest(), "x0", outLabel); 
					break;
			}
			co.code.add(floatCmp); // Add the floating-point comparison instruction
		}
		co.code.add(branchCond); // Add the branch instruction based on the condition
		
		// Add the code for the statements inside the while loop
		co.code.addAll(slist.code);
		
		// After the loop body, jump back to the start of the loop to check the condition again
		jumpStmt = new J(loopLabel);
		co.code.add(jumpStmt);
		
		// Label for the exit point of the loop
		outStmt = new Label(outLabel);
		co.code.add(outStmt); // Add the exit label
		
		// Return the code object representing the entire while loop
		return co;
	}
	
	/**
	 * Generating code for returns
	 * 
	 * For now, we don't do anything with return values, so just generate HALT
	 */
	@Override
	protected CodeObject postprocess(ReturnNode node, CodeObject retExpr) {
		CodeObject co = new CodeObject();

		//if retexpr is an lval, load from it
		if (retExpr.lval == true) {
			retExpr = rvalify(retExpr);
		}

		co.code.addAll(retExpr.code);

		//We don't support functions yet, so a return is just a halt
		Instruction h = new Halt();
		co.code.add(h);
		co.type = null; //set to null to trigger errors

		return co;
	}
	
	/**
	 * Generate a fresh temporary
	 * 
	 * @return new temporary register name
	 */
	protected String generateTemp(Scope.Type t) {
		switch(t) {
			case INT: return intTempPrefix + String.valueOf(++intRegCount);
			case FLOAT: return floatTempPrefix + String.valueOf(++floatRegCount);
			default: throw new Error("Generating temp for bad type");
		}
	}

	protected String generateLoopLabel() {
		return "loop_" + String.valueOf(++loopLabel);
	}

	protected String generateElseLabel() {
		return  "else_" + String.valueOf(++elseLabel);
	}

	protected String generateOutLabel() {
		return "out_" +  String.valueOf(++outLabel);
	}
	
	/**
	 * Take a code object that results in an lval, and create a new code
	 * object that adds a load to generate the rval.
	 * 
	 * @param lco The code object resulting in an address
	 * @return A code object with all the code of <code>lco</code> followed by a load
	 *         to generate an rval
	 */
	protected CodeObject rvalify(CodeObject lco) {
		// Step 0: Create a new code object
		CodeObject co = new CodeObject();
		Instruction load;
	
		// Ensure that the input CodeObject is an l-value
		assert (lco.lval == true);
	
		// Step 1: Add all the lco code to the new code object
		// If lco is just a variable, store the address of the variable in a code object
		if (lco.isVar()) {
			lco = generateAddrFromVariable(lco);
		}
		co.code.addAll(lco.code);
	
		// Step 2: Generate a load instruction based on the type of lco
		switch (lco.getType()) {
			case INT:
				load = new Lw(generateTemp(Scope.Type.INT), lco.temp, "0");
				break;
			case FLOAT:
				load = new Flw(generateTemp(Scope.Type.FLOAT), lco.temp, "0");
				break;
			default:
				throw new Error("Unsupported type for rvalify");
		}
	
		// Add the load instruction to the code object
		co.code.add(load);
	
		// Step 3: Update the temp and lval fields of the code object
		co.temp = load.getDest(); // The result is stored in the new temporary
		co.lval = false; // The code object now holds an r-value (data)
		co.type = lco.type; // Preserve the type information
	
		return co;
	}

	/**
	 * Take a code object that holds just a variable and turn it into a code object
	 * that places the address of that variable into a registers
	 * 
	 * @param lco The code object holding a variable
	 * @return A code object with the variable's address in a register
	 */
	private CodeObject generateAddrFromVariable(CodeObject lco) {

		CodeObject co = new CodeObject();

		//Step 1:
		SymbolTableEntry symbol = lco.getSTE();
		String address = String.valueOf(symbol.getAddress());

		//Step 2:
		//la tmp' addr //Register type needs to be an int
		Instruction loadAddr = new La(generateTemp(Scope.Type.INT), address);

		co.code.add(loadAddr); //add instruction to code object
		co.lval = true; //co holds an lval, because it's an address
		co.temp = loadAddr.getDest(); //temp is in destination of la
		co.ste = null; //not a variable
		co.type = symbol.getType(); //even though register type is an int, address points to Type

		return co;
	}

}
