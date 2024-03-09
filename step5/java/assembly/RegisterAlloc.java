package assembly;

import java.util.*;
import assembly.instructions.*;
import assembly.instructions.Instruction.OpCode;
import compiler.Scope;
import compiler.Scope.SymbolTableEntry;
import compiler.SymbolTable;

import compiler.LocalScope;

public class RegisterAlloc {
    int numReg;
    InstructionList il;
    LocalScope lc;
    SymbolTable symbolTab;

    String src1Reg;

    InstructionList code;

    List<InstructionList> basicBlocks = new LinkedList<InstructionList>();
    
    ArrayList<String> intRegList = new ArrayList<>();
    Map<Integer,Integer> dirtyIntRegList = new HashMap<Integer,Integer>();

    ArrayList<String> floatRegList = new ArrayList<>();
    Map<Integer,Integer> dirtyFloatRegList = new HashMap<Integer,Integer>();

    ArrayList<Integer> usedIntRegList = new ArrayList<>();
    ArrayList<Integer> usedFloatRegList = new ArrayList<>();
    
    
    /*Initializes the RegisterAlloc object with the specified number of registers, instruction list, local scope, and symbol table.
    Initializes the integer and floating point register lists (intRegList and floatRegList) with "empty" to denote available registers.
    Sets certain registers as reserved or designated for specific uses, such as "address" or marked with "no" to indicate they are not available for general use. */
    public RegisterAlloc(int num, InstructionList i, LocalScope l, SymbolTable st)
    {
        this.numReg = num;
        this.il = i;
        this.lc = l;
        this.symbolTab = st;

        // Initialize the lists for integer and floating-point registers.
        for(int j = 0; j < this.numReg; j++)
        {
            this.intRegList.add("empty");
            this.floatRegList.add("empty");
        }

        // Set certain registers as reserved or for specific uses.
        this.intRegList.set(0,"no");
        this.intRegList.set(1,"no");	
        this.intRegList.set(2,"no");
        this.intRegList.set(3,"address");
        this.intRegList.set(8,"no");
    }

    public ArrayList<Integer> getUsedIntRegList()
    {
        return this.usedIntRegList;
    }
    
    public ArrayList<Integer> getUsedFloatRegList()
    {
        return this.usedFloatRegList;
    }
    
    public InstructionList getCode()
    {
        return this.code;
    }

    public int getNumLocals()
    {
        return this.lc.getNumLocals();
    }

    /*Splits the instruction list into basic blocks. Basic blocks are sequences of instructions without any jumps or labels, except at the beginning and end.
    The method identifies leaders (the first instruction of a basic block) and constructs basic blocks based on these leaders.
    Branch instructions and their following instructions are considered when determining leaders.
    The resulting list of basic blocks is stored in this.basicBlocks */
    public List<InstructionList> Split_BaseBlock() {
        int i, j, k;  // Initialize loop counters.
    
        InstructionList firstil = new InstructionList();  // List to store the first instructions of basic blocks.
        InstructionList temp = new InstructionList();  // Temporary list for assembling basic blocks.
        InstructionList workList = new InstructionList();  // Worklist to process instructions.
    
        i = 0;  // Initialize index for iterating through instruction list.
        while (i < this.il.size() - 1) {  // Loop through all instructions except the last.
            if (i == 0)  // Check if it's the first instruction.
                firstil.add(this.il.nodes.get(i));  // Add the first instruction to the firstil list.
    
            // Check for labels (indicating jump targets).
            if (this.il.nodes.get(i).toString().indexOf(':') != -1) {
                if (!firstil.contains(this.il.nodes.get(i)))  // check no duplicate.
                    firstil.add(this.il.nodes.get(i));  // Add labels to firstil list.
            } else if (this.il.nodes.get(i).getDest() != "pop") {  // Check for branch conditions.
                // Identify and add subsequent instructions after branches to firstil list.
                switch (this.il.nodes.get(i).getOC()) {
                    case BLE: case BLT: case BGE: case BGT: case BEQ: case BNE: case J:
                        firstil.add(this.il.nodes.get(i + 1));
                        break;
                }
            }
            i++;  // Increment the loop counter.
        }
    
        // Initialize variables for creating basic blocks.
        i = 0; k = 0; workList = firstil;
        while (workList.size() > 0) {  // Loop until the workList is empty.
            temp.add(workList.nodes.get(0));  // Add the first node of workList to temp.
            i = this.il.nodes.indexOf(workList.nodes.get(0));  // Find the index of the first node.
            workList.nodes.remove(0);  // Remove the processed node from workList.
            j = i + 1;
            // Add subsequent instructions to temp until reaching next leader in firstil.
            while (j < this.il.size() && !firstil.contains(this.il.nodes.get(j))) {
                temp.add(this.il.nodes.get(j));
                j++;
            }
            this.basicBlocks.add(temp);  // Add the formed basic block to basicBlocks list.
            temp = new InstructionList();  // Reset temp for the next basic block.
            i++; k++;  // Increment counters.
        }
        return this.basicBlocks;  // Return the list of basic blocks.
    }
    
    



    public Map<Integer,LinkedList<String>> liveness(InstructionList isl)
    {
        Instruction temp = null; // Temporary variable for current instruction.
        Map<Integer,LinkedList<String>> liveOut = new HashMap<Integer,LinkedList<String>>();// Map to store live variables for each instruction.
        LinkedList<String> liveSet = new LinkedList<String>(); // List to store live variables.
        int i,j;
        i = isl.size() - 1;
        temp = isl.nodes.get(i);// Get the last instruction.
        Collection<SymbolTableEntry> setVarName = this.lc.getEntries();
        for(SymbolTableEntry steLocal : setVarName){	
            liveSet.add("$l"+steLocal.addressToString());}
        setVarName = symbolTab.getGlobalScope().getEntries() ;
        for(SymbolTableEntry steLocal : setVarName) {	
            if(!(steLocal instanceof Scope.FunctionSymbolTableEntry))
                liveSet.add("$g"+steLocal.getName());
        }
        liveOut.put(i,liveSet); // Map the last instruction index to the live set.

        i--;

        // Iterate over the instructions in reverse order.
        while(i >= 0 )
        {
            liveSet = new LinkedList<String>();
            System.out.println(liveOut.get(i+1)+"\t");
            for(String s: liveOut.get(i+1))
                liveSet.add(s);
            // Update live set based on current instruction's operation.
            if(temp.getOC() != null)
            {
                switch(temp.getOC())
                {
                    //2 operand instructions - dest, src1 and src2
                    case ADD: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2()))liveSet.add(temp.getsrc2()); break;
                    case SUB: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case MUL: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case DIV: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case FADDS: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case FSUBS: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case FMULS: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case FDIVS: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case FLT: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case FLE: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case FEQ: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2()); break;
                    case NEG: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case FNEGS: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case MV: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case FMVS: liveSet.remove(temp.getDest());if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case BLE: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2());break;
                    case BLT: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2());break;
                    case BGE: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2());break;
                    case BGT: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2());break;
                    case BEQ: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2());break;
                    case BNE: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1());if(temp.getsrc2().charAt(0) == '$' && !liveSet.contains(temp.getsrc2())) liveSet.add(temp.getsrc2());break;
                    case PUSHINT: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case POPINT: if(temp.getsrc1().charAt(0) == '$') liveSet.remove(temp.getsrc1()); break;
                    case PUSHFLOAT: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case POPFLOAT: if(temp.getsrc1().charAt(0) == '$') liveSet.remove(temp.getsrc1()); break;
                    case PUTS: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case PUTI: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case PUTF: if(temp.getsrc1().charAt(0) == '$' && !liveSet.contains(temp.getsrc1()))liveSet.add(temp.getsrc1()); break;
                    case GETF: liveSet.remove(temp.getDest()); break;
                    case GETI: liveSet.remove(temp.getDest()); break;	
                }
            }
            temp = isl.nodes.get(i);  // Update the current instruction.
            liveOut.put(i, liveSet);  // Map the current index to its live set.
            liveSet = new LinkedList<String>();  // Reset liveSet for the next iteration.
            i--;  // Move to the previous instruction.
        }
        return liveOut;  // Return the map of live sets for each instruction.
    }
    
    public String check(String src, int intOrFloat, LinkedList<String> liveOut) {
        int allocatedIndex;  // Index for the allocated register.
    
        // Check if src is an integer (0) or float (1).
        if (intOrFloat == 0) {  // Integer case.
            if (this.intRegList.contains(src)) {  // If src is already in the integer register list.
                // Output the index of src in the integer register list.
                return "x" + String.valueOf(this.intRegList.indexOf(src));  
            } else {  // If src is not in the integer register list.
                allocatedIndex = allocate(src, intOrFloat, liveOut);  // Allocate a register for src.
                // Handle global, local, temporary, and immediate integer values.
                if (src.charAt(0) == '$' && src.charAt(1) == 'g') {  // Global variable case.
                    // Load global variable address and value into a register.
                    SymbolTableEntry ste = this.lc.getSymbolTableEntry(src.substring(2));
                    this.code.add(new La("x3", ste.addressToString()));
                    this.code.add(new Lw("x" + String.valueOf(allocatedIndex), "x3", "0"));
                } else if (src.charAt(0) == '$' && src.charAt(1) == 'l') {  // Local variable case.
                    this.code.add(new Lw("x" + String.valueOf(allocatedIndex), "fp", src.substring(2)));
                } else if (src.charAt(0) == '$' && src.charAt(1) == 't') {  // Temporary variable case.
                    SymbolTableEntry ste = this.lc.getSymbolTableEntry(src);
                    this.code.add(new Lw("x" + String.valueOf(allocatedIndex), "fp", ste.addressToString()));
                } else {  // Immediate value case.
                    this.code.add(new Li("x" + String.valueOf(allocatedIndex), src));
                }
                this.intRegList.set(allocatedIndex, src);  // Update the integer register list.
                // Return the register where src is allocated.
                return "x" + String.valueOf(allocatedIndex);
            }
        } else {  // Float case.
            if (this.floatRegList.contains(src)) {  // If src is already in the float register list.
                return "f" + String.valueOf(this.floatRegList.indexOf(src));  // Return the register index.
            } else {  // If src is not in the float register list.
                allocatedIndex = allocate(src, intOrFloat, liveOut);  // Allocate a register for src.
                // Handle global, local, temporary, and immediate float values.
                if (src.charAt(0) == '$' && src.charAt(1) == 'g') {  // Global variable case.
                    SymbolTableEntry ste = this.lc.getSymbolTableEntry(src.substring(2));
                    this.code.add(new La("x3", ste.addressToString()));
                    this.code.add(new Flw("f" + String.valueOf(allocatedIndex), "x3", "0"));
                } else if (src.charAt(0) == '$' && src.charAt(1) == 'l') {  // Local variable case.
                    this.code.add(new Flw("f" + String.valueOf(allocatedIndex), "fp", src.substring(2)));
                } else if (src.charAt(0) == '$' && src.charAt(1) == 't') {  // Temporary variable case.
                    SymbolTableEntry ste = this.lc.getSymbolTableEntry(src);
                    this.code.add(new Flw("f" + String.valueOf(allocatedIndex), "fp", ste.addressToString()));
                } else {  // Immediate value case.
                    this.code.add(new FImm("f" + String.valueOf(allocatedIndex), src));
                }
                this.floatRegList.set(allocatedIndex, src);  // Update the float register list.
                return "f" + String.valueOf(allocatedIndex);  // Return the register where src is allocated.
            }
        }
    }

    public int allocate(String src, int intOrFloat, LinkedList<String> liveOut) {
        int ret, j;  // Variables for return value and loop index.
    
        // Debugging output.
        System.out.println("Allocate: this.src1Reg " + this.src1Reg);
    
        // Check if src is an integer (0) or float (1).
        if (intOrFloat == 0) {  // Integer case.
            ret = this.intRegList.indexOf("empty");  // Find an empty spot in the integer register list.
            if (ret != -1) {  // If an empty spot is found.
                if (!this.usedIntRegList.contains(ret)) {
                    this.usedIntRegList.add(ret);  // Add to used integer register list if not already there.
                }
                this.intRegList.set(ret, src);  // Set the source in the found empty spot.
                return ret;  // Return the index of the allocated register.
            }
            // Loop to find a non-dirty register.
            for (j = 0; j < this.numReg; j++) {
                // Conditions for a non-dirty register.
                if (this.dirtyIntRegList.get(j) == 0 && intRegList.get(j) != "no" && intRegList.get(j) != "address" && !intRegList.get(j).equals(this.src1Reg)) {
                    ret = j;
                    free(ret, intOrFloat, liveOut);  // Free the found register.
                    this.intRegList.set(ret, src);  // Set the source in the register.
                    if (!this.usedIntRegList.contains(ret)) {
                        this.usedIntRegList.add(ret);  // Add to used integer register list if not already there.
                    }
                    return ret;  // Return the index of the allocated register.
                }
            }
            // Loop to free and use a random register.
            for (j = 0; j < this.numReg; j++) {
                // Conditions for a register to be freed.
                if (intRegList.get(j) != "no" && intRegList.get(j) != "address" && !intRegList.get(j).equals(this.src1Reg)) {
                    ret = j;
                    free(ret, intOrFloat, liveOut);  // Free the chosen register.
                    this.intRegList.set(ret, src);  // Set the source in the register.
                    if (!this.usedIntRegList.contains(ret)) {
                        this.usedIntRegList.add(ret);  // Add to used integer register list if not already there.
                    }
                    return ret;  // Return the index of the allocated register.
                }
            }
            return ret;  // Return the allocated register index (or -1 if none found).
        } else {  // Float case.
            // Similar logic as above, but for float registers.
            ret = floatRegList.indexOf("empty");
            if (ret != -1) {
                if (!this.usedFloatRegList.contains(ret)) {
                    this.usedFloatRegList.add(ret);
                }
                this.floatRegList.set(ret, src);
                return ret;
            }
            for (j = 0; j < this.numReg; j++) {
                if (this.dirtyFloatRegList.get(j) == 0 && !floatRegList.get(j).equals(this.src1Reg)) {
                    ret = j;
                    free(ret, intOrFloat, liveOut);
                    this.floatRegList.set(ret, src);
                    if (!this.usedFloatRegList.contains(ret)) {
                        this.usedFloatRegList.add(ret);
                    }
                    return ret;
                }
            }
            for (j = 0; j < this.numReg; j++) {
                if (!floatRegList.get(j).equals(this.src1Reg)) {
                    ret = j;
                    free(ret, intOrFloat, liveOut);
                    this.floatRegList.set(ret, src);
                    if (!this.usedFloatRegList.contains(ret)) {
                        this.usedFloatRegList.add(ret);
                    }
                    return ret;
                }
            }
            return ret;  // Return the allocated register index (or -1 if none found).
        }
    }
    
    public void free(int tempregi, int intOrFloat, LinkedList<String> liveSet)
    {
        
        String src;
        
        if(intOrFloat == 0)
        {
            System.out.println("freeing: "+String.valueOf(tempregi));
            System.out.println("Freed: "+this.intRegList.get(tempregi)+" in x"+String.valueOf(tempregi));
            src = this.intRegList.get(tempregi);
            if(liveSet.contains(src) && dirtyIntRegList.get(tempregi) == 1)
            {
                if(src.charAt(0) == '$' && src.charAt(1) == 'g')
                {
                    SymbolTableEntry ste = this.lc.getSymbolTableEntry(src.substring(2));
                    this.code.add(new La("x3", ste.addressToString()));
                    this.code.add(new Sw("x"+String.valueOf(tempregi),"x3", "0"));
                    System.out.println("Free: stored "+src);
                    
                }
                else if(src.charAt(0) == '$' && src.charAt(1) == 'l')
                {
                    this.code.add(new Sw("x"+String.valueOf(tempregi), "fp", src.substring(2)));
                }
                else if(src.charAt(0) == '$' && src.charAt(1) == 't')
                {
                    this.lc.addSymbol(Scope.Type.INT, src);

                    SymbolTableEntry ste = this.lc.getSymbolTableEntry(src);

                    this.code.add(new Sw("x"+String.valueOf(tempregi), "fp", ste.addressToString()));
                }
                
            }
            this.intRegList.set(tempregi,"empty");
            
        }

        else
        {
            System.out.println("Freed: "+this.floatRegList.get(tempregi)+" in f"+String.valueOf(tempregi));
            src = this.floatRegList.get(tempregi);
            if(liveSet.contains(src) && dirtyFloatRegList.get(tempregi) == 1)
            {
                if(src.charAt(0) == '$' && src.charAt(1) == 'g')
                {
                    SymbolTableEntry ste = this.lc.getSymbolTableEntry(src.substring(2));
                    this.code.add(new La("x3", ste.addressToString()));
                    this.code.add(new Fsw("f"+String.valueOf(tempregi),"f3", "0"));
                    
                }
                else if(src.charAt(0) == '$' && src.charAt(1) == 'l')
                {
                    this.code.add(new Fsw("f"+String.valueOf(tempregi), "fp", src.substring(2)));
                }
                else if(src.charAt(0) == '$' && src.charAt(1) == 't')
                {
                    this.lc.addSymbol(Scope.Type.FLOAT, src);

                    SymbolTableEntry ste = this.lc.getSymbolTableEntry(src);

                    this.code.add(new Fsw("f"+String.valueOf(tempregi), "fp", ste.addressToString()));
                }
                
            }
            this.floatRegList.set(tempregi,"empty");							
    
        }
    }

    public void regSave()
    {
        int i;

        System.out.println("Entering reg save");
        for(i = 0; i < this.numReg; i++)
        {
            System.out.println("x"+String.valueOf(i)+" ="+this.intRegList.get(i));
            if(this.intRegList.get(i).charAt(0)== '$' && this.intRegList.get(i).charAt(1) == 'l' && 				this.dirtyIntRegList.get(i) == 1)
                this.code.add(new Sw("x"+String.valueOf(i), "fp", this.intRegList.get(i).substring(2)));

            else if (this.intRegList.get(i).charAt(0)== '$' && this.intRegList.get(i).charAt(1) == 'g' && 					this.dirtyIntRegList.get(i) == 1)
            {
                System.out.println("Saving "+this.intRegList.get(i));
                SymbolTableEntry ste = this.lc.getSymbolTableEntry(this.intRegList.get(i).substring(2));
                this.code.add(new La("x3", ste.addressToString()));
                this.code.add(new Sw("x"+String.valueOf(i),"x3", "0"));
            }

            if(this.floatRegList.get(i).charAt(0)== '$' && this.floatRegList.get(i).charAt(1) == 'l' && 					this.dirtyFloatRegList.get(i) == 1)
                this.code.add(new Fsw("f"+String.valueOf(i), "fp", this.floatRegList.get(i).substring(2)));

            else if (this.floatRegList.get(i).charAt(0)== '$' && this.floatRegList.get(i).charAt(1) == 'g' && 					this.dirtyFloatRegList.get(i)==1)
            {
                SymbolTableEntry ste = this.lc.getSymbolTableEntry(this.floatRegList.get(i).substring(2));
                this.code.add(new La("x3", ste.addressToString()));
                this.code.add(new Fsw("f"+String.valueOf(i),"x3", "0"));
            }
        }
    }

    public void regInit()
    {
        this.intRegList = new ArrayList<>();
        this.floatRegList = new ArrayList<>();
        for(int j = 0; j < this.numReg; j++)
        {
            this.intRegList.add(j,"empty");
            this.floatRegList.add(j,"empty");
        }

        this.intRegList.set(0,"no");
        this.intRegList.set(1,"no");	
        this.intRegList.set(2,"no");
        this.intRegList.set(3,"address");
        this.intRegList.set(8,"no");
        
        this.dirtyIntRegList = new HashMap<Integer,Integer>();
        this.dirtyFloatRegList = new HashMap<Integer,Integer>();
        for(int j = 0; j < this.numReg; j++)
        {
            this.dirtyIntRegList.put(j,0);
            this.dirtyFloatRegList.put(j,0);
        }
        code = new InstructionList();

    }


    public void registerAllocation(InstructionList isl, Map<Integer,LinkedList<String>> liveOut)
    {
        String rx,ry;
        int rz;
        LinkedList<String> liveSet = new LinkedList<String>();

        //Counter for instructions in isl. Inst #
        int counter = 0;
        //Instruction temp
        Instruction i = null;

        //Initialize registers for each call to regAllocation
        regInit();

        //Iterate through instructions in isl
        while(counter < isl.size())
        {
            i = isl.nodes.get(counter);
            liveSet = liveOut.get(counter);

            System.out.println("\nInstruction :"+i.toString());
            if(i.getOC() == null)
                this.code.add(i);
            else
            {
                switch(i.getOC())
                {	
                    //Updated MUL with src1Reg float?
                    //2 operand instructions
                    //INT
                    case ADD: rx = check(i.getsrc1(),0,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),0,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Add(rx,ry,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break;
                    
                    //sp?
                    case ADDI:  rx =  i.getsrc1();
                        ry = i.getsrc2();
                    if(rx == "sp" || ry == "sp")
                        this.code.add(new Addi(rx,ry,i.getDest()));
                    break;

                    case SUB: rx = check(i.getsrc1(),0,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),0,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Sub(rx,ry,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break;
    
                    case MUL: rx = check(i.getsrc1(),0,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),0,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Mul(rx,ry,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break; 

                    case DIV:rx = check(i.getsrc1(),0,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),0,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Div(rx,ry,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break; 
                    

                    //FLOAT
                    case FADDS: rx = check(i.getsrc1(),1,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),1,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.floatRegList.indexOf(i.getsrc2()), 1, liveSet);
                    rz = allocate(i.getDest(), 1, liveSet);
                    this.code.add(new FAdd(rx,ry,"f"+String.valueOf(rz)));
                    this.dirtyFloatRegList.put(rz,1);break;

                    case FSUBS: rx = check(i.getsrc1(),1,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),1,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.floatRegList.indexOf(i.getsrc2()), 1, liveSet);
                    rz = allocate(i.getDest(), 1, liveSet);
                    this.code.add(new FSub(rx,ry,"f"+String.valueOf(rz)));
                    this.dirtyFloatRegList.put(rz,1);break;

                    case FMULS: rx = check(i.getsrc1(),1,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),1,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.floatRegList.indexOf(i.getsrc2()), 1, liveSet);
                    rz = allocate(i.getDest(), 1, liveSet);
                    this.code.add(new FMul(rx,ry,"f"+String.valueOf(rz)));
                    this.dirtyFloatRegList.put(rz,1);break;

                    case FDIVS: rx = check(i.getsrc1(),1,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),1,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.floatRegList.indexOf(i.getsrc2()), 1, liveSet);
                    rz = allocate(i.getDest(), 1, liveSet);
                    this.code.add(new FDiv(rx,ry,"f"+String.valueOf(rz)));
                    this.dirtyFloatRegList.put(rz,1);break;

                    case FLT: rx = check(i.getsrc1(),1,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),1,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.floatRegList.indexOf(i.getsrc2()), 1, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Flt(rx,ry,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break;

                    case FLE:rx = check(i.getsrc1(),1,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),1,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.floatRegList.indexOf(i.getsrc2()), 1, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Fle(rx,ry,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break;

                    case FEQ: rx = check(i.getsrc1(),1,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),1,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.floatRegList.indexOf(i.getsrc2()), 1, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Feq(rx,ry,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break;


                    //1 operand
                    case NEG:  rx = check(i.getsrc1(),0,liveSet); 
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Neg(rx,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break;

                    case FNEGS: rx = check(i.getsrc1(),1,liveSet); 
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    rz = allocate(i.getDest(), 1, liveSet);
                    this.code.add(new FNeg(rx,"f"+String.valueOf(rz)));
                    this.dirtyFloatRegList.put(rz,1);break;

                    case MV: rx = check(i.getsrc1(),0,liveSet); 
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new Mv(rx,"x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break;

                    case FMVS: rx = check(i.getsrc1(),1,liveSet); 
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    rz = allocate(i.getDest(), 1, liveSet);
                    this.code.add(new FMv(rx,"f"+String.valueOf(rz)));
                    this.dirtyFloatRegList.put(rz,1);break;


                    //Get & Put
                    case GETI: rz = allocate(i.getDest(), 0, liveSet);
                    this.code.add(new GetI("x"+String.valueOf(rz)));
                    this.dirtyIntRegList.put(rz,1);break;

                    case GETF: rz = allocate(i.getDest(), 1, liveSet);
                    this.code.add(new GetF("f"+String.valueOf(rz)));
                    this.dirtyFloatRegList.put(rz,1);break;

                    case PUTI:rx = check(i.getsrc1(),0,liveSet); 
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    this.code.add(new PutI(rx));break;

                    case PUTF:rx = check(i.getsrc1(),1,liveSet); 
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    this.code.add(new PutF(rx));break;

                    case PUTS: System.out.println("came to puts");SymbolTableEntry ste = this.lc.getSymbolTableEntry(i.getsrc1().substring(2));this.code.add(new La("x3", ste.addressToString()));this.code.add(new PutS("x3"));break;


                    //Branches
                    case BLT: rx = check(i.getsrc1(),0,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),0,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    if(counter == isl.size()-1)
                        regSave();
                    this.code.add(new Blt(rx,ry,i.getLabel()));break;

                    case BLE: rx = check(i.getsrc1(),0,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),0,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    if(counter == isl.size()-1)
                        regSave();
                    this.code.add(new Ble(rx,ry,i.getLabel()));break;

                    case BGT: rx = check(i.getsrc1(),0,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),0,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    if(counter == isl.size()-1)
                        regSave();
                    this.code.add(new Bgt(rx,ry,i.getLabel()));break;

                    case BGE:  rx = check(i.getsrc1(),0,liveSet);
                    this.src1Reg = i.getsrc1();
                    ry = check(i.getsrc2(),0,liveSet);
                    this.src1Reg = "*";
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())))
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    if(counter == isl.size()-1)
                        regSave();
                    this.code.add(new Bge(rx,ry,i.getLabel()));break;

                    case BNE: 
                    if(i.getsrc1() == "x0")
                        rx = "x0";
                    else 
                    {
                        rx = check(i.getsrc1(),0,liveSet);
                        this.src1Reg = i.getsrc1();
                    }
                    if(i.getsrc2() == "x0")
                        ry = "x0";
                    else 
                    {
                        ry = check(i.getsrc2(),0,liveSet);
                        this.src1Reg = "*";
                    }
                    if(!(liveSet.contains(i.getsrc1())) && i.getsrc1() != "x0" )
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())) && i.getsrc2() != "x0")
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    if(counter == isl.size()-1)
                        regSave();
                    this.code.add(new Bne(rx,ry,i.getLabel()));break;


                    case BEQ:  
                    if(i.getsrc1() == "x0")
                        rx = "x0";
                    else 
                    {
                        rx = check(i.getsrc1(),0,liveSet);
                        this.src1Reg = i.getsrc1();
                    }
                    if(i.getsrc2() == "x0")
                        ry = "x0";
                    else 
                    {
                        ry = check(i.getsrc2(),0,liveSet);
                        this.src1Reg = "*";
                    }
                    if(!(liveSet.contains(i.getsrc1())) && i.getsrc1() != "x0" )
                        free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                    if(!(liveSet.contains(i.getsrc2())) && i.getsrc2() != "x0")
                        free(this.intRegList.indexOf(i.getsrc2()), 0, liveSet);
                    if(counter == isl.size()-1)
                        regSave();
                    this.code.add(new Beq(rx,ry,i.getLabel()));break;


                    //Jumps
                    case J: if(counter == isl.size()-1)
                        regSave();
                    this.code.add(new J(i.getLabel()));break;
                    
                    case JR: this.code.add(new Jr(i.getLabel()));break;
                
                    


                    //Push/pop
                    case PUSHINT: 
                    System.out.println("pushint: src1= "+i.getsrc1());
                    if(i.getsrc1() == "ra")
                        this.code.add(new Sw(i.getsrc1(), "sp", "0"));
                    else
                    {
                        
                        rx = check(i.getsrc1(),0,liveSet); 
                        if(!(liveSet.contains(i.getsrc1())))
                            free(this.intRegList.indexOf(i.getsrc1()), 0, liveSet);
                        this.code.add(new Sw(rx, "sp", "0"));
                    }
                    break;

                    case POPINT: 
                    if(i.getsrc1() == "ra")
                        this.code.add(new Lw(i.getsrc1(), "sp", "0"));
                    else
                    {
                        rz = allocate(i.getsrc1(), 0, liveSet);
                        this.code.add(new Lw("x"+String.valueOf(rz), "sp", "0"));
                        this.dirtyIntRegList.put(rz,1);
                    }
                    break;

                    case PUSHFLOAT: rx = check(i.getsrc1(),1,liveSet); 
                    if(!(liveSet.contains(i.getsrc1())))
                        free(this.floatRegList.indexOf(i.getsrc1()), 1, liveSet);
                    this.code.add(new Fsw(rx, "sp", "0"));break;

                    case POPFLOAT: rz = allocate(i.getsrc1(), 1, liveSet);
                    this.code.add(new Flw("f"+String.valueOf(rz), "sp", "0"));
                    this.dirtyIntRegList.put(rz,1);break;

                }
            }
            
            counter++;   // Move to the next instruction.
        }

        // Saving registers if needed after processing all instructions.
        if(i.getOC() != OpCode.BLT && i.getOC() != OpCode.BGT && i.getOC() != OpCode.BLE && i.getOC() != OpCode.BGE && i.getOC() != OpCode.BNE && i.getOC() != OpCode.BEQ && i.getOC() != OpCode.J)
        regSave();
    }
        
}
