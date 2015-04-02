import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ ExcelReaderTest.class, RoomTest.class, StudentTest.class,
		TeacherTest.class })
public class AllTests {

}
