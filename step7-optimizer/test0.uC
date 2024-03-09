string true = "True\n";
string false = "False\n";
float a;
float b;

int main() {

	int c;
	int *d;
	d = malloc(1);

	a = 3.1;
	b = 2.1;

	c = 7;

	*d = a * 2;
		
	if (c >= a) {
		print(*d);
		print(true);
	} else {

		print(c);
		print(false);
	}
	
	return 0;
}

float foo(float x, float y) {

	int d;
	float f;
	float a;

	return (y + x);

}
