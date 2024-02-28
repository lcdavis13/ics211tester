/**
 * 
 */
/**
 * 
 */
module ics211tester {
    requires java.compiler;
    requires org.junit.jupiter.api;
    requires org.junit.platform.launcher;
    requires org.junit.platform.engine;
    requires org.junit.jupiter.params;
    requires java.desktop;
	requires org.junit.platform.suite.api;
    opens ics211tester.tests to org.junit.jupiter.api, org.junit.platform.commons;
}
