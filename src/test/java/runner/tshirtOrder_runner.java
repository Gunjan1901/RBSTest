package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features= {"src/test/resources/features/TshirtOrder.feature"},glue= {"stepDefinition"})

public class tshirtOrder_runner {

}
