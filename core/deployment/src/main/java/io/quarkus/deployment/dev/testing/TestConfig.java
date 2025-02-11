package io.quarkus.deployment.dev.testing;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * This is used currently only to suppress warnings about unknown properties
 * when the user supplies something like: -Dquarkus.test.profile=someProfile or -Dquarkus.test.native-image-profile=someProfile
 * <p>
 * TODO refactor code to actually use these values
 */
@ConfigRoot
public class TestConfig {

    /**
     * If continuous testing is enabled.
     *
     * The default value is 'paused', which will allow you to start testing
     * from the console or the Dev UI, but will not run tests on startup.
     *
     * If this is set to 'enabled' then testing will start as soon as the
     * application has started.
     *
     * If this is 'disabled' then continuous testing is not enabled, and can't
     * be enabled without restarting the application.
     *
     */
    @ConfigItem(defaultValue = "paused")
    public Mode continuousTesting;

    /**
     * If output from the running tests should be displayed in the console.
     */
    @ConfigItem(defaultValue = "false")
    public boolean displayTestOutput;

    /**
     * Tags that should be included for continuous testing.
     */
    @ConfigItem
    public Optional<List<String>> includeTags;

    /**
     * Tags that should be excluded by default with continuous testing.
     *
     * This is ignored if include-tags has been set.
     *
     * Defaults to 'slow'
     */
    @ConfigItem(defaultValue = "slow")
    public Optional<List<String>> excludeTags;

    /**
     * Tests that should be included for continuous testing. This is a regular expression and
     * is matched against the test class name (not the file name).
     */
    @ConfigItem
    public Optional<String> includePattern;

    /**
     * Tests that should be excluded with continuous testing. This is a regular expression and
     * is matched against the test class name (not the file name).
     *
     * This is ignored if include-pattern has been set.
     *
     */
    @ConfigItem(defaultValue = ".*\\.IT[^.]+|.*IT|.*ITCase")
    public Optional<String> excludePattern;

    /**
     * Disable the testing status/prompt message at the bottom of the console
     * and log these messages to STDOUT instead.
     *
     * Use this option if your terminal does not support ANSI escape sequences.
     *
     * This is deprecated, {@literal quarkus.console.basic} should be used instead.
     */
    @Deprecated
    @ConfigItem
    public Optional<Boolean> basicConsole;

    /**
     * Disable color in the testing status and prompt messages.
     *
     * Use this option if your terminal does not support color.
     *
     * This is deprecated, {@literal quarkus.console.disable-color} should be used instead.
     */
    @ConfigItem
    @Deprecated
    public Optional<Boolean> disableColor;

    /**
     * If test results and status should be displayed in the console.
     *
     * If this is false results can still be viewed in the dev console.
     *
     * This is deprecated, {@literal quarkus.console.enabled} should be used instead.
     */
    @Deprecated
    @ConfigItem
    public Optional<Boolean> console;

    /**
     * Disables the ability to enter input on the console.
     *
     * This is deprecated, {@literal quarkus.console.disable-input} should be used instead.
     */
    @ConfigItem
    @Deprecated
    public Optional<Boolean> disableConsoleInput;

    /**
     * Changes tests to use the 'flat' ClassPath used in Quarkus 1.x versions.
     *
     * This means all Quarkus and test classes are loaded in the same ClassLoader,
     * however it means you cannot use continuous testing.
     *
     * Note that if you find this necessary for your application then you
     * may also have problems running in development mode, which cannot use
     * a flat class path.
     */
    @ConfigItem(defaultValue = "false")
    public boolean flatClassPath;

    /**
     * The profile to use when testing the native image
     */
    @ConfigItem(defaultValue = "prod")
    String nativeImageProfile;

    /**
     * Profile related test settings
     */
    @ConfigItem
    Profile profile;

    /**
     * Container related test settings
     */
    @ConfigItem
    Container container;

    /**
     * Additional launch parameters to be used when Quarkus launches the produced artifact for {@code @QuarkusIntegrationTest}
     * When the artifact is a {@code jar}, this string is passed right after the {@code java} command.
     * When the artifact is a {@code container}, this string is passed right after the {@code docker run} command.
     * When the artifact is a {@code native binary}, this string is passed right after the native binary name.
     */
    @ConfigItem(defaultValue = "")
    Optional<List<String>> argLine;

    /**
     * Used in {@code @QuarkusIntegrationTest} and {@code NativeImageTest} to determine how long the test will wait for the
     * application to launch
     */
    @ConfigItem(defaultValue = "PT1M")
    Duration waitTime;

    /**
     * Configures the hang detection in @QuarkusTest. If no activity happens (i.e. no test callbacks are called) over
     * this period then QuarkusTest will dump all threads stack traces, to help diagnose a potential hang.
     *
     * Note that the initial timeout (before Quarkus has started) will only apply if provided by a system property, as
     * it is not possible to read all config sources until Quarkus has booted.
     */
    @ConfigItem(defaultValue = "10m")
    Duration hangDetectionTimeout;

    /**
     * The type of test to run, this can be either:
     *
     * quarkus-test: Only runs {@code @QuarkusTest} annotated test classes
     * unit: Only runs classes that are not annotated with {@code @QuarkusTest}
     * all: Runs both, running the unit tests first
     *
     */
    @ConfigItem(defaultValue = "all")
    TestType type;

    /**
     * If a class matches this pattern then it will be cloned into the Quarkus ClassLoader even if it
     * is in a parent first artifact.
     *
     * This is important for collections which can contain objects from the Quarkus ClassLoader, but for
     * most parent first classes it will just cause problems.
     */
    @ConfigItem(defaultValue = "java\\..*")
    String classClonePattern;

    /**
     * If this is true then only the tests from the main application module will be run (i.e. the module that is currently
     * running mvn quarkus:dev).
     *
     * If this is false then tests from all dependency modules will be run as well.
     */
    @ConfigItem(defaultValue = "false")
    boolean onlyTestApplicationModule;

    /**
     * Modules that should be included for continuous testing. This is a regular expression and
     * is matched against the module groupId:artifactId.
     */
    @ConfigItem
    public Optional<String> includeModulePattern;

    /**
     * Modules that should be excluded for continuous testing. This is a regular expression and
     * is matched against the module groupId:artifactId.
     *
     * This is ignored if include-module-pattern has been set.
     *
     */
    @ConfigItem
    public Optional<String> excludeModulePattern;

    @ConfigGroup
    public static class Profile {

        /**
         * The profile (dev, test or prod) to use when testing using @QuarkusTest
         */
        @ConfigItem(name = ConfigItem.PARENT, defaultValue = "test")
        String profile;

        /**
         * The tags this profile is associated with.
         * When the {@code quarkus.test.profile.tags} System property is set (its value is a comma separated list of strings)
         * then Quarkus will only execute tests that are annotated with a {@code @TestProfile} that has at least one of the
         * supplied (via the aforementioned system property) tags.
         */
        @ConfigItem(defaultValue = "")
        Optional<List<String>> tags;
    }

    @ConfigGroup
    public static class Container {

        /**
         * Controls the container network to be used when @QuarkusIntegration needs to launch the application in a container.
         * This setting only applies if Quarkus does not need to use a shared network - which is the case if DevServices are
         * used when running the test.
         */
        @ConfigItem
        Optional<String> network;

        /**
         * Set additional ports to be exposed when @QuarkusIntegration needs to launch the application in a container.
         */
        @ConfigItem
        Map<String, String> additionalExposedPorts;
    }

    public enum Mode {
        PAUSED,
        ENABLED,
        DISABLED
    }
}
