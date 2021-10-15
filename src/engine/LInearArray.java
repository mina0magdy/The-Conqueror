package engine;

public class LInearArray {
public Object[] array;
int num;
public LInearArray(int c) {
	array=new Object[c];
}
public void insertLast(Object x) {
	if(num>=array.length)
		System.out.println("full");
	else {
		array[num]=x;
		num++;
	}
	
}
public void insertFirst(Object x) {
	if(num>=array.length)
		System.out.println("full");
	else {
		for(int i=num-1;i>=0;i--) {
			array[i+1]=array[i];
		}
		array[0]=x;
		num++;
	}
}
public int LinearSearch(Object x) {
	for(int i=0;i<num;i++) {
		if(x.equals(array[i]))
			return i;
	}
	return -1;
}
public void Delete(Object x) {
	int c=LinearSearch( x);
	for(int i=c;i<num-1;i++)
		array[i]=array[i+1];
	num--;
}
	public static void main(String[] args) {
	LInearArray x=new LInearArray(10);
	x.insertLast(14);
	x.insertLast(60);
	x.insertLast(6);
	x.insertLast(4);
	x.insertFirst(22);
	x.insertFirst(69);
	x.Delete(69);
	for(int i=0;i<x.num;i++)
		System.out.print(x.array[i]+" ");

	}
	

}
