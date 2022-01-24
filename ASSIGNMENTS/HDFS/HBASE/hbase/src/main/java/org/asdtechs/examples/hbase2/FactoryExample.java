package org.asdtechs.examples.hbase2;

public class FactoryExample {

	class Car {
	
		@Override
		public java.lang.String toString() {
			
			return "I am " + carName + " from " + carManufacturer;
		}
		
		public void setCarName(String carName) {
			this.carName = carName;
		}

		public void setCarManufacturer(String carManufacturer) {
			this.carManufacturer = carManufacturer;
		}

		String carName ;
		String carManufacturer;
		
		private Car() {
			carName = "UNSET";
			carManufacturer = "UNSET";
		}
		
		private Car(String carName, String carManufacturer) {
			this.carName = carName;
			this.carManufacturer = carManufacturer;
		}
		
		

	}

	public Car build(String carName, String carManufacturer) {
		
		return new Car(carName, carManufacturer);

	}
	
	public static void main(String[] args) {
		
		FactoryExample ex = new FactoryExample();
//		Car car1 = ex.new Car();
//		System.out.println(car1);

//		Car car2 = ex.new Car("Maruti", "800");
//		System.out.println(car2);
		
//		Car car3 = ex.new Car();
//		car3.setCarManufacturer("Mercedes");
//		car3.setCarName("SClass");
//		System.out.println(car3);
		
		Car car4 = ex.build("800", "Maruti");
		// Car car5 = CarBuilder.setManufacturer("Maruti").setCar("800").build();
		System.out.println(car4);
		
	}
	
	
}
