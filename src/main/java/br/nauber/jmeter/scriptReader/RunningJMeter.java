package br.nauber.jmeter.scriptReader;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
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
		Properties properties=new Properties();
		properties.setProperty("ip", "www.vbulletin.com");
		jmeter.setProperties(properties);
		
		
		Summariser summer = null;
		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");//$NON-NLS-1$
		if (summariserName.length() > 0) {
		    summer = new Summariser(summariserName);
		}

		String logFile = "/Users/naubergois/Downloads/apache-jmeter-2.11/bin/filelognew.jtl";
		ResultCollector logger = new ResultCollector(summer);
		logger.setFilename(logFile);
		testPlanTree.add(testPlanTree.getArray()[0], logger);
		
		TestPlan plan= (TestPlan) testPlanTree.getArray()[0];
		Arguments args1=new Arguments();
		args1.addArgument("ip", "www.vbulletin.com");
		//System.out.print("ip "+plan.setUserDefinedVariables(vars));
		//plan.setProperty("ip", "www.vbulletin.com");
		plan.setUserDefinedVariables(args1);
		testPlanTree.getArray()[0]=plan;
		jmeter.configure(testPlanTree);
		
		jmeter.run();
	}

}
