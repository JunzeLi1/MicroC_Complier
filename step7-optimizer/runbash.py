import os


for i in range(0,6):
	test_str = "tests/test"+str(i)+".uC"
	op_str = "MyOp/o"+str(i)
	cmd = "./runme "+test_str+" "+op_str
	os.system(cmd)


for i in range(0,6):
	op_str = "MyOp/o"+str(i)
	expected_str = "outputs/test"+str(i)+".asm"
	print("Test funcs "+str(i))
	cmd = "python3 RiscSim/driver.py "+op_str
	os.system(cmd)


