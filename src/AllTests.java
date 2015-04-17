import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ ExcelReaderTest.class, HelperMethodsTest.class,
		HungarianResultTest.class, HungarianTestFaith.class,
		HungarianTestRyan.class, PairTest.class, PairTimeTest.class,
		RoomDayTimeTest.class, RoomTest.class, ScoringEngineTest.class,
		StudentTest.class, TeacherTest.class })
public class AllTests {

}
