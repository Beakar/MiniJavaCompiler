// simple dynamic dispatch test
// this should print "200 100"
class Main extends Lib {
	public void main() {
		Vehicle v1 = new Car();
		Vehicle v2 = new Vehicle();
		super.printInt(v1.price());
		super.printStr(" ");
		super.printInt(v2.price());
		super.printStr("\n");
	}
}

class Vehicle {
	public int price() {
		return 100;
	}
}

class Car extends Vehicle {
	public int price() {
		return 200;
	}
}
