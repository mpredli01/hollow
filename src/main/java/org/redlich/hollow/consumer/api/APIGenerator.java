package org.redlich.hollow.consumer.api;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.netflix.hollow.api.codegen.HollowAPIGenerator;
import com.netflix.hollow.core.write.HollowWriteStateEngine;
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper;

public class APIGenerator {

    private static final String GENERATED_API_NAME = "MovieAPI";

    private static final String DATA_MODEL_PACKAGE = "org.redlich.hollow.producer.datamodel";
    private static final String GENERATED_API_PACKAGE = "org.redlich.hollow.consumer.api.generated";
    private static final String ROOT_PROJECT_FOLDER = "/usr/local/publications/hollow";

    private static final String DATA_MODEL_SOURCE_FOLDER = "src/main/java/" + DATA_MODEL_PACKAGE.replace('.','/');
    private static final String GENERATED_API_CODE_FOLDER = "src/main/java/" + GENERATED_API_PACKAGE.replace('.','/');

    private final File projectRootFolder;

    public APIGenerator(File projectRootFolder) {
        this.projectRootFolder = projectRootFolder;
        }

    /**
     * Run this main method to (re)generate the API based on the POJOs defining the data model.
     *
     * If the first arg is populated, it will specify the root project folder.  If not, we will attempt to discover the root project folder.
     */
    public static void main(String args[]) throws ClassNotFoundException,IOException {
        File projectRootFolder;

        if(args.length > 0) {
            projectRootFolder = new File(args[0]);
            }
        else {
            projectRootFolder = new File(ROOT_PROJECT_FOLDER); // findRootProjectFolder();
            }
        APIGenerator generator = new APIGenerator(projectRootFolder);
        generator.generateFiles();
        }

    /**
     * Generate the API.
     */
    public void generateFiles() throws IOException,ClassNotFoundException {
        /// use the following write state engine and object mapper to build schemas
        HollowWriteStateEngine stateEngine = new HollowWriteStateEngine();
        HollowObjectMapper mapper = new HollowObjectMapper(stateEngine);

        /// iterate over all java POJO files describing the data model.
        for(String filename : findProjectFolder(DATA_MODEL_SOURCE_FOLDER).list()) {
            if(filename.endsWith(".java") && !filename.equals("SourceDataRetriever.java")) {
                String discoveredType = filename.substring(0,filename.indexOf(".java"));
                /// initialize the schema for that data model type.
                mapper.initializeTypeState(Class.forName(DATA_MODEL_PACKAGE + "." + discoveredType));
                }
            }

        HollowAPIGenerator codeGenerator = new HollowAPIGenerator(
                GENERATED_API_NAME,
                GENERATED_API_PACKAGE,
                stateEngine);

        File apiCodeFolder = findProjectFolder(GENERATED_API_CODE_FOLDER);

        for(File file : apiCodeFolder.listFiles())
            file.delete();

        codeGenerator.generateFiles(apiCodeFolder);
        }

    /**
     * Find the relative project folder
     */
    private File findProjectFolder(String projectFolder) {
        File file = projectRootFolder;

        for(String string : projectFolder.split("//")) {
            file = new File(file,string);
            }

        return file;
        }

    /**
     * Attempts to find the root project folder.
     * Assumption: A file 'readme', which is in the classpath, is nested somewhere underneath the root project folder.
     */
    private static File findRootProjectFolder() {
        File file = new File(APIGenerator.class.getResource("/readme").getFile());
        file = file.getParentFile();

        while(!containsBuildGradle(file)) {
            file = file.getParentFile();
            }
        return file;
        }

    /**
     * Assumption: The root project folder contains a file called 'build.gradle'
     */
    private static boolean containsBuildGradle(File file) {
        return file.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir,String name) {
                return name.equals("build.gradle");
                }
            }).length > 0;
        }
    }
