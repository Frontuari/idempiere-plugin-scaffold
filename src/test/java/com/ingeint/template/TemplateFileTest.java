package com.ingeint.template;

import com.ingeint.settings.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateFileTest {

    private static final String TEST_SOURCE_TEMPLATE_PATH = "testPath";
    private static final String TEST_RELATIVE_PATH = "src/package";
    private static final String TEST_FILE_TXT = "file.txt";
    private static final String TEST_PATH = TEST_SOURCE_TEMPLATE_PATH + "/" + TEST_RELATIVE_PATH + "/" + TEST_FILE_TXT;
    private static final String TEST_EXPORT_PATH = "exportPathTest";

    private File sourceFile;
    private TemplateFile templateFile;

    @BeforeEach
    void setUp() {
        sourceFile = new File(TEST_PATH);
        templateFile = new TemplateFile(sourceFile);
        Settings.set(Settings.SOURCE_PATH, TEST_SOURCE_TEMPLATE_PATH);
        Settings.set(Settings.TARGET_PATH, TEST_EXPORT_PATH);
    }

    @Test
    void shouldGetSourceFilePath() {
        assertThat(templateFile.getSourcePath())
                .isEqualTo(Paths.get(TEST_PATH));
    }

    @Test
    void shouldGetTargetPath() {
        Settings.set(Settings.PLUGIN_NAME, "Test Name");
        assertThat(templateFile.getTargetPath())
                .isEqualTo(Paths.get(TEST_EXPORT_PATH, "test-name", TEST_RELATIVE_PATH, TEST_FILE_TXT));
    }

    @Test
    void shouldGetTargetPathWithSpecialCharacters() {
        Settings.set(Settings.PLUGIN_NAME, "Test Name * ' -+ Text");
        assertThat(templateFile.getTargetPath())
                .isEqualTo(Paths.get(TEST_EXPORT_PATH, "test-name-text", TEST_RELATIVE_PATH, TEST_FILE_TXT));
    }
}
