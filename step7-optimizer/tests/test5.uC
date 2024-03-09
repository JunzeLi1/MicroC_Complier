int main()
{
int a;
float b;
int *p;
float *q;
a =2;
b = 3.5;

p=&a;
q=&b;
if(*p<*q)
{
	print(*q);
}
return 0;
}
