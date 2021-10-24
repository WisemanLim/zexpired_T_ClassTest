/**
 * 
 */
package wisedatalabs.classtest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.inference.OneWayAnova;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test cases for the OneWayAnovaImpl class.
 *
 */

public class OneWayAnovaTest {

    protected OneWayAnova testStatistic = new OneWayAnova();

    private double[] emptyArray = {};

    /**
     *  가설 (1)귀무가설 : class별 평균은 다 같다. (2)대립가설 : class별 평균은 같지 않다.
     *  귀무가설이 적합 즉 기각된다면(가설이 틀렸다)면 class별 평균은 고르지 않다라는 뜻
     */
    private double[] classA =
            {93.0, 103.0, 95.0, 101.0, 91.0, 105.0, 96.0, 94.0, 101.0 };
    private double[] classB =
            {99.0, 92.0, 102.0, 100.0, 102.0, 89.0 };
    private double[] classC =
            {110.0, 115.0, 111.0, 117.0, 128.0, 117.0 };

    @Test
    public void testAnovaFValue() {
    	// F value : F 값이 클수록 집단간 평균 차이가 없다.
        // Target comparison values computed using R version 2.6.0 (Linux version)
        List<double[]> threeClasses = new ArrayList();
        threeClasses.add(classA);
        threeClasses.add(classB);
        threeClasses.add(classC);

        Assert.assertEquals("ANOVA F-value",  24.67361709460624,
                 testStatistic.anovaFValue(threeClasses), 1E-12);
        System.out.println(String.format("ANOVA F-value(A,B,C) %f", testStatistic.anovaFValue(threeClasses)));

        List<double[]> twoClasses = new ArrayList();
        twoClasses.add(classA);
        twoClasses.add(classB);

        Assert.assertEquals("ANOVA F-value",  0.0150579150579,
                 testStatistic.anovaFValue(twoClasses), 1E-12);
        System.out.println(String.format("ANOVA F-value(A,B) %f", testStatistic.anovaFValue(twoClasses)));

        List<double[]> emptyContents = new ArrayList();
        emptyContents.add(emptyArray);
        emptyContents.add(classC);
        try {
            testStatistic.anovaFValue(emptyContents);
            Assert.fail("empty array for key classX, MathIllegalArgumentException expected");
        } catch (MathIllegalArgumentException ex) {
            // expected
        }

        List<double[]> tooFew = new ArrayList();
        tooFew.add(classA);
        try {
            testStatistic.anovaFValue(tooFew);
            Assert.fail("less than two classes, MathIllegalArgumentException expected");
        } catch (MathIllegalArgumentException ex) {
            // expected
        }
    }


    @Test
    public void testAnovaPValue() {
    	// P value : 95%인 5%를 유의수준으로 사용했을 경우, P Value<0.05라면 귀무가설 기각, 통계 차이 있음. 즉, P Value가 작으면 작을 수록 차이가 존재한다라는 의미
        // Target comparison values computed using R version 2.6.0 (Linux version)
        List<double[]> threeClasses = new ArrayList();
        threeClasses.add(classA);
        threeClasses.add(classB);
        threeClasses.add(classC);

        Assert.assertEquals("ANOVA P-value", 6.959446E-06,
                 testStatistic.anovaPValue(threeClasses), 1E-12);
        System.out.println(String.format("ANOVA P-value(A,B,C) %f", testStatistic.anovaPValue(threeClasses)));

        List<double[]> twoClasses = new ArrayList();
        twoClasses.add(classA);
        twoClasses.add(classB);

        Assert.assertEquals("ANOVA P-value",  0.904212960464,
                 testStatistic.anovaPValue(twoClasses), 1E-12);
        System.out.println(String.format("ANOVA P-value(A,B) %f", testStatistic.anovaPValue(twoClasses)));

    }

    @Test
    public void testAnovaPValueSummaryStatistics() {
        // Target comparison values computed using R version 2.6.0 (Linux version)
        List<SummaryStatistics> threeClasses = new ArrayList();
        SummaryStatistics statsA = new SummaryStatistics();
        for (double a : classA) {
            statsA.addValue(a);
        }
        threeClasses.add(statsA);
        SummaryStatistics statsB = new SummaryStatistics();
        for (double b : classB) {
            statsB.addValue(b);
        }
        threeClasses.add(statsB);
        SummaryStatistics statsC = new SummaryStatistics();
        for (double c : classC) {
            statsC.addValue(c);
        }
        threeClasses.add(statsC);

        Assert.assertEquals("ANOVA P-value", 6.959446E-06,
                 testStatistic.anovaPValue(threeClasses, true), 1E-12);
        System.out.println(String.format("ANOVA P-value(A,B,C) %f", testStatistic.anovaPValue(threeClasses, true)));

        List<SummaryStatistics> twoClasses = new ArrayList();
        twoClasses.add(statsA);
        twoClasses.add(statsB);

        Assert.assertEquals("ANOVA P-value",  0.904212960464,
                 testStatistic.anovaPValue(twoClasses, false), 1E-12);
        System.out.println(String.format("ANOVA P-value(A,B) %f", testStatistic.anovaPValue(twoClasses, true)));

    }

    @Test
    public void testAnovaTest() {
        // Target comparison values computed using R version 2.3.1 (Linux version)
        List<double[]> threeClasses = new ArrayList();
        threeClasses.add(classA);
        threeClasses.add(classB);
        threeClasses.add(classC);

        Assert.assertTrue("ANOVA Test P<0.01", testStatistic.anovaTest(threeClasses, 0.01));
        System.out.println(String.format("ANOVA Test(A,B,C) P<0.01 %b", testStatistic.anovaTest(threeClasses, 0.01)));

        List<double[]> twoClasses = new ArrayList();
        twoClasses.add(classA);
        twoClasses.add(classB);

        Assert.assertFalse("ANOVA Test P>0.01", testStatistic.anovaTest(twoClasses, 0.01));
        System.out.println(String.format("ANOVA Test(A,B) P<0.01 %b", testStatistic.anovaTest(twoClasses, 0.01)));
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OneWayAnovaTest owat = new OneWayAnovaTest();
	    System.out.println(String.format("Class A %s", Arrays.toString(owat.classA)));
	    System.out.println(String.format("Class B %s", Arrays.toString(owat.classB)));
	    System.out.println(String.format("Class C %s", Arrays.toString(owat.classC)));

	    owat.testAnovaFValue();
		owat.testAnovaPValue();
		owat.testAnovaPValueSummaryStatistics();
		owat.testAnovaTest();
	}

}
