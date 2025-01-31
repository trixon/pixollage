/*
 * Copyright 2025 Patrik Karlström <patrik@trixon.se>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.trixon.pixollage.engine;

import java.awt.Color;
import java.io.File;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.PicocliException;
import se.trixon.almond.util.PomInfo;
import se.trixon.pixollage.engine.PixollageCli.VersionProvider;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
@Command(name = "pixollage-cli", versionProvider = VersionProvider.class, mixinStandardHelpOptions = true)
public class PixollageCli implements Runnable {

    @Option(names = {"-c", "--border-color"}, description = "Collage border color", converter = ColorConverter.class)
    Color borderColor = Color.BLACK;
    @Option(names = {"-s", "--border-size"}, description = "Collage border size")
    int borderSize = 1;
    @Option(names = {"-h", "--height"}, description = "Collage height")
    int height = 1080;
    @Option(names = {"-o", "--out"}, description = "Target file", required = true)
    File out;
    @Option(names = {"?", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;
    @Option(names = {"--version"}, versionHelp = true, description = "display version info")
    boolean versionInfoRequested;
    @Option(names = {"-cw", "--width"}, description = "Collage width")
    int width = 1600;
    @Parameters(arity = "1..*", paramLabel = "<photo>", description = "Photos to be included in the collage.")
    private File[] files;

    public static void main(String[] args) {
        var commandLine = new CommandLine(new PixollageCli());
        try {
            commandLine.parseArgs(args);
            if (commandLine.isUsageHelpRequested()) {
                commandLine.usage(System.out);
                return;
            } else if (commandLine.isVersionHelpRequested()) {
                commandLine.printVersionHelp(System.out);
                return;
            }

            int exitCode = commandLine.execute(args);
            System.exit(exitCode);
        } catch (PicocliException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        System.out.println("DO STUFF");
        System.out.println(this.borderColor);
        System.out.println(this.borderSize);
        System.out.println(this.height);
        System.out.println(this.width);
        System.out.println(this.out);
        for (var file : files) {
            System.out.println(file);
        }
    }

    static class ColorConverter implements ITypeConverter<Color> {

        @Override
        public Color convert(String value) throws Exception {
            if (!StringUtils.startsWith(value, "#")) {
                value = "#" + value;
            }

            return Color.decode(value);
        }
    }

    static class VersionProvider implements IVersionProvider {

        @Override
        public String[] getVersion() throws Exception {
            var c = PixollageCli.class;
            var pomInfo = new PomInfo(c, "se.trixon.pixollage", "engine");

            return new String[]{
                "pixollage-cli  version %s".formatted(pomInfo.getVersion()),
                "Copyright (C) 2025 by Patrik Karlström",
                "Web site https://trixon.se",
                """

                  Licensed under the Apache License, Version 2.0 (the "License");
                  You may obtain a copy of the License at

                       http://www.apache.org/licenses/LICENSE-2.0

                  Unless required by applicable law or agreed to in writing, software
                  distributed under the License is distributed on an "AS IS" BASIS,
                  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
                                """
            };
        }

    }
}
