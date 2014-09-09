package br.nauber.jmeter.scriptReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class RunningJMeter {

	public static void main(String[] args) throws Exception {
		StandardJMeterEngine jmeter = new StandardJMeterEngine();

		// Initialize Properties, logging, locale, etc.
		JMeterUtils
				.loadJMeterProperties("/Users/naubergois/Downloads/apache-jmeter-2.92/bin/jmeter.properties");
		JMeterUtils
				.setJMeterHome("/Users/naubergois/Downloads/apache-jmeter-2.92/");
		JMeterUtils.initLogging();// you can comment this line out to see extra
									// log messages of i.e. DEBUG level
		JMeterUtils.initLocale();

		// Initialize JMeter SaveService
		SaveService.loadProperties();

		// Load existing .jmx Test Plan
		FileInputStream in = new FileInputStream(
				"/Users/naubergois/Downloads/apache-jmeter-2.11/bin/includeuser.jmx");
		HashTree testPlanTree = SaveService.loadTree(in);
		in.close();

		System.out.print(testPlanTree);
		// Run JMeter Test
		jmeter.configure(testPlanTree);
		jmeter.run();
	}

}
