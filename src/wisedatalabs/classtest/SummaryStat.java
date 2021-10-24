package wisedatalabs.classtest;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well1024a;
import org.junit.Assert;
import org.junit.Test;

public class SummaryStat {

	@Test
	public void testDoubleDirect() {
	    SummaryStatistics sample = new SummaryStatistics();
	    final int N = 10000;
	    final RandomGenerator random = new Well1024a(0xd015982e9f31ee04l);
	    for (int i = 0; i < N; ++i) {
	        sample.addValue(random.nextDouble());
	    }
	    System.out.println(String.format("Sample  %s", sample.toString()));

	    Assert.assertEquals("Note: This test will fail randomly about 1 in 100 times.",
	            0.5, sample.getMean(), FastMath.sqrt(N/12.0) * 2.576);
	    Assert.assertEquals(1.0 / (2.0 * FastMath.sqrt(3.0)), sample.getStandardDeviation(), 0.01);
	    
	    System.out.println(String.format("Mean : %f, Variance : %f, Standatd Deviation : %f"
	    		, sample.getMean(), sample.getVariance(), sample.getStandardDeviation()));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SummaryStat ss = new SummaryStat();
		ss.testDoubleDirect();
	}

}
