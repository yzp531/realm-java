package com.tightdb.lib;

import static com.tightdb.test.ExtraTests.assertArrayEquals;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

import java.nio.ByteBuffer;
import java.util.Date;

import org.testng.annotations.Test;

import com.tightdb.Mixed;
import com.tightdb.example.Employee;
import com.tightdb.example.EmployeeView;
import com.tightdb.example.PhoneTable;
import com.tightdb.test.EmployeesFixture;
import com.tightdb.test.PhoneData;

public class TableColumnsTest extends AbstractTest {

	@Test
	public void shouldFindFirstRecordByColumnValue() throws IllegalAccessException {
		Employee record = null;

		record = employees.firstName.findFirst(EmployeesFixture.EMPLOYEES[1].firstName);
		assertEquals(1, record.getPosition());

		record = employees.salary.findFirst(EmployeesFixture.EMPLOYEES[0].salary);
		assertEquals(0, record.getPosition());

		record = employees.salary.findFirst(12345);
		assertNull(record);

		record = employees.driver.findFirst(EmployeesFixture.EMPLOYEES[0].driver);
		assertEquals(0, record.getPosition());

		record = employees.driver.findFirst(EmployeesFixture.EMPLOYEES[1].driver);
		assertEquals(1, record.getPosition());

		record = employees.birthdate.findFirst(EmployeesFixture.EMPLOYEES[1].birthdate);
		assertEquals(1, record.getPosition());

		record = employees.birthdate.findFirst(EmployeesFixture.EMPLOYEES[2].birthdate);
		assertEquals(2, record.getPosition());

		record = employees.birthdate.findFirst(new Date(12345));
		assertNull(record);

	}

	@Test
	public void shouldFindAllRecordsByColumnValue() throws IllegalAccessException {
		EmployeeView view = null;
		view = employees.firstName.findAll(EmployeesFixture.EMPLOYEES[1].firstName);
		assertEquals(1, view.size());

		view = employees.salary.findAll(EmployeesFixture.EMPLOYEES[0].salary);
		assertEquals(2, view.size());

		view = employees.salary.findAll(12345);
		assertEquals(0, view.size());

		view = employees.driver.findAll(false);
		assertEquals(1, view.size());

		view = employees.driver.findAll(true);
		assertEquals(2, view.size());

		view = employees.birthdate.findAll(EmployeesFixture.EMPLOYEES[2].birthdate);
		assertEquals(1, view.size());

		view = employees.birthdate.findAll(EmployeesFixture.EMPLOYEES[1].birthdate);
		assertEquals(1, view.size());

		view = employees.birthdate.findAll(new Date(0));
		assertEquals(0, view.size());
	}

	@Test
	public void shouldAggregateColumnValue() {
		assertEquals(EmployeesFixture.EMPLOYEES[0].salary, employees.salary.minimum());
		assertEquals(EmployeesFixture.EMPLOYEES[1].salary, employees.salary.maximum());
		long sum = EmployeesFixture.EMPLOYEES[0].salary + EmployeesFixture.EMPLOYEES[1].salary + EmployeesFixture.EMPLOYEES[2].salary;
		assertEquals(sum, employees.salary.sum());
	}

	@Test
	public void shouldAddValueToWholeColumn() {
		employees.salary.addLong(123);
		for (int i = 0; i < EmployeesFixture.EMPLOYEES.length; ++i)
			assertEquals(EmployeesFixture.EMPLOYEES[i].salary + 123, employees.at(i).getSalary());
	}

	@Test
	public void shouldGetAllColumnValues() {
		assertArrayEquals(EmployeesFixture.getAll(0), employees.firstName.getAll());
		assertArrayEquals(EmployeesFixture.getAll(1), employees.lastName.getAll());
		assertArrayEquals(EmployeesFixture.getAll(2), employees.salary.getAll());
		assertArrayEquals(EmployeesFixture.getAll(3), employees.driver.getAll());
		assertArrayEquals(EmployeesFixture.getAll(4), employees.photo.getAll());
		assertArrayEquals(EmployeesFixture.getAll(5), employees.birthdate.getAll());
		assertArrayEquals(EmployeesFixture.getAll(6), employees.extra.getAll());

		PhoneTable[] phoneTables = employees.phones.getAll();
		assertEquals(EmployeesFixture.PHONES.length, phoneTables.length);

		for (int i = 0; i < phoneTables.length; i++) {
			PhoneData[] phones = EmployeesFixture.PHONES[i];
			assertEquals(phones.length, phoneTables[i].size());
			for (int j = 0; j < phones.length; j++) {
				assertEquals(phones[j].type, phoneTables[i].at(j).type.get());
				assertEquals(phones[j].number, phoneTables[i].at(j).number.get());
			}
		}
	}

	@Test
	public void shouldSetAllColumnValues() {
		employees.firstName.setAll("A");
		assertSameArrayElement("A", employees.firstName.getAll());

		employees.lastName.setAll("B");
		assertSameArrayElement("B", employees.lastName.getAll());

		Long num = 12345L;
		employees.salary.setAll(num);
		assertSameArrayElement(num, employees.salary.getAll());

		employees.driver.setAll(true);
		assertSameArrayElement(true, employees.driver.getAll());

		ByteBuffer buf = ByteBuffer.allocateDirect(2);
		buf.put(new byte[] { 10, 20 });
		employees.photo.setAll(buf);
		for (ByteBuffer buffer : employees.photo.getAll()) {
			ByteBuffer buf2 = ByteBuffer.wrap(new byte[] { 10, 20 });
			assertEquals(buf2, buffer);
		}

		Date date = new Date(13579);
		employees.birthdate.setAll(date);
		assertSameArrayElement(date, employees.birthdate.getAll());

		Mixed extra = Mixed.mixedValue("extra");
		employees.extra.setAll(extra);
		assertSameArrayElement(extra, employees.extra.getAll());
	}

	private void assertSameArrayElement(Object expected, Object[] arr) {
		for (Object element : arr) {
			assertEquals(expected, element);
		}
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void shouldntGetDirectColumnValue() {
		employees.firstName.get();
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void shouldntSetDirectColumnValue() {
		employees.firstName.set("x");
	}
	
	@Test
	public void shouldGetColumnInformation() {
		assertEquals(8, employees.getColumnCount());
		for (int i=0; i < employees.getColumnCount(); ++i) {
			assertEquals(EXPECTED_COLUMNS[i], employees.getColumnName(i));
			assertEquals(EXPECTED_COLUMN_TYPE[i], employees.getColumnType(i));
		}
	}

}
