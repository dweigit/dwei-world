package com.bestsoft.test.poi;

import com.bestsoft.poi.JacobWordUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class JacobWordUtilTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public JacobWordUtilTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(JacobWordUtilTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		JacobWordUtil.insertImage("F:/test.docx", "F:/1.png", "12");
		assertTrue(true);
	}
}
