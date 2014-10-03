package DependencyParsing;

import DependencyParsing.doInitialParsing.ParseNewspapers;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by kjoseph on 10/2/14.
 */
public class TryNew {

    public static void main(String[] args) {
        try {
            final List<File> files = new ArrayList<>();
            Files.walkFileTree(Paths.get("/Users/kjoseph/Desktop/tmp_arab/dat/rev_2010-07-01/"), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println(file);
                    if (file.toString().contains(".DS_Store")) {
                        return FileVisitResult.CONTINUE;
                    }
                    files.add(file.toFile());
                    return FileVisitResult.CONTINUE;
                }
            });

            // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
            Properties props = new Properties();
            props.put("annotators", "tokenize, ssplit, pos, lemma,  parse"); //,ner, dcoref");
            props.put("parser.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz");
            props.put("outputDirectory", "/Users/kjoseph/Desktop/tmp_arab/pipeline_out");
            props.put("outputFormat","text");
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


            // create an empty Annotation just with the given text
            pipeline.processFiles(files, 3);

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

