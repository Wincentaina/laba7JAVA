import java.util.ArrayList;
import java.util.List;

// Интерфейс для тестовых раннеров
interface ITestRunner {
    boolean executeTest(String input, String expected);
}

// Простой тестовый раннер
class SimpleTestRunner implements ITestRunner {
    @Override
    public boolean executeTest(String input, String expected) {
        return input.equals(expected);
    }
}

// Расширенный тестовый раннер
class AdvancedTestRunner implements ITestRunner {
    private int complexityLevel;

    public AdvancedTestRunner(int complexityLevel) {
        this.complexityLevel = complexityLevel;
    }

    @Override
    public boolean executeTest(String input, String expected) {
        System.out.println("Executing with complexity level: " + complexityLevel);
        return input.equals(expected) && complexityLevel > 2;
    }
}

// Класс для представления теста
class TestCase {
    private final String input;
    private final String expected;
    private final ITestRunner testRunner;  // Поле для тестового раннера

    public TestCase(String input, String expected, ITestRunner testRunner) {
        this.input = input;
        this.expected = expected;
        this.testRunner = testRunner;
    }

    public String getInput() {
        return this.input;
    }

    public String getExpected() {
        return this.expected;
    }

    public ITestRunner getTestRunner() {
        return this.testRunner;
    }

    // Метод для выполнения теста с использованием конкретного раннера
    public ExecutionResult executeTest() {
        ExecutionResult result = new ExecutionResult();
        boolean isPassed = testRunner.executeTest(input, expected);
        result.setActualOutput(input);
        result.setIsPassed(isPassed);
        return result;
    }
}

// Класс для представления набора тестов
class TestSuite {
    private static int totalTestSuitesCreated = 0;
    private final List<TestCase> tests;

    public TestSuite(List<TestCase> tests) {
        this.tests = new ArrayList<>(tests);
        totalTestSuitesCreated++;
    }

    public List<TestCase> getTests() {
        return tests;
    }

    public int getTestCount() {
        return tests.size();
    }

    public static int getTotalTestSuitesCreated() {
        return totalTestSuitesCreated;
    }
}

// Класс для представления задачи
class Task {
    private final String description;
    private final TestSuite testSuite;

    public Task(String description, TestSuite testSuite) {
        this.description = description;
        this.testSuite = testSuite;
    }

    public String getDescription() {
        return this.description;
    }

    public TestSuite getTestSuite() {
        return this.testSuite;
    }
}

// Класс для представления решения пользователя
class UserSolution {
    private final String solutionCode;

    public UserSolution(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public String getSolutionCode() {
        return this.solutionCode;
    }
}

// Класс для представления результата выполнения теста
class ExecutionResult {
    private String actualOutput;
    private boolean isPassed;

    public ExecutionResult() {
        this.isPassed = false;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setIsPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    public boolean getIsPassed() {
        return isPassed;
    }
}

// Класс для представления отправки решения
class Submission {
    private final UserSolution solution;
    private final List<ExecutionResult> results;
    private int totalPassed;

    public Submission(UserSolution solution, int testCount) {
        this.solution = solution;
        this.results = new ArrayList<>(testCount);
        for (int i = 0; i < testCount; i++) {
            results.add(new ExecutionResult());
        }
        this.totalPassed = 0;
    }

    public void setTotalPassed(int totalPassed) {
        this.totalPassed = totalPassed;
    }

    public int getTotalPassed() {
        return totalPassed;
    }

    public List<ExecutionResult> getResults() {
        return results;
    }

    public UserSolution getSolution() {
        return this.solution;
    }
}

// Абстрактный класс для сущностей тестов
abstract class TestEntity {
    protected String id;

    public TestEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void execute();
}

// Класс для расширенного теста
class ExtendedTestCase extends TestCase {
    private final String description;

    public ExtendedTestCase(String input, String expected, String description) {
        super(input, expected, new SimpleTestRunner()); // По умолчанию используем SimpleTestRunner
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getInput() {
        return "Description: " + description + ", Input: " + super.getInput();
    }
}

// Класс для подробной задачи
class DetailedTask extends Task {
    private final String details;

    public DetailedTask(String description, TestSuite testSuite, String details) {
        super(description, testSuite);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Details: " + details;
    }
}

// Класс для улучшенного набора тестов
class AdvancedTestSuite extends TestSuite {
    public AdvancedTestSuite(List<TestCase> tests) {
        super(tests);
    }

    @Override
    public int getTestCount() {
        return super.getTestCount() + 1; // Дополнительный бонусный тест
    }
}

// Интерфейс для клонирования
interface ClonableEntity {
    ClonableEntity shallowClone();
    ClonableEntity deepClone();
}

// Класс для клонируемой задачи
class CloneableTask extends Task implements ClonableEntity {
    public CloneableTask(String description, TestSuite testSuite) {
        super(description, testSuite);
    }

    @Override
    public ClonableEntity shallowClone() {
        return this; // Поверхностное клонирование
    }

    @Override
    public ClonableEntity deepClone() {
        return new CloneableTask(this.getDescription(), this.getTestSuite()); // Глубокое клонирование
    }
}

// Класс для виртуальной задачи
class VirtualTask extends Task {
    public VirtualTask(String description, TestSuite testSuite) {
        super(description, testSuite);
    }

    @Override
    public String getDescription() {
        return "Virtual: " + super.getDescription();
    }
}

// Класс для проверки реализации интерфейса
class InterfaceImplementation implements ClonableEntity {
    private final String data;

    public InterfaceImplementation(String data) {
        this.data = data;
    }

    @Override
    public ClonableEntity shallowClone() {
        return new InterfaceImplementation(this.data);
    }

    @Override
    public ClonableEntity deepClone() {
        return new InterfaceImplementation(new String(this.data));
    }

    public String getData() {
        return data;
    }
}

public class Main {

    public static ExecutionResult runTestCase(UserSolution solution, TestCase test) {
        return test.executeTest(); // Используем метод executeTest, который уже использует нужный раннер
    }

    public static Submission checkSolution(UserSolution solution, Task task) {
        Submission submission = new Submission(solution, task.getTestSuite().getTestCount());

        int totalPassed = 0;
        for (int i = 0; i < task.getTestSuite().getTestCount(); i++) {
            TestCase test = task.getTestSuite().getTests().get(i);
            ExecutionResult result = runTestCase(solution, test);
            submission.getResults().set(i, result);
            if (result.getIsPassed()) {
                totalPassed++;
            }
        }

        submission.setTotalPassed(totalPassed);
        return submission;
    }

    public static void executeTestEntity(TestEntity entity) {
        entity.execute(); // Вызов виртуальной функции
    }

    public static void main(String[] args) {
        // Создаем тесты с разными раннерами
        List<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase("input1", "expected1", new SimpleTestRunner()));  // Используем SimpleTestRunner
        tests.add(new TestCase("input2", "input2", new AdvancedTestRunner(3)));  // Используем AdvancedTestRunner с уровнем сложности 3

        TestSuite testSuite = new TestSuite(tests);

        // Создаем задачу с тестами
        Task task = new Task("Task with Multiple Runners", testSuite);

        // Проверяем решение
        UserSolution userSolution = new UserSolution("user solution code");

        Submission submission = checkSolution(userSolution, task);
        System.out.println("Total Passed: " + submission.getTotalPassed());

        // Демонстрация дополнительных классов
        ExtendedTestCase extendedTestCase = new ExtendedTestCase("input1", "expected1", "Test with description");
        System.out.println(extendedTestCase.getInput());

        AdvancedTestSuite advancedSuite = new AdvancedTestSuite(tests);
        System.out.println("Advanced suite test count: " + advancedSuite.getTestCount());

        // Виртуальные функции
        VirtualTask virtualTask = new VirtualTask("Virtual Task", testSuite);
        System.out.println(virtualTask.getDescription());
    }
}
