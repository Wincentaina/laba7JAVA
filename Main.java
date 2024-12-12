import java.util.ArrayList;
import java.util.List;

class TestCase {
    private final String input;
    private final String expected;

    public TestCase(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    public String getInput() {
        return this.input;
    }

    public String getExpected() {
        return this.expected;
    }
}

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

class UserSolution {
    private final String solutionCode;

    public UserSolution(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public String getSolutionCode() {
        return this.solutionCode;
    }
}

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

// Абстрактный класс TestEntity для использования в качестве базового
abstract class TestEntity {
    protected String id;

    public TestEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    // Абстрактный метод
    public abstract void execute();
}

// Производный класс ExtendedTestCase, наследующий TestCase
class ExtendedTestCase extends TestCase {
    private final String description;

    public ExtendedTestCase(String input, String expected, String description) {
        super(input, expected);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Перегрузка метода getInput с вызовом базового метода
    @Override
    public String getInput() {
        return "Description: " + description + ", Input: " + super.getInput();
    }
}

// Производный класс DetailedTask
class DetailedTask extends Task {
    private final String details;

    // Конструктор производного класса с вызовом конструктора базового класса
    public DetailedTask(String description, TestSuite testSuite, String details) {
        super(description, testSuite); // Вызов конструктора базового класса
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

// Производный класс AdvancedTestSuite
class AdvancedTestSuite extends TestSuite {
    public AdvancedTestSuite(List<TestCase> tests) {
        super(tests);
    }

    // Перегрузка метода getTestCount без вызова базового метода
    @Override
    public int getTestCount() {
        return super.getTestCount() + 1; // "Бонусный" тест
    }
}

// Интерфейс ClonableEntity
interface ClonableEntity {
    ClonableEntity shallowClone();
    ClonableEntity deepClone();
}

// Класс Task, реализующий интерфейс ClonableEntity
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

// Производный класс VirtualTask для демонстрации виртуальных функций
class VirtualTask extends Task {
    public VirtualTask(String description, TestSuite testSuite) {
        super(description, testSuite);
    }

    // Виртуальная функция
    @Override
    public String getDescription() {
        return "Virtual: " + super.getDescription();
    }
}

// Класс для проверки интерфейса
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
        return new InterfaceImplementation(new String(this.data)); // Глубокое клонирование
    }

    public String getData() {
        return data;
    }
}

public class Main {
    public static ExecutionResult runTestCase(UserSolution solution, TestCase test) {
        ExecutionResult result = new ExecutionResult();
        result.setActualOutput(test.getInput()); // Симуляция выполнения решения
        result.setIsPassed(result.getActualOutput().equals(test.getExpected()));
        return result;
    }

    public static Submission checkSolution(UserSolution solution, Task task) {
        Submission submission = new Submission(solution, task.getTestSuite().getTestCount());

        int totalPassed = 0;
        for (int i = 0; i < task.getTestSuite().getTestCount(); i++) {
            ExecutionResult result = runTestCase(solution, task.getTestSuite().getTests().get(i));
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
        // Демонстрация производных классов
        ExtendedTestCase extendedTestCase = new ExtendedTestCase("input1", "expected1", "Description1");
        System.out.println(extendedTestCase.getInput());

        List<TestCase> tests = new ArrayList<>();
        tests.add(extendedTestCase);

        AdvancedTestSuite advancedSuite = new AdvancedTestSuite(tests);
        System.out.println("Advanced suite test count: " + advancedSuite.getTestCount());

        // Protected доступ
        TestEntity entity = new TestEntity("Entity1") {
            @Override
            public void execute() {
                System.out.println("Executing entity with ID: " + this.id);
            }
        };
        executeTestEntity(entity);

        // Перегрузка метода
        System.out.println("ExtendedTestCase Input: " + extendedTestCase.getInput());

        // Виртуальные функции
        Task baseTask = new Task("Base Task", advancedSuite);
        VirtualTask virtualTask = new VirtualTask("Derived Task", advancedSuite);

        System.out.println("Base Task Description: " + baseTask.getDescription());
        System.out.println("Virtual Task Description: " + virtualTask.getDescription());

        Task dynamicTask = virtualTask; // Динамический вызов
        System.out.println("Dynamic Task Description: " + dynamicTask.getDescription());

        // Клонирование
        CloneableTask task1 = new CloneableTask("Task 1", advancedSuite);
        CloneableTask shallowClone = (CloneableTask) task1.shallowClone();
        CloneableTask deepClone = (CloneableTask) task1.deepClone();

        System.out.println("Shallow Clone Description: " + shallowClone.getDescription());
        System.out.println("Deep Clone Description: " + deepClone.getDescription());

        // Вызов конструктора базового класса
        DetailedTask detailedTask = new DetailedTask("Task with details", advancedSuite, "These are additional details.");
        System.out.println("Detailed Task Description: " + detailedTask.getDescription());
        System.out.println("Detailed Task Details: " + detailedTask.getDetails());

        // Абстрактный класс
        TestEntity detailedEntity = new TestEntity("AbstractEntity") {
            @Override
            public void execute() {
                System.out.println("Executing abstract entity with ID: " + this.id);
            }
        };
        executeTestEntity(detailedEntity);

        // Интерфейсы
        InterfaceImplementation impl = new InterfaceImplementation("Example Data");
        InterfaceImplementation shallowImpl = (InterfaceImplementation) impl.shallowClone();
        InterfaceImplementation deepImpl = (InterfaceImplementation) impl.deepClone();
        System.out.println("Interface Shallow Clone Data: " + shallowImpl.getData());
        System.out.println("Interface Deep Clone Data: " + deepImpl.getData());
    }
}
