/**
 * 
 */
/**
 * 
 */
module ics211tester {
	requires java.compiler;
	requires junit;
	requires org.junit.jupiter.api;
	requires org.junit.platform.launcher;
	requires org.junit.platform.engine;
	requires org.junit.platform.suite.api;
	requires org.junit.jupiter.params;
	requires java.desktop;
	// Exports the test package to JUnit platform modules
    exports ics211tester.tests to org.junit.jupiter.api, org.junit.platform.commons;
}
