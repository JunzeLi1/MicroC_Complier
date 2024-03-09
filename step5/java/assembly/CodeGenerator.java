package assembly;

import compiler.Scope.SymbolTableEntry;
import ast.visitor.AbstractASTVisitor;

import java.util.*;
import ast.*;
import assembly.instructions.*;
import compiler.Scope;
import compiler.SymbolTable;

public class CodeGenerator extends AbstractASTVisitor<CodeObject> {

	int intRegCount;
	int floatRegCount;
	static final public String intTempPrefix = "$t";
	static final public String floatTempPrefix = "$f";
	
	int loopLabel;
	int elseLabel;
	int outLabel;

	static final public int numIntRegisters = 32;
	static final public int numFloatRegisters = 32;

	int numRegisters;
	String currFunc;
	SymbolTable symbolTab;
	
	public CodeGenerator(String numReg, SymbolTable symTab) {
		loopLabel = 0;
		elseLabel = 0;
		outLabel = 0;
		intRegCount = 0;		
		floatRegCount = 0;
		numRegisters = Integer.parseInt(numReg);
		symbolTab = symTab;
	}

	public int getIntRegCount() {
		return intRegCount;
	}

	public int getFloatRegCount() {
		return floatRegCount;
	}

	public int getNumReg()
	{
		return numRegisters;
	}
	
	/**
	 * Generate code for Variables
	 * 
	 * Create a code object that just holds a variable
	 * 
	 * NOTE THAT THIS HAS CHANGED TO GENERATE 3AC INSTEAD
	 */
	@Override
	protected CodeObject postprocess(VarNode node) {
		
		Scope.SymbolTableEntry sym = node.getSymbol();
		
		CodeObject co = new CodeObject(sym);
		co.lval = true;
		co.type = node.getType();
		if (sym.isLocal()) {
			co.temp = "$l" + String.valueOf(sym.getAddress());
		} else {
			co.temp = "$g" + sym.getName();
		}


		return co;
	}

	/** Generate code for IntLiterals
	 * 
	 * NOTE THAT THIS HAS CHANGED TO GENERATE 3AC INSTEAD
	 */
	@Override
	protected CodeObject postprocess(IntLitNode node) {
		CodeObject co = new CodeObject();
		
		//Load an immediate into a register
		//The li and la instructions are the same, but it's helpful to distinguish
		//for readability purposes.
		//li tmp' value
		//Instruction i = new Li(generateTemp(Scope.Type.INT), node.getVal());

		//co.code.add(i); //add this instruction to the code object
		co.lval = false; //co holds an rval -- data
		co.temp = String.valueOf(node.getVal()); //temp is in destination of li
		co.type = node.getType();

		return co;
	}

	/** Generate code for FloatLiteras
	 * 
	 * NOTE THAT THIS HAS CHANGED TO GENERATE 3AC INSTEAD
	 */
	@Override
	protected CodeObject postprocess(FloatLitNode node) {
		CodeObject co = new CodeObject();
		
		//Load an immediate into a regisster
		//The li and la instructions are the same, but it's helpful to distinguish
		//for readability purposes.
		//li tmp' value
		//Instruction i = new FImm(generateTemp(Scope.Type.FLOAT), node.getVal());

		//co.code.add(i); //add this instruction to the code object
		co.lval = false; //co holds an rval -- data
		co.temp = String.valueOf(node.getVal()); //temp is in destination of li
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
		co.code.addAll(left.code);

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
        co.type = node.getType();

		System.out.println("In BinaryOpNode inst = "+i);
	
		return co;
	}


	/**
	 * Generate code for unary operations.
	 * 
	 * Step 0: create new code object
	 * Step 1: add code from child expression
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
	
		// // Ensure left is an lval and holds a variable
		// if (!left.lval) {
		// 	throw new Error("Left-hand side of assignment does not hold a variable.");
		// }
	
		// InstructionList il = new InstructionList();
		// SymbolTableEntry symbol = left.getSTE();
		// String address = symbol.addressToString();
	
		// // Generate address code for LHS if it's a variable
		// il = generateAddrFromVariable(left);
		// co.code.addAll(il);
	
		// // Ensure right is rval
		// if (right.lval) {
		// 	right = rvalify(right);
		// }
		// co.code.addAll(right.code);
	
		// // Generate the store instruction based on whether the variable is local or global
		// Instruction storeInstruction;
		// if (symbol.isLocal()) {
		// 	// For local variables, store at the offset from the frame pointer
		// 	storeInstruction = (left.type == Scope.Type.INT) ? new Sw(right.temp, "fp", address) : new Fsw(right.temp, "fp", address);
		// } else {
		// 	// For global variables, store at the address contained in the last instruction of 'il'
		// 	String lhsAddress = il.getLast().getDest();
		// 	storeInstruction = (left.type == Scope.Type.INT) ? new Sw(right.temp, lhsAddress, "0") : new Fsw(right.temp, lhsAddress, "0");
		// }
	
		// co.code.add(storeInstruction);
	
		// // Set type based on LHS variable
		// co.type = left.type;
		co.code.addAll(right.code);
		Instruction store = null;
		// Depending on the type of the node (INT or FLOAT), create the appropriate move instruction.
		switch(node.getType())
		{
			case INT: store = new Mv(right.temp, left.temp);break;
			case FLOAT: store = new FMv(right.temp, left.temp);break;
		}
		co.code.add(store);
		
		
		//System.out.println("In AssignNode left temp = "+left.temp);
		co.lval = false;
		return co;
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
	 * 
	 * NOTE THAT THIS HAS CHANGED TO GENERATE 3AC INSTEAD
	 */
	@Override
	protected CodeObject postprocess(ReadNode node, CodeObject var) {
		
		//Step 0
		CodeObject co = new CodeObject();

		//Generating code for read(id)
		assert(var.getSTE() != null); //var had better be a variable

		InstructionList il = new InstructionList();
		switch(node.getType()) {
			case INT: 
				//Code to generate if INT:
				//geti var.tmp
				Instruction geti = new GetI(var.temp);
				il.add(geti);
				break;
			case FLOAT:
				//Code to generate if FLOAT:
				//getf var.tmp
				Instruction getf = new GetF(var.temp);
				il.add(getf);
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
	 * 
	 * NOTE THAT THIS HAS CHANGED TO GENERATE 3AC INSTEAD
	 */
	@Override
	protected CodeObject postprocess(WriteNode node, CodeObject expr) {
		CodeObject co = new CodeObject();

		//generating code for write(expr)

		//for strings, we expect a variable
		if (node.getWriteExpr().getType() == Scope.Type.STRING) {
			//Step 1:
			assert(expr.getSTE() != null);

			//Step 2:
			Instruction write = new PutS(expr.temp);
			co.code.add(write);
		} else {			
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
	 * FILL IN FROM STEP 3
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
		// if (left.lval) {
		// 	left = rvalify(left);
		// }
		// if (right.lval) {
		// 	right = rvalify(right);
		// }
		
		// Add code from left and right sides of the condition
		co.code.addAll(left.code);
		co.code.addAll(right.code);
		
		// Save the type and temporary registers for later branching instruction generation
		co.type = left.getType();
		co.leftTemp = left.temp;
		co.rightTemp = right.temp;
	
		return co;
	}

	/**
	 * FILL IN FROM STEP 3
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
	 * 
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
			//System.out.println("found? "+branchCond.toString().indexOf(':'));
			co.code.add(outStmt);
		}
		return co;
	}

		/**
	 * FILL IN FROM STEP 3
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
	 * FILL IN FOR STEP 4
	 * 
	 * Generating code for returns
	 * 
	 * Step 0: Generate new code object
	 * 
	 * Step 1: Add retExpr code to code object (rvalify if necessary)
	 * 
	 * Step 2: Store result of retExpr in appropriate place on stack (fp + 8)
	 * 
	 * Step 3: Jump to out label (use @link{generateFunctionOutLabel()})
	 */
	@Override
	protected CodeObject postprocess(ReturnNode node, CodeObject retExpr) {
		CodeObject co = new CodeObject();
	
		// Generate the label for the end of the function, to which the return statement will jump
		String fnOutLabel = generateFunctionOutLabel();
	
		// Step 1: If the return expression is an lvalue (i.e., an address), convert it to an rvalue (i.e., a value)
		// if (retExpr.lval) {
		// 	retExpr = rvalify(retExpr);
		// }
		// Add the code for evaluating the return expression to the code object
		co.code.addAll(retExpr.code);
	
		// Step 2: Depending on the type of the return expression, store its value in the correct stack position
		// The return value is always placed at an offset of 8 from the frame pointer (fp)
		Instruction store = null;
		switch(retExpr.getType()) {
			case INT:
				// If the return type is an integer, use the store word (sw) instruction
				store = new Mv(retExpr.temp, "$l8");
				break;
			case FLOAT:
				// If the return type is a float, use the floating-point store (fsw) instruction
				store = new FMv(retExpr.temp, "$l8");
				break;
			// Add cases for additional types if necessary
		}
		// Add the store instruction to the code object
		co.code.add(store);    
	
		// Step 3: Add a jump instruction to jump to the function out label
		// This is where the function will perform cleanup and then return to the caller
		Instruction jumpStmt = new J(fnOutLabel);
		co.code.add(jumpStmt);
	
		// Return the completed code object containing the instructions for the return statement
		return co;
	}	

	@Override
	protected void preprocess(FunctionNode node) {
		// Generate function label information, used for other labels inside function
		currFunc = node.getFuncName();

		//reset register counts; each function uses new registers!
		intRegCount = 0;
		floatRegCount = 0;
	}

	/**
	 * FILL IN FOR STEP 4
	 * 
	 * Generate code for functions
	 * 
	 * Step 1: add the label for the beginning of the function
	 * 
	 * Step 2: manage frame  pointer
	 * 			a. Save old frame pointer
	 * 			b. Move frame pointer to point to base of activation record (current sp)
	 * 			c. Update stack pointer
	 * 
	 * Step 3: allocate new stack frame (use scope infromation from FunctionNode)
	 * 
	 * Step 4: save registers on stack (Can inspect intRegCount and floatRegCount to know what to save)
	 * 
	 * Step 5: add the code from the function body
	 * 
	 * Step 6: add post-processing code:
	 * 			a. Label for `return` statements inside function body to jump to
	 * 			b. Restore registers
	 * 			c. Deallocate stack frame (set stack pointer to frame pointer)
	 * 			d. Reset fp to old location
	 * 			e. Return from function
	 */
	@Override
	protected CodeObject postprocess(FunctionNode node, CodeObject bodyCodeObj) {
		CodeObject co = new CodeObject();  // Create a new CodeObject for the function.
	
		// Initialize register allocation with the function's body, scope, and symbol table.
		RegisterAlloc regAlloc = new RegisterAlloc(getNumReg(), bodyCodeObj.code, node.getScope(), symbolTab);
	
		List<InstructionList> basicBlocks; // To hold basic blocks after splitting.
		InstructionList compiledCode = new InstructionList(); // Final compiled code for the function.
		ArrayList<Integer> usedIntRegisters = new ArrayList<>(); // List of used integer registers.
		ArrayList<Integer> usedFloatRegisters = new ArrayList<>(); // List of used floating-point registers.
		Map<Integer, LinkedList<String>> livenessOutMap = new HashMap<>(); // Map for liveness analysis results.
		int maxNumLocals = 0; // To track the maximum number of local variables.
	
		// Split the body code into basic blocks for register allocation.
		basicBlocks = regAlloc.Split_BaseBlock();
		for (InstructionList block : basicBlocks) {
			// Perform liveness analysis and register allocation on each basic block.
			livenessOutMap = regAlloc.liveness(block);
			regAlloc.registerAllocation(block, livenessOutMap);
	
			// Add the compiled code for this block to the final code list.
			compiledCode.addAll(regAlloc.getCode());
	
			// Update lists of used registers and maximum number of local variables.
			for (int reg : regAlloc.getUsedIntRegList()) {
				if (!usedIntRegisters.contains(reg)) {
					usedIntRegisters.add(reg);
				}
			}
			for (int reg : regAlloc.getUsedFloatRegList()) {
				if (!usedFloatRegisters.contains(reg)) {
					usedFloatRegisters.add(reg);
				}
			}
			if (regAlloc.getNumLocals() > maxNumLocals) {
				maxNumLocals = regAlloc.getNumLocals();
			}
	
			// Reinitialize register allocation for the next block.
			regAlloc = new RegisterAlloc(getNumReg(), bodyCodeObj.code, node.getScope(), symbolTab);
		}
	
		// Start generating function code.
	
		// Step 1: Add label for function entry.
		String functionLabel = generateFunctionLabel(node.getFuncName());
		co.code.add(new Label(functionLabel));
	
		// Steps 2a and 2b: Save the frame pointer and update the stack pointer.
		co.code.add(new Sw("fp", "sp", "0"));
		co.code.add(new Mv("sp", "fp"));
	
		// Step 2c: Allocate space on the stack for local variables.
		String stackPointerOffset = String.valueOf(-4 * maxNumLocals);
		co.code.add(new Addi("sp", stackPointerOffset, "sp"));
	
		// Step 4: Save registers to the stack.
		for (int reg : usedIntRegisters) {
			String regName = "x" + reg;
			co.code.add(new Sw(regName, "sp", "0"));
			co.code.add(new Addi("sp", "-4", "sp"));
		}
		for (int reg : usedFloatRegisters) {
			String regName = "f" + reg;
			co.code.add(new Fsw(regName, "sp", "0"));
			co.code.add(new Addi("sp", "-4", "sp"));
		}
	
		// Step 5: Add compiled code from function body.
		co.code.addAll(compiledCode);
	
		// Step 6: Handle function return.
		String exitLabel = generateFunctionOutLabel();
		co.code.add(new Label(exitLabel));
	
		// Step 6b: Restore registers from the stack in reverse order.
		Collections.reverse(usedFloatRegisters);
		for (int reg : usedFloatRegisters) {
			String regName = "f" + reg;
			co.code.add(new Addi("sp", "4", "sp"));
			co.code.add(new Flw(regName, "sp", "0"));
		}
		Collections.reverse(usedIntRegisters);
		for (int reg : usedIntRegisters) {
			String regName = "x" + reg;
			co.code.add(new Addi("sp", "4", "sp"));
			co.code.add(new Lw(regName, "sp", "0"));
		}
	
		// Step 6c and 6d: Move the stack pointer back to the frame pointer and reset the frame pointer.
		co.code.add(new Mv("fp", "sp"));
		co.code.add(new Lw("fp", "fp", "0"));
	
		// Step 6e: Add the return instruction.
		co.code.add(new Ret());
	
		return co;  // Return the compiled code for the function.
	}
	
	/**
	 * Generate code for the list of functions. This is the "top level" code generation function
	 * 
	 * Step 1: Set fp to point to sp
	 * 
	 * Step 2: Insert a JR to main
	 * 
	 * Step 3: Insert a HALT
	 * 
	 * Step 4: Include all the code of the functions
	 */
	@Override
	protected CodeObject postprocess(FunctionListNode node, List<CodeObject> funcs) {
		CodeObject co = new CodeObject();

		co.code.add(new Mv("sp", "fp"));
		co.code.add(new Jr(generateFunctionLabel("main")));
		co.code.add(new Halt());
		co.code.add(new Blank());

		//add code for each of the functions
		for (CodeObject c : funcs) {
			co.code.addAll(c.code);
			co.code.add(new Blank());
		}

		return co;
	}

	/**
	* 
	* FILL IN FOR STEP 4
	* 
	* Generate code for a call expression
	 * 
	 * Step 1: For each argument:
	 * 
	 * 	Step 1a: insert code of argument (don't forget to rvalify!)
	 * 
	 * 	Step 1b: push result of argument onto stack 
	 * 
	 * Step 2: alloate space for return value
	 * 
	 * Step 3: push current return address onto stack
	 * 
	 * Step 4: jump to function
	 * 
	 * Step 5: pop return address back from stack
	 * 
	 * Step 6: pop return value into fresh temporary (destination of call expression)
	 * 
	 * Step 7: remove arguments from stack (move sp)
	 */
	@Override
	protected CodeObject postprocess(CallNode node, List<CodeObject> args) {
		
		//STEP 0
		CodeObject co = new CodeObject();

		/* FILL IN */
		
		// Initialize offset to track the stack pointer movement
		int i = 0;
		Instruction store = null;

		// Iterate over each argument provided to the function call
		for(CodeObject arg : args)
		{
			// Add the code required to evaluate the argument
			co.code.addAll(arg.code);

			// Based on the type of the argument, generate the appropriate store instruction
			switch(arg.getType())
			{
				case INT: 
					store = new PushInt(arg.temp);
					co.code.add(store);
					store = new Addi("sp", "-4", "sp");
					co.code.add(store);
					break;
				case FLOAT: 
					store = new PushFloat(arg.temp);
					co.code.add(store);
					store = new Addi("sp", "-4", "sp");
					co.code.add(store);
					break;
			}
			// Update the stack offset after pushing each argument
			i = i - 4;
		}

		// Allocate space on the stack for the function's return value
		store = new Addi("sp", "-4", "sp");
		co.code.add(store);

		// Store the return address (ra) on the stack and move the stack pointer
		store = new PushInt("ra");
		co.code.add(store);
		store = new Addi("sp", "-4", "sp");
		co.code.add(store);
	
		// Jump to the function's entry point
		store = new Jr(generateFunctionLabel(node.getFuncName()));
		co.code.add(store);

		// After the function call, retrieve the return address and adjust the stack pointer
		store = new Addi("sp", "4", "sp");
		co.code.add(store);
		store = new PopInt("ra");
		co.code.add(store);

		// Retrieve the return value from the stack into a fresh temporary register
		store = new Addi("sp", "4", "sp");
		co.code.add(store);
		String tempin = generateTemp(Scope.Type.INT);
		String tempfl = generateTemp(Scope.Type.FLOAT);
		// Choose the correct load instruction based on the return type
		switch(node.getType())
		{
			case INT: 
				store = new PopInt(tempin);
				co.temp = tempin;
				break;
			case FLOAT: 
				store = new PopFloat(tempfl);
				co.temp = tempfl;
				break;
		}
		// Add the instruction to load the return value and record the destination temporary
		co.code.add(store);
		System.out.println("Ret val: "+co.temp);
		//co.temp = store.getDest();
		// Adjust the stack pointer to remove the space allocated for arguments
		i = -1 * i;
		store = new Addi("sp", String.valueOf(i), "sp");
		co.code.add(store);

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

	protected String generateFunctionLabel() {
		return "func_" + currFunc;
	}

	protected String generateFunctionLabel(String func) {
		return "func_" + func;
	}

	protected String generateFunctionOutLabel() {
		return "func_ret_" + currFunc;
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
		
		assert (lco.lval == true);
		CodeObject co = new CodeObject();

		/* THIS WON'T BE NECESSARY IF YOU'RE GENERATING 3AC */

		/* DON'T FORGET TO ADD CODE TO GENERATE LOADS FOR LOCAL VARIABLES */

		return co;
	}

	/**
	 * Generate an instruction sequence that holds the address of the variable in a code object
	 * 
	 * If it's a global variable, just get the address from the symbol table
	 * 
	 * If it's a local variable, compute the address relative to the frame pointer (fp)
	 * 
	 * @param lco The code object holding a variable
	 * @return a list of instructions that puts the address of the variable in a register
	 */
	private InstructionList generateAddrFromVariable(CodeObject lco) {

		InstructionList il = new InstructionList();

		//Step 1:
		SymbolTableEntry symbol = lco.getSTE();
		String address = symbol.addressToString();

		//Step 2:
		Instruction compAddr = null;
		if (symbol.isLocal()) {
			//If local, address is offset
			//need to load fp + offset
			//addi tmp' fp offset
			compAddr = new Addi("fp", address, generateTemp(Scope.Type.INT));
		} else {
			//If global, address in symbol table is the right location
			//la tmp' addr //Register type needs to be an int
			compAddr = new La(generateTemp(Scope.Type.INT), address);
		}
		il.add(compAddr); //add instruction to code object

		return il;
	}

}
