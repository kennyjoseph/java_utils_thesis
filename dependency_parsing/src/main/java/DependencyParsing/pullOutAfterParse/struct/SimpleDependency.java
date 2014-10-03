package DependencyParsing.pullOutAfterParse.struct;

/**
 * Created by kjoseph on 10/2/14.
 */
public class SimpleDependency {

    public String docId;
    public String sentenceId;
    public String governingTerm = "";
    public String relationshipTerm = "";
    public String childTerm = "";

    public SimpleDependency(String docString, String sentenceId,
                            String fromToken, String fullVerb, String toToken) {
        docId = docString;
        this.sentenceId = sentenceId;
        governingTerm = fromToken;
        relationshipTerm = fullVerb;
        childTerm = toToken;
    }
}
