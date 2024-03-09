package assembly.instructions;

public class Malloc extends Instruction {

 
    /**
     * Models the magic instruction MALLOC
     */
    public Malloc(String src, String dest) {
        super();
        this.src1 = src;
        this.dest = dest;
        this.oc = OpCode.MALLOC;
    }

    /**
     * @return "HALT"
     */
    public String toString() {
        return String.valueOf(this.oc) + " " + this.dest + ", " + this.src1;
    }
}
