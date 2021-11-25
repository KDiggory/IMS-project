Coverage: ~65%
# Project Title
Inventory Management System software

The brief of this project was to make an inventory management system with CRUD functionality that saves data to a persistant database, in this case MySQL. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

In the documentation folder there is a folder with the FatJar in, this can be downloaded and run on the command line using java -jar ims-0.0.1-jar-with-dependencies.jar.
This will take you into the IMS. 

### Prerequisites

What things you need to install the software and how to install them
java - can be downloaded from https://www.java.com/download/ie_manual.jsp. 

Command line - accessed by typing cmd in the search bar on windows. 

MySQL workbench - from https://dev.mysql.com/downloads/workbench/.

An IDE - Eclipse or intelliJ - this was done in Eclipse. It can be downloaded here. https://www.eclipse.org/downloads/

### Installing

This program can be run from the commandline and doesn't need anything installing other than java, but if you want to access the code you will need to use an IDE. 
You should make the tables in MySQL before using the program for ease using these commands:

DROP TABLE IF EXISTS `order_items`;
 DROP TABLE IF EXISTS `orders`; 
 DROP TABLE IF EXISTS `items`;
DROP TABLE IF EXISTS `customers`;

CREATE TABLE IF NOT EXISTS `customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `items` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(40) NOT NULL,
    `size` VARCHAR(40)  NOT NULL,
    `cost` INT  NOT NULL,
    PRIMARY KEY (`id`)
    );
    
    CREATE TABLE IF NOT EXISTS `orders` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `customerId` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (customerId) REFERENCES customers(id) 
  ON DELETE CASCADE
  ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS `order_items` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `orderId` INT NOT NULL,
    `itemId` INT NOT NULL,
    `itemName` VARCHAR(40) NOT NULL,
    `numItems` INT NOT NULL,
    `cost` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (orderId) REFERENCES orders(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);


## Running the tests

The tests for this code can be found in the src/test/java file. Several that don't work have been commented out so that the jar and fatJar compile properly but these can be un commented if you want to look at why they aren't working. I would say its because of insufficient developer knowledge on integration testing!

### Unit Tests 

Unit tests test the methods in a class, they test them in isolation and are useful to check that each method does what you think it does. They are done using JUNIT

This test tests that the setName function has actually changed the name when asked to
@Test
	public void setNameTest() {
		item.setName("Fancy wine");
		String expected = item.getName();
		String test = "Fancy wine";
		assertEquals(expected, test);
	}


### Integration Tests 
Integration tests use Mockito and JUNIT to mock certain aspects of the program so testing can be done on combined modules rather than individually. It is usually done after unit testing but before systems testing. 

This test tests if an item has been created.

	@Test
	public void testCreate() { 
		final String name = "pie";
		final String size = "large";
		final Long cost = 5L;
		final Item created = new Item(name, size, cost);

		Mockito.when(utils.getString()).thenReturn(name, size);
		Mockito.when(utils.getLong()).thenReturn(cost);
		Mockito.when(DAO.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(2)).getString();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(DAO, Mockito.times(1)).create(created);
	}


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* Katie Diggory - the rest of the work

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

Jordan Benbelaid and Anoush Lowton for all their help. 
